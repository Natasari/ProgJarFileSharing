/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Nata
 */
public class Sserver {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<ThreadClient> alThread = new ArrayList<>();
        
        ServerSocket ssServer = new ServerSocket(6060);
        System.out.println("Listening...");
        
        Socket sockClient = ssServer.accept();
        System.out.println(sockClient.getInetAddress().toString() + " masuk");
        
        
        ThreadClient tc;
        tc = new ThreadClient(sockClient,alThread);
        alThread.add(tc);
        Thread t = new Thread(tc);
        t.start();
        
        
        
        sockClient.close();
        ssServer.close();
                
              
                
    }
    
}
