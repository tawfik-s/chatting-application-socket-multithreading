package com.example.chattingapplicationsocketmultithreading.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.System.out;

public class Server {

    public  static ArrayList<String> users = new ArrayList<>();
    public static ArrayList<MessagingThread> clients = new ArrayList<>();

    public static DbOperations dbOperations;

    public static void main(String[]args) throws Exception {
      ServerSocket server = new ServerSocket(9091, 10);
        dbOperations=DbOperations.getInstance();
        out.println("Now Server Is Running");


        dbOperations.createUserTable();
        dbOperations.createChatTable();
        dbOperations.createCommonGroupTableIfNotExists();

        int clientsNmber =0;

        while (true) {
            Socket client = server.accept();
            MessagingThread thread = new MessagingThread(client);
            clients.add(thread);
            thread.start();
            System.out.println("CLINT: " + (++clientsNmber) + " is running");
        }
    }




}
