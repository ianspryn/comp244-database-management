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
			//Get a properties variable so we can pass the username and password to the database.
			Properties info = new Properties();
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
    		//if an exception is thrown, display the message so that we know what went wrong.
            System.out.print(ex.getMessage());
        }	
    }

    
    // Fill in code here that checks if the egccuser exists in the database or not.  
    // make sure you retrieve the user ID and store it in the member variable
    //returns true if operation succeeded, false otherwise
    public boolean login(String username, String password) {
    	try {
    		boolean userExists = true;
    		//prepare SQL statement
    		PreparedStatement stmt = conn.prepareStatement("select username, password from egccUser where username = '" + username
    				+ "' and password = '" + password+"'");
    		//run SQL statement
    		ResultSet rst = stmt.executeQuery();
    		if (!rst.isBeforeFirst() ) {    
    		    userExists = false; //all output was null. Username and password do not match anything
    		} else {
    			ResultSet ID = stmt.executeQuery("select userID from egccuser where username = '"+username+"'");
    				ID.next();
        			userID = ID.getInt("userID");
    		}
    		stmt.close();	
    		return userExists;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }

    // Fill in code here that allows the egccuser to change their password to the new password
    //returns true if operation succeeded, false otherwise
    public boolean changePassword( String username, String newPassword) {
    	try {
    		boolean updateSuccessful = false;
    		//prepare SQL statement
    		PreparedStatement pstmt = conn.prepareStatement(
			"update egccUser set password=? where username='" + username + "'");
			//insert user-specified input into query
			pstmt.setString(1, newPassword);
			//run SQL statement and get the number of rows effected
			int rows = pstmt.executeUpdate();
			//Check if any rows got updated. 
			if (rows > 0) {
				conn.commit();
				updateSuccessful = true;
			}
			pstmt.close();
			return updateSuccessful;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }

    // Fill in code here that displays all the items (all attributes) that the user has bid on.
    public void viewMyBiddingItems () {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
			ResultSet rst = stmt.executeQuery("select * from Bid where BuyerID = " + userID);
			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			//output the column names
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			//output each row
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Ian
    // Fill in code here that displays all the items (all attributes) that the user has put up for auction. 
    public void viewMyItems() {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
			ResultSet rst = stmt.executeQuery("select * from item where sellerID = '" + Integer.toString(userID) + "'");
			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			//output the column names
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			//output each row
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    //Nate
    // Fill in code here that displays all the items (title, description, price, categoryID, dateSold and dateShipped) that the user has purchased
    public void viewMyPurchases() {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
    		ResultSet rst = stmt.executeQuery("select * from item where itemID in ( select itemID from purchase where buyerID = " + Integer.toString(userID) + ")");

			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			//output the column names
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			//output each row
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Ian
    // fill in code here that displays all the items whose description contains the keyword
    public void searchByKeyword(String keyword) {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
    		ResultSet rst = stmt.executeQuery("select * from item where description like '%" + keyword + "%'");

			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			
			//output the column names
			for (int i = 1; i <= numberOfColumns; i++ ) {
				System.out.print(rsmd.getColumnName(i)+ "\t");
			}
			System.out.println();
			
			//output each row
			while (rst.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.print(rst.getString(i) + "\t");
				}
				System.out.println();
			}
			stmt.close();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			rst.next();
			row = rst.getDouble(1);	
			// Make sure you close the statement object when you are done.
			stmt.close();
			return row;	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0.0;
    }

    //Ian
    // put the item specified up for auction 
    //returns true if operation succeeded, false otherwise
    public boolean putItem(String title, double startingBid, String endDate, String categories[]) {
    	try {
    		boolean categoryDoesNotExist = false;
    		Statement stmt = conn.createStatement();
    		ResultSet rst = stmt.executeQuery("select ItemID from item");
    		int itemID = -1;
    		rst.next();
    		int lastItemID = rst.getInt("ItemID");
    		if (lastItemID > itemID) {
    			itemID = lastItemID;
    		}
    		while (rst.next()) {
    			lastItemID = rst.getInt("ItemID");
        		if (lastItemID > itemID) {
        			itemID = lastItemID;
        		}
    		}
    		itemID++;
    		stmt.close();
    		
    		PreparedStatement pstmt = conn.prepareStatement("insert into item values (?, '?', '?', ?, ?, '?', ?, '?'");
    		pstmt.setInt(1, itemID);
    		pstmt.setString(2, title);
    		pstmt.setString(3, title);
    		pstmt.setDouble(4, startingBid);
    		pstmt.setDouble(5, startingBid);
    		pstmt.setString(6, endDate);
    		pstmt.setInt(7, userID);
    		pstmt.setString(8, "open");
    		
			//run SQL statement and get the number of rows effected
    		int rows = pstmt.executeUpdate();
    		
    		for (int i = 0; i < categories.length; i++) {
    			pstmt = conn.prepareStatement("insert into itemcategory values(itemID, (select ID from category where descrption = '" + categories[i] + "')");
    			int rows2 = pstmt.executeUpdate();
    			if (rows2 < 1) {
    				categoryDoesNotExist = true;
    			}
    		}
    		pstmt.close();
    		//Check if any rows got updated. 
    		if (rows > 0) {
    			return true && !categoryDoesNotExist;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }

    //Nate
    //ships the item specified
    //returns true if operation succeeded, false otherwise
    public boolean shipItem(int itemID) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("update item set status = 'shipped' where ItemID = " + itemID);
    		PreparedStatement stmt2 = conn.prepareStatement("update purchase set dateSold = CURDATE() where ItemID = " + itemID);
    		
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			int numRowsEffectedItem = stmt.executeUpdate();
			int numRowsEffectedPurchase = stmt2.executeUpdate();			
			
			// Make sure you close the statement object when you are done.
			stmt.close();
			stmt2.close();
			if (numRowsEffectedItem > 0 && numRowsEffectedPurchase > 0) {
				conn.commit();
				return true;
			}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    //Ian
    //returns the value of the highest bid. You can assume that the trigger is working properly.
    public double viewHighestBid (int itemID) {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
    		ResultSet rst = stmt.executeQuery("select max(currentBid) from bid where itemID = " + itemID);    		
    		rst.next(); //move past column name
    		double maxBid = rst.getDouble("max(currentBid)"); //get maximum current bid
    		stmt.close();
    		return maxBid;
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
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
    			int numRowsEffectedBid = stmt.executeUpdate();
    			if(numRowsEffectedBid > 0){
    				stmt.close();
        			conn.commit();
        			return true;
    			}else{
    				stmt.close();
    				return false;
    			}
    		}else{
    			System.out.println("Your bid isn't high enough");
    			stmt.close();
    			return false;
    		}
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

    //Ian
    // place a rating on the seller specified
    //returns true if operation succeeded, false otherwise
    public boolean rateSeller(int sellerID, double rating) {
    	try {
    		Statement stmt = conn.createStatement();
			//prepare and run SQL statement
    		ResultSet rst = stmt.executeQuery("select buyerID from sellerRating where buyerID = " + userID);
    		stmt.close();
    		if (rst.isBeforeFirst()) {
    			return false; //the user has already rated this person.
    		}    		
    		
    		//place the rating
    		PreparedStatement pstmt = conn.prepareStatement("insert into item values (?, ?, ?, '?', sysdate()");
    		pstmt.setInt(1, sellerID);
    		pstmt.setInt(2, userID);
    		pstmt.setDouble(3, rating);
    		pstmt.setString(4, "Awful. Terrible. 10/10 would not buy again.");
    		
			//run SQL statement and get the number of rows effected
    		int rows = pstmt.executeUpdate();
    		pstmt.close();
    		//Check if any rows got updated. 
    		if (rows > 0) {
    			return true;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }	

    //Nate
    // close the auction on the item whose ID is specified
    //returns true if operation succeeded, false otherwise
    public boolean closeAuction (int itemID) {
    	try {
    		PreparedStatement stmt = conn.prepareStatement("update item set status = 'closed' where ItemID = "+itemID);
    		PreparedStatement stmt2 = conn.prepareStatement("insert into purchase values ("+userID+", "+itemID+", (select highestBid from item where ItemID = "+itemID+"), CURDATE(), null)");
			
    		// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
    		int numRowsEffectedItem = stmt.executeUpdate();
    		int numRowsEffectedPurchase = stmt2.executeUpdate();
    		
    		if(numRowsEffectedItem > 0 && numRowsEffectedPurchase > 0){
    			stmt.close();
        		stmt2.close();
        		return true;  		
    		}else{
    			stmt.close();
        		stmt2.close();
        		return false;  
    		}
    		
			
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }

    //Ian
    // close the connection here and any preparedstatements you added
    //returns true if operation succeeded, false otherwise
    public boolean closeConnection() {	
		try{
			conn.close();
	    	return true;
		}
    	catch(SQLException e){
    		System.out.println(e.getMessage());
    		return false;
    	}
		catch(Exception e2){
			System.out.println(e2.getMessage());
			return false;
		}
    }

}

    
