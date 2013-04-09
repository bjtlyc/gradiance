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
       int choice = Util.inputInt("For "+course.cid+":\n1.Add homework\n2.Edit Homework\n3.Delete Homework\n4.Add question\n5.Add answer\n6.Reports\n7.Add topic\n8.Show Student\n9.Assigan a Ta\n10.Back");
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
               while(delHomework(course))
                   continue;
               break;
           case 4:
               while(addQuestion(course))
                   continue;
               break;
           case 5:
               while(addAnswer(course))
                   continue;
               break;
           case 6:
               while(report(course))
                   continue;
               break;
           case 7:
               while(addTopic(course))
                   continue;
               break;
           case 8:
               while(showStudent(course))
                   continue;
               break;
           case 9:
               while(assignTa(course))
                   continue;
               break;
           case 10:
               return false;
           default:
               return true;
       }
       return true;
    }

    boolean delHomework(Course course)
    {
        if(course.showHomework(1,this.mid))
        {
            int choice = Util.inputInt("");
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
                while(hw.delete())
                    continue;
            }                
        }
        return false;
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
        int choice = Util.inputInt("\n1.Link existing topic\n2.Create a new topic\n3.back\n");
        switch(choice)
        {
            case 1:
                if(course.showTopic(1))
                {
                    choice = Util.inputInt("");
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
            case 2:
                try{
                    DBcontrol.query("select max(tid) from topic");
                    if(DBcontrol.rs.next())
                    {
                        int newtid = DBcontrol.rs.getInt("max(tid)") + 1;
                        String topicname = Util.c.readLine("please enter the topic you want to create: "); 
                        if(DBcontrol.update("insert into topic values ("+newtid+",'"+topicname+"')"))
                        {
                            course.linkTopic(newtid);
                        }
                    }
                }catch(Throwable oops){;}
                return false;
            case 3:
                return false;
            default: 
                return true;
        }
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
                                return false;
                            }
                        }
                        else
                        {
                            if(DBcontrol.update("insert into enroll values ('"+taid+"','"+course.token+"','ta')"))
                                System.out.println("Assign Ta successfully");
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
                return false;
            case 2:
                return false;
            default:
                return true;
        }
    }

}
