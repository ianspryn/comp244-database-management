import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class EgccConnector {

    // Object that stores the connection to the database
    Connection conn;
    String username;
    String password;
    // userID of the egccuser once they login
    int userID;
 
    public EgccConnector(String username, String password, String schema) {
	// Fill in code here to initialize conn so it connects to the database
    	try {
			//Get a properties variable so we can pass the username and password to 
			// the database.
			Properties info = new Properties();
			//Type in your ID number instead of the XXXXXX
			String username = "u206894";
			String pass = "Firebird1402";
			String schema = "schema206894";
			//set the username and password appropriately
			info.put( "user", username );
			info.put( "password", pass );
			//connect to the database
			conn = DriverManager.getConnection("jdbc:mysql://COMPDBS300/"+schema, info);
			//if all goes well, this statement should print
			System.out.println("Connection successful!");
    	} catch (SQLException ex) {
    		//if an exception is thrown, display the message so that we know 
    		// what went wrong.
            System.out.print(ex.getMessage());
        }	
	// using the provided parameters
    }

    
    public boolean login(String username, String password) {
	// Fill in code here that checks if the egccuser exists in the database or not.  
	// make sure you retrieve the user ID and store it in the member variable
	//returns true if operation succeeded, false otherwise
	return false;
    }

    public boolean changePassword( String username, String newPassword) {
	// Fill in code here that allows the egccuser to change their password to the new password
	//returns true if operation succeeded, false otherwise
	return false;
    }

    public void viewMyBiddingItems () {
	// Fill in code here that displays all the items (all attributes) that the user has bid on.
    }

    public void viewMyItems() {
	// Fill in code here that displays all the items (all attributes) that the user has put up for auction. 
    }

    public void viewMyPurchases() {
	// Fill in code here that displays all the items (title, description, price, categoryID, dateSold and dateShipped) that the user has purchased
    }

    public void searchByKeyword(String keyword) {
	// fill in code here that displays all the items whose description contains the keyword
    }

    public double viewSellerRating(int itemID) {
	// return the rating of the seller that is selling the item
	return 0.0;
    }
    
    public boolean putItem(String title, double startingBid, String endDate, String categories[]) {
	// put the item specified up for auction 
	//returns true if operation succeeded, false otherwise
	return false;
    }
    
    public boolean shipItem(int itemID) {
	//ships the item specified
	//returns true if operation succeeded, false otherwise
	return false;
    }
    
    public double viewHighestBid (int itemID) {
	//returns the value of the highest bid. You can assume that the trigger is working properly.
    }

    public boolean placeBid(int itemID, double bidValue) {
	// place the bid on the item 
	//returns true if operation succeeded, false otherwise
	return false;
    }

    public boolean rateSeller(int sellerID, double rating) {
	// place a rating on the seller specified
	//returns true if operation succeeded, false otherwise
	return false;
    }	

    public boolean closeAuction (int itemID) {
	// close the auction on the item whose ID is specified
	//returns true if operation succeeded, false otherwise
	return false;
    }

    public boolean closeConnection() { 
	// close the connection here and any preparedstatements you added
	//returns true if operation succeeded, false otherwise
	return true;
    }

}

    
