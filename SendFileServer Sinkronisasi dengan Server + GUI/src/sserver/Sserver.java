/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sserver;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Person.Person;
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
        ArrayList<Socket> sock = new ArrayList<>();
        
        ServerSocket ssServer = new ServerSocket(6060);
        System.out.println("Listening...");
        int ke=0;
        String nama = "";
        while(true){
            //nama = "User " + ++ke;
            Socket sockClient = ssServer.accept();
            System.out.println(sockClient.getInetAddress().toString() + " masuk");
            sock.add(sockClient);
            ThreadClient tc = new ThreadClient(sockClient,alThread, sock);
            alThread.add(tc);
            Thread t = new Thread(tc);
            t.start();
        }
     
        //sockClient.close();
        //ssServer.close();
           
    }
    
}
