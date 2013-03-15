package com.gradiance;
import java.io.*;

class Student extends User{

    Student(){}
    Student(String userid, String name, String major)
    {
        super(userid,name,major);
    }
    
    boolean addCourse()
    {
        Console c = System.console();
        if(c==null)
        {
            System.err.println("no console");
            System.exit(0);
        }
        String course_token = c.readLine("Please enter a course token");
        String q = "select * from course where token='"+course_token+"'";
        DBcontrol.query(q);
        try{
            if(DBcontrol.rs.next())
            {
                String cid = DBcontrol.rs.getString("cid");
                String token = DBcontrol.rs.getString("token");
                String cname = DBcontrol.rs.getString("cname");
                Course course = new Course(cid,cname,token,userid);
                String u = "insert into enroll values ('"+userid+"','"+course_token+"','stud')";
                DBcontrol.update(u);
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
    }

    boolean aboutCourse(Course course)
    {
        System.out.println("Course Options:\n1.View Scores\n2.Attempt Homework\n3.View Past Submission");

       InputStreamReader cin = new InputStreamReader(System.in);
       int choice = cin.read();
       switch(choice)
       {
           case '1':
               while(viewScore(course))
                   continue;
               break;
           case '2':
               while(attemptHw(course))
                   continue;
               break;
           case '3':
               while(viewPastSubmit(course))
                   continue;
               break;
           case '4':
               return false;
           default:
               return true;
       }
       return true;
   }

    boolean viewScore(Course course)
    {
        course.showScore();
    }
    boolean viewPastSubmit()
    {
        course.showPastSubmit();
    }
    boolean attemptHomework();
    {
        course.showAttempHomework();
    }
}

