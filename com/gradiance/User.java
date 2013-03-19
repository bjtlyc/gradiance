package com.gradiance;

import java.io.*;
import java.util.*;

//abstract 
abstract class User {

    public String mid = null; 
    public String name = null; 
    public String dept = null; 
    public static long maxid=0;
    public int role=0; 
    public HashMap<Integer,Course> clist = new HashMap<Integer,Course>(); 

    User(){}
    User(String mid,String name,int role)
    {
        this.mid = mid;
        this.name = name;
        this.role = role;
    }
    static void init()
    {
        String s = "select count(*) as num from member";
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
                    while(selectCourse(role))
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
        //System.out.println("student"+this.mid);
        String s;
        if(role == 0)  // role is student
            DBcontrol.query("select C.token,C.cid,C.cname from member S,enroll E,course C where E.token = C.token and S.mid = E.mid and S.mid = '" + this.mid +"'");
        else if(role == 1)// role is professor 
            DBcontrol.query("select token,cid,cname from member M,course C where M.mid = C.profid and M.mid = '" + this.mid +"'");
        int coursenum = 1;
        try{
            while(DBcontrol.rs.next())
            {
                String cid = DBcontrol.rs.getString("cid").toUpperCase();
                String token = DBcontrol.rs.getString("token").toUpperCase();
                String cname = DBcontrol.rs.getString("cname");
                Course course = new Course(cid,token,cname,mid);
                clist.put(coursenum++,course);
                //System.out.print(cid+cname);
            }
        }catch(Throwable oops){
            System.err.println("stduent oops error");
            //oops.printStackTrace();
        }
        if(showCourse())
        {
            int choice = Util.inputInt("");
            if(choice==clist.size()+1)
                return false;
            Course course = clist.get(choice);
            if(course!=null)
            {
                if(role == 0)
                {
                    DBcontrol.query("select role from enroll where mid='"+mid+"' and token='"+course.token+"'");
                    String ifta=null;
                    try{
                        if(DBcontrol.rs.next())
                            ifta = DBcontrol.rs.getString("role");
                    }catch(Throwable oops){
                        oops.printStackTrace();
                    }
                    if(ifta.equals("ta"))
                    {
                        Prof ta = new Prof(mid,name,1);
                        while(ta.aboutCourse(course))
                            continue;
                        return true; 
                    }
                }
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
            System.out.println(i+"."+clist.get(i).token+" "+clist.get(i).cname);
        System.out.println((clist.size()+1)+".Back");
        return true; 
    }
    boolean addCourse(){return true;}        //Course course = new Course()
    boolean aboutCourse(Course course){return true;}
    boolean viewScore(Course course){return true;}
    boolean viewPastSubmit(Course course){return true;}
    boolean attemptHomework(Course course){return true;}
    boolean addHomework(Course course){return true;}
    boolean editHomework(Course course){return true;}
    boolean addQuestion(Course course){return true;}
    boolean addAnswer(Course course){return true;}
}
