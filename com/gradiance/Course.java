package com.gradiance;
import java.util.*;
import java.io.*;

class Course {
    public int hwnum=0;
    public int topic_num=0;
    public String cid = null ;
    public String token = null;
    public String cname = null;
    public String profid = null;
    public Date start_date = null;
    public Date end_date = null;
    //public HashMap<Integer,Homework> hwlist = new HashMap<Integer,Homework>();
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    public static ArrayList<Homework> hwlist = new ArrayList<Homework>();
    public static ArrayList<Question> cqlist = new ArrayList<Question>();
    public static ArrayList<Student> slist = new ArrayList<Student>();

    Course(){}
    Course(String token)
    {
        this.token = token;
    }
    Course(String cid,String token,String cname)
    {
        this.cid = cid;
        this.token = token;
        this.cname = cname;
    }

    boolean showHomework(int role,String mid)
    {
        hwlist.clear();
        hwnum=0;
        System.out.println("Please select a homework\nHomework_number | Attempt_number");
        String s=null;
        if(role == 1)
            s = "select * from homework where token='"+this.token.toUpperCase()+"' order by hwid";
        else if(role == 0)
            s = "select * from report where token='"+this.token.toUpperCase()+"' and mid ='"+mid+"'"; 
        try{
            DBcontrol.query(s);
            while(DBcontrol.rs.next())
            {
                hwnum++;
                int hwid = DBcontrol.rs.getInt("hwid"); 
                if(role == 1)
                {
                    Date start_date = DBcontrol.rs.getDate("hwstart");
                    Date end_date = DBcontrol.rs.getDate("hwend");
                    String hwtitle = DBcontrol.rs.getString("hwtitle");
                    System.out.println(hwnum+".HW"+hwid+" "+hwtitle);
                    Homework hw = new Homework(hwid,this.token,Util.date_format.format(start_date),Util.date_format.format(end_date));
                    hwlist.add(hw);
                }
                else if(role == 0)
                {
                    int attemptnum = DBcontrol.rs.getInt("attnum"); 
                    int score = DBcontrol.rs.getInt("rscore"); 
                    System.out.println(hwnum+".HW "+hwid+"      | "+attemptnum+"-attempt");
                    Homework hw = new Homework(hwid,this.token,score);
                    hwlist.add(hw);
                }
            }
            System.out.println((hwnum+1)+".back");
            return true;
        }catch(Throwable oops){
            System.err.println("Get homework error");
        }
        System.out.println("Sorry, there is no homeworks about this user");
        return false;
    }

    boolean showTopic(int cOrQ)
    {
        list.clear();
        int no=0;
        if(cOrQ==0)
            DBcontrol.query("select T.tid,T.tname from topic T, c_topic C where C.tid=T.tid and C.token ='"+ this.token+"'");
        else if(cOrQ==1)
            DBcontrol.query("select tid,tname from topic");
        try{
            while(DBcontrol.rs.next())
            {
                int topic_id = DBcontrol.rs.getInt("tid");
                String topic_name = DBcontrol.rs.getString("tname");
                System.out.println((++no)+"."+topic_name);
                list.add(topic_id);
            }
            System.out.println(++no+".back");
            return true;
        }catch(Throwable oops){
            oops.printStackTrace();
            System.out.println("Get topic error");
        }
        return false;
    }

    boolean showOpenHomework(String mid)
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
                //String start_date = DBcontrol.rs.getString("hwstart");
                //String end_date = DBcontrol.rs.getString("hwend");
                String ssmethod = DBcontrol.rs.getString("ssmethod");
                System.out.println(hwnum+".HW "+hwid+" "+hwtitle);
                Homework hw = new Homework(hwid,mid,this.token,qnum,retrynum,point,penalty,ssmethod,0,1);
                //hw.setDate(start_date, end_date);
                hwlist.add(hw);
            }
            System.out.println((hwnum+1)+".back");
            return true;
        }catch(Throwable oops){
            oops.printStackTrace();
            System.err.println("Error: Get Open Homework");
        }
        System.out.println("there is no open course about this course");
        return false;
    }

    boolean showPastHomework(String mid)
    {
        hwlist.clear();
        hwnum=0;
        DBcontrol.query("select * from homework h,report r where h.hwid=r.hwid and h.token='"+this.token+"' and r.mid='"+mid+"'");
        try{
            while(DBcontrol.rs.next())
            {
                hwnum++;
                int hwid = DBcontrol.rs.getInt("hwid");
                String hwtitle = DBcontrol.rs.getString("hwtitle");
                int qnum = DBcontrol.rs.getInt("qnum");
                int retrynum = DBcontrol.rs.getInt("retrynum");
                int attnum = DBcontrol.rs.getInt("attnum");
                int seed = DBcontrol.rs.getInt("seed");
                Date start_date = DBcontrol.rs.getDate("hwstart");
                Date end_date = DBcontrol.rs.getDate("hwend");
                int point = DBcontrol.rs.getInt("point");
                int penalty = DBcontrol.rs.getInt("penalty");
                String ssmethod = DBcontrol.rs.getString("ssmethod");
                if(end_date.after(new Date()))
                    System.out.println(hwnum+".HW "+hwid+" "+hwtitle+"      "+attnum+"-attempt");
                else
                    System.out.println(hwnum+".HW "+hwid+" "+hwtitle+"      "+attnum+"-attempt.Already due");
                Homework hw = new Homework(hwid,mid,this.token,qnum,retrynum,point,penalty,ssmethod,seed,attnum);
                hw.setDate(Util.date_format.format(start_date), Util.date_format.format(end_date));
                hwlist.add(hw);
            }
            System.out.println((hwnum+1)+".back");
            return true;
        }catch(Throwable oops){
            oops.printStackTrace();
            System.err.println("Error: Get Open Homework");
        }
        System.out.println("there is no open course about this course");
        return false;
    }
    //show questions from question pool  whose topic is related to the topic relavent to the course; 
    boolean showQuestion()
    {
        cqlist.clear();
        int qnum=0;
        DBcontrol.query("select * from c_topic c,q_topic q,question qq where c.tid=q.tid and q.qid=qq.qid and token='"+this.token+"'");
        try{
            while(DBcontrol.rs.next())
            {
                qnum++;
                int qid = DBcontrol.rs.getInt("qid");
                String qcontent = DBcontrol.rs.getString("qcontent");
                String longexp = DBcontrol.rs.getString("longexp");
                System.out.println(qnum+".Q"+qid+"\t"+qcontent);
                Question q = new Question(qid,qcontent,longexp);
                cqlist.add(q);
            }
            System.out.println((qnum+1)+".back");
            return true;
        }catch(Throwable oops){
            oops.printStackTrace();
            System.err.println("Error: Get Question");
        }
        System.out.println("there is no question about this course");
        return false;
    }
    //add new topic to the course
    void linkTopic(int tid)
    {
        if(DBcontrol.update("insert into c_topic values ('"+this.token+"',"+tid+")"))
            System.out.println("Add topic successfully");
        else
            System.out.println("Topic already linked, please choose another topic");
    }

    boolean showStudent()
    {
        slist.clear();
        int mnum=0;
        DBcontrol.query("select * from enroll e,member m where e.mid=m.mid and e.token='"+this.token+"'");
        try{
            while(DBcontrol.rs.next())
            {
                mnum++;
                String mid = DBcontrol.rs.getString("mid"); 
                String mname = DBcontrol.rs.getString("mname");
                System.out.println(mnum+"."+mid+"\t"+mname);
                Student s = new Student(mid,mname);
                slist.add(s);
            }
            System.out.println((mnum+1)+".back");
            return true;
        }catch(Throwable oops){
            System.err.println("Error: show student");
        }
        return false;
    }

    boolean viewScore(int role, String mid)
    {
        if(showHomework(role,mid))
        {
            int choice = Util.inputInt("");
            if(choice == this.hwlist.size()+1)
                return false;
            if(choice > this.hwlist.size()+1 || choice <0)
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
            else
            {
                Homework temp = this.hwlist.get(choice-1);
                System.out.println("Score: "+temp.score+"\n");
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return true;
    }

    boolean viewPastSubmit(int role,String mid)
    {
        if(showPastHomework(mid))
        {
            int choice = Util.inputInt("");
            if(choice == this.hwlist.size()+1)
                return false;
            else if(choice > this.hwlist.size()+1 || choice <0)
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
            else
            {
                Homework temp = this.hwlist.get(choice-1);
                temp.showReport(role);
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return false;
    }



}
