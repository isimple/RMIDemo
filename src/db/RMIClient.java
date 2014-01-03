package db;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class RMIClient {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			DataServer dataServer = (DataServer)Naming.lookup("//localhost:1111/showScore");
			System.out.println("create table student");
			dataServer.CreateTable();
			System.out.println("Create succeed。。");
			boolean isrunning = true;
			while(isrunning)
			{
				System.out.println("Choose。\n1.insert   2.retrieve    3.find by name   0.quit");
				int select = sc.nextInt();
				if(select == 1)
				{
					System.out.println("please input the student's number/name/score：");
					int num = sc.nextInt();
					String name = sc.next();
					double score = sc.nextDouble();
					dataServer.insert(num, name, score);
				}
				else if(select == 2)
				{
					System.out.println("please input the student's number ：");
					int num = sc.nextInt();
					double score = dataServer.select(num);
					System.out.println("name： "+num +"   score为： "+ score);
				}
				else if(select == 3)
				{
					System.out.println("please input the student's name：");
					String name  = sc.next();
					double score = dataServer.select(name);
					System.out.println("name：  "+name +"   score： "+ score);
				}
				else if(select == 0)
				{
					isrunning = false;
				}
				else 
				{
					System.out.println("input error!");
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
