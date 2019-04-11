package Servlet;

import java.util.Scanner;

public class Client {
    public static void main(String[] args)
    {
        Servlet s=new Servlet();
        s.deal("check");

        Scanner scanner= new Scanner(System.in);
        for(int i=0;i<=99;i++)
        {
            s.deal(scanner.nextLine());
        }

    }
}
