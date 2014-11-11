/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package sserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private ArrayList <ThreadClient> alThread;
    private SocketAddress sa;
    private BufferedOutputStream bos = null;
    private DataInputStream dis = null;
    private BufferedReader br = null;
    
    public ThreadClient(Socket sockClient, ArrayList alThread){
        this.sockClient=sockClient;
        this.alThread=alThread;
        this.sa = this.sockClient.getRemoteSocketAddress();
        System.out.println("masuk di thread");
    }
    /*ThreadClient(Socket sockClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    @Override
    public void run() {
    try {
        
            bos = new BufferedOutputStream(this.sockClient.getOutputStream());
            dis = new DataInputStream(this.sockClient.getInputStream());
            bos.write("...".getBytes());
            bos.flush();
            
            br = new BufferedReader(new InputStreamReader(dis));
            
            String msg;
            while((msg = br.readLine()) != null ){
                this.sa.toString();
                msg = msg + "\r\n";
                this.sendMassage(msg);
                bos.flush();
            }
            
            System.out.println("Koneksi terputus");
            bos.close();
            getSockClient().close();
            this.alThread.removeAll(alThread);
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
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
 
    public void send(String msg) throws IOException{
        this.bos.write(msg.getBytes());
        this.bos.flush();
    }
    public void sendMassage (String msg) throws IOException{
     for(int i=0; i<this.alThread.size(); i++){
         ThreadClient tc = this.alThread.get(i);
         tc.send(msg);
         System.out.println("kirim ke " + i);
     }   
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
    
    
}
