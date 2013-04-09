

import java.sql.*;

public class droptable {

static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

public static void main(String[] args) {
 try {

     // Load the driver. This creates an instance of the driver
	    	// and calls the registerDriver method to make Oracle Thin
	    	// driver available to clients.
     Class.forName("oracle.jdbc.driver.OracleDriver");
     
     		//String user = "yliu63";	// For example, "jsmith"
     		//String passwd = "001083962";	// Your 9 digit student ID number

	    	String user = "yliu63";	// For example, "jsmith"
	    	String passwd = "001083962";	// Your 9 digit student ID number

     Connection conn = null;
     Statement stmt = null;
     

     try {
     		// Get a connection from the first driver in the
				// DriverManager list that recognizes the URL jdbcURL

				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				// Create a statement object that will be sending your
				// SQL statements to the DBMS

				stmt = conn.createStatement();
				
			
				stmt.executeUpdate("drop table enroll");
				stmt.executeUpdate("drop table c_topic");
				stmt.executeUpdate("drop table q_topic");
				stmt.executeUpdate("drop table hw_ques");
				stmt.executeUpdate("drop table hw_mem");

				stmt.executeUpdate("drop table stuans");

				stmt.executeUpdate("drop table answer");
				stmt.executeUpdate("drop table question");
				stmt.executeUpdate("drop table report");
				stmt.executeUpdate("drop table homework");
				stmt.executeUpdate("drop table course");
				stmt.executeUpdate("drop table member");
				stmt.executeUpdate("drop table topic");
				
			
				
				
				
				//----------------------------------------------------------------------------------------------
			
				

				// Now rs contains the rows of coffees and prices from
				// the COFFEES table. To access the data, use the method
				// NEXT to access all rows in rs, one row at a time

				
     } finally {
         
         close(stmt);
         close(conn);
     }
 } catch(Throwable oops) {
     oops.printStackTrace();
 }
}

static void close(Connection conn) {
 if(conn != null) {
     try { conn.close(); } catch(Throwable whatever) {}
 }
}

static void close(Statement st) {
 if(st != null) {
     try { st.close(); } catch(Throwable whatever) {}
 }
}

static void close(ResultSet rs) {
 if(rs != null) {
     try { rs.close(); } catch(Throwable whatever) {}
 }
}
}

