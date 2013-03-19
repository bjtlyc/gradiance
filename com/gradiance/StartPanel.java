package com.gradiance;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class StartPanel
{
    
    StartPanel() throws IOException{
        DBcontrol db = new DBcontrol();
        //init();
        //User.init();
        while(true)
        {
            System.out.println("Please input a number to make a choice"+
                    "\n1.Login\n2.Create User\n3.Back");
            InputStreamReader cin = new InputStreamReader(System.in);
            int choice = cin.read();
            switch(choice)
            {
                case '1':
                    UserLogin();//User user = UserLogin();
                    break;
                case '2':
                    CreateUser();
                    break;
                case '3':
                    DBcontrol.close(DBcontrol.stmt);
                    DBcontrol.close(DBcontrol.rs);
                    System.exit(0);
                    break;
                default:
                    DBcontrol.close(DBcontrol.stmt);
                    DBcontrol.close(DBcontrol.rs);
                    System.err.println("invalid input");
                    break;
            }
        }
    }

    void UserLogin() throws IOException
    {
        Console c = System.console();
        if(c==null)
        {
            System.err.println("no console");
            System.exit(0);
        }
        String user_id = c.readLine("Enter your user id: ");
        char [] password  = c.readPassword("Enter your password: ");
        if(verify(user_id, password))
        {
            //User user = UserFactory.createUser("default");
            User user=null;
            try{
                    String name = DBcontrol.rs.getString("uname");
                    int role = DBcontrol.rs.getInt("faculty");
                    //user = new User(Long.parseLong(user_id),name,major,role);
                    user = UserFactory.createUser(user_id,name,role);
                }catch(Throwable oops){
                    oops.printStackTrace();
                }
            System.out.println("welcome back " + user.name + " :");
            while(user.doSomething())
                continue;
        }
        else
            System.out.println("the userid/passwrod is not valid, please try again");
    }

    void CreateUser() throws IOException
    {
        Console c = System.console();
        if(c==null)
        {
            System.err.println("no console");
            System.exit(0);
        }
        String userid = c.readLine("Please enter your login id"); 
        String username = c.readLine("Enter your full name: ");
        char [] password  = c.readPassword("Enter your password: ");
        String pwd = new String(password);
        String major = c.readLine("Enter your major: ");
        //String role = c.readLine("Enter your role: ");
        String q = "insert into member values ('"+userid+"','"+username+"','"+pwd+"',0";//'"+major+"')";
        DBcontrol.update(q);
        User user = new Student(userid,username,0);
        System.out.println("Create User Successfully, welcome "+username);
        while(user.doSomething())
            continue;
    }

    boolean verify(String user_id, char [] password)
    {
        String s = "SELECT * FROM member where mid = '" + user_id+"'";
        DBcontrol.query(s);
        try{
            if(DBcontrol.rs.next())
            {
                String pwd = DBcontrol.rs.getString("password");
                String userpwd = new String(password);
                if(pwd.equals(userpwd))
                    return true;
            }
            else
                return false;
        }catch(Throwable oops){
            oops.printStackTrace();
        }
        return false;
    }

    public static void  main(String[] args) throws Exception
    {
        StartPanel sp = new StartPanel();
        //String s = "SELECT COF_NAME, PRICE FROM COFFEES";
        //DBcontrol.query(s);
    }
    void init()
    {
        DBcontrol.update("drop table enroll");
        DBcontrol.update("drop table users");
        DBcontrol.update("drop table course");

        DBcontrol.update("create table course ( cid varchar2(20),token varchar2(20),cname varchar2(40), cstart date, cend date, prof varchar2(25), ta varchar2(25), constraint CourseKey primary key (token))");
        DBcontrol.update("create table users ( userid varchar2(20), uname varchar2(25), password varchar2(20), major varchar2(20), constraint userKey primary key (userid)  )");
        DBcontrol.update("create table enroll ( userid varchar2(20), token varchar2(20), role varchar2(20), primary key (userid,token), constraint fk_mid foreign key (userid) references users on delete cascade, constraint fk_token foreign key (token) references course on delete cascade) ");

        /*course*/
        DBcontrol.update("insert into course(cid,token,cname,cstart,cend,prof,ta) values ('CSC440', 'CSC440SPR13', 'Database Systems', '01-JAN-13', '10-MAY-13', 'Kemafor Ogan', 'Aishwarya Neelakantan')");
        DBcontrol.update("insert into course(cid,token,cname,cstart,cend,prof,ta) values ('CSC541', 'CSC541FLL11', 'Advanced Data Structures', '01-AUG-11', '15-DEC-11', 'Rada Chirkova', 'Jitendra Harlalka')");
        DBcontrol.update("insert into course(cid,token,cname,cstart,cend,prof,ta) values ('CSC501', 'CSC501SPR12', 'Operating Systems', '01-JAN-12', '10-MAY-12', 'Dr. R. Mueller', 'Pamela Hart')");

        /*user*/
        DBcontrol.update("insert into users(userid, uname, password) values ('ssbudha', 'Sam S. Budha', '123bud')");
        DBcontrol.update("insert into users(userid, uname, password) values ('sskanit', 'Sara S. Kanit', '123kan')");
        DBcontrol.update("insert into users(userid, uname, password) values ('agholak', 'Alan G. Holak', '123hol')");
        DBcontrol.update("insert into users(userid, uname, password) values ('rjoseph', 'Rose Joseph', '123jos')");
        DBcontrol.update("insert into users(userid, uname, password) values ('tbirajd', 'Ted Birajd', '123bir')");
        DBcontrol.update("insert into users(userid, uname, password) values ('kogan', 'Kemafor Ogan', '123kogan')");
        DBcontrol.update("insert into users(userid, uname, password) values ('aneelak', 'Aishwarya Neelakantan', '123nee')");

        /*enroll*/
        DBcontrol.update("insert into enroll(userid, token, role) values ('ssbudha', 'CSC440SPR13', 'stud')");
        DBcontrol.update("insert into enroll(userid, token, role) values ('sskanit', 'CSC440SPR13', 'stud')");
        DBcontrol.update("insert into enroll(userid, token, role) values ('agholak', 'CSC440SPR13', 'stud')");
        DBcontrol.update("insert into enroll(userid, token, role) values ('rjoseph', 'CSC440SPR13', 'stud')");
        DBcontrol.update("insert into enroll(userid, token, role) values ('aneelak', 'CSC440SPR13', 'ta')");
        DBcontrol.update("insert into enroll(userid, token, role) values ('kogan', 'CSC440SPR13', 'faculty')");

        DBcontrol.update("insert into enroll(userid, token, role) values ('kogan', 'CSC501SPR13', 'stud')");

    }
    
}
