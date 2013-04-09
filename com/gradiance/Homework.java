package com.gradiance;

import java.io.*;
import java.util.*;


class Homework{
    public int hwid;
    public String mid;
    public String token;
    public String start_date;
    public String end_date;
    public float score;
    public int seed;
    public int retrynum;
    public int attnum;
    public String ssmethod;//first,last,max,avg
    public int qnum;
    public float point;
    public float penalty;
    public ArrayList<Question> qlist = new ArrayList<Question>();
    public HashMap<Integer,Integer> ans=new HashMap<Integer,Integer>();

    Homework(){}
    Homework(int hwid,String token, String start_date,String end_date,int qnum,int retrynum,float point,float penalty,String ssmethod)
    {
        this.hwid = hwid;
        this.token = token;
        this.start_date = start_date;
        this.end_date = end_date;
        this.qnum = qnum;
        this.retrynum = retrynum;
        this.point = point;
        this.penalty = penalty;
        this.ssmethod = ssmethod;
    }
    Homework(int hwid,String mid,String token,int qnum,int retrynum,float point,float penalty,String ssmethod,int seed,int attnum, float score)
    {
        this.hwid = hwid;
        this.mid = mid;
        this.token = token;
        this.qnum = qnum;
        this.retrynum = retrynum;
        this.point = point;
        this.penalty = penalty;
        this.ssmethod = ssmethod;
        this.seed = seed;
        this.attnum = attnum;
        this.score = score;
    }
    Homework(int hwid,String token, float score,int attnum)
    {
        this.hwid = hwid;
        this.token = token;
        this.score = score;
        this.attnum = attnum;
    }

    void setDate(String start_date, String end_date)
    {
        this.start_date = start_date;
        this.end_date = end_date;
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
        int choice = Util.inputInt("Choose what to update:\n1.Start date\n2.End date\n3.Number of attempts\n4.Score selection\n5.Question numbers\n6.Correct answer points\n7.Incorrect answer points\n8.Assign questions\n9.Show Question\n10.back");
        switch(choice)
        {
            case 1:
                
                Date start_date=null,end_date=null;
                String start_date_str=null;
                end_date=Util.parseDate(this.end_date);
                System.out.println("Current start date: "+this.start_date+"\n");
                while(start_date==null)
                {
                    start_date_str = Util.c.readLine("Please enter the new start date(in the format of dd-MMM-yy(27-Apr-91))");
                    start_date = Util.parseDate(start_date_str);
                }
                if(start_date.after(end_date))
                {
                    System.err.println("The start date is not valid, later than end date");
                    return true;
                }
                else 
                    if(DBcontrol.update("update homework set hwstart='"+start_date_str+"' where hwid="+this.hwid+" and token='"+this.token+"'"))
                    {
                        this.start_date = start_date_str;
                        System.out.println("Update successfully\n");
                    }
                return false;
            case 2:
                end_date=null;
                start_date=Util.parseDate(this.start_date);
                String end_date_str=null;
                System.out.println("Current end date: "+this.end_date+"\n");
                while(end_date==null)
                {
                    end_date_str = Util.c.readLine("Please enter the new end date(in the format of dd-MMM-yy(27-Apr-91))");
                    end_date = Util.parseDate(end_date_str);
                }
                if(start_date.after(end_date))
                {
                    System.err.println("The start date is not valid, later than end date");
                    return true;
                }
                else
                    if(DBcontrol.update("update homework set hwend='"+end_date_str+"' where hwid="+this.hwid+" and token='"+this.token+"'"))
                    {
                        this.end_date = end_date_str;
                        System.out.println("Update successfully\n");
                    }
                return false;
            case 3:
                System.out.println("Current attempt limit is: "+this.retrynum+"\n");
                int attnum = Util.inputInt("Enter the number of attempts");
                if(attnum < 0)
                {
                    System.out.println("Error: negative number, try again");
                    return true;
                }
                else
                    if(DBcontrol.update("update homework set retrynum="+attnum+" where hwid="+this.hwid+" and token='"+this.token+"'"))
                    {
                        this.retrynum = attnum;
                        System.out.println("Update successfully\n");
                    }
                return false;
            case 4:
                System.out.println("Current score method is: "+this.ssmethod+"\n");
                int score_method = Util.inputInt("Enter score method\n1.first\n2.last\n3.max\n4.avg");
                if(score_method == 1 )
                {
                    DBcontrol.update("update homework set ssmethod='first' where hwid="+this.hwid+" and token='"+this.token+"'");
                    this.ssmethod = "first";
                }
                else if (score_method == 2)
                {
                    DBcontrol.update("update homework set ssmethod='last' where hwid="+this.hwid+" and token='"+this.token+"'");
                    this.ssmethod = "last";
                }
                else if (score_method == 3)
                {
                    DBcontrol.update("update homework set ssmethod='max' where hwid="+this.hwid+" and token='"+this.token+"'");
                    this.ssmethod = "max";
                }
                else if (score_method == 4)
                {
                    DBcontrol.update("update homework set ssmethod='avg' where hwid="+this.hwid+" and token='"+this.token+"'");
                    this.ssmethod = "avg";
                }
                else
                {
                    System.out.println("Error: invalid choice, try again");
                    return true;
                }
                System.out.println("Update successfully\n");
                return false;
            case 5:
                System.out.println("Current question number is: "+this.qnum+"\n");
                int qnum = Util.inputInt("Enter question number");
                if(qnum < 1) 
                {
                    System.out.println("Error: Question number must larger than 1, try again");
                    return true;
                }
                else
                    if(DBcontrol.update("update homework set qnum="+qnum+" where hwid="+this.hwid+" and token='"+this.token+"'"))
                    {
                        this.qnum = qnum;
                        System.out.println("Update successfully\n");
                    }
                return false;
            case 6:
                System.out.println("Current correct answer point is: "+this.point+"\n");
                float corretpoint = Util.inputFloat("Enter correct answer points");
                if(corretpoint < 0) 
                    System.out.println("Warning: corret answer earns negative points");
                else
                    if(DBcontrol.update("update homework set point="+corretpoint+" where hwid="+this.hwid+" and token='"+this.token+"'"))
                    {
                        this.point = corretpoint ;
                        System.out.println("Update successfully\n");
                    }
                return false;
            case 7:
                System.out.println("Current incorrect answer point is: "+this.penalty+"\n");
                float penalty = Util.inputFloat("Enter incorrect answer points");
                if(DBcontrol.update("update homework set penalty="+penalty+" where hwid="+this.hwid+" and token='"+this.token+"'"))
                {
                    this.penalty = penalty;
                    System.out.println("Update successfully\n");
                }
                return false;
            case 8:
                while(addQuetoHw())
                    continue;
                return false;
            case 9:
                while(showQuestion())
                    continue;
                return false;
            case 10:
                return false;
            default:
                return true;
        }
    }

    boolean delete()
    {
        if(DBcontrol.update("delete from homework where hwid="+this.hwid+" and token='"+this.token+"'"))
            System.out.println("Delete homework successfully");
        else
            System.out.println("Delete error");
        return false;
    }

    boolean doHomework()
    {
        int curattnum=0;
        DBcontrol.query("select max(attnum) from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        //System.out.println("select max(attnum) from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        try{
            if(DBcontrol.rs.next())
            {
                curattnum = DBcontrol.rs.getInt("max(attnum)");
                //System.out.println(retrynum+"before: "+ curattnum);
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

        getQlist();

        this.seed = Util.rg.nextInt(500);
        Util.randomlist(this.seed,qlist);
        int [] record = new int[4];
        int truenum=0,falsenum=0,i=0;
        String currdate = Util.date_format.format(new Date()); 
        DBcontrol.update("insert into report values ('"+this.token+"',"+this.hwid+",'"+this.mid+"',"+curattnum+","+this.seed+","+this.score+",'"+currdate+"')");
        while(!qlist.isEmpty())
        {
            Question q = qlist.remove(this.seed%(qlist.size()));
            int rindex = (this.seed + q.qid) % 4;
            System.out.println(q.content);
            int select = 0, incnum=0;
            while(select < 4)
            {
                if(select==rindex)
                {
                    System.out.println((select+1)+"."+q.canslist.get(0).anscontent);
                    record[select++]=q.canslist.get(0).ansid;
                }
                else
                {
                    System.out.println((select+1)+"."+q.incanslist.get(incnum).anscontent);
                    record[select++]=q.incanslist.get(incnum++).ansid;
                }
            }
            int choice = Util.inputInt("select your answer");
            String text = Util.c.readLine("enter some explaination: ");
            if(choice == rindex+1)
            {
                truenum++;
                System.out.println("True");
            }
            else
            {
                falsenum++;
                System.out.println("False");
            }
            DBcontrol.update("insert into stuans values ('"+this.token+"',"+this.hwid+",'"+this.mid+"',"+curattnum+","+q.qid+","+record[choice-1]+",'"+text+"')");
        }
        this.score = truenum*point + falsenum*penalty;
        DBcontrol.update("update report set rscore="+this.score+" where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"' and attnum="+curattnum);

        //if(curattnum==this.retrynum)
        calculateScore(curattnum);
        System.out.println("Score: "+this.score+"\n");
        return false;

    }

    boolean showReport(int role)
    {
        getQlist();
        getStuAns();
        Date end_date =null;
        end_date=Util.parseDate(this.end_date);

        int seed = this.seed;
        Util.randomlist(seed,qlist);
        //int rindex=seed % 4;
        int [] record = new int[4];
        int truenum=0,falsenum=0,i=0;
        int yourchoice=0;
        while(!qlist.isEmpty())
        {
            Question q = qlist.remove(seed%(qlist.size()));
            int rindex = (seed + q.qid) % 4;
            System.out.println(q.content);
            int select = 0, incnum=0;
            while(select < 4)
            {
                if(select==rindex)
                {
                     System.out.println((select+1)+"."+q.canslist.get(0).anscontent+"\tCorrect answer: "+q.canslist.get(0).shortexp);
                        //System.out.println((select+1)+"."+q.canslist.get(0).anscontent);
                    if(ans.get((Integer)q.qid) == q.canslist.get(0).ansid) 
                        yourchoice = select;
                    record[select++]=q.canslist.get(0).ansid;
                }
                else
                {
                    System.out.println((select+1)+"."+q.incanslist.get(incnum).anscontent+"\t"+q.incanslist.get(incnum).shortexp);
                        //System.out.println((select+1)+"."+q.incanslist.get(incnum).anscontent);
                    try{
                    if(ans.get((Integer)q.qid) == q.incanslist.get(incnum).ansid) 
                        yourchoice = select;
                    record[select++]=q.incanslist.get(incnum++).ansid;
                    }catch(NullPointerException e){System.out.println(ans.size());}
                }
            }
            System.out.println("Your choice: "+(yourchoice+1)+"\tCorrect Answer: "+(rindex+1));
            if(role == 1 || end_date.before(new Date()))
                System.out.println("Explaination: "+q.longexp+"\n");
        }
        System.out.println("Score: "+this.score+"\n");
        return true;
    }

    //show the question in a homework
    boolean showQuestion()
    {
        getQlist();
        System.out.println("Please select a question to see its answers");
        for(int i=0;i<qlist.size();i++)
        {
            System.out.println((i+1)+". "+qlist.get(i).qid+" "+qlist.get(i).content);
        }
        System.out.println((qlist.size()+1)+".Back");
        int choice = Util.inputInt("");
        if(choice == qlist.size()+1)
            return false;
        else if(choice < 1 || choice > qlist.size()+1)
        {
            System.out.println("Invalid choice");
            return true;
        }
        else
        {
            Question q = qlist.get(choice-1);
            q.showAnswer();
            return false;
        }
        

    }

    void getQlist()
    {
        qlist.clear();
        String qq = "select * from homework h,hw_ques hq,question q where h.hwid=hq.hwid and hq.qid=q.qid and h.token=hq.token and h.token='"+this.token+"' and h.hwid="+hwid;
        //System.out.println(qq);
        DBcontrol.query(qq);
        try{
            while(DBcontrol.rs.next())
            {
                int qid = DBcontrol.rs.getInt("QID");
                String content = DBcontrol.rs.getString("qcontent");
                String longexp = DBcontrol.rs.getString("longexp");
                Question q = new Question(qid,content,longexp);
                qlist.add(q);
            }
        }catch(Throwable oops){
            oops.printStackTrace();
            System.out.println("Error");
        }



        System.out.println("--------debug: number of question: "+qlist.size()+"----------");
        //System.out.println("-------- query: "+qq+"----------");

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
            //System.out.println(qlist.get(i).canslist.size()+" "+qlist.get(i).incanslist.size());
            }catch(Throwable oops){
                System.out.println("");
            }
        }

    }

    public void getStuAns()
    {
        ans.clear();
        DBcontrol.query("select a.qid,a.ansid,b.TorF from stuans a,answer b where a.token='"+this.token+"' and a.hwid="+this.hwid+" and a.attnum="+this.attnum+" and a.mid='"+this.mid+"' and a.qid=b.qid and a.ansid=b.ansid");
        try{
            while(DBcontrol.rs.next())
            {
                int qid = DBcontrol.rs.getInt("qid");
                int ansid = DBcontrol.rs.getInt("ansid");
                String torf = DBcontrol.rs.getString("TorF");
                ans.put(new Integer(qid),ansid);
            }
        }catch(Throwable oops){
            oops.printStackTrace();
        }
    }

    public boolean addQuetoHw()
    {
        DBcontrol.query("select count(*) from hw_ques hq where hq.hwid="+this.hwid+" and token='"+this.token+"'");
        try{
            if(DBcontrol.rs.next())
            {
                int quenum = DBcontrol.rs.getInt("count(*)");
                System.out.println("question limit: "+this.qnum+" Already has "+quenum+" questions");
                if(this.qnum<=quenum)
                {
                    System.out.println("Already reach question limit");
                    return false;
                }
            }
        }catch(Throwable oops)
        {;}
        Course course = new Course(this.token);
        if(course.showQuestion())
        {
            int choice = Util.inputInt("");
            if(choice == course.cqlist.size()+1 )
                return false;
            else if(choice > course.cqlist.size() || choice < 1)
            {
                System.out.println("Invalid choice");
                return true;
            }
            else
            {
                Question q = course.cqlist.get(choice-1);
                //Homework hw = course.hwlist.get(choice-1);
                if(DBcontrol.update("insert into hw_ques values('"+this.token+"',"+this.hwid+","+q.qid+")"))
                    System.out.println("Assign question successfully");
                else
                    System.out.println("The question has been assigned, choose another one");
            }
        }
        else
            System.out.println("There is no Question");
        return false;
    }

    public void calculateScore(int curattnum)
    {
        float score=0, maxscore=0, lastscore=0, firstscore=0;
        int num=0;
        DBcontrol.query("select rscore from report where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"' order by rtime");
        try{
            float temp=0;
            while(DBcontrol.rs.next())
            {
                temp = DBcontrol.rs.getFloat("rscore");
                maxscore = maxscore>temp?maxscore:temp;
                score = score + temp;
                num++;
                if(num==1)
                    firstscore=temp;
            }
            lastscore=temp;
        }catch(Throwable oops){
            System.out.println("ERROR");
        }
        if(ssmethod.equals("avg"))
            score = score/num;
        else if(ssmethod.equals("max"))
            score = maxscore;
        else if(ssmethod.equals("last"))
            score = lastscore;
        else if(ssmethod.equals("first"))
            score = firstscore;
        DBcontrol.update("update hw_mem set hwscore="+score+",totalatt="+curattnum+" where token='"+this.token+"' and hwid="+this.hwid+" and mid='"+this.mid+"'");
        //System.out.println("Homework score: "+score+"\n");

    }


}
