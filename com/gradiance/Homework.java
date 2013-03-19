package com.gradiance;

import java.io.*;
import java.util.*;


class Homework{
    public int hwid;
    public String mid;
    public String token;
    public String start_date;
    public String end_date;
    public int score;
    public int retrynum;
    public int ssmethod;
    public int qnum;
    public int point;
    public int penalty;
    public ArrayList<Question> qlist = new ArrayList<Question>();

    Homework(){}
    Homework(int hwid,String token, String start_date,String end_date)
    {
        this.hwid = hwid;
        this.token = token;
        this.start_date = start_date;
        this.end_date = end_date;
    }
    Homework(int hwid,String mid,String token,int qnum,int retrynum,int point,int penalty,int ssmethod)
    {
        this.hwid = hwid;
        this.mid = mid;
        this.token = token;
        this.qnum = qnum;
        this.retrynum = retrynum;
        this.point = point;
        this.penalty = penalty;
        this.ssmethod = ssmethod;
    }
    Homework(int hwid,String token, int score)
    {
        this.hwid = hwid;
        this.token = token;
        this.score = score;
    }

    boolean edit()
    {
        int choice = Util.inputInt("Choose what to update:\n1.Start date\n2.End date\n3.Number of attempts\n4.Score selection\n5.Question numbers");
        switch(choice)
        {
            case 1:
                Date start_date=null,end_date;
                end_date=Util.parseDate(this.end_date);
                while(start_date==null)
                {
                    this.start_date = Util.c.readLine("Please enter the start date(in the format of dd-MMM-yy(27-Apr-91))");
                    start_date = Util.parseDate(this.start_date);
                }
                if(start_date.after(end_date))
                {
                    System.err.println("The start date is not valid, later than end date");
                    return true;
                }
                else
                    DBcontrol.update("update report set hwstart='"+this.start_date+"' where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break;
            case 2:
                Date end_date=null,start_date;
                start_date=Util.parseDate(this.start_date);
                while(end_date==null)
                {
                    this.end_date = Util.c.readLine("Please enter the end date(in the format of dd-MMM-yy(27-Apr-91))");
                    end_date = Util.parseDate(this.end_date);
                }
                if(start_date.after(end_date))
                {
                    System.err.println("The start date is not valid, later than end date");
                    return true;
                }
                else
                    DBcontrol.update("update report set hwend='"+this.end_date+"' where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break;
            case 3:
                int attnum = inputInt("Enter the number of attempts");
                if(attnum < 0)
                {
                    System.out.println("Error: negative number, try again");
                    return true;
                }
                else
                    DBcontrol.update("update homework set retrynum="+attnum+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break;
            case 4:
                int socre_method = inputInt("Enter score method\n1.hightest\n2.average");
                if(score_method ==1 || score_method ==2)
                    DBcontrol.update("update homework set ssmethod="+score_method+" where hwid="+this.hwid+" and token='"+this.token+"'");
                else
                {
                    System.out.println("Error: invalid choice, try again");
                    return true;
                }
                return false;
                break;
            case 5:
                int qnum = inputInt("Enter question number");
                if(score_method < 1) 
                {
                    System.out.println("Error: Question number must larger than 1, try again");
                    return true;
                }
                else
                    DBcontrol.update("update homework set qnum="+qnum+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break; 
            case 6:
                float corretpoint = inputFloat("Enter correct answer points");
                if(score_method < 0) 
                    System.out.println("Warning: corret answer earns negative points");
                else
                    DBcontrol.update("update homework set point="+corretpoint+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break;
            case 7:
                float penalty = inputFloat("Enter incorrect answer points");
                DBcontrol.update("update homework set penalty="+penalty+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
                break;
            case 8:
                break;
            case 9:
                return false;
                break;
            default:
                return true;
                break;
        }
        
    }

    boolean doHomework()
    {
        int curattnum;
        DBcontrol.query("select max(attnum) from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        if(DBcontrol.rs.next())
        {
            try{
                curattnum = DBcontrol.rs.getInt("max(attnum)");
                if(maxattnum >= retrynum)
                {
                    System.out.println("already reach sumbit limit");
                    return false;
                }
            }catch(Throwable oops){
                System.out.println("Error");
            }
        }
        else
            curattnum = 1; 
        DBcontrol.query("select * from homework h,hw_ques hq,question q where h.hwid=hq.hwid and hq.qid=q.qid and h.token=hq.token and h.token='"+this.token+"'");
        try{
            while(DBcontrol.rs.next())
            {
                int qid = DBcontrol.rs.getInt("qid");
                String content = DBcontrol.rs.getString("qcontent");
                Question q = new Question(qid,content);
                DBcontrol.query("select * from answer a where a.qid="+qid);
                try{
                    while(DBcontrol.rs.next())
                    {
                        int ansid = DBcontrol.rs.getInt("ansid");
                        String anscontent = DBcontrol.rs.getString("anscontent");
                        String shortexp = DBcontrol.rs.getString("shortexp");
                        String torf = DBcontrol.rs.getString("torf");
                        Answer a = Answer(ansid,anscontent,shortexp);
                        if(torf.equals('t'))
                            q.canslist.add(anscontent);
                        else
                            q.incanslist.add(anscontent);
                    }
                    qlist.add(q);
                }catch(Throwalbe oops){
                    System.out.println("");
                }
            }
        }catch(Throwable oops){
                System.out.println("");
        }
        int seed = Util.rg.nextInt(this.qnum);
        int seed1 = seed;
        while(!qlist.isEmpty())
        {
            System.out.println(qlist.get(seed).content);
            qlist.remove(seed);
            if(seed>qlist.size())
                seed = seed % qlist.size();
        }
    }

}
