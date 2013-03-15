package com.gradiance;
import java.util.*;
import java.io.*;

class Course {
    public String cid = null ;
    public String token = null;
    public String cname = null;
    public String userid = null;
    public HashMap<Integer,Homework> hwlist = new HashMap<Integer,Homework>();

    Course(){}
    Course(String cid,String cname,String token,String userid)
    {
        this.cid = cid;
        this.cname = cname;
        this.token = token;
        this.userid = userid;
    }
    boolean showScore()
    {
        System.out.println("Homework_number | Attempt_number");
        String s = "";
        DBcontrol.query(s);
        int num=1;
        try{
            while(DBcontrol.rs.next())
            {
                String hwtoken = DBcontrol.rs.getString("score");
                Homework hw = new Homework();
                hwlist.put(num++,hw);
            }
        }catch(Throwable oops){
            System.err.println("Get homework error");
        }
        if(showHomework())
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            Homework temp = hwlist.get(choice);
            if(temp!=null)
            {
                //Homework hw = hwlist.get(choice);
                ;
            }
            else
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
        }
        else
        {
            System.out.println("You don't have homework attemp");
            return false; 
        }
    }


}
