import java.util.*;
import java.text.*;
import java.io.*;

class text{
    public static int a = 1;
    public static int b;
    public ArrayList<Object> al;// = new ArrayList<Object>();
    static{
        b=1;
    }
    class A{
        public int a=1;
        A(){a=5;}
    }
    class B{
        public int b=2;
        B(){b=6;}
    }
    void init()
    {
        System.out.println(this.a);
        A a = new A();
        B b = new B();
        al.add(a);
        al.add(b);
    }
    
    public static void main(String argv[])
    {
        /*Console c = System.console();
        Scanner sc = new Scanner(c.reader());
        /*while(!sc.hasNextInt())
        {
            System.out.println("invalid int");
            sc.next();
        }
        System.out.println(sc.nextInt());
        System.out.println(a++);*/
        //text t = new text();
        //t.init();
        //A temp = t.al.get(0);
        //System.out.println(temp.a);
        Random r = new Random();
        for(int i=0;i<10;i++)
            System.out.println(r.nextInt(5));
        /*System.out.println(a);
        System.out.println(b);
        String str;
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.println(df.format(d));
        try{
            str = c.readLine();
            SimpleDateFormat a = new SimpleDateFormat(str);
            Date date = a.parse("19910427");
            System.out.println(date.toString());
        }catch(ParseException e){
        }*/

    }
}
