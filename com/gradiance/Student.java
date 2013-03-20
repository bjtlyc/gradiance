package com.gradiance;
import java.io.*;
import java.util.*;
import java.text.*;

class Student extends User{

    Student(){}
    Student(String userid, String name, int role)
    {
        super(userid,name,role);
    }
    
    boolean addCourse()
    {
        String course_token = Util.c.readLine("Please enter a course token: ").toUpperCase();
        String q = "select * from course where token='"+course_token+"'";
        DBcontrol.query(q);
        try{
            if(DBcontrol.rs.next())
            {
                String cid = DBcontrol.rs.getString("cid");
                String token = DBcontrol.rs.getString("token");
                String cname = DBcontrol.rs.getString("cname");
                Course course = new Course(cid,token,cname,this.mid);
                String u = "insert into enroll values ('"+this.mid+"','"+course_token+"','stud')";
                DBcontrol.update(u);
                System.out.println("Enroll successfully");
                while(aboutCourse(course))
                    continue;
            }
            else
            {
                System.out.println("This token is invalid");
                return false;
            }
        }catch(Throwable oops){
            System.out.println("Error when add course");
        }
        return true;
    }

    boolean aboutCourse(Course course)
    {

       int choice = Util.inputInt("Course Options:\n1.View Scores\n2.Attempt Homework\n3.View Past Submission\n4.back");
       switch(choice)
       {
           case 1:
               while(viewScore(course))
                   continue;
               break;
           case 2:
               while(attemptHomework(course))
                   continue;
               break;
           case 3:
               while(viewPastSubmit(course))
                   continue;
               break;
           case 4:
               return false;
           default:
               return true;
       }
       return true;
   }

    boolean viewScore(Course course)
    {
        if(course.showHomework(0))
        {
            int choice = Util.inputInt("");
            if(choice == course.hwlist.size()+1)
                return false;
            if(choice > course.hwlist.size()+1 || choice <0)
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
            else
            {
                Homework temp = course.hwlist.get(choice-1);
                System.out.println("Score: "+temp.score+"\n");
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return true;
    }

    boolean viewPastSubmit(Course course)
    {
        if(course.showPastHomework())
        {
            int choice = Util.inputInt("");
            if(choice == course.hwlist.size()+1)
                return false;
            else if(choice > course.hwlist.size()+1 || choice <0)
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
            else
            {
                Homework temp = course.hwlist.get(choice-1);
                temp.showReport();
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return false;
    }

    boolean attemptHomework(Course course)
    {
        if(course.showOpenHomework())
        {
            int choice = Util.inputInt("");
            if(choice == course.hwlist.size()+1)
                return false;
            else if(choice > course.hwlist.size()+1 || choice <0)
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
            else
            {
                Homework temp = course.hwlist.get(choice-1);
                while(temp.doHomework())
                    continue;
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return false;
    }
}

