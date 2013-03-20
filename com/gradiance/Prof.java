package com.gradiance;
import java.io.*;
import java.util.*;


class Prof extends User{
    Prof(){}
    Prof(String mid, String name,int role)
    {
        super(mid,name,role);
    }
    boolean aboutCourse(Course course)
    {
       int choice = Util.inputInt("For "+course.cid+":\n1.Add homework\n2.Edit Homework\n3.Add question\n4.Add answer\n5.Reports\n6.Add topic\n7.Back");
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
                Course course = new Course(cid,course_token,cname,mid);
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

    boolean addHomework(Course course)
    {
        int hwid = Homework.getid(course.token); 
        System.out.println(hwid);
        String hwtitle = Util.c.readLine("Please enter the title for the homework: ");
        String start_date_str=null,end_date_str=null;
        while(true)
        {
            Date start_date=null,end_date=null;
            while(start_date==null)
            {
                start_date_str = Util.c.readLine("Please enter the start date(in the format of dd-MMM-yy): ");
                start_date = Util.parseDate(start_date_str);
            }
            while(end_date==null)
            {
                end_date_str = Util.c.readLine("Please enter the end date(in the format of dd-MMM-yy): ");
                end_date = Util.parseDate(end_date_str);
            }
            if(start_date.after(end_date))
            {
                System.out.println("The date is invalid");
                continue;
            }
            else
                break;
        }
        int attemptnum=Util.inputInt("Please enter the number of attempts allowed: ");
        String ssmethod =Util.c.readLine("Please enter the score selection way(in word): \n1.first\n2.last\n3.max\n4.avg\n");
        int qnum=Util.inputInt("Please enter the number of questions: ");
        int r_ans_p=Util.inputInt("Please enter the points for right answer: ");
        int w_ans_p=Util.inputInt("Please enter the points for wrong answer: ");
        try{
            String u = "insert into homework values ('"+course.token+"',"+hwid+",'"+hwtitle+"',"+qnum+","+attemptnum+",'"+start_date_str+"','"+end_date_str+"',"+r_ans_p+","+w_ans_p+",'"+ssmethod+"')";
                if(!DBcontrol.update(u))
                    return false;
                else
                {
                    System.out.println("Add a new homework successfully.");
                    ArrayList<String> temp=new ArrayList<String>();
                    DBcontrol.query("select mid from enroll where token='"+course.token+"'");
                    try{
                        while(DBcontrol.rs.next())
                            temp.add(DBcontrol.rs.getString("mid"));
                    }catch(Throwable oops){}
                    for(int i=0;i<temp.size();i++)
                    {
                        DBcontrol.update("insert into hw_mem values('"+course.token+"',"+hwid+",'"+temp.get(i)+"',0,0)");
                    }
                }
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
                while(hw.edit())
                    continue;
            }                
        }
        return false;
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

    boolean showStudent()
    {
        DBcontrol.query("select * from enroll e,student s where e.");
        return false;
    }

}
