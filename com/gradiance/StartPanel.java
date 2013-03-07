package com.gradiance;
import java.sql.*;
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
        String username = c.readLine("Enter your user name: ");
        char [] password  = c.readPassword("Enter your password: ");
        if(verify(username, password))
        {
            ;
        }
        else{
            System.out.println("the username/passwrod is not valid, please try again");}
    
    }
    void CreateUser(){}
    boolean verify(String username, char [] password){
        String s = "SELECT COF_NAME, PRICE FROM COFFEES";
        DBcontrol.query(s);
        return true;
    }

    public static void  main(String[] args) throws Exception
    {
        StartPanel sp = new StartPanel();
        //String s = "SELECT COF_NAME, PRICE FROM COFFEES";
        //DBcontrol.query(s);
    }
}
