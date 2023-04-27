package org.example.clients;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {
    public static void main(String []args) throws IOException {
        while(true) {
            Scanner scan = new Scanner(System.in);
            String content = scan.nextLine();
            if (content.equals("Exit")) {
                return;
            }
            Socket socket=new Socket("localhost",9091);
            var br = new BufferedReader( new InputStreamReader( socket.getInputStream()) ) ;
            var pw = new PrintWriter(socket.getOutputStream(),true);
            pw.println(content);

            String st = new String(br.readLine());
            System.out.println(st);
            while(true) {
                content = scan.nextLine();
                if (content.equals("Exit")) {
                    socket.close();
                    return;
                }
                pw.println(content);
                st = new String(br.readLine());
                System.out.println(st);
            }

        }
    }
}
