package com.gradiance;

import java.io.*;

//abstract 
class User {

    public long sid = 0; 
    public String name = null; 
    public String major = null; 
    public String role = null; 
    public static long maxid=0;
    //public String 

    User(){}
    User(long sid,String name,String major,String role)
    {
        this.sid = sid;
        this.name = name;
        this.major = major;
        this.role = role;
    }
    static void init()
    {
        String s = "select sid from student where sid >= all (select sid from student)";
        DBcontrol.query(s);
        try{
            if(DBcontrol.rs.next())
            {
                maxid = DBcontrol.rs.getLong("sid");
            }
        }catch(Throwable oops){
            oops.printStackTrace();
        }
    }

    void doSomething() throws IOException
    {
        System.out.println("Please select a number as a choice\n" + 
                "1.select Course\n2.Add Course\n3.Back\n");
        InputStreamReader cin = new InputStreamReader(System.in);
        int choice = cin.read();
        switch(choice)
        {
            case '1':
                selectCourse();
                break;
            case '2':
                addCourse();
                break;
            case '3':
                return ;
            default : 
                return;
        }
    }

    void selectCourse()
    {
        //Course course = new Course();
    }
    void addCourse()
    {
        //Course course = new Course();
    }
}
