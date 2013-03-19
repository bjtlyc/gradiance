package com.gradiance;
import java.sql.*;
import java.io.*;


class Prof extends User{
    Prof(){}
    Prof(String mid, String name,int role)
    {
        super(mid,name,role);
    }
    boolean aboutCourse(Course course)
    {
        System.out.println("Course Options:\n1.View Scores\n2.Attempt Homework\n3.View Past Submission");

       InputStreamReader cin = new InputStreamReader(System.in);
       int choice = cin.read();
       switch(choice)
       {
           case '1':
               while(addHomework(course))
                   continue;
               break;
           case '2':
               while(editHomework(course))
                   continue;
               break;
           case '3':
               while(addQuestion(course))
                   continue;
               break;
           case '4':
               while(addAnswer(course))
                   continue;
               break;
           case '5':
               while(report(course))
                   continue;
               break;
           case '6':
               return false;
           default:
               return true;
       }
       return true;
    }

    boolean addCourse()
    {
        Console c = System.console();
        if(c==null)
        {
            System.err.println("no console");
            System.exit(0);
        }
        String cid = c.readLine("Please enter the course id");
        String course_token = c.readLine("Please enter the course token");
        String cname = c.readLine("Please enter the course name");
        Date start_date=null,end_date=null;
        while(start_date==null)
            start_date = Util.checkDate(c.readLine("Please enter the start date(in the format of dd-MMM-yy(27-Apr-91))"));
        while(end_date==null)
            end_date = Util.checkDate(c.readLine("Please enter the end date(in the format of dd-MMM-yy(27-Apr-91))"));
        try{
                Course course = new Course(cid,cname,token,mid);
                String u = "insert into course values ('"+mid+"','"+course_token+"','"+course_token+"','"+course_token+"',)";
                DBcontrol.update(u);
                while(aboutCourse(course))
                    continue;
                return false;
        }catch(Throwable oops){
            System.out.println("Error when add course");
            return true;
        }
    }

    boolean addHomework(Course course)
    {
        int hid = Homework.getid(); 
        String hwtitle = c.readLine("Please enter the title for the homework");
        Date start_date=null,end_date=null;
        String start_date_str,end_date_str;
        while(true)
        {
            while(start_date==null)
            {
                start_date_str = c.readLine("Please enter the start date(in the format of YYYYMMDD)");
                start_date = Util.parseDate(start_date_str);
            }
            while(end_date==null)
            {
                end_date_str = c.readLine("Please enter the end date(in the format of YYYYMMDD)");
                end_date = Util.parseDate(end_date_str);
            }
            if(start_date.after(end_date))
                continue;
            else
            {
                System.out.println("The date is invalid");
                break;
            }
        }
        int attemptnum=inputInt("Please enter the number of attempts allowed: ");
        int score_selection=inputInt("Please enter one of the score selection way: \n1.latest\n2.highest ");
        int qnum=inputInt("Please enter the number of questions: ");
        int r_ans_p=inputInt("Please enter the points for right answer: ");
        int w_ans_p=inputInt("Please enter the points for wrong answer: ");
        
        try{
                //Homework hw = new Homework(hwid,start_date.,end_date,attemptnum,score_selection,qnum,r_ans_p,w_ans_p);
                String u = "insert into course values ('"+token+"',"+hwid+",'"+hwtitle+"',"+attemptnum+",'"+start_date+"','"+end_date+"',"+qnum+","+r_ans_p+","+w_ans_p+","+score_selection+")";
                DBcontrol.update(u);
                System.out.println("Add a new course successfully.");
                //while(aboutHomework(hw))
                //    continue;
                return false;
        }catch(Throwable oops){
            System.out.println("Error when add Homework");
            return true;
        }
    }

    boolean editHomework(Course course)
    {
        if(course.showHomework(1))
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            if(choice == course.hwnum+1 )
                return false;
            else if(choice > course.hwnum || choice < 1)
            {
                System.out.println("Invalid choice");
                return true;
            }
            else
            {
                Homework hw = course.hwlist.get(choice-1);
                while(hw.edit())
                    continue;
            }                
        }
        return false;
    }

    boolean addQuestion(Course course)
    {
        if(course.showTopic())
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            if(choice == topic_num+1 )
                return false;
            else if(choice > topic_num || topic_num < 1)
            {
                System.out.println("Invalid choice");
                return true;
            }
            else
            {
                Question q = new Question(course.list.get(choice-1));
                q.add();
            }                
        }
        else
            System.out.println("There is no topic");
        return false;

    }
    boolean addAnswer()
    {
    }
    boolean report()
    {
        String q = Util.c.readLine("Please enter the query");
        //DBcontrol.query();
    }

}
