package server;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import client.IAuctionListener;

/**
 * Implements server methods responsible for auction system.
 * 
 */
final class AuctionServerImpl extends UnicastRemoteObject implements IAuctionServer{
	
	private Observable observable = new Observable(){};
	    
   /**
    * Represents an observer (client).
    */
    private class WrappedObserver implements Observer, Serializable {
        private static final long serialVersionUID = 1L;
        private IAuctionListener ro = null;
        private String itemName = null;

        public WrappedObserver(IAuctionListener ro, String name) {
            this.ro = ro;
            this.itemName = name;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                if(this.itemName.equals(((Item)arg).getItemName())){
                    ro.update((Item)arg);
                }
            } catch (RemoteException err) {
                System.out.println("Remote exception observer:" + this);
            }
        }
    }
    
    private static volatile AuctionServerImpl instance = null;
    private ArrayList<Item> items;
    private static final String authCode = "DFER#CT%$$@GEFXEG";
    
    
    /** 
     * Singleton pattern implementation.
     * source: http://en.wikipedia.org/wiki/Singleton_pattern
     * "should not be used prior to J2SE 5.0"
     * 
     * @return AuctionServerImpl the only object in the application
     * @throws RemoteException 
     * @throws IOException
     */
    public static AuctionServerImpl getInstance() throws RemoteException, IOException {
        if (instance == null) {
            synchronized (AuctionServerImpl.class) {
                if (instance == null) {
                    instance = new AuctionServerImpl();
                }
            }
        }
        return instance;
    }
    
    /**
     * Private constructor of the class to prevent the creation of more than one
     * object (singleton pattern).
     * 
     * @throws RemoteException 
     * @throws IOException
     */
    private AuctionServerImpl() throws RemoteException, IOException {
        super();
        
        items = new ArrayList<>();
        items.add(new Item("Jan Marcinowski", "Budzik", "Dobry budzik.", 20.0, 30.0, 14));
        items.add(new Item("Piotr Piotrowski", "Laptop", "Kiepski laptop z 2007 roku.", 10.0, 30.0, 30));
        items.add(new Item("Krzysztof Jackowski", "Kubek", "Bardzo pojemny pojemnik na ciecze, zele i zole.", 1.0, 30.0, 30));
        
    }
    
    /**
     * Creates a new Item object and adds to the list of items.
     * 
     * @param authCode  authorisation code
     * @param ownerName owner name
     * @param itemName  item name
     * @param itemDesc  iten description
     * @param startBid  start bid
     * @param maxBid    max bid
     * @param auctionTime aution time
     * @throws RemoteException 
     */
    @Override
    public void placeItemForBid(String authCode, String ownerName, String itemName, String itemDesc, double startBid, double maxBid, int auctionTime) throws RemoteException {
        if(!authCode.equals(AuctionServerImpl.authCode)){
            throw new RemoteException("Authorisation error!");
        }        
        checkUniqueName(itemName);
        items.add(new Item(ownerName, itemName, itemDesc, startBid, maxBid, auctionTime));
    }
    
    /**
     * Bids one Item. If an Item is changed, observers are noitfied.
     * 
     * @param authCode  authorisation code
     * @param bidderName    name of the bidder
     * @param itemName      name of the item
     * @param bid           value of the bid
     * @throws RemoteException 
     */
    @Override
    public void bidOnItem(String authCode, String bidderName, String itemName, double bid) throws RemoteException{
        if(!authCode.equals(AuctionServerImpl.authCode)){
            throw new RemoteException("Authorisation error!");
        }
        for(Item item:items){
            if(item.getItemName().matches(itemName)){
                if (item.getCurrentBid() < bid){
                    item.setCurrentBid(bid);
                    item.setWinnerName(bidderName);
                    if(item.getMaxBid() <= bid){
                        item.setAuctionTime(0);
                    }
                    setChanged();
                    observable.notifyObservers(item);
                }
                break;
            }
        }
    }
    
    /**
     * Returns a list of items.
     * 
     * @param authCode  authorisation code
     * @return ArrayList<Item> list of items
     * @throws RemoteException 
     */
    @Override
    public ArrayList<Item> getItems(String authCode) throws RemoteException {
        if(!authCode.equals(AuctionServerImpl.authCode)){
            throw new RemoteException("Authorisation error!");
        }
        return items;
    }
    
    /**
     * Register a client to observe an Item (observer pattern).
     * 
     * @param authCode  authorisation code
     * @param al       client object
     * @param itemName Item name
     * @throws RemoteException 
     */
    @Override
    public void registerListener(String authCode, IAuctionListener al, String itemName) throws RemoteException {
        if(!authCode.equals(AuctionServerImpl.authCode)){
            throw new RemoteException("Authorisation error!");
        }
        System.out.println("Registered new client for: "+itemName);
        WrappedObserver mo = new WrappedObserver(al, itemName);
        observable.addObserver(mo);
        setChanged();
        observable.notifyObservers(items.get(0));
    }
    
    private void setChanged(){
    	try {
			Method method = Observable.class.getDeclaredMethod("setChanged");
			method.setAccessible(true);
			method.invoke(observable);
		} catch (Exception e) {
			//cannot reach
			e.printStackTrace();
		}
    }
    
    /**
     * Checks if there is an item with the same itemName. If so, a
     * RemoteException is throwed.
     * 
     * @param name  the name to be check
     * @throws RemoteException 
     */
    public void checkUniqueName(String name) throws RemoteException {
        for(Item item:items){
            if(item.getItemName().equals(name)){
                throw new RemoteException("The name is not unique!");
            }
        }
    }

}
