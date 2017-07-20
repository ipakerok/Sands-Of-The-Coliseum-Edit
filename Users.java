//package com;

import java.io.*;
import java.util.*;

public class Users implements Serializable{
	private List<User> akuns = new ArrayList<>();

	public void clone(Users thi){
		this.akuns = thi.akuns();
	}
	public Users(){}


	public void save(){
		try {
			FileOutputStream fileOut =new FileOutputStream("usr");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Data is saved");
		}catch(IOException i) {
			String f = i.getMessage();
			System.out.println(f);
		}
	}

	public void load(){
		try {
			Users thi = new Users();
			FileInputStream fileIn = new FileInputStream("usr");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			thi = (Users) in.readObject();
			clone(thi);
			in.close();
			fileIn.close();
		}catch(IOException i) {
			String f = i.getMessage();
			System.out.println(f);
		}catch(ClassNotFoundException c) {
			System.out.println("Class not found");
			String f = c.getMessage();
			System.out.println(f);
		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
		}

	}
	public List<User> akuns(){return this.akuns;}
	public void addUser(String name, String token){
		User user = new User(name, token);
		if(user.isValidNumber(user.number()))akuns.add( user );
	}
	public void addUser(User user){
		if(user.isValidNumber(user.number()))akuns.add( user );
	}
	public void resetUser(){
		akuns = new ArrayList<>();
	}
	public boolean removeUser(int i){
		int a=0;
		for(User b:akuns){
			if(++a==i){
				akuns.remove(b);
				return true;
			}
		}
		return false;
	}
	public User getUser(int i){
		int a=0;
		for(User b:akuns){
			if(++a==i){
				return b;
			}
		}
		return null;
	}
	public void showUsers(int c){

		int i=0;
		for(User a: akuns){
			System.out.print((++i)+".");
			if((c>>0)%2==1)System.out.println(a.name());
			if((c>>1)%2==1)System.out.println("  "+a.token());
			if((c>>2)%2==1)System.out.println("  kong"+a.number()+"");
		}
		if(i==0)System.out.println("none");
	}

	public static void main(String[] args) {
		Users users=new Users();
		Scanner sc=new Scanner(System.in);
		users.load();
		
		System.out.println("    ");
		System.out.println("    ▞▚              ");
		System.out.println("    ▚════════════════════════════════╗");
		System.out.println("     ▚ ▛▜ ▛    kongregate            ║");
		System.out.println("    ▚▞ ▙▟ ▙    data transporter   ╭╌╌╢");
		System.out.println("    ╚═════════════════════════════╧ipakerok");

		while(true){
			System.out.println("═══════════════════════");
			//System.out.println(" 1. Backup");
			//System.out.println(" 2. Send");
			//System.out.println(" 3. Add Account");
			//System.out.println(" 4. Remove Account");
			//System.out.println(" 5. Show Accounts");
			//System.out.println(" 0. Exit");
			System.out.println(" 1. Get Data       4. Remove Account");
			System.out.println(" 2. Send Data      5. Show Accounts");
			System.out.println(" 3. Add Account    0. Exit");
			System.out.print(">> ");

			int a=sc.nextInt();
			if(a==3){
				System.out.println("╔═════════════╗");
				System.out.println("║ Add Account ║");
				System.out.println("╚═════════════╝");
				//System.out.print(" Name (wihout space)  :");
				//String param1 = sc.next();
				System.out.print(" Player Token :");
				String param2 = sc.next();
				//User param3 = new User(param1,param2);
				User param3 = new User(param2);
				if(param3.isValidNumber(param3.number())){
					users.addUser(param3);
					System.out.println(" New account added");
					users.save();
				}else{
					System.out.println(" Wrong token!");
				}

			}
			if(a==4){
				System.out.println("╔════════════════╗");
				System.out.println("║ Remove Account ║");
				System.out.println("╚════════════════╝");
				System.out.println(" Accounts list :");
				users.showUsers(1);
				System.out.print(" Choose account : ");
				int param1 = sc.nextInt();
				if(users.removeUser(param1)){
					System.out.println(" Account removed");
					users.save();
				}else{
					System.out.println(" Wrong choice!!!");
				}

			}
			if(a==5){
				System.out.println("╔═══════════════╗");
				System.out.println("║ Accounts List ║");
				System.out.println("╚═══════════════╝");
				users.showUsers(5);
			}
			if(a==1){
				System.out.println("╔════════╗");
				System.out.println("║ Backup ║");
				System.out.println("╚════════╝");
				System.out.println(" Accounts list :");
				users.showUsers(1);
				System.out.print(" Choose account : ");
				User user = users.getUser(sc.nextInt());
				if(user!=null){
					Bot bot = new Bot(user.token(), user.number());
					System.out.print(" Save as (wihout space) : ");
					String filename=sc.next();
					bot.getData();
					try{
						//bot.saveBody("save/backup_"+user.name()+".txt" ,bot.response());
						bot.saveBody(""+filename ,bot.response());
						System.out.println(" Backup success");
					}catch(Exception e){
						System.out.println(" Backup failed");
					}
				}else{
					System.out.println(" Wrong choice!!!");
				}
			}
			if(a==2){
				System.out.println("╔════════════════╗");
				System.out.println("║ Send New Saves ║");
				System.out.println("╚════════════════╝");
				System.out.println(" Accounts list :");
				users.showUsers(1);
				System.out.print(" Choose destination : ");
				User param1 = users.getUser(sc.nextInt());
				if(param1!=null){
					Bot bot = new Bot(param1.token(), param1.number());
					System.out.print(" Load from: ");
					String filename=sc.next();
					System.out.println(bot.response());
					bot.sendData(""+filename);
					//System.out.print(" Save success");
					System.out.println(bot.response());
				}else{
					System.out.print(" Wrong choice!!!");
				}
				//System.out.print("slot : ");
				//String filename=sc.next();
				//bot.sendData("save/backup_"+filename+".txt");
				//System.out.println(bot.response());
			}

			if(a==0){
				System.out.println(" Bye.");
				break;
			}

		}
	}
}
