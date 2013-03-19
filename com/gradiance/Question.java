package com.gradiance;

import java.io.*;
import java.util.*;


class Question{
    public int tid;
    public String content;
    public static int maxqid;
    static{
        maxqid = 0;
    }
    public ArrayList<Answer> canslist = new ArrayList<Answer>();
    public ArrayList<Answer> incanslist = new ArrayList<Answer>();

    Question(){}
    Question(int tid)
    {
        this.tid = tid;
        init();
    }
    Question(int qid,String content)
    {
        this.qid = qid;
        this.content = content;
    }
    void init()
    {
        try{
            DBconrtol.query("select qid from question where qid >= all (select qid from question)");
            if(DBcontrol.rs.next())
                maxqid = DBcontrol.getInt("qid")+1;
            else
                maxqid = 1;
        }catch(Throwable oops){
            System.err.println("query question error");
        }
    }
    void add()
    {
        String q = Util.c.readLine("Enter question");
        String longexp = Util.c.readLine("Enter an explaination for the question");
        int qdif = Util.inputInt("Enter the difficulty level for the question");
        String cans = Util.c.readLine("Enter the correct answer");
        String shortexp1 = Util.c.readLine("Enter an explaination for the answer");
        String incans1 = Util.c.readLine("Enter the first wrong answer");
        String shortexp2 = Util.c.readLine("Enter an explaination for the answer");
        String incans2 = Util.c.readLine("Enter the second wrong answer");
        String shortexp3 = Util.c.readLine("Enter an explaination for the answer");
        String incans3 = Util.c.readLine("Enter the third wrong answer");
        String shortexp4 = Util.c.readLine("Enter an explaination for the answer");
        DBcontrol.update("insert into question(qid,qcontent,qdif,longexp) values ("+maxqid+"'"+q+"',"+qdif+",'"+longexp+"')");
        DBcontrol.update("insert into q_topic values("+tid+","+maxqid+")");
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+cans+"','t','"+shortexp1+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans1+"','f','"+shortexp2+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans2+"','f','"+shortexp3+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans3+"','f','"+shortexp4+"')");
        maxansid++;
        maxqid++;
        
    }

}
