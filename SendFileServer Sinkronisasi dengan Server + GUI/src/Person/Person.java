/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Nata
 */
public class Person implements Serializable{
    public String nama;
    private ArrayList <String> daftar = new ArrayList<String>();
    private String namaFile;
    private byte [] mybytearray;
    private byte [] mybytearrayServer;
    private ArrayList <String> Penerima = new ArrayList<String>();
    private ArrayList <String> daftarFile = new ArrayList<String>();
    private ArrayList <String> FileYangDiunduh = new ArrayList<String>();
    private ArrayList <Byte> listByte = new ArrayList <Byte>();
    
    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the daftar
     */
    public ArrayList <String> getDaftar() {
        return daftar;
    }

    /**
     * @param daftar the daftar to set
     */
    public void setDaftar(ArrayList <String> daftar) {
        this.daftar = daftar;
    }

    /**
     * @return the namaFile
     */
    public String getNamaFile() {
        return namaFile;
    }

    /**
     * @param namaFile the namaFile to set
     */
    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    /**
     * @return the mybytearray
     */
    public byte[] getMybytearray() {
        return mybytearray;
    }

    /**
     * @param mybytearray the mybytearray to set
     */
    public void setMybytearray(byte[] mybytearray) {
        this.mybytearray = mybytearray;
    }

    /**
     * @return the daftarFile
     */
    public ArrayList <String> getDaftarFile() {
        return daftarFile;
    }

    /**
     * @param daftarFile the daftarFile to set
     */
    public void setDaftarFile(ArrayList <String> daftarFile) {
        this.daftarFile = daftarFile;
    }

    /**
     * @return the FileYangDiunduh
     */
    public ArrayList <String> getFileYangDiunduh() {
        return FileYangDiunduh;
    }

    /**
     * @param FileYangDiunduh the FileYangDiunduh to set
     */
    public void setFileYangDiunduh(ArrayList <String> FileYangDiunduh) {
        this.FileYangDiunduh = FileYangDiunduh;
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
     * @return the mybytearrayServer
     */
    public byte[] getMybytearrayServer() {
        return mybytearrayServer;
    }

    /**
     * @param mybytearrayServer the mybytearrayServer to set
     */
    public void setMybytearrayServer(byte[] mybytearrayServer) {
        this.mybytearrayServer = mybytearrayServer;
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
