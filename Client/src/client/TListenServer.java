/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nata
 */
public class TListenServer implements Runnable{
    private Socket SocketClient;
    private BufferedOutputStream bos = null;
    private DataInputStream dis = null;
    private BufferedReader br = null;
    private ObjectOutputStream ous = null;
    private ObjectInputStream ois = null;
    private FileOutputStream fos = null;
    private FileInputStream fis = null;
    
    @Override
    public void run() {
        
        try {
            System.out.println("Masuk ke while :D");
            bos = new BufferedOutputStream(this.getSocketClient().getOutputStream());
            dis = new DataInputStream(this.getSocketClient().getInputStream());
            br = new BufferedReader(new InputStreamReader(dis));
            ous = new ObjectOutputStream(this.getSocketClient().getOutputStream());
            ois = new ObjectInputStream(this.getSocketClient().getInputStream());  
            String msg;
            while((msg = ois.readUTF()) != null){
                if(msg.equals("CON")){
                    System.out.println("Masuk ke while :D");
                }
            }
            
            bos.close();
            getSocketClient().close();
        } catch (IOException ex) {
            Logger.getLogger(TListenServer.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public TListenServer(Socket SocketClient){
        this.SocketClient = SocketClient;
    }

    /**
     * @return the SocketClient
     */
    public Socket getSocketClient() {
        return SocketClient;
    }

    /**
     * @param SocketClient the SocketClient to set
     */
    public void setSocketClient(Socket SocketClient) {
        this.SocketClient = SocketClient;
    }
}
