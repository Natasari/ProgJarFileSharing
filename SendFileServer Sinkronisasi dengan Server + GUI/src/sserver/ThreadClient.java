/*/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package sserver;

import Person.Person;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
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
    private String Username;
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
    private ArrayList <String> listFile = new ArrayList<String>();
    private ArrayList <String> listDownload = new ArrayList<String>();
    private ArrayList <String> Penerima = new ArrayList<String>();
    private ArrayList <Byte> listByte = new ArrayList<Byte>();
    private Person p;
    private String user;
    int FILE_SIZE = 0x396f174;
    String FILE_TO_RECEIVED;
    int current =0;
    private File file = null;
    private String FileToSend="d:/";
    private BufferedInputStream bi = null;
    
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
                this.setUsername(ois.readUTF());
                file = new File("d:/" + this.getUsername());
                    if (!file.exists()) {
                        if (file.mkdir()) {
                                System.out.println("Directory is created!");
                        } else {
                                System.out.println("Failed to create directory!");
                        }
                    }
                System.out.println("Username " + this.getUsername());
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
            else if(msg.equals("CHEK")){
                this.listFile();
            }
            else if(msg.equals("DOWN")){
                this.down();
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
        ThreadClient tc = null;
        for(int i=0; i<this.getAlThread().size(); i++){
            tc  = this.getAlThread().get(i);
            list.add(tc.getUsername());
        }
        p.setDaftar(list);
        ous.writeObject(p);
        ous.flush();
        ous.reset();
        list.clear();
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
        Penerima = p.getPenerima();
        for(int i=0; i<Penerima.size(); i++){
            byte [] mybyte = new byte[p.getMybytearray().length + 2];
            fos = new FileOutputStream("d:/" + Penerima.get(i) + "/"+ p.getNamaFile());
            bos = new BufferedOutputStream(fos);
            mybyte = p.getMybytearray();

            bos.write(p.getMybytearray(),0, p.getMybytearray().length);
            bos.flush();
            bos.close();
            fos.close();
        }
        /*System.out.println(p.getMybytearray().length);
        System.out.println("penerima nya " + p.getPenerima().get(i));
        */
        //System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
    }

    /**
     * @return the Username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * @param Username the Username to set
     */
    public void setUsername(String Username) {
        this.Username = Username;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    private void listFile() throws IOException {
        
        File folder = new File("d:/" + this.getUsername());
        File[] listOfFiles = folder.listFiles();
        ThreadClient tc = null;
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
            System.out.println("File " + listOfFiles[i].getName());
            listFile.add(listOfFiles[i].getName());
          } //else if (listOfFiles[i].isDirectory()) {
            //System.out.println("Directory " + listOfFiles[i].getName());
          //}
        }
        p.setDaftarFile(listFile);
        ous.writeObject(p);
        ous.flush();
        ous.reset();
        list.clear();
    }

    /**
     * @return the listFile
     */
    public ArrayList <String> getListFile() {
        return listFile;
    }

    /**
     * @param listFile the listFile to set
     */
    public void setListFile(ArrayList <String> listFile) {
        this.listFile = listFile;
    }

    private void down() throws IOException {
        try { 
            p = (Person) ois.readObject();
            listDownload = p.getFileYangDiunduh();
            for(int i=0; i<listDownload.size(); i++){
                File myFile = new File (FileToSend + this.getUsername()+ "/" + listDownload.get(i));
                byte[] mybyte  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bi = new BufferedInputStream(fis);
                bi.read(mybyte,0,mybyte.length);
                p.setMybytearrayServer(mybyte);
                ous.writeObject(p);
                System.out.println(FileToSend + this.getUsername()+ "/" + listDownload.get(i));
                System.out.println(myFile.length());
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the listDownload
     */
    public ArrayList <String> getListDownload() {
        return listDownload;
    }

    /**
     * @param listDownload the listDownload to set
     */
    public void setListDownload(ArrayList <String> listDownload) {
        this.listDownload = listDownload;
    }

    /**
     * @return the listByte
     */
    public ArrayList <Byte> getListByte() {
        return listByte;
    }

    /**
     * @param listByte the listByte to set
     */
    public void setListByte(ArrayList <Byte> listByte) {
        this.listByte = listByte;
    }

    /**
     * @return the Penerima
     */
    public ArrayList <String> getPenerima() {
        return Penerima;
    }

    /**
     * @param Penerima the Penerima to set
     */
    public void setPenerima(ArrayList <String> Penerima) {
        this.Penerima = Penerima;
    }

  
}
