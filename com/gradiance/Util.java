package com.gradiance;
import java.io.*;
import java.text.*;
import java.util.*;

class Util{

    public static Console c;
    public static Random rg;
    public static SimpleDateFormat date_format = new SimpleDateFormat("dd-MMM-yy");
    static{
        c = System.console();
        if(c==null)
            System.err.println("No console");
        rg = new Random();
    }
    public static Scanner sc = new Scanner(c.reader());
    
    static Date parseDate(String str)
    {
        try{
            Date date = date_format.parse(str);
            return date;
        }catch(ParseException e){
            System.err.println("The date is invalid");
            return null;
        }
    }


    static int inputInt(String str)
    {
        System.out.println(str);
        while(!sc.hasNextInt()){
            System.out.println("This is not a valid number");
            sc.next();
        }
        return sc.nextInt();
    }

    static float inputFloat(String str)
    {
        System.out.println(str);
        while(!sc.hasNextFloat()){
            System.out.println("This is not a valid number");
            sc.next();
        }
        return sc.nextFloat();
    }
    //randomize the answers for every question. remove extra answers, keep 3 wrong answer and 1 correct answer.
    static void randomlist(int seed, ArrayList<Question> qlist)
    {
        for(int i=0;i<qlist.size();i++)
        {
            Question q = qlist.get(i);
            while(q.canslist.size()>1)
                q.canslist.remove(seed%(q.canslist.size()));
            while(q.incanslist.size()>3)
                q.incanslist.remove(seed%(q.incanslist.size()));
        }
    }
}
