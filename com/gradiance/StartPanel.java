package com.gradiance;
import java.sql.*;
import java.math.*;
import java.util.*;
import java.io.*;


public class StartPanel
{
    int choice=0;
    
    StartPanel() throws IOException{
        DBcontrol db = new DBcontrol();
        while(true)
        {
            System.out.println("Please input a number to make a choice\n1.Select Course\n2.Add Course\n3.Back");
            choice=System.in.read();
            switch(choice)
            {
                case '1':
                    UserLogin();//User user = UserLogin();
                    break;
                case '2':
                    //User user = CreateUser();
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

    void UserLogin() throws IOException{
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
            System.out.println("welcome back" + user_id + " :");
        }
        else{
            System.out.println("the userid/passwrod is not valid, please try again");}
    
    }
    void CreateUser(){}
    boolean verify(String user_id, char [] password){
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
