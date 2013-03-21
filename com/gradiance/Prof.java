package com.gradiance;
import java.io.*;
import java.util.*;


class Prof extends Manager{
    Prof(){}
    Prof(String mid, String name)
    {
        super(mid,name);
    }
    boolean aboutCourse(Course course)
    {
       int choice = Util.inputInt("For "+course.cid+":\n1.Add homework\n2.Edit Homework\n3.Add question\n4.Add answer\n5.Reports\n6.Add topic\n7.Show Student\n8.Assigan a Ta\n9.Back");
       switch(choice)
       {
           case 1:
               while(addHomework(course))
                   continue;
               break;
           case 2:
               while(editHomework(course))
                   continue;
               break;
           case 3:
               while(addQuestion(course))
                   continue;
               break;
           case 4:
               while(addAnswer(course))
                   continue;
               break;
           case 5:
               while(report(course))
                   continue;
               break;
           case 6:
               while(addTopic(course))
                   continue;
               break;
           case 7:
               while(showStudent(course))
                   continue;
           case 8:
               while(assignTa(course))
                   continue;
           case 9:
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
        String cid = c.readLine("Please enter the course id: ").toUpperCase();
        String course_token = c.readLine("Please enter the course token: ").toUpperCase();
        String cname = c.readLine("Please enter the course name: ");
        Date start_date=null,end_date=null;
        String start_date_str=null,end_date_str=null;
        while(start_date==null)
        {
            start_date_str=c.readLine("Please enter the start date(in the format of dd-MMM-yy(27-Apr-91))");
            start_date = Util.parseDate(start_date_str);
        }
        while(end_date==null)
        {
            end_date_str = c.readLine("Please enter the end date(in the format of dd-MMM-yy(27-Apr-91))");
            end_date = Util.parseDate(end_date_str);
        }
        if(end_date.before(start_date))
        {
            System.out.println("Invalid: end date is before start date");
            return true;
        }
        try{
                Course course = new Course(cid,course_token,cname);
                String u = "insert into course values ('"+cid+"','"+course_token+"','"+cname+"','"+start_date_str+"','"+end_date_str+"','"+this.mid+"')";
                DBcontrol.update(u);
                while(aboutCourse(course))
                    continue;
                return false;
        }catch(Throwable oops){
            System.out.println("Error when add course");
            return true;
        }
    }

    boolean addQuestion(Course course)
    {
        if(course.showTopic(0))
        {
            int choice = Util.inputInt("");
            if(choice == course.list.size()+1 )
                return false;
            else if(choice > course.list.size() || choice < 1)
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
    boolean addAnswer(Course course)
    {
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
                while(q.addAnswer())
                    continue;
            }
        }
        else
            System.out.println("There is no topic");
        return false;
    }
    boolean report(Course course)
    {
        String q = Util.c.readLine("Please enter the query: ");
        DBcontrol.query(q);
        try{
            DBcontrol.meta = DBcontrol.rs.getMetaData();
            int columnnum = DBcontrol.meta.getColumnCount();
            while(DBcontrol.rs.next())
            {
                for(int col =1 ;col < columnnum;col++)
                {
                    Object value = DBcontrol.rs.getObject(col);
                    if(value != null)
                        System.out.print(value.toString()+"\t|\t");
                }
                System.out.print("\n");
            }
        }catch(Throwable oops){
            oops.printStackTrace();
        }

        return false;
    }
    boolean addTopic(Course course)
    {
        if(course.showTopic(1))
        {
            int choice = Util.inputInt("");
            if(choice == course.list.size()+1 )
                return false;
            else if(choice > course.list.size() || choice < 1)
            {
                System.out.println("Invalid choice");
                return true;
            }
            else
            {
                course.linkTopic(course.list.get(choice-1));
            }                
        }
        else
            System.out.println("There is no topic");
        return false;
    }
    boolean assignTa(Course course)
    {
        System.out.println("1.Enter a student id to assign a Ta\n2.Back");
        int choice = Util.inputInt("");
        switch(choice)
        {
            case 1:
                String taid = Util.c.readLine("Please enter a student id to assign a ta: ");
                DBcontrol.query("select * from member where mid='"+taid+"'");
                try{
                    if(DBcontrol.rs.next())
                    {
                        DBcontrol.query("select role from enroll where mid='"+taid+"' and token='"+course.token+"'");
                        if(DBcontrol.rs.next())
                        {
                            String ifta = DBcontrol.rs.getString("role");
                            if(ifta.equals("stud"))
                            {
                                System.out.println("This student has enrolled "+course.token+" and cannot be a Ta");
                                return true;
                            }
                            else if(ifta.equals("ta"))
                            {
                                System.out.println("This student has been assigned a Ta");
                                return true;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Student id");
                        return true;
                    }
                }catch(Throwable oops){
                    System.out.println("Error");
                }
                if(DBcontrol.update("update enroll set role='ta' where mid='"+taid+"'"))
                    System.out.println("Assign Ta successfully");
                else
                    System.out.println("Error");
                return false;
            case 2:
                return false;
            default:
                return true;
        }
    }

}
