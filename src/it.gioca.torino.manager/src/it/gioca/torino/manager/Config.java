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
	public static int RESIZEMAX;
	public static int RESIZEMIN;
	public static int RESIZEMID;
	public static int[] CUSTOM1 = new int[3];
	public static int[] CUSTOM2 = new int[3];
	public static int[] CUSTOM3 = new int[3];
	public static int SELECT;
	public static boolean ALTERNATIVE_HISTORY;
	public static boolean ALTERNATIVE_FIND_USER;
	public static int MAXDIALOGMULTI;
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
		RESIZEMAX= Integer.parseInt(prop.getProperty("resizeMAX"));
		RESIZEMID= Integer.parseInt(prop.getProperty("resizeMID"));
		RESIZEMIN= Integer.parseInt(prop.getProperty("resizeMIN"));
		
		SHORT_TIME = Integer.parseInt(prop.getProperty("short_time"));
		MID_TIME = Integer.parseInt(prop.getProperty("mid_time"));
		
		ALTERNATIVE_HISTORY = Boolean.parseBoolean(prop.getProperty("alt_history", "false"));
		ALTERNATIVE_FIND_USER = Boolean.parseBoolean(prop.getProperty("alt_find_user", "false"));
		for(int i=1; i<4; i++){
			String[] values = prop.getProperty("CUSTOM"+i).split(",");
			for(int j=0; j<values.length; j++){
				switch(i){
				case 1: CUSTOM1[j] = Integer.parseInt(values[j]); break;
				case 2: CUSTOM2[j] = Integer.parseInt(values[j]); break;
				case 3: CUSTOM3[j] = Integer.parseInt(values[j]); break;
				}
			}
		}
		SELECT = Integer.parseInt(prop.getProperty("select"));
		MAXDIALOGMULTI = Integer.parseInt(prop.getProperty("maxDialogMulti"));
	}
}
