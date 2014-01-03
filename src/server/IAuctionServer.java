package server;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import client.IAuctionListener;

/**
 * Describes methods provided by AuctionServerImpl. Necessary for RMI. 
 * 
 */
public interface IAuctionServer extends Remote {
    
    /**
     * Creates a new Item object and adds to the list of items.
     * 
     * @param authCode  authorisation code
     * @param ownerName owner name
     * @param itemName  item name
     * @param itemDesc  item description
     * @param startBid  start bid
     * @param maxBid    max bid
     * @param auctionTime auction time
     * @throws RemoteException 
     */
    public void placeItemForBid(String authCode, String ownerName, String itemName, String itemDesc, double startBid, double maxBid, int auctionTime) 
	throws RemoteException;
		
    /**
     * Bids one Item. If an Item is changed, observers are notified.
     * 
     * @param authCode  authorisation code
     * @param bidderName    name of the bidder
     * @param itemName      name of the item
     * @param bid           value of the bid
     * @throws RemoteException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public void bidOnItem(String authCode, String bidderName, String itemName, double bid) throws RemoteException;
	
    /**
     * Returns a list of items.
     * 
     * @param authCode  authorisation code
     * @return ArrayList<Item> list of items
     * @throws RemoteException 
     */
    public ArrayList<Item> getItems(String authCode) throws RemoteException;

    /**
     * Register a client to observe an Item (observer pattern).
     * 
     * @param authCode  authorisation code
     * @param al       client object
     * @param itemName Item name
     * @throws RemoteException 
     */
    public void registerListener(String authCode, IAuctionListener al, String itemName) throws RemoteException;
}
