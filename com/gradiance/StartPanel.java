package com.gradiance;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.io.*;


public class StartPanel
{
    
    StartPanel() throws IOException{
        DBcontrol db = new DBcontrol();
        User.init();
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
                    System.exit(0);
                    break;
                default:
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
            System.out.println("welcome back " + user_id + " :");
            //User user = UserFactory.createUser("default");
            User user=null;
            try{
                    String name = DBcontrol.rs.getString("sname");
                    String role = DBcontrol.rs.getString("role");
                    String major = DBcontrol.rs.getString("major");
                    user = new User(Long.parseLong(user_id),name,major,role);
                }catch(Throwable oops){
                    oops.printStackTrace();
                }
            user.doSomething();
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
        String username = c.readLine("Enter your user name: ");
        char [] password  = c.readPassword("Enter your password: ");
        String pwd = new String(password);
        String major = c.readLine("Enter your major: ");
        String role = c.readLine("Enter your role: ");
        long sid = ++User.maxid; 
        String q = "insert into student values ("+sid+",'"+username+"','"+pwd+"','"+major+"','"+role+"')"   ;
        DBcontrol.update(q);
        User user = new User(sid,username,major,role);
        System.out.println("Create User Successfully, welcome "+username);
        user.doSomething();
    }

    boolean verify(String user_id, char [] password)
    {
        String s = "SELECT * FROM student where sid = " + user_id;
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
}
