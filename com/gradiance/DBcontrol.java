// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.

// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

//Note: If you run the program more than once, it will not be able to create the COFFEES table anew after the first run; 
//	you can remove the COFFEES tables between the runs by typing "drop table COFFEES;" in SQL*Plus.

package com.gradiance;
import java.sql.*;

public class DBcontrol {

    static final String jdbcURL 
	//= "jdbc:oracle:thin:@//orca.csc.ncsu.edu:1521/ORCL.WORLD";
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:ORCL";
    static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;
    public static ResultSetMetaData meta = null;

    DBcontrol(){
    
        try {

            // Load the driver. This creates an instance of the driver
	    // and calls the registerDriver method to make Oracle Thin
	    // driver available to clients.

        Class.forName("oracle.jdbc.driver.OracleDriver");

	    String user = "yliu63";	// For example, "jsmith"
	    String passwd = "001083962";	// Your 9 digit student ID number




		// Get a connection from the first driver in the
		// DriverManager list that recognizes the URL jdbcURL
                
		//conn = DriverManager.getConnection(jdbcURL, user, passwd);
                
		conn = DriverManager.getConnection(jdbcURL, user, passwd);

		// Create a statement object that will be sending your
		// SQL statements to the DBMS

		stmt = conn.createStatement();
       
           
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void query(String q){
        try{
            rs = stmt.executeQuery(q);
        }catch(Throwable oops){
            oops.printStackTrace();
        }
    }

    static boolean update(String q){
        try{
            stmt.executeUpdate(q);
            return true;
        }catch(Throwable oops){
            oops.printStackTrace();
            return false;
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

