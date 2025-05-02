/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;

/**
 *
 * @author Admin
 */
public class ThreadServer implements Runnable{
    private Socket client;
    private int id;
    private BufferedReader is;
    private BufferedWriter os;
    //private ThreadBus threadBus;
            

    public ThreadServer(Socket client, int id) {
        this.client = client;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public void run() {
        try{
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            sendMess("setId" + ",." + id);
            JavaChatServer.threadBus.sendListOnline();
            while(true){
                String mess = is.readLine();
                if(mess.isEmpty()) break;
                String[] messSplit = mess.split(",.");
                System.out.println(mess);
                if(messSplit[0].equals("global")){
                    JavaChatServer.threadBus.broadcast(messSplit[1], Integer.parseInt(messSplit[2]));
                }
                if(messSplit[0].equals("personTOperson")){
                    JavaChatServer.threadBus.sendSingle(messSplit[1],Integer.parseInt(messSplit[2]),Integer.parseInt(messSplit[3]));
                }
            }
        }
        catch(Exception e){
            JavaChatServer.threadBus.deleteThreadServer(id);
            JavaChatServer.threadBus.sendListOnline();
        }
    }
    
    public void sendMess(String mess) throws IOException{
        os.write(mess);
        os.newLine();
        os.flush();
    }
}
