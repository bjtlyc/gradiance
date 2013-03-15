package com.gradiance;

class Prof extends User{
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
    boolean addHomework(){}
    boolean editHomework(){}
    boolean addQuestion(){}
    boolean addAnswer(){}
    boolean report(){}

}
