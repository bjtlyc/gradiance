package com.gradiance;
import java.io.*;
import java.util.*;


class Manager extends User{
    Manager(){}
    Manager(String mid, String name)
    {
        super(mid,name);
    }
    boolean aboutCourse(Course course)
    {
       int choice = Util.inputInt("For "+course.cid+":\n1.Edit Homework\n2.Show Student\n3.Back");
       switch(choice)
       {
           case 1:
               while(editHomework(course))
                   continue;
               break;
           case 2:
               while(showStudent(course))
                   continue;
           case 3:
               return false;
           default:
               return true;
       }
       return true;
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
                    DBcontrol.query("select mid,role from enroll where token='"+course.token+"'");
                    try{
                        while(DBcontrol.rs.next())
                        {
                            String role = DBcontrol.rs.getString("role");
                            if(role.equals("stud"))
                                temp.add(DBcontrol.rs.getString("mid"));
                        }
                    }catch(Throwable oops){}
                    for(int i=0;i<temp.size();i++)
                    {
                        DBcontrol.update("insert into hw_mem values('"+course.token+"',"+hwid+",'"+temp.get(i)+"',0,0)");
                    }
                    System.out.println(temp.size()+" student updated");
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
                while(hw.edit())
                    continue;
                return true;
            }                
        }
        return false;
    }


    boolean showStudent(Course course)
    {
        if(course.showStudent())
        {
            int choice = Util.inputInt("");
            if(choice == course.slist.size()+1)
                return false;
            else if(choice > course.slist.size() || choice < 1)
            {
                System.out.println("Invalid choice");
                return true;
            }
            else
            {
                Student s = course.slist.get(choice-1);
                while(viewStudHw(s,course))
                    continue;
                return true;
            }
        }
        else
            System.out.println("There is no students enrolled in this course");
        return false;
    }

    boolean viewStudHw(Student s,Course course)
    {
        System.out.println("Course Options:\n1.View Scores\n2.View Past Submisstion\n3.Back");
        int choice = Util.inputInt("");
        switch(choice)
        {
            case 1:
                while(course.viewScore(0,s.mid))
                    continue;
                return true;
            case 2:
                while(course.viewPastSubmit(1,s.mid))
                    continue;
                return true;
            case 3:
                return false;
            default :
                return true;
        }
    }

}
