package com.gradiance;

import java.io.*;
import java.util.*;
import java.sql.*;


class Question{
    public int tid;
    public int qid;
    public String content;
    public String longexp;
    public static int maxqid;
    public static int maxansid;
    static{
        try{
            DBcontrol.query("select max(qid) from question");
            if(DBcontrol.rs.next())
                maxqid = DBcontrol.rs.getInt("max(QID)")+1;
            else
                maxqid = 1;
            DBcontrol.query("select max(ansid) from answer");
            if(DBcontrol.rs.next())
                maxansid = DBcontrol.rs.getInt("max(ANSID)")+1;
            else
                maxansid = 1;
        }catch(Throwable oops){
            oops.printStackTrace();
            System.err.println("query question error");
        }
    }
    public ArrayList<Answer> canslist = new ArrayList<Answer>();
    public ArrayList<Answer> incanslist = new ArrayList<Answer>();

    Question(){}
    Question(int tid)
    {
        this.tid = tid;
    }
    Question(int qid,String content,String longexp)
    {
        this.qid = qid;
        this.content = content;
        this.longexp = longexp;
    }
    void add()
    {
        System.out.println(maxqid+" "+maxansid);
        String q = Util.c.readLine("Enter a new question: ");
        String longexp = Util.c.readLine("Enter an explaination for the question: ");
        int qdif = Util.inputInt("Enter the difficulty level for the question: ");
        String cans = Util.c.readLine("Enter the correct answer: ");
        String shortexp1 = Util.c.readLine("Enter an explaination for the answer: ");
        String incans1 = Util.c.readLine("Enter the first wrong answer: ");
        String shortexp2 = Util.c.readLine("Enter an explaination for the answer: ");
        String incans2 = Util.c.readLine("Enter the second wrong answer: ");
        String shortexp3 = Util.c.readLine("Enter an explaination for the answer: ");
        String incans3 = Util.c.readLine("Enter the third wrong answer: ");
        String shortexp4 = Util.c.readLine("Enter an explaination for the answer: ");
        DBcontrol.update("insert into question values ("+maxqid+",'"+q+"',"+qdif+",'"+longexp+"')");
        DBcontrol.update("insert into q_topic values("+tid+","+maxqid+")");
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+cans+"','T','"+shortexp1+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans1+"','F','"+shortexp2+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans2+"','F','"+shortexp3+"')");
        maxansid++;
        DBcontrol.update("insert into answer values("+maxqid+","+maxansid+",'"+incans3+"','F','"+shortexp4+"')");
        maxansid++;
        maxqid++;
        
    }
    boolean addAnswer()
    {
        int TorF = Util.inputInt("Enter answer type\n1.Correct\n2.Incorrect");
        if(TorF != 1 && TorF !=2 )
        {
            System.out.println("invalid choice");
            return true;
        }
        String answer = Util.c.readLine("Enter answer: ");
        String shortexp = Util.c.readLine("Enter a short explaination: ");
        if(TorF == 1)
            DBcontrol.update("insert into answer values( "+this.qid+","+maxansid+",'"+answer+"','T','"+shortexp+"')");
        else if(TorF == 2)
            DBcontrol.update("insert into answer values( "+this.qid+","+maxansid+",'"+answer+"','F','"+shortexp+"')");
        maxansid++;
        System.out.println("Add answer successfully");
        return false;

    }

    void showAnswer()
    {
        System.out.println("Correct Answer");
        for(int i=0;i<canslist.size();i++)
        {
            System.out.println((i+1)+"."+canslist.get(i).anscontent + "("+canslist.get(i).shortexp+")");
        }
        System.out.println("Incorrect Answer");
        for(int i=0;i<incanslist.size();i++)
        {
            System.out.println((i+1)+"."+incanslist.get(i).anscontent + "("+incanslist.get(i).shortexp+")");
        }
        

    }

}
