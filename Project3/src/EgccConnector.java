import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class EgccConnector {

    // Object that stores the connection to the database
    Connection conn;
    String username;
    String password;
    String schema;
    // userID of the egccuser once they login
    int userID;
 
    // Fill in code here to initialize conn so it connects to the database
    // using the provided parameters
    public EgccConnector(String username, String password, String schema) {
    	try {
			//Get a properties variable so we can pass the username and password to 
			// the database.
			Properties info = new Properties();
			//Type in your ID number instead of the XXXXXX
			this.username = username;
			this.password = password;
			this.schema = schema;
			//set the username and password appropriately
			info.put("user", username);
			info.put("password", password);
			//connect to the database
			conn = DriverManager.getConnection("jdbc:mysql://COMPDBS300/"+schema, info);
			//if all goes well, this statement should print
			System.out.println("Connection successful!");
    	} catch (SQLException ex) {
    		//if an exception is thrown, display the message so that we know 
    		// what went wrong.
            System.out.print(ex.getMessage());
        }	
    }

    
    // Fill in code here that checks if the egccuser exists in the database or not.  
    // make sure you retrieve the user ID and store it in the member variable
    //returns true if operation succeeded, false otherwise
    public boolean login(String username, String password) {
    	try {
    		boolean userExists = true;
    		// declare and create an sql statement so we can run SQL statements:
    		PreparedStatement stmt = conn.prepareStatement("select username, password from egccUser where username = '" + username
    				+ "' and password = '" + password+"'");
    		// Specify the SQL query to run and execute the query. 
    		// Store the result in a ResultSet Object
    		ResultSet rst = stmt.executeQuery();

    		if (!rst.isBeforeFirst() ) {    
    		    userExists = false;
    		}
    		
    		// Make sure you close the statement object when you are done.
    		stmt.close();	
    		
    		return userExists;
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return false;
    }

    // Fill in code here that allows the egccuser to change their password to the new password
    //returns true if operation succeeded, false otherwise
    public boolean changePassword( String username, String newPassword) {
    	try {
    		boolean updateSuccessful = false;
    		PreparedStatement pstmt = conn.prepareStatement(
			"update egccUser set password=? where username='" + username + "'");
			//Replace the 1st question mark with the new password
			pstmt.setString(1, newPassword);
			//Use executeUpdate() to run an update or an insert query. This returns the number of 
			// rows that were updated/inserted
			//If we want to run a select query, we can use executeQuery.
			int rows = pstmt.executeUpdate();
			//Check if any rows got updated. 
			if (rows > 0) {
				updateSuccessful = true;
			}

			pstmt.close();
		return updateSuccessful;
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return false;
    }

    // Fill in code here that displays all the items (all attributes) that the user has bid on.
    public void viewMyBiddingItems () {
    	try {
    		Statement stmt = conn.createStatement();
			// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			ResultSet rst = stmt.executeQuery("select * from Bid where BuyerID = '" + Integer.toString(userID) + "'");
			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			// Make sure you close the statement object when you are done.
			stmt.close();
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    //Ian
    // Fill in code here that displays all the items (all attributes) that the user has put up for auction. 
    public void viewMyItems() {
    	try {
    		Statement stmt = conn.createStatement();
			// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			ResultSet rst = stmt.executeQuery("select * from item where sellerID = '" + Integer.toString(userID) + "'");
			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			// Make sure you close the statement object when you are done.
			stmt.close();
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }


    //Nate
    // Fill in code here that displays all the items (title, description, price, categoryID, dateSold and dateShipped) that the user has purchased
    public void viewMyPurchases() {
    	try {
    		Statement stmt = conn.createStatement();
			// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
    		ResultSet rst = stmt.executeQuery("select * from item where itemID in ( select itemID from purchase where buyerID = " + Integer.toString(userID) + ")");

			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			// Make sure you close the statement object when you are done.
			stmt.close();
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    //Ian
    // fill in code here that displays all the items whose description contains the keyword
    public void searchByKeyword(String keyword) {
    	
    }

    //Nate
    // return the rating of the seller that is selling the item
    public double viewSellerRating(int itemID) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("select avg(rating) from sellerRating join item on sellerRating.sellerID = item.SellerID where item.ItemID = " + itemID);
			
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			ResultSet rst = stmt.executeQuery();
			double row = 0.0;
			while(rst.next()){
				row = rst.getDouble(1);	
			}
			// Make sure you close the statement object when you are done.
			stmt.close();
			return row;	
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0.0;
		}
    }

    //Ian
    // put the item specified up for auction 
    //returns true if operation succeeded, false otherwise
    public boolean putItem(String title, double startingBid, String endDate, String categories[]) {
    	return false;
    }

    //Nate
    //ships the item specified
    //returns true if operation succeeded, false otherwise
    public boolean shipItem(int itemID) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("update item set status = 'shipped' where ItemID = " + itemID);
    		PreparedStatement stmt2 = conn.prepareStatement("update purchase set dateSold = current_date where ItemID = " + itemID);
    		
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			double numRowsEffectedItem = stmt.executeUpdate();
			double numRowsEffectedPurchase = stmt2.executeUpdate();			
			
			// Make sure you close the statement object when you are done.
			stmt.close();
			stmt2.close();
			return true;
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
    }
    
    //Ian
    //returns the value of the highest bid. You can assume that the trigger is working properly.
    public double viewHighestBid (int itemID) {
    	return 0.0;
    }

    //Nate
    // place the bid on the item 
    //returns true if operation succeeded, false otherwise
    public boolean placeBid(int itemID, double bidValue) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("insert into Bid values ("+userID+", "+itemID+", CURDATE(), CURTIME(), "+bidValue+")");
			
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
    		double highestBid = viewHighestBid(itemID);
    		if(bidValue > highestBid){
    			double numRowsEffectedBid = stmt.executeUpdate();
    			stmt.close();
    			return true;
    		}else{
    			System.out.println("Your bid isn't high enough");
    			stmt.close();
    			return false;
    		}
			
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
    }

    //Ian
    // place a rating on the seller specified
    //returns true if operation succeeded, false otherwise
    public boolean rateSeller(int sellerID, double rating) {
    	return false;
    }	

    //Nate
    // close the auction on the item whose ID is specified
    //returns true if operation succeeded, false otherwise
    public boolean closeAuction (int itemID) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("update item set status = 'closed' where ItemID = "+itemID);
    		PreparedStatement stmt2 = conn.prepareStatement("insert into purchase values ("+userID+", "+itemID+", (select highestBid from item where ItemID = "+itemID+"), current_date, null)");
			
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
    		double numRowsEffectedItem = stmt.executeUpdate();
    		double numRowsEffectedPurchase = stmt2.executeUpdate();
    		stmt.close();
    		stmt2.close();
    		return true;  		
			
    	} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
    }

    //Ian
    // close the connection here and any preparedstatements you added
    //returns true if operation succeeded, false otherwise
    public boolean closeConnection() {	
		try{
			conn.close();
	    	return true;
		}
    	catch(SQLException e1){
    		System.out.println(e1.getMessage());
    		return false;
    	}
		catch(Exception e2){
			System.out.println(e2.getMessage());
			return false;
		}
    }

}

    
