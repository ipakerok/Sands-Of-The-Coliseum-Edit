//package com;

import java.io.*;
import java.util.*;
import java.net.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class User implements Serializable{
	private String name, token, kongnumber, cookie;
	int status;

	public User(){}

	public User(String name, String token){
		this(token);
		this.name = name;
	}
	public User(String token){
		this.name = name;
		this.token = token;
		Bot bot = new Bot(token);
		bot.getData();
		String aa=bot.response();
		int bb = aa.indexOf("kong");
		this.kongnumber = aa.substring(bb+4, bb+12);
		if(!isValidNumber(kongnumber)){
			this.kongnumber=null;
		}
		else{
			this.name = bot.getUsername();
			/*
			bb = aa.indexOf(" id=\"");
			if(bb>-1){
				int cc=aa.substring(bb+5).indexOf("\"");
				this.name = aa.substring(bb+5,cc>-1?cc+5+bb:bb+7);
				if(this.name.length()==0)this.name="bro!";
			}else{
				this.kongnumber=null;
				//this.name="bro!";
			}//*///
		}

	}

	public boolean isValidNumber(String a){
		if(a instanceof String)
			return (int) a.charAt(0)>=48 && (int) a.charAt(0)<=57 && (a.length()==1?true:isValidNumber(a.substring(1)));
		return false;
	}

	public String number(){return this.kongnumber;}
	public String name(){return this.name;}
	public String token(){return this.token;}

/*
	public static void main(String[] args) {
		User user=new User("A+4cAAA3HAAAbMEkKHwcAACvAwAAAAAAALPXAU4A2QgCAYe5st3nYrAj1bfnfEELC6tDin3bVMnjoOctYo9Rhrv4");
		System.out.println(user.number());
	}//*///
}