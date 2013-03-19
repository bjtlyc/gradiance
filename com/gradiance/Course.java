package com.gradiance;
import java.util.*;
import java.io.*;

class Course {
    public int hwnum=0;
    public int topic_num=0;
    public String cid = null ;
    public String token = null;
    public String cname = null;
    public String mid = null;
    public String profid = null;
    public Date start_date = null;
    public Date end_date = null;
    //public HashMap<Integer,Homework> hwlist = new HashMap<Integer,Homework>();
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    public static ArrayList<Homework> hwlist = new ArrayList<Homework>();

    Course(){}
    Course(String cid,String token,String cname,String mid)
    {
        this.cid = cid;
        this.cname = cname;
        this.token = token;
        this.mid = mid;
    }

    boolean showHomework(int role)
    {
        hwlist.clear();
        hwnum=0;
        System.out.println("Please select a homework\nHomework_number | Attempt_number");
        String s=null;
        if(role == 1)
            s = "select * from homework where token='"+this.token+"'";
        else if(role == 0)
            s = "select * from report where token='"+this.token+"' and mid ='"+this.mid+"'"; 
        DBcontrol.query(s);
        try{
            while(DBcontrol.rs.next())
            {
                hwnum++;
                int hwid = DBcontrol.rs.getInt("hwid"); 
                if(role == 1)
                {
                    Date start_date = DBcontrol.rs.getDate("hwstart");
                    Date end_date = DBcontrol.rs.getDate("hwend");
                    System.out.println(hwnum+".HW"+hwid+" ");
                    Homework hw = new Homework(hwid,this.token,Util.date_format.format(start_date),Util.date_format.format(end_date));
                    hwlist.add(hw);
                }
                else if(role == 0)
                {
                    int attemptnum = DBcontrol.rs.getInt("attnum"); 
                    int score = DBcontrol.rs.getInt("score"); 
                    System.out.println(hwnum+".HW"+hwid+" "+attemptnum+" attempt");
                    Homework hw = new Homework(hwid,this.token,score);
                    hwlist.add(hw);
                }
            }
            return true;
        }catch(Throwable oops){
            System.err.println("Get homework error");
        }
        System.out.println("Sorry, there is no homeworks about this user");
        return false;
    }

    boolean showTopic()
    {
        list.clear();
        DBcontrol.query("select T.tid,T.tname from topic T, c_topic C where C.tid=T.tid and C.token ='"+ this.token+"'");
        try{
            while(DBcontrol.rs.next())
            {
                int topic_id = DBcontrol.rs.getInt("tid");
                int topic_name = DBcontrol.rs.getInt("tname");
                System.out.println(++topic_num+". "+topic_name);
                list.add(topic_id);
            }
            return true;
        }catch(Throwable oops){
            System.out.println("Get topic error");
        }
        System.out.println("there is no related topics about this course");
        return false;
    }

    boolean showOpenHomework()
    {
        hwlist.clear();
        hwnum=0;
        DBcontrol.query("select * from homework a where a.token='"+this.token+"' and a.hwend > sysdate");
        try{
            while(DBcontrol.rs.next())
            {
                hwnum++;
                int hwid = DBcontrol.rs.getInt("hwid");
                String hwtitle = DBcontrol.rs.getString("hwtitle");
                int qnum = DBcontrol.rs.getInt("qnum");
                int retrynum = DBcontrol.rs.getInt("retrynum");
                int point = DBcontrol.rs.getInt("point");
                int penalty = DBcontrol.rs.getInt("penalty");
                int ssmethod = DBcontrol.rs.getInt("ssmethod");
                System.out.println(hwnum+".HW "+hwid+" "+hwtitle);
                Homework hw = new Homework(hwid,this.mid,this.token,qnum,retrynum,point,penalty,ssmethod);
                hwlist.add(hw);
            }
            return true;
        }catch(Throwable oops){
            System.err.println("Error: Get Open Homework");
        }
        System.out.println("there is no open course about this course");
        return false;
    }

}
    
