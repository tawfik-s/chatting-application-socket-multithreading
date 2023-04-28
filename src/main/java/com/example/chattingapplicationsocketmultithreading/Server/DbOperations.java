package com.example.chattingapplicationsocketmultithreading.Server;

import com.example.chattingapplicationsocketmultithreading.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOperations {

    private static volatile Connection connection;

    private DbOperations() {
    }

    public static DbOperations getInstance() throws SQLException {

        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_application?createDatabaseIfNotExist=true", "root", "root");
        }

        return new DbOperations();

    }


    public void createUserTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "password VARCHAR(255)" +
                ")";
        statement.executeUpdate(sql);
    }

    public void createCommonGroupTableIfNotExists() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS common_group (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "sender_id INT," +
                "message TEXT," +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (sender_id) REFERENCES users(id)" +
                ")");

    }

    public List<String> getAllCommonGroupMessages() throws SQLException {
        List<String> messages = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM common_group");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int senderId = resultSet.getInt("sender_id");
            String messageText = resultSet.getString("message");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            String message = getUserById(senderId).getName() + "|" + messageText + "|" + timestamp;
            messages.add(message);
        }

        return messages;
    }

    public void addCommonGroupMessage(int senderId, String messageText) throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO common_group (sender_id, message) VALUES (?, ?)");
        statement.setInt(1, senderId);
        statement.setString(2, messageText);
        statement.executeUpdate();

    }


    public List<String> getChatMessages(int userId1, int userId2) throws SQLException {
        List<String> chatMessages = new ArrayList<>();
        String query = "SELECT chat.id, chat.sender_id, chat.receiver_id, chat.message, chat.timestamp " +
                "FROM chat " +
                "JOIN users sender ON chat.sender_id = sender.id " +
                "JOIN users receiver ON chat.receiver_id = receiver.id " +
                "WHERE (chat.sender_id = ? AND chat.receiver_id = ?) OR " +
                "(chat.sender_id = ? AND chat.receiver_id = ?) " +
                "ORDER BY chat.timestamp ASC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId1);
        statement.setInt(2, userId2);
        statement.setInt(3, userId2);
        statement.setInt(4, userId1);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                String message = resultSet.getString("message");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String chatMessage = getUserById(userId1).getName() + "|" + getUserById(userId2).getName() + "|" + message + "|" + timestamp + "|";
                chatMessages.add(chatMessage);
            }
        }

        return chatMessages;
    }

    public void createChatTable() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS chat (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "sender_id INT," +
                "receiver_id INT," +
                "message TEXT," +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (sender_id) REFERENCES users(id)," +
                "FOREIGN KEY (receiver_id) REFERENCES users(id)" +
                ")";
        statement.executeUpdate(sql);

    }


    public User getUserByName(String name) throws SQLException {
        User user = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
        statement.setString(1, name);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"));
            }
        }
        return user;
    }

    public User getUserById(int id) throws SQLException {
        User user = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setInt(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"));
            }
        }
        return user;
    }

    public User addUser(String name, String password) throws SQLException {
        User user = null;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setString(2, password);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    user = new User(id, name, password);
                }
            }
        }

        return user;
    }

    public void addMessage(int senderId, int receiverId, String message) throws SQLException {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO chat (sender_id, receiver_id, message) VALUES (?, ?, ?)");
        statement.setInt(1, senderId);
        statement.setInt(2, receiverId);
        statement.setString(3, message);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted <= 0) {
            throw new SQLException("Failed to insert new message");
        }
    }

    public List<String> getUserNames() throws SQLException {
        List<String> userNames = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name FROM users");
        while (resultSet.next()) {
            String userName = resultSet.getString("name");
            userNames.add(userName);
        }
        return userNames;
    }

    public List<User> getRecentChatUsers(int currentUserId) throws SQLException {
        List<User> recentChatUsers = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT u.id, u.name, u.password FROM users u WHERE u.id IN (SELECT CASE WHEN c.sender_id = ? THEN c.receiver_id ELSE c.sender_id END FROM chat c WHERE ? IN (c.sender_id, c.receiver_id)) ORDER BY (SELECT MAX(c.timestamp) FROM chat c WHERE u.id IN (c.sender_id, c.receiver_id)) DESC");
        statement.setInt(1, currentUserId);
        statement.setInt(2, currentUserId);

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String userName = resultSet.getString("name");
                String password = resultSet.getString("password");
                User user = new User(userId, userName, password);
                recentChatUsers.add(user);
            }
        }

        return recentChatUsers;
    }

}