package server;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * AuctionServerImpl's Factory.
 * 
 */
public class AuctionServerImplFactory {

    /**
     * Returns an AuctionServerImpl object. AuctionServerImpl class uses 
     * singleton pattern and always the same object is returned.
     * 
     * @return AuctionServerImpl 
     * @throws RemoteException
     * @throws IOException
     */
    public static AuctionServerImpl makeAuctionServerImpl() throws RemoteException, IOException {
        return AuctionServerImpl.getInstance();
    }
}
