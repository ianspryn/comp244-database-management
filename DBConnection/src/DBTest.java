//import the classes from the JDBC connector to access the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.PreparedStatement;

public class DBTest {
	public static void main (String args[]) {
		//Connection variable we are going to use to connect to the database
		Connection conn = null;
		//In case an exception is thrown, we are putting all our database connector
		//code inside a try-catch block
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
			
			
			
			//PAGE 4
			// declare and create an sql statement so we can run SQL statements:
			Statement stmt = conn.createStatement();
			// Specify the SQL query to run and execute the query. 
			// Store the result in a ResultSet Object
			ResultSet rst = stmt.executeQuery("select * from bank");
			// Get the metadata of the query and store it in a ResultSetMetaData object
			ResultSetMetaData rsmd = rst.getMetaData();
			// Get the number of columns retrieved 
			int numberOfColumns = rsmd.getColumnCount();
			// Go over the columns and print the name of the columns. 
			for (int i =0; i< numberOfColumns; i++ )
				// Note that the columns start at 1
			System.out.print(rsmd.getColumnName(i+1)+ "\t"); //cuz THEY START AT 1 NOT 0 #dum
			System.out.println();
			// Go over the rows in the ResultSet object
			while (rst.next()) {
			String row = "";
			// Use getString if you are reading a varchar. 
			// Again note that the column number starts at 1
			// There are getInt(), getDouble(), getDate() and many other methods to read data.
			row += rst.getString(1) + "\t";
			row += rst.getString(2) + "\t";
			row += rst.getString(3);
			System.out.println(row);	
			}
			// Make sure you close the statement object when you are done.
			stmt.close();

			
			
			
			System.out.println();
			System.out.println();
			System.out.println();
			
			
			//PAGE 5
			conn.setAutoCommit(true);
			// create a PreparedStatement Object
			// Specify the SQL statement to run. 
			// Values inputted by the program/user are replaced by question marks (?)			
			PreparedStatement pstmt = conn.prepareStatement(
			"update account set balance=? where account_no=?");
			// Replace the first question mark with 200
			pstmt.setDouble(1, 200.00);
			//Replace the second question mark with the account number
			pstmt.setString(2, "121234345610");
			//Use executeUpdate() to run an update or an insert query. This returns the number of 
			// rows that were updated/inserted
			//If we want to run a select query, we can use executeQuery.
			int rows = pstmt.executeUpdate();
			//Check if any rows got updated. 
			if (rows > 0)
			System.out.println("Update successfull!");

			pstmt.close();
			
			
			
			
			//close the connection		
			conn.close();
        } catch (SQLException ex) {
		//if an exception is thrown, display the message so that we know 
// what went wrong.
            System.out.print(ex.getMessage());
        }	
	}
}