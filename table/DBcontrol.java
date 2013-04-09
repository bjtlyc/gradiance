// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.

// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

//Note: If you run the program more than once, it will not be able to create the COFFEES table anew after the first run; 
//	you can remove the COFFEES tables between the runs by typing "drop table COFFEES;" in SQL*Plus.

import java.sql.*;

public class DBcontrol {

    static final String jdbcURL 
	//= "jdbc:oracle:thin:@//orca.csc.ncsu.edu:1521/ORCL.WORLD";
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:ORCL";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    DBcontrol(){
    
        try {

            // Load the driver. This creates an instance of the driver
	    // and calls the registerDriver method to make Oracle Thin
	    // driver available to clients.

        Class.forName("oracle.jdbc.driver.OracleDriver");

	    String user = "yliu63";	// For example, "jsmith"
	    String passwd = "001083962";	// Your 9 digit student ID number
		//conn = DriverManager.getConnection(jdbcURL, user, passwd);
		conn = DriverManager.getConnection(jdbcURL, user, passwd);
		stmt = conn.createStatement();
           
        } catch(Throwable oops) {
            oops.printStackTrace();
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

    public static void main(String argv[])
    {
        DBcontrol db = new DBcontrol();
        try{
            db.rs = stmt.executeQuery("select * from coffees where price > all (select price from coffees)");
            if(db.rs.next())
                System.out.println(db.rs.getString("sup_id"));

            }catch(Throwable oops)
            {
                System.err.println("err query sup_id");
            }
    }
}

