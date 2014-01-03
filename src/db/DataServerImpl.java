package db;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class DataServerImpl extends UnicastRemoteObject implements DataServer {

	static  {
		DBManager.init();
	}
	public DataServerImpl() throws RemoteException {
		super();
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void CreateTable() throws RemoteException {
		DBManager.CreateTable();
	}

	@Override
	public void insert(int id, String name, double score)
			throws RemoteException {
		DBManager.insert(id, name, score);
	}

	@Override
	public double select(int id) throws RemoteException {
		// TODO Auto-generated method stub
		double score = DBManager.select(id);
		return score;
	}

	@Override
	public double select(String name) throws RemoteException {
		double score = DBManager.select(name);
		return score;
	}

}
