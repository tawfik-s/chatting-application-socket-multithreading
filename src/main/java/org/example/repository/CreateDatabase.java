package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/chat_app?createDatabaseIfNotExist=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {


            // Create the users table
            String createUserTable = "CREATE TABLE users ("
                    + "user_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "username VARCHAR(255) NOT NULL UNIQUE,"
                    + "password VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(createUserTable);

            // Create the contacts table
            String createContactsTable = "CREATE TABLE contacts ("
                    + "user_id INT NOT NULL,"
                    + "contact_user_id INT NOT NULL,"
                    + "PRIMARY KEY (user_id, contact_user_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (contact_user_id) REFERENCES users(user_id))";
            stmt.executeUpdate(createContactsTable);

            // Create the messages table
            String createMessagesTable = "CREATE TABLE messages ("
                    + "message_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "sender_id INT NOT NULL,"
                    + "receiver_id INT NOT NULL,"
                    + "message_text VARCHAR(255) NOT NULL,"
                    + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (sender_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (receiver_id) REFERENCES users(user_id))";
            stmt.executeUpdate(createMessagesTable);

            // Create the inappropriate_words table
            String createInappropriateWordsTable = "CREATE TABLE inappropriate_words ("
                    + "word_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "word_text VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(createInappropriateWordsTable);

            // Create the user_warnings table
            String createUserWarningsTable = "CREATE TABLE user_warnings ("
                    + "warning_id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "user_id INT NOT NULL,"
                    + "warning_count INT NOT NULL DEFAULT 0,"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id))";
            stmt.executeUpdate(createUserWarningsTable);

            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating database schema: " + e.getMessage());
        }
    }
}
