package it.gioca.torino.manager.gui.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerPoints {

	private static LoggerPoints instance;
	
	private FileWriter flLog;
	private FileWriter loadListLog;
	
	public static LoggerPoints getInstace(){
		if(instance==null)
			instance = new LoggerPoints();
		return instance;
	}
	
	private LoggerPoints() {
		
		try {
			flLog = new FileWriter("info.log", true);
			loadListLog = new FileWriter("load.log", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveLoad(String log){
		SimpleDateFormat spdata = new SimpleDateFormat("dd-MM-YYYY;HH:mm -- ");
		String now = spdata.format(new Date());
		try {
			loadListLog.append(now+log);
			loadListLog.append(System.getProperty("line.separator"));
			loadListLog.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String log){
		SimpleDateFormat spdata = new SimpleDateFormat("dd-MM-YYYY;HH:mm -- ");
		String now = spdata.format(new Date());
		try {
			flLog.append(now+log);
			flLog.append(System.getProperty("line.separator"));
			flLog.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
