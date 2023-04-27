package org.example.Server;

import org.example.entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.example.Server.MessagingService.sendToAll;
import static org.example.Server.MessagingService.sendToUser;
import static org.example.Server.Server.*;

public class MessagingThread extends Thread {

    User user;
    BufferedReader input;
    PrintWriter output;

    public MessagingThread(Socket client) throws Exception {
        System.out.println("inside messaging thread constructor");
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);

        String data = input.readLine();
        List<String> tokens = Arrays.stream(data.split("\\|")).toList();
        if (tokens.size() != 3) {
            output.println("wrong message format");
            input.close();
            output.close();
            this.interrupt();
        }
        User u;
        if (tokens.get(0).equals("Login")) {
            String name = tokens.get(1);
            String password = tokens.get(2);
            u = dbOperations.getUserByName(name);
            if (u == null || !u.getPassword().equals(password)) {
                output.println("401|wrong in user name or password");
                input.close();
                output.close();
                this.interrupt();
            } else {
                user = u;
                users.add(user.getName());
                output.println("done");
            }
        } else if (tokens.get(0).equals("Register")) {
            String name = tokens.get(1);
            String password = tokens.get(2);
            u = dbOperations.getUserByName(name);
            if (u != null || password.length() == 1) {
                output.println("fail|user is already exit choose another handle");
                input.close();
                output.close();
                this.interrupt();
            } else {
                user = dbOperations.addUser(name, password);
                users.add(user.getName());
                output.println("done");
            }
        } else {
            output.println("fail");
            input.close();
            output.close();
            this.interrupt();
        }

        // DbOperations.addUserInDB(user);
    }

    public void sendMessage(String chatUser, String msg) {
        output.println(chatUser + "|" + msg);
    }

    public void sendCast(String chatUser,String msg){
        output.println("BroadCast"+"|"+chatUser+"|"+msg);
    }


    public User getUser() {
        return user;
    }



    @Override
    public void run() {
        String line;
        try {
            while (true) {
                line = input.readLine();
                List<String> tokens = Arrays.stream(line.split("\\|")).toList();

                if (line.equals("exit")) {
                    clients.remove(this);
                    users.remove(user.getName());
                    break;
                } else if (tokens.get(0).equals("LoadUsers")) {
                    var names = dbOperations.getUserNames();
                    StringBuffer res = new StringBuffer();
                    for (var x : names) {
                        res.append(x + '|');
                    }
                    output.println(res.toString());
                } else if (tokens.get(0).equals("LastChats")) {
                    List<User> recentChatUsers = dbOperations.getRecentChatUsers(user.getId());
                    StringBuffer res = new StringBuffer();
                    for (var x : recentChatUsers) {
                        res.append(x.getName());
                        res.append("|");
                    }
                    output.println(res.toString());
                } else if (tokens.get(0).equals("Load")) {
                    var userName = tokens.get(1);
                    User receiver = dbOperations.getUserByName(userName);
                    var messages = dbOperations.getChatMessages(user.getId(), receiver.getId());
                    StringBuilder stringBuffer = new StringBuilder();
                    for (var message : messages) {
                        stringBuffer.append(message);
                        stringBuffer.append('|');
                    }
                    System.out.println(stringBuffer.toString());
                    output.println(stringBuffer.toString());
                } else if (tokens.get(0).equals("BroadCast")) {
                    String message=tokens.get(1);
                    dbOperations.addCommonGroupMessage(user.getId(),message);
                    sendToAll(user.getName(),message);
                }else if (tokens.get(0).equals("BroadCastLoad")) {
                    var messages = dbOperations.getAllCommonGroupMessages();
                    StringBuilder stringBuffer = new StringBuilder();
                    for (var message : messages) {
                        stringBuffer.append(message);
                        stringBuffer.append('|');
                    }
                    System.out.println(stringBuffer.toString());
                    output.println(stringBuffer.toString());
                }else if(tokens.get(0).equals("SendMessage")){
                    String userName = tokens.get(1);
                    String message = tokens.get(2);
                    User receiver = dbOperations.getUserByName(userName);
                    if (receiver == null) {
                        output.println("404");
                        continue;
                    }
                    dbOperations.addMessage(user.getId(), receiver.getId(), message);
                    sendToUser(user.getName(), userName, message);
                    sendMessage(userName,message);
                }else{
                    output.println("404");
                }

            }
        }catch (Exception ex){
            output.println("LoggedOut");
            System.out.println(ex);
        }

    }
}