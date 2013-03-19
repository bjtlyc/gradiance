package com.gradiance;
import java.io.*;

class Student extends User{

    Student(){}
    Student(String userid, String name, int role)
    {
        super(userid,name,role);
    }
    
    boolean addCourse()
    {
        if(Util.c==null)
            System.exit(0);
        String course_token = Util.c.readLine("Please enter a course token");
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
        if(course.showHomework(0))
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            Homework temp = hwlist.get(choice);
            if(temp!=null)
                System.out.println(hwlist.get(choice).score);
            else
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return false;
    }

    boolean viewPastSubmit(Course course)
    {
        return false;
    }
    boolean attemptHomework(Course course)
    {
        if(course.showOpenHomework())
        {
            InputStreamReader cin = InputStreamReader(System.in);
            int choice = cin.read();
            Homework temp = hwlist.get(choice);
            if(temp!=null)
                temp.doHomework();
            else
            {
                System.out.println("Invalid Choice, please enter another number");
                return true;
            }
        }
        else
            System.out.println("You don't have homework attemp");
        return false;
    }
}

