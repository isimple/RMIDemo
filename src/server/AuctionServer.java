package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Main class responsible for running the server.
 * 
 */
public class AuctionServer {

	public static final int  SERVER_PORT = 8099;
	public static final String SERVER_HOST = "rmi://localhost:" + SERVER_PORT + "/auction";
	
    /**
     *  Gets AuctionServerImpl object, makes a stub and registers it.
     * 
     * @param args system arguments (not used)
     */
    public static void main(String[] args) {
        try {
            AuctionServerImpl servImpl = AuctionServerImplFactory.makeAuctionServerImpl();
            LocateRegistry.createRegistry(SERVER_PORT) ;
            Naming.rebind(SERVER_HOST, servImpl);
            System.out.println("Auction Server ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
