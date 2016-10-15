package it.gioca.torino.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static String LANGUAGE;
	public static String USERDB;
	public static String PASSWORDDB;
	public static String URL;
	public static int MAXINT;
	
	/*
	 * Search configs
	 */
	public static int SHORT_TIME;
	public static int MID_TIME;
	
	
	public static void load(){
		
		Properties prop = new Properties();
		final File configFile = new File("Config/Config.properties");
		
		try 
		{
			InputStream is = new FileInputStream(configFile);
			prop.load(is);
		}catch (Exception e){
			System.out.println("Could not open: config.properties");
			return;
		}
		LANGUAGE = prop.getProperty("language","en_US");
		USERDB = prop.getProperty("user");
		PASSWORDDB = prop.getProperty("password");
		URL = prop.getProperty("url");
		MAXINT = Integer.parseInt(prop.getProperty("maxNumber"));
		
		
		SHORT_TIME = Integer.parseInt(prop.getProperty("short_time"));
		MID_TIME = Integer.parseInt(prop.getProperty("mid_time"));
	}
}
