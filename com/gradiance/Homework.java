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
    public String ssmethod;//first,last,max,avg
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
    Homework(int hwid,String mid,String token,int qnum,int retrynum,int point,int penalty,String ssmethod)
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

    static int getid(String token)
    {
        try{
            DBcontrol.query("select max(hwid) from homework where token='"+token+"'");
            if(DBcontrol.rs.next())
                return DBcontrol.rs.getInt("max(hwid)")+1;
        }catch(Throwable oops){
            System.err.println("ERROR: get homework id");
        }
        return 1;
    }

    boolean edit()
    {
        int choice = Util.inputInt("Choose what to update:\n1.Start date\n2.End date\n3.Number of attempts\n4.Score selection\n5.Question numbers");
        switch(choice)
        {
            case 1:
                Date start_date=null,end_date=null;
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
            case 2:
                end_date=null;
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
            case 3:
                int attnum = Util.inputInt("Enter the number of attempts");
                if(attnum < 0)
                {
                    System.out.println("Error: negative number, try again");
                    return true;
                }
                else
                    DBcontrol.update("update homework set retrynum="+attnum+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
            case 4:
                int score_method = Util.inputInt("Enter score method\n1.first\n2.last\n3.max\n4.avg");
                if(score_method == 1 )
                    DBcontrol.update("update homework set ssmethod='first' where hwid="+this.hwid+" and token='"+this.token+"'");
                else if (score_method == 2)
                    DBcontrol.update("update homework set ssmethod='last' where hwid="+this.hwid+" and token='"+this.token+"'");
                else if (score_method == 3)
                    DBcontrol.update("update homework set ssmethod='max' where hwid="+this.hwid+" and token='"+this.token+"'");
                else if (score_method == 4)
                    DBcontrol.update("update homework set ssmethod='avg' where hwid="+this.hwid+" and token='"+this.token+"'");
                else
                {
                    System.out.println("Error: invalid choice, try again");
                    return true;
                }
                return false;
            case 5:
                int qnum = Util.inputInt("Enter question number");
                if(qnum < 1) 
                {
                    System.out.println("Error: Question number must larger than 1, try again");
                    return true;
                }
                else
                    DBcontrol.update("update homework set qnum="+qnum+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
            case 6:
                float corretpoint = Util.inputFloat("Enter correct answer points");
                if(corretpoint < 0) 
                    System.out.println("Warning: corret answer earns negative points");
                else
                    DBcontrol.update("update homework set point="+corretpoint+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
            case 7:
                float penalty = Util.inputFloat("Enter incorrect answer points");
                DBcontrol.update("update homework set penalty="+penalty+" where hwid="+this.hwid+" and token='"+this.token+"'");
                return false;
            case 8:
                break;
            case 9:
                return false;
            default:
                return true;
        }
        return true;
    }

    boolean doHomework()
    {
        int curattnum=0;
        DBcontrol.query("select max(attnum) from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        System.out.println("select max(attnum) from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        try{
            if(DBcontrol.rs.next())
            {
                curattnum = DBcontrol.rs.getInt("max(attnum)");
                System.out.println("before: "+ curattnum);
                curattnum = DBcontrol.rs.getInt("max(attnum)");
                System.out.println("before: "+ curattnum);
                if(curattnum >= retrynum)
                {
                    System.out.println("already reach sumbit limit");
                    return false;
                }
            }
            else
                curattnum = 1; 
        }catch(Throwable oops){
            System.out.println("Error");
        }
        curattnum++;
        String qq = "select * from homework h,hw_ques hq,question q where h.hwid=hq.hwid and hq.qid=q.qid and h.token=hq.token and h.token='"+this.token+"' and h.hwid="+hwid;
        System.out.println(qq);
        DBcontrol.query(qq);
        try{
            while(DBcontrol.rs.next())
            {
                int qid = DBcontrol.rs.getInt("QID");
                String content = DBcontrol.rs.getString("qcontent");
                Question q = new Question(qid,content);
                qlist.add(q);
            }
        }catch(Throwable oops){
            oops.printStackTrace();
            System.out.println("");
        }

        System.out.println(qlist.size());

        for(int i=0;i<qlist.size();i++)
        {
            DBcontrol.query("select * from answer a where a.qid="+qlist.get(i).qid);
            try{
                while(DBcontrol.rs.next())
                {
                    int ansid = DBcontrol.rs.getInt("ansid");
                    String anscontent = DBcontrol.rs.getString("anscontent");
                    String shortexp = DBcontrol.rs.getString("shortexp");
                    String torf = DBcontrol.rs.getString("torf");
                    Answer a = new Answer(ansid,anscontent,shortexp);
                    if(torf.equals("T"))
                        qlist.get(i).canslist.add(a);
                    else
                        qlist.get(i).incanslist.add(a);
                }
            System.out.println(qlist.get(i).canslist.size()+" "+qlist.get(i).incanslist.size());
            }catch(Throwable oops){
                System.out.println("");
            }
        }

        int seed = Util.rg.nextInt(qlist.size());
        int seed1 = seed;
        int seed2 = seed;
        int [] record = new int[4];
        int truenum=0,falsenum=0,i=0;
        while(!qlist.isEmpty())
        {
            int rindex=0;
            Question q = qlist.remove(seed1);
            System.out.println(q.content);
            int select = 0;
            if(seed2 >= 4)
                seed2 = seed2 % 4;
            while(select < 4)
            {
                if(seed2==0)
                {
                    System.out.println((select+1)+"."+q.canslist.get(0).anscontent);
                    rindex=select+1;
                    seed2=3;
                    record[select++]=0;
                }
                else
                {
                    System.out.println((select+1)+"."+q.incanslist.get(seed2).anscontent);
                    seed2--;
                    record[select++]=seed2;
                }
            }
            int choice = Util.inputInt("select your answer");
            String text = Util.c.readLine("enter some explaination");
            if(choice == rindex)
            {
                truenum++;
                DBcontrol.update("insert into stuans values ('"+this.token+"',"+this.hwid+",'"+this.mid+"',"+curattnum+","+q.qid+","+q.canslist.get(0).ansid+",'"+text+"')");
                System.out.println("True");
            }
            else
            {
                falsenum++;
                DBcontrol.update("insert into stuans values ('"+this.token+"',"+this.hwid+",'"+this.mid+"',"+curattnum+","+q.qid+","+q.incanslist.get(record[choice-1]).ansid+",'"+text+"')");
                System.out.println("False");
            }
            if(seed1>=qlist.size())
                seed1--;
            i++;
        }
        String currdate = Util.date_format.format(new Date()); 
        int score = truenum*point + falsenum*penalty;
        DBcontrol.update("insert into report values ('"+this.token+"',"+this.hwid+",'"+this.mid+"',"+curattnum+","+seed+","+score+",'"+currdate+"')");
        return true;
    }

    boolean showReport()
    {
        return true;
    }

}
