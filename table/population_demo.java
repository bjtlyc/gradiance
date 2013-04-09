
import java.sql.*;

public class population_demo {

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
				
				//----member data
				//stmt.executeUpdate("insert into member(mid, mname, password) values ('ssbudha', 'Sam S. Budha', '123bud')");
				stmt.executeUpdate("insert into member(mid, mname, password) values ('sskanit', 'Sara S. Kanit', '123kan')");
				stmt.executeUpdate("insert into member(mid, mname, password) values ('agholak', 'Alan G. Holak', '123hol')");
				stmt.executeUpdate("insert into member(mid, mname, password) values ('rjoseph', 'Rose Joseph', '123jos')");
				//stmt.executeUpdate("insert into member(mid, mname, password) values ('tbirajd', 'Ted Birajd', '123bir')");
				stmt.executeUpdate("insert into member(mid, mname, password, faculty) values ('kogan', 'Kemafor Ogan', '123kogan', 1)");
				stmt.executeUpdate("insert into member(mid, mname, password) values ('aneel', 'Aishwarya Neelakantan', '123ane')");
				stmt.executeUpdate("insert into member(mid, mname, password) values ('jharl', 'Jitendra Harlalka', '123jha')");
				stmt.executeUpdate("insert into member(mid, mname, password, faculty) values ('chirk', 'Rada Chirkova', '123chirk', 1)");
				//stmt.executeUpdate("insert into member(mid, mname, password, faculty) values ('muell', 'Dr. R. Mueller', '123muell', 1)");
		
				//----course data----
				stmt.executeUpdate("INSERT INTO course " +
			   "VALUES ('CSC440', 'CSC440SPR13', 'Database Systems', '01-JAN-13', '10-MAY-13', 'kogan')");
				stmt.executeUpdate("INSERT INTO course " +
				"VALUES ('CSC541', 'CSC541FLL11', 'Advanced Data Structures', '01-AUG-11', '15-DEC-11', 'chirk')");
				//stmt.executeUpdate("INSERT INTO course " +
				//"VALUES ('CSC501', 'CSC501SPR12', 'Operating Systems', '01-JAN-12', '10-MAY-12', 'muell')");
				
				
				//----topic data-----
				stmt.executeUpdate("insert into topic(tid, tname) values (1, 'Database Fundamentals')");
				stmt.executeUpdate("insert into topic(tid, tname) values (2, 'Security and Authorization')");
				stmt.executeUpdate("insert into topic(tid, tname) values (3, 'ER Design and other topics')");

				stmt.executeUpdate("insert into topic(tid, tname) values (4, 'Binary search trees and Btrees')");
				stmt.executeUpdate("insert into topic(tid, tname) values (5, 'Hashing')");
				stmt.executeUpdate("insert into topic(tid, tname) values (6, 'Files and indexing and other topics')");

				//stmt.executeUpdate("insert into topic(tid, tname) values (7, 'Processes and Threads')");
				//stmt.executeUpdate("insert into topic(tid, tname) values (8, 'Memory Organization')");
				//stmt.executeUpdate("insert into topic(tid, tname) values (9, 'Deadlocks and other topics')");


				//----c_topic data----
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC440SPR13', 1)");
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC440SPR13', 2)");
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC440SPR13', 3)");
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC541FLL11', 4)");
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC541FLL11', 5)");
				stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC541FLL11', 6)");
				//stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC501SPR12', 7)");
				//stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC501SPR12', 8)");
				//stmt.executeUpdate("insert into c_topic(token, tid) values ('CSC501SPR12', 9)");


				//----enroll data----
				//stmt.executeUpdate("insert into enroll(mid, token, role) values ('ssbudha', 'CSC440SPR13', 'stud')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('sskanit', 'CSC440SPR13', 'stud')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('agholak', 'CSC440SPR13', 'stud')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('rjoseph', 'CSC440SPR13', 'stud')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('aneel', 'CSC440SPR13', 'ta')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('sskanit', 'CSC541FLL11', 'stud')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('jharl', 'CSC541FLL11', 'ta')");
				stmt.executeUpdate("insert into enroll(mid, token, role) values ('aneel', 'CSC541FLL11', 'stud')");

				//stmt.executeUpdate("insert into enroll(mid, token, role) values ('agholak', 'CSC501SPR12', 'stud')");
				


				//----homework data----
				stmt.executeUpdate("insert into homework(token, hwid, hwTitle, qNum, retryNum, hwStart, hwEnd, point, penalty, ssMethod) " +
					"values ('CSC440SPR13', 1, 'based on Database Fundamentals', 2, 2, '12-FEB-13', '01-Mar-13', 3, 1, 'first')");
				//stmt.executeUpdate("insert into homework(token, hwid, hwTitle, qNum, retryNum, hwStart, hwEnd, point, penalty, ssMethod) " +
				//	"values ('CSC440SPR13', 2, 'based on ER design', 2, 1, '12-Mar-13', '01-Apr-13', 5, 2, 'avg')");

				//----question data----
				stmt.executeUpdate("insert into question(qid, qContent, qDif, longExp) " +
					"values (1, 'Question 1', 5, 'longExp of Q1')");
				stmt.executeUpdate("insert into question(qid, qContent, qDif, longExp) " +
					"values (2, 'Question 2', 3, 'longExp of Q2')");
				//stmt.executeUpdate("insert into question(qid, qContent, qDif, longExp) " +
				//	"values (3, 'Question 3', 3, 'longExp of Q3')");

				//----q_topic data----
				stmt.executeUpdate("insert into q_topic(tid, qid) values (3, 1)");
				stmt.executeUpdate("insert into q_topic(tid, qid) values (1, 2)");
				//stmt.executeUpdate("insert into q_topic(tid, qid) values (1, 3)");


				//----hw_ques data----
				//stmt.executeUpdate("insert into hw_ques(token, hwid, qid) values ('CSC440SPR13', 1, 3)");
				stmt.executeUpdate("insert into hw_ques(token, hwid, qid) values ('CSC440SPR13', 1, 1)");
				stmt.executeUpdate("insert into hw_ques(token, hwid, qid) values ('CSC440SPR13', 1, 2)");


				//----answer data----
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 1, 'Correct 1.1', 'T', 'Explanation 1.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 2, 'Correct 1.2', 'T', 'Explanation 1.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 3, 'Correct 1.3', 'T', 'Explanation 1.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 4, 'Incorrect 1.1', 'F', 'Hint 1.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 5, 'Incorrect 1.2', 'F', 'Hint 1.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 6, 'Incorrect 1.3', 'F', 'Hint 1.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 7, 'Incorrect 1.4', 'F', 'Hint 1.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 8, 'Incorrect 1.5', 'F', 'Hint 1.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 9, 'Incorrect 1.6', 'F', 'Hint 1.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (1, 10, 'Incorrect 1.7', 'F', 'Hint 1.3')");

				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 1, 'Correct 2.1', 'T', 'Explanation 2.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 2, 'Correct 2.2', 'T', 'Explanation 2.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 3, 'Correct 2.3', 'T', 'Explanation 2.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 4, 'Incorrect 2.1', 'F', 'Hint 2.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 5, 'Incorrect 2.2', 'F', 'Hint 2.1')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 6, 'Incorrect 2.3', 'F', 'Hint 2.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 7, 'Incorrect 2.4', 'F', 'Hint 2.2')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 8, 'Incorrect 2.5', 'F', 'Hint 2.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 9, 'Incorrect 2.6', 'F', 'Hint 2.3')");
				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (2, 10, 'Incorrect 2.7', 'F', 'Hint 2.3')");

//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 1, 'Correct 3.1', 'T', 'Explanation 3.1')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 2, 'Correct 3.2', 'T', 'Explanation 3.2')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 3, 'Correct 3.3', 'T', 'Explanation 3.3')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 4, 'Incorrect 3.1', 'F', 'Hint 3.1')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 5, 'Incorrect 3.2', 'F', 'Hint 3.1')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 6, 'Incorrect 3.3', 'F', 'Hint 3.2')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 7, 'Incorrect 3.4', 'F', 'Hint 3.2')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 8, 'Incorrect 3.5', 'F', 'Hint 3.3')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 9, 'Incorrect 3.6', 'F', 'Hint 3.3')");
//				stmt.executeUpdate("insert into answer(qid, ansid, ansContent, TorF, shortExp) values (3, 10, 'Incorrect 3.7', 'F', 'Hint 3.3')");
//				

				//----report data----
				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
					"values ('CSC440SPR13', 1, 'sskanit', 1, 100, 2, '18-Feb-13')");
				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
					"values ('CSC440SPR13', 1, 'sskanit', 2, 200, 6, '19-Feb-13')");
				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
					"values ('CSC440SPR13', 1, 'rjoseph', 1, 250, 6, '19-Feb-13')");
				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
					"values ('CSC440SPR13', 1, 'agholak', 1, 300, 6, '25-Feb-13')");
//				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
//					"values ('CSC440SPR13', 2, 'ssbudha', 1, 150, 10, '27-Feb-13')");
//				stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
//					"values ('CSC440SPR13', 2, 'rjoseph', 1, 260, 10, '26-Feb-13')");
				
				//stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
				//	"values ('CSC440SPR13', 1, 'ssbudha', 0, 10, -1, '01-Mar-13')");
				//stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
				//	"values ('CSC440SPR13', 1, 'rjoseph', 0, 20, -1, '01-Mar-13')");
				//stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
				//	"values ('CSC440SPR13', 2, 'sskanit', 0, 30, -4, '01-Mar-13')");
				//stmt.executeUpdate("insert into report(token, hwid, mid, attNum, seed, rScore, rTime) " +
				//	"values ('CSC440SPR13', 2, 'rjoseph', 0, 40, -4, '01-Mar-13')");

				//----hw_mem data----
				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
					"values ('CSC440SPR13', 1, 'sskanit', 0, 2)");
				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
					"values ('CSC440SPR13', 1, 'agholak', 3, 1)");
//				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
//					"values ('CSC440SPR13', 1, 'ssbudha', 0, 0)");
				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
					"values ('CSC440SPR13', 1, 'rjoseph', 3, 1)");

//				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
//					"values ('CSC440SPR13', 2, 'ssbudha', 10, 1)");
//				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
//					"values ('CSC440SPR13', 2, 'agholak', 0, 0)");
//				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
//					"values ('CSC440SPR13', 2, 'sskanit', 0, 0)");
//				stmt.executeUpdate("insert into hw_mem(token, hwid, mid, hwScore, totalAtt) " +
//					"values ('CSC440SPR13', 2, 'rjoseph', 10, 1)");


				//----stuAns data----
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'sskanit', 1, 1, 4, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'sskanit', 1, 2, 3, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'sskanit', 2, 1, 2, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'sskanit', 2, 2, 1, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'agholak', 1, 1, 1, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'agholak', 1, 2, 2, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'rjoseph', 1, 1, 3, 'input_box')");
				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
					"values ('CSC440SPR13', 1, 'rjoseph', 1, 2, 1, 'input_box')");
//				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
//					"values ('CSC440SPR13', 2, 'ssbudha', 1, 1, 2, 'input_box')");
//				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
//					"values ('CSC440SPR13', 2, 'ssbudha', 1, 2, 3, 'input_box')");
//				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
//					"values ('CSC440SPR13', 2, 'rjoseph', 1, 1, 3, 'input_box')");
//				stmt.executeUpdate("insert into stuAns(token, hwid, mid, attNum, qid, ansid, text) " +
//					"values ('CSC440SPR13', 2, 'rjoseph', 1, 2, 1, 'input_box')");

				
				
				//------------------------------------------------------------------------------------------------------
				
				// Get data from the course table
				
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

