//package com;

import java.io.*;
import java.util.*;
import java.net.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Bot {
	private String token, url;
	private String cookie="";
	private int status=0;
	private String kongnumber;// = "29805237";
	public  HttpURLConnection conn;

	public Bot(String token, String kongnumber){
		this.token = token;
		this.kongnumber = kongnumber;
	}
	public Bot(String token){
		this.token = token;
		getData();
		String aa=response();
		int bb = aa.indexOf("kong");
		this.kongnumber = aa.substring(bb+4, bb+12);
		if(!isValidNumber(kongnumber))this.kongnumber=null;
		System.out.println("kong"+kongnumber);
	}

	public boolean isValidNumber(String a){
		return (int) a.charAt(0)>=48 && (int) a.charAt(0)<=57 && (a.length()==1?true:isValidNumber(a.substring(1)));
	}

	public void getData(){
		try{
			System.out.println("Connecting to http://api.playerio.com/api/103");
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL obj = new URL("http://api.playerio.com/api/103");

			conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);

			conn.addRequestProperty("Accept","*/*");
			conn.addRequestProperty("Accept-Encoding","gzip, deflate, lzma");
			conn.addRequestProperty("Accept-Language","id-ID,id;q=0.8,en-US;q=0.6,en;q=0.4");
			conn.addRequestProperty("Host","api.playerio.com");
			conn.addRequestProperty("Connection","keep-alive");
			conn.addRequestProperty("Content-Length","2");
			conn.addRequestProperty("Origin","http://chat.kongregate.com");
			conn.addRequestProperty("X-Requested-With","ShockwaveFlash/22.0.0.209");
			conn.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48");
			conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.addRequestProperty("Referer","http://chat.kongregate.com/gamez/0014/6097/live/SOC_BerzerkLand.swf?kongregate_game_version=1356114479/[[DYNAMIC]]/1");
			conn.addRequestProperty("playertoken",token);
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(":");
			wr.flush();
			wr.close();
			status = conn.getResponseCode();
			if((int) status/100 == 2){
				System.out.println(status+" OK");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 3){
				System.out.println(status+" Redirect");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 4){
				System.out.println(status+" Client error");
			}
			else
			if((int) status/100 == 5){
				System.out.println(status+" Server Error");
			}
		}catch (java.net.ProtocolException e) {
			String f = e.getMessage();
			System.out.println(f);
		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
			
		}
		finally{
		}
	}
	public void httpGET(String url,String post){
		try{
			System.out.println("Connecting to "+url);
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL obj = new URL(url);

			conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);

			conn.addRequestProperty("Accept","*/*");
			conn.addRequestProperty("Accept-Encoding","gzip, deflate, lzma");
			conn.addRequestProperty("Accept-Language","id-ID,id;q=0.8,en-US;q=0.6,en;q=0.4");
			//conn.addRequestProperty("Host","api.playerio.com");
			conn.addRequestProperty("Connection","keep-alive");
			//conn.addRequestProperty("Origin","http://chat.kongregate.com");
			//conn.addRequestProperty("X-Requested-With","ShockwaveFlash/22.0.0.209");
			conn.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48");
			//conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
			//conn.addRequestProperty("Referer","http://chat.kongregate.com/gamez/0014/6097/live/SOC_BerzerkLand.swf?kongregate_game_version=1356114479/[[DYNAMIC]]/1");
			conn.setRequestMethod("GET");

			//conn.setDoOutput(true);
			//DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			//wr.writeBytes(post);
			//wr.flush();
			//wr.close();
			status = conn.getResponseCode();
			if((int) status/100 == 2){
				System.out.println(status+" OK");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 3){
				System.out.println(status+" Redirect");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 4){
				System.out.println(status+" Client error");
			}
			else
			if((int) status/100 == 5){
				System.out.println(status+" Server Error");
			}
		}catch (java.net.ProtocolException e) {
			String f = e.getMessage();
			System.out.println(f);
		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
			
		}
	}
	public String getUsername(){
		httpGET("http://www.kongregate.com/users/"+this.kongnumber+"/posts", "");
		String a = response();
		//System.out.println(a.substring(0,300));
		try{
			return a.substring(a.indexOf("Recent posts by ")+16, a.indexOf(" on Kongregate</title>"));
		}catch (Exception e) {
			return "-bro-";
		}
		//A+4cAAA3HAAA3oUfKHwcAACvAwAAAAAAAHtSK8IAlggCAYtD+wwpvwoYHHcJFitU2kNZ0U8JZAkXSeqSrlZOosAi
	}

	public void sendData(String filename){
		try{
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			URL obj = new URL("http://api.playerio.com/api/88");
			conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);

			conn.addRequestProperty("Accept","*/*");
			conn.addRequestProperty("Accept-Encoding","gzip, deflate, lzma");
			conn.addRequestProperty("Accept-Language","id-ID,id;q=0.8,en-US;q=0.6,en;q=0.4");
			conn.addRequestProperty("Host","api.playerio.com");
			conn.addRequestProperty("Connection","keep-alive");
			conn.addRequestProperty("Origin","http://chat.kongregate.com");
			conn.addRequestProperty("X-Requested-With","ShockwaveFlash/22.0.0.209");
			conn.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36 OPR/39.0.2256.48");
			conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.addRequestProperty("Referer","http://chat.kongregate.com/gamez/0014/6097/live/SOC_BerzerkLand.swf?kongregate_game_version=1356114479/[[DYNAMIC]]/1");
			conn.addRequestProperty("playertoken",token);
			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

			String[] head = {"18", "01", "08", "02", "12", "bf", "c7", "05", "22", "9c", "c7", "05", "0a", "08", "73", "61", "76", "65", "66", "69", "6c", "65", "12", "8e", "c7", "05", "50", "00", "12", "f0", "c6", "05", "32", "7e", "45", "4e", "7e", "30", "7e", "31", "7e", "30", "7e", "31", "34", "7e", "32", "30", "7e", "2a", "7e"};
			
			for(int i=0;i<head.length;i++){
				String a=head[i];
				wr.writeByte((char) Integer.parseInt(a,16));
			}

			int len=loadText(wr,filename);
			int stdlen=90969;
			if(len>stdlen)throw new Exception("Memory pengiriman penuh.");
			while(len<stdlen){
				wr.writeByte(Integer.parseInt(len%2==0?"20":"0a",16));
				len++;
			}
			String[] tail = {"31", "2e", "34", "32", "28", "00", "20", "00", "18", "00", "41", "00", "00", "00", "e0", "ff", "ff", "ff", "7f", "30", "00", "08", "00", "3d", "ff", "ff", "ff", "7f", "12", "0c", "6b", "6f", "6e", "67",
				"3"+ kongnumber.charAt(0),
				"3"+ kongnumber.charAt(1),
				"3"+ kongnumber.charAt(2),
				"3"+ kongnumber.charAt(3),
				"3"+ kongnumber.charAt(4),
				"3"+ kongnumber.charAt(5),
				"3"+ kongnumber.charAt(6),
				"3"+ kongnumber.charAt(7),
				"28", "00", "0a", "0d", "50", "6c", "61", "79", "65", "72", "4f", "62", "6a", "65", "63", "74", "73"};

			for(int i=0;i<tail.length;i++){
				String a=tail[i];
				wr.writeByte((char) Integer.parseInt(a,16));
			}

			wr.flush();
			wr.close();
			System.out.println("Connecting to http://api.playerio.com/api/88");
			status = conn.getResponseCode();
			if((int) status/100 == 2){
				System.out.println(status+" OK");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 3){
				System.out.println(status+" Redirect");
				cookie=conn.getHeaderField("Set-Cookie");
			}
			else
			if((int) status/100 == 4){
				System.out.println(status+" Client error");
			}
			else
			if((int) status/100 == 5){
				System.out.println(status+" Server Error");
			}
		}catch (java.net.ProtocolException e) {
			String f = e.getMessage();
			System.out.println(f);
		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
			
		}
	}


	public int status(){
		return (int) this.status/100;
	}

	public String response(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			StringBuffer html = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();

			return html.toString().replaceAll("[]", ".");



		}
		catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
			return "";
		}
	}

	public void printHeader(){
		try{

			Map<String, List<String>> map = conn.getHeaderFields();
			System.out.println("Printing Response Header...\n");
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey()
		                           + " ,Value : " + entry.getValue());
			}
		}
		catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
		}
	}

	public void settoken(String token){
		this.token=token;
	}

	public void loadHex(DataOutputStream wr, String n){
		try{
			File file=new File(n);
			Scanner sc=new Scanner(file);

			while(sc.hasNext()){
				String m=sc.next();
				//wr.writeInt(Byte.parseByte(m,16));
				//wr.writeInt(Integer.parseInt(m,16));
				wr.writeByte((char) Integer.parseInt(m,16));
				//sc.next();
			}
		}catch(FileNotFoundException e){
			String f = e.getMessage();
			System.out.println(f);
		}catch (IOException e) {
			
			String f = e.getMessage();
			System.out.println(f);
		}
	}
	//*
	public int loadText(DataOutputStream wr, String n) throws Exception{
		int len=0;
		File file=new File(n);
		Scanner sc=new Scanner(file);

		while(sc.hasNext()){
			String m=sc.next();
			m+=" ";
			for(int i=0;i<m.length();i++){
				wr.writeByte((char) Integer.parseInt( Integer.toHexString((int)m.charAt(i)) ,16));
				len++;
			}
		}
		return len;

	}

	public void saveBody(String output, String data) throws Exception {  
		//FileInputStream in = null;
		//FileOutputStream out = null;
		//System.out.println(data);
		try {
			File out = new File(output);
			out.createNewFile();
			FileWriter writer = new FileWriter(out);
			//StringBuffer br = new StringBuffer();

			data = (data.split("<School"))[1] ;//.replaceAll("([SOC_GP].*1.42)","");
			data = data.substring(0,data.lastIndexOf("[SOC_GP]") + 8);

			writer.write("<School"+data);
			writer.flush();
			writer.close();
		}catch (IndexOutOfBoundsException e) {
			throw new Exception("wrong saved data");
		}
	}
	


	public void hex2text(String input, String output) {  
		//FileInputStream in = null;
		//FileOutputStream out = null;

		try {
			File in = new File(input);
			Scanner sc = new Scanner(in);
			File out = new File(output);
			out.createNewFile();
			FileWriter writer = new FileWriter(out);
			StringBuffer br = new StringBuffer();
         
			while ( sc.hasNext() ) {
				String m=sc.next();
				br.append(hex2char(m));
				//br.append(" ");
			}
			//System.out.println( new String(br) );
			writer.write(new String(br));
			writer.flush();
			writer.close();

		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);
		}
	}

	public String hex2char(String hex){
		switch (hex) {
			case "20" : return " ";//space
			case "21" : return "!";
			case "22" : return "\"";
			case "23" : return "#";
			case "24" : return "$";
			case "25" : return "%";
			case "26" : return "&";
			case "27" : return "'";
			case "28" : return "(";
			case "29" : return ")";
			case "2a" : return "*";
			case "2b" : return "+";
			case "2c" : return "`";
			case "2d" : return "-";
			case "2e" : return ".";
			case "2f" : return "/";
			case "30" : return "0";
			case "31" : return "1";
			case "32" : return "2";
			case "33" : return "3";
			case "34" : return "4";
			case "35" : return "5";
			case "36" : return "6";
			case "37" : return "7";
			case "38" : return "8";
			case "39" : return "9";
			case "3a" : return ":";
			case "3b" : return "'";
			case "3c" : return "<";
			case "3d" : return "=";
			case "3e" : return ">";
			case "3f" : return "?";
			case "40" : return "@";
			case "41" : return "A";
			case "42" : return "B";
			case "43" : return "C";
			case "44" : return "D";
			case "45" : return "E";
			case "46" : return "F";
			case "47" : return "G";
			case "48" : return "H";
			case "49" : return "I";
			case "4a" : return "J";
			case "4b" : return "K";
			case "4c" : return "L";
			case "4d" : return "M";
			case "4e" : return "N";
			case "4f" : return "O";
			case "50" : return "P";
			case "51" : return "Q";
			case "52" : return "R";
			case "53" : return "S";
			case "54" : return "T";
			case "55" : return "U";
			case "56" : return "V";
			case "57" : return "W";
			case "58" : return "X";
			case "59" : return "Y";
			case "5a" : return "Z";
			case "5b" : return "[";
			case "5c" : return "\\";
			case "5d" : return "]";
			case "5e" : return "^";
			case "5f" : return "_";
			case "60" : return "`";
			case "61" : return "a";
			case "62" : return "b";
			case "63" : return "c";
			case "64" : return "d";
			case "65" : return "e";
			case "66" : return "f";
			case "67" : return "g";
			case "68" : return "h";
			case "69" : return "i";
			case "6a" : return "j";
			case "6b" : return "k";
			case "6c" : return "l";
			case "6d" : return "m";
			case "6e" : return "n";
			case "6f" : return "o";
			case "70" : return "p";
			case "71" : return "q";
			case "72" : return "r";
			case "73" : return "s";
			case "74" : return "t";
			case "75" : return "u";
			case "76" : return "v";
			case "77" : return "w";
			case "78" : return "x";
			case "79" : return "y";
			case "7a" : return "z";
			case "7b" : return "{";
			case "7c" : return "|";
			case "7d" : return "}";
			case "7e" : return "~";
			//case "7f" : return ""; del
			case "0a" : return "\n";//\n
			//case "" : return "";

			default : return "?";
		}
	}

	public static void main(String[] args) {
		//String token = "A+4cAAA3HAAAbMEkKHwcAACvAwAAAAAAACXfQRoASQMCAcn6HXW74/UYzE1i9zGT3VwmftX9pyQD0ie+alpdcd2o"; //ipak
		//String token = "A+4cAAA3HAAA3oUfKHwcAACvAwAAAAAAAHtSK8IAlggCAYtD+wwpvwoYHHcJFitU2kNZ0U8JZAkXSeqSrlZOosAi"; //Jackal
		//String token = "A+4cAAA3HAAAcswOKnwcAACvAwAAAAAAAFuguJQAogoCAd2+niUDAR77YeSax5TfE6zKcbGcH06GdysQjY/HhRjC"; //fathan3


		try{
			if(args[0].equals("get")){
				//if(args.length<3)throw new Exception("KURANG");
				Bot bot = new Bot (args[1]);
				bot.getData();
				//System.out.println(bot.response());
				bot.saveBody(args[2] ,bot.response());
			}
			if(args[0].equals("send")){
				//if(args.length<3)throw new Exception("KURANG");
				Bot bot = new Bot (args[1]);
				bot.getData();
				bot.sendData(args[2]);
				System.out.println(bot.response());
			}
				System.out.println("Done");

		}catch (IndexOutOfBoundsException e) {
			//e.printStackTrace();
			System.out.println("x");
		}catch (Exception e) {
			String f = e.getMessage();
			System.out.println(f);

		}

		
	}

}