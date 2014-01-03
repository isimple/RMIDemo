package db;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataServer extends Remote {  
    public void CreateTable() throws RemoteException;  
    public void insert(int id ,String name,double Score) throws RemoteException;  
    public double select(int id)throws RemoteException;  
    public double select (String name)throws RemoteException;  
}  