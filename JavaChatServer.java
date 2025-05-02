/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package java_chat_server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class JavaChatServer {
    public static ThreadBus threadBus;
    private static int numberClient = 0;
    
    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            server = new ServerSocket(5000);
            threadBus = new ThreadBus();
            System.out.println("Server dang lang nghe.");
            while(true){
                Socket client = server.accept();
                ++numberClient;
                System.out.println("Client " + numberClient + " ket noi.");
                ThreadServer threadServer = new ThreadServer(client, numberClient);
                threadBus.add(threadServer);
                new Thread(threadServer).start();
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
              //return;
        }
        finally {
            try {
                server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
