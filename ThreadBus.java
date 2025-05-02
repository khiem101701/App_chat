/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chat_server;

import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThreadBus {
    private List<ThreadServer> listThreadServer;
    private ThreadServer ts;

    public ThreadBus() {
        this.listThreadServer = new ArrayList<>();
    }
    
    public void add(ThreadServer threadServer){
        listThreadServer.add(threadServer);
    }
    
    public void broadcast(String mess, int id){
        for(ThreadServer threadServer : listThreadServer){
            if(threadServer.getId() == id){
                continue;
            }
            else{
                try {
                        threadServer.sendMess("global,."+"Client "+id+": "+mess);
                } 
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public synchronized void sendListOnline(){
        String messListOnline = "updateComboBox";
        for(ThreadServer x : listThreadServer){
            messListOnline += ",." + x.getId();
        }
        JavaChatServer.threadBus.sendMulticart(messListOnline);
    }
    
    public void sendMulticart(String mess){
        for(ThreadServer x : listThreadServer){
            try {
                x.sendMess(mess);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    public void sendSingle(String mess, int id1, int id2){
        for(ThreadServer x : listThreadServer){
            if(x.getId() == id2){
                try {
                    x.sendMess("personTOperson,."+"Client "+id1+"(den ban): "+mess);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
    
    public void deleteThreadServer(int id){
        for(ThreadServer x : listThreadServer){
            if(x.getId() == id){
                listThreadServer.remove(x);
            }
        }
    }
}

