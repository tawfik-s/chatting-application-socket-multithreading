package com.example.chattingapplicationsocketmultithreading.Server;


import static com.example.chattingapplicationsocketmultithreading.Server.Server.clients;

public class MessagingService {
    public static void sendToAll(String user, String message) {

        for (MessagingThread c : clients) {
            if (!c.getUser().getName().equals(user)) {
                c.sendCast(user, message);
            }else{
                c.sendCast(user, message);
            }
        }
    }

    public static void sendToUser(String sender,String receiver,String message){

        clients.forEach(c->{
            if(c.getUser().getName().equals(receiver)){
                c.sendMessage(sender,message);
            }
        });

    }
}
