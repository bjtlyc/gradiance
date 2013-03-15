package com.gradiance;

import java.io.*;
import java.util.*;

//abstract 
class User {

    public String userid = null; 
    public String name = null; 
    public String major = null; 
    public static long maxid=0;
    public int role=0; 
    public HashMap<Integer,Course> clist = new HashMap<Integer,Course>(); 

    User(){}
    User(String userid,String name,String major)
    {
        this.userid = userid;
        this.name = name;
        this.major = major;
    }
    static void init()
    {
        String s = "select count(*) as num from users";
        DBcontrol.query(s);
        try{
            if(DBcontrol.rs.next())
            {
                maxid = DBcontrol.rs.getLong("num");
            }
        }catch(Throwable oops){
            oops.printStackTrace();
        }
    }

    boolean doSomething() throws IOException
    {
            System.out.println("Please select a number as a choice\n" + 
                    "1.select Course\n2.Add Course\n3.Back\n");
            InputStreamReader cin = new InputStreamReader(System.in);
            int choice = cin.read();
            switch(choice)
            {
                case '1':
                    while(selectCourse())
                        continue;
                    break;
                case '2':
                    while(addCourse())
                        continue;
                    break;
                case '3':
                    return false;
                default : 
                    return true;
            }
            return true;
    }

    boolean selectCourse(int role){
        //System.out.println("student"+this.userid);
        String s;
        if(role == 0)  // role is student
            DBcontrol.query("select C.token,C.cid,C.cname from users S,enroll E,course C where E.token = C.token and S.userid = '" + this.userid +"'");
        else if(role == 1)// role is professor 
            DBcontrol.query("select C.token,C.cid,C.cname from users S,enroll E,course C where E.token = C.token and S.userid = '" + this.userid +"'");
        int coursenum = 1;
        try{
            while(DBcontrol.rs.next())
            {
                String cid = DBcontrol.rs.getString("cid");
                String token = DBcontrol.rs.getString("token");
                String cname = DBcontrol.rs.getString("cname");
                Course course = new Course(cid,cname,token,userid);
                clist.put(coursenum++,course);
                //System.out.print(cid+cname);
            }
        }catch(Throwable oops){
            System.err.println("stduent oops error");
            //oops.printStackTrace();
        }
        if(showCourse())
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            if(choice==clist.size()+1)
                return false;
            Course course = clist.get(choice);
            if(course!=null)
            {
                while(aboutCourse(course))
                    continue;
            }
            else
            {
                System.out.println("The course id is invalid");
                return true;
            }
        }
        return false;
    }
    boolean addCourse()
    {

        //Course course = new Course();
    }
    boolean showCourse()
    {
        if(clist.isEmpty())
        {
            System.out.println("Sorry, there is no courses about this user");
            return false;
        }
        //Set<String> keys = clist.keySet();
        //for(String key : keys)
        System.out.println("Please select a course by entering the number");
        for(int i=1;i<=clist.size();i++)
            System.out.println(i+"."+clist.get(i).get(token)+" "+clist.get(i).cname);
        System.out.println((clist.size()+1)+".Back");
        return true; 
    }
}
