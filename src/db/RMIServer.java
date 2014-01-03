package db;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

	public static void main(String[] args) {
		try {
			DataServerImpl dataServer = new DataServerImpl();
			LocateRegistry.createRegistry(1111);   
			
			Naming.rebind("//localhost:1111/showScore", dataServer);
			System.out.println("server started。。");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
