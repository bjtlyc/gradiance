

//Acknowledgments: This example is a modification of code provided 
//by Dimitri Rakitine.

//Usage from command line on key.csc.ncsu.edu: 
//see instructions in FAQ
//Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

//Note: If you run the program more than once, it will not be able to create the COFFEES table anew after the first run; 
//	you can remove the COFFEES tables between the runs by typing "drop table COFFEES;" in SQL*Plus.


import java.sql.*;

public class table {

static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

public static void main(String[] args) {
 try {

     // Load the driver. This creates an instance of the driver
	    	// and calls the registerDriver method to make Oracle Thin
	    	// driver available to clients.
     Class.forName("oracle.jdbc.driver.OracleDriver");

	    	String user = "yliu63";	// For example, "jsmith"
	    	String passwd = "001083962";	// Your 9 digit student ID number
	    	
	    	//String user = "sdu2";	// For example, "jsmith"
	    	//String passwd = "001085023";	// Your 9 digit student ID number
	    	

     Connection conn = null;
     Statement stmt = null;
     

     try {
     		// Get a connection from the first driver in the
				// DriverManager list that recognizes the URL jdbcURL

				conn = DriverManager.getConnection(jdbcURL, user, passwd);

				// Create a statement object that will be sending your
				// SQL statements to the DBMS

				stmt = conn.createStatement();
				
				//----------------------------------------------------------------------------------------------

				
				//----member table----
				stmt.executeUpdate("CREATE TABLE member " +
				"(mid VARCHAR(10), " +
				"mName VARCHAR(30) NOT NULL, " +
				"password VARCHAR(20) DEFAULT '123456', " +
				"faculty INTEGER DEFAULT 0, " +
				"PRIMARY KEY (mid), " +
				"CONSTRAINT chk_faculty CHECK (faculty = 0 OR faculty = 1))");
				
				
				//----course table----
				stmt.executeUpdate("CREATE TABLE course " +
				"(cid VARCHAR(20) NOT NULL, " +
				"token VARCHAR(20), " +
				"cName VARCHAR(40) NOT NULL, " +
				"cStart DATE DEFAULT SYSDATE, " +
				"cEnd DATE DEFAULT SYSDATE+120, " +
				"profid VARCHAR(10) NOT NULL, " +
				"PRIMARY KEY (token), " +
				"FOREIGN KEY (profid) REFERENCES member (mid))");
				
				
				//----topic table----
				stmt.executeUpdate("CREATE TABLE topic " +
				"(tid INTEGER, " +
				"tName VARCHAR(50) NOT NULL, " +
				"PRIMARY KEY (tid), " +
				"CONSTRAINT chk_tid CHECK (tid > 0))");
				

				//stmt.executeUpdate("create sequence topic_seq start with 1 increment by 1 nomaxvalue");
				//stmt.executeUpdate("create trigger topic_trigger " +
				//"before insert on topic " +
				//"for each row " +
				//"begin " +
				//"select topic_seq.nextval into :new.tid from dual; " +
				//"end; ");
				
				
				//----c_topic table (relation)----
				stmt.executeUpdate("CREATE TABLE c_topic " +
						"(token VARCHAR(20), " +
						"tid INTEGER, " +
						"PRIMARY KEY (tid, token), " +
						"FOREIGN KEY (token) REFERENCES course (token) " +
						"ON DELETE CASCADE, " +
						"FOREIGN KEY (tid) REFERENCES topic (tid))");
		

				//----enroll table (relation, no participation constraint)----
				stmt.executeUpdate("CREATE TABLE enroll " +
						"(mid VARCHAR(10), " +
						"token VARCHAR(20), " +
						"role VARCHAR(10) DEFAULT 'stud', " +
						"PRIMARY KEY (mid, token), " +
						"FOREIGN KEY (mid) REFERENCES member (mid) " +
						"ON DELETE CASCADE, " +
						"FOREIGN KEY (token) REFERENCES course (token) " +
						"ON DELETE CASCADE, " +
						"CONSTRAINT chk_role CHECK (role = 'ta' OR role = 'stud'))");
					
				//----homework table (weak entity)----
				stmt.executeUpdate("CREATE TABLE homework " +
				"(token VARCHAR(20), " +
				"hwid INTEGER, " +
				"hwTitle VARCHAR(50) DEFAULT 'No Title', " +
				"qNum INTEGER DEFAULT 1, " +
				"retryNum INTEGER DEFAULT 0, " +
				"hwStart DATE DEFAULT SYSDATE, " +
				"hwEnd DATE DEFAULT SYSDATE+7, " +
				"point NUMBER(3,1) DEFAULT 3, " +
				"penalty NUMBER(3,1) DEFAULT 1, " +
				"ssMethod VARCHAR(5) DEFAULT 'max', " +
				"PRIMARY KEY (token, hwid), " +
				"FOREIGN KEY (token) REFERENCES course (token) " +
				"ON DELETE CASCADE, " +
				"CONSTRAINT chk_hw CHECK (hwid>0 AND qNum>0 AND retryNum>=0 AND point>0 AND penalty>=0), " +
				"CONSTRAINT chk_ssMethod CHECK (ssMethod = 'first' OR ssMethod = 'last' OR ssMethod = 'max' OR ssMethod = 'avg'))");
				
				
				//----question table (weak entity)----
				stmt.executeUpdate("CREATE TABLE question " +
						"(qid INTEGER, " +
						"qContent VARCHAR(500), " +
						"qDif INTEGER DEFAULT 3, " +
						"longExp VARCHAR(500), " +
						"PRIMARY KEY (qid), " +
						"CONSTRAINT chk_ques CHECK (qDif > 0 AND qDif < 6 AND qid>0))");
				
				//----q_topic table (relation)----
				stmt.executeUpdate("CREATE TABLE q_topic " +
						"(tid INTEGER, " +
						"qid INTEGER, " +
						"PRIMARY KEY (tid, qid), " +
						"FOREIGN KEY (qid) REFERENCES question (qid) " +
						"ON DELETE CASCADE, " +
						"FOREIGN KEY (tid) REFERENCES topic (tid))");


				//----hw_ques table (relation)----
				stmt.executeUpdate("CREATE TABLE hw_ques " +
						"(token VARCHAR(20), " +
						"hwid INTEGER, " +
						"qid INTEGER, " +
						"PRIMARY KEY (token, hwid, qid), " +
						"FOREIGN KEY (token, hwid) REFERENCES homework (token, hwid) " +
						"ON DELETE CASCADE, " +
						"FOREIGN KEY (qid) REFERENCES question (qid))");


				//----answer table (weak entity)----
				stmt.executeUpdate("CREATE TABLE answer " +
						"(qid INTEGER, " +
						"ansid INTEGER, " +
						"ansContent VARCHAR(200) NOT NULL, " +
						"TorF VARCHAR(1) NOT NULL, " +
						"shortExp VARCHAR(200), " +
						"PRIMARY KEY (qid, ansid), " +
						"FOREIGN KEY (qid) REFERENCES question (qid) " +
						"ON DELETE CASCADE, " +
						"CONSTRAINT chk_ansid CHECK (ansid > 0), " +
						"CONSTRAINT chk_TorF CHECK (TorF = 'T' OR TorF = 'F'))");
				
				

				//----report table----
				stmt.executeUpdate("CREATE TABLE report " +
				"(token VARCHAR(20), " +
				"hwid INTEGER, " +
				"mid VARCHAR(10), " +
				"attNum INTEGER, " +
				"seed INTEGER, " +
				"rScore NUMBER(3,1) DEFAULT 0, " +
				"rTime DATE DEFAULT SYSDATE, " +
				"PRIMARY KEY (token, hwid, mid, attNum), " +
				"FOREIGN KEY (token, hwid) REFERENCES homework (token, hwid) " +
				"ON DELETE CASCADE, " +
				"FOREIGN KEY (mid) REFERENCES member (mid) " +
				"ON DELETE CASCADE, " +
				"CONSTRAINT chk_attNum CHECK (attNum > 0))");


				//----hw_mem table----
				stmt.executeUpdate("CREATE TABLE hw_mem " +
				"(token VARCHAR(20), " +
				"hwid INTEGER, " +
				"mid VARCHAR(10), " +
				"hwScore NUMBER(3,1) DEFAULT 0, " +
				"totalAtt INTEGER DEFAULT 0, " +
				"PRIMARY KEY (token, hwid, mid), " +
				"FOREIGN KEY (token, hwid) REFERENCES homework (token, hwid) " +
				"ON DELETE CASCADE, " +
				"FOREIGN KEY (mid) REFERENCES member (mid) " +
				"ON DELETE CASCADE)");


				//----stuAns table----
				stmt.executeUpdate("CREATE TABLE stuAns " +
				"(token VARCHAR(20), " +
				"hwid INTEGER, " +
				"mid VARCHAR(10), " +
				"attNum INTEGER, " +
				"qid INTEGER, " +
				"ansid INTEGER, " +
				"text VARCHAR(300), " +
				"PRIMARY KEY (token, hwid, mid, attNum, qid), " +
				"FOREIGN KEY (token, hwid, mid, attNum) REFERENCES report (token, hwid, mid, attNum) " +
				"ON DELETE CASCADE, " +
				"FOREIGN KEY (qid, ansid) REFERENCES answer (qid, ansid))");
				
			
				
				//------------------------------------------------------------------------------------------------------
				

				
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

