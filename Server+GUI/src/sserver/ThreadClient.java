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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private FileOutputStream fos = null;
    private FileInputStream fis = null;
    private ArrayList <String> list = new ArrayList<String>();
    private Person p;
    private String user;
    int FILE_SIZE = 0x396f174;
    String FILE_TO_RECEIVED;
    int current =0;
    
    public ThreadClient(Socket sockClient, ArrayList<ThreadClient> t, ArrayList<Socket> s, String user){
        this.sockClient=sockClient;
        this.alThread=t;
        this.sock=s;
        this.sa = this.sockClient.getRemoteSocketAddress();
        this.user=user;
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
                p.setNama(this.getUser());
                ous.writeObject(p);
                ous.flush();
                ous.reset();
            }
            else if(msg.equals("QUIT")){
                break;
            }
            else if(msg.equals("LIST")){
               this.list();  
            }
            else if(msg.equals("MESS")){
                
                this.create(p.getMybytearray());
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
        
        //System.out.println("masuk list");
        ThreadClient tc = null;
        for(int i=0; i<this.getAlThread().size(); i++){
            tc  = this.getAlThread().get(i);
            //System.out.println(tc.getSockClient().getRemoteSocketAddress().toString());
            list.add(tc.getUser());
        }
        p.setDaftar(list);
        ous.writeObject(p);
        ous.flush();
        //System.out.println("bawah");
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

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    private void create(byte[] mybytearray) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        p = (Person) ois.readObject();
        System.out.println(p.getMybytearray().length);
        byte [] mybyte = new byte[p.getMybytearray().length + 2];
        fos = new FileOutputStream("e:/source-downloaded.txt");
        bos = new BufferedOutputStream(fos);
        mybyte = p.getMybytearray();
        
    bos.write(p.getMybytearray(),0, p.getMybytearray().length);
        bos.flush();
        bos.close();
        fos.close();
        //System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
    }

  
}
