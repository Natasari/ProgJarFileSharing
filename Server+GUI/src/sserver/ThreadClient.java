/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package sserver;

import Person.Person;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nata
 */

public class ThreadClient implements Runnable {
    private Socket sockClient;
    private ArrayList <Socket> sock;
    private ArrayList <ThreadClient> alThread;
    private SocketAddress sa;
    private BufferedOutputStream bos = null;
    private DataInputStream dis = null;
    private BufferedReader br = null;
    private ObjectOutputStream ous = null;
    private ObjectInputStream ois = null;
    private ArrayList <String> list;
    private Person p;
    
    public ThreadClient(Socket sockClient, ArrayList<ThreadClient> t, ArrayList<Socket> s){
        this.sockClient=sockClient;
        this.alThread=t;
        this.sock=s;
        this.sa = this.sockClient.getRemoteSocketAddress();
        
    }
    
    @Override
    public void run() {
    try {
            bos = new BufferedOutputStream(this.getSockClient().getOutputStream());
            dis = new DataInputStream(this.getSockClient().getInputStream());
            br = new BufferedReader(new InputStreamReader(dis));
            
            ous = new ObjectOutputStream(this.getSockClient().getOutputStream());
            ois = new ObjectInputStream(this.getSockClient().getInputStream());            
            String msg;
            p = new Person();
        
        OUTER:
        while ((msg = ois.readUTF()) != null) {
            System.out.println("pesan yang masuk " + msg);
            if (msg.equals("CON") == true) {
                msg = "SUCCESS";
                ous.writeUTF(msg);
                ous.flush();
                ous.reset();
            }
            else if(msg.equals("QUIT")){
                break;
            }
            else if(msg.equals("LIST")){
               this.list();  
            }
        }
            
            //System.out.println(this.getSockClient().getInetAddress().toString() + " Koneksi terputus");
            //System.out.println(this.sa.toString());
            bos.close();
            getSockClient().close();
            this.getAlThread().remove(this);
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(String msg) throws IOException{
        this.bos.write(msg.getBytes());
        this.bos.flush();
    }
    public void list() throws IOException, ClassNotFoundException{
        
        System.out.println("masuk list");
        ThreadClient tc = null;
        p = (Person) ois.readObject();
        for(int i=0; i<this.getAlThread().size(); i++){
            tc  = this.getAlThread().get(i);
             p.setList(tc.getSockClient().getRemoteSocketAddress().toString());
        }
        ous.writeObject(p);
        ous.flush();
        ous.reset();
    }
    
    public void sendMassage (String msg) throws IOException{
     for(int i=0; i<this.getAlThread().size(); i++){
         ThreadClient tc = this.getAlThread().get(i);
         tc.send(msg);
         System.out.println("kirim ke " + i);
         
     }
    }
    
    /**y77yyh
     * @return the sockClient
     */
    public Socket getSockClient() {
        return sockClient;
    }
    
    /**
     * @param sockClient the sockClient to set
     */
    public void setSockClient(Socket sockClient) {
        this.sockClient = sockClient;
    }

    /**
     * @return the alThread
     */
    public ArrayList <ThreadClient> getAlThread() {
        return alThread;
    }

    /**
     * @param alThread the alThread to set
     */
    public void setAlThread(ArrayList <ThreadClient> alThread) {
        this.alThread = alThread;
    }

    /**
     * @return the sock
     */
    public ArrayList <Socket> getSock() {
        return sock;
    }

    /**
     * @param sock the sock to set
     */
    public void setSock(ArrayList <Socket> sock) {
        this.sock = sock;
    }

    private void erase(String inetAddress) {
        
        for(int i=0; i<this.getSock().size(); i++){
         
         //System.out.println("Inet : " + inetAddress.toString());
         //System.out.println("Quit is : " + this.getSock().get(i).getRemoteSocketAddress().toString());
         String msg = this.getSock().get(i).getRemoteSocketAddress().toString();
            try {
                if(inetAddress.equals(this.getSock().get(i).getRemoteSocketAddress().toString())){
                    sendMassage(this.getSock().get(i).getRemoteSocketAddress().toString() + "is quit \r\n");
                    this.getSock().get(i).close();
                } 
                else {
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendObject(Person p) throws IOException {
        ous.writeObject(p);
        ous.flush();
        ous.reset();
    }

  
}
