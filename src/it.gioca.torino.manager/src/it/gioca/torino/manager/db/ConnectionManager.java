package it.gioca.torino.manager.db;

import it.gioca.torino.manager.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class ConnectionManager {

	private DataSource ds;
	
	protected Connection conn;
	
	protected PreparedStatement pstmt;
	
	protected ResultSet rset;
	
	protected boolean exit = false;
	
	public ConnectionManager(IRequestDTO request) {
		
		ds = new DataSource();
		ds.setSchema("giocatorino");
		ds.setType("mysql");
		ds.setDriver("com.mysql.jdbc.Driver");
		ds.setDebug(false);
		ds.setUser(Config.USERDB);
		ds.setPassword(Config.PASSWORDDB);
		ds.setUrl(Config.URL);
		
		//FIXME: da modificare con i valori presi dal file di configurazione
		
		getConn();
		execute(request);
		close();
	}
	
	protected abstract void execute(IRequestDTO request);
	
	private void getConn(){
		
		conn = null;
		
		try {
			Class.forName(ds.getDriver());
		}catch (ClassNotFoundException e) {
			System.out.println("Please include Classpath  Where your Driver is located");
			e.printStackTrace();
			return;
		}
		
		try {
			if(ds.isDebug()){
				System.out.println("URL: "+ds.getUrl());
				System.out.println("USER: "+ds.getUser());
				System.out.println("PASSWORD: "+ds.getPassword());
			}
				
			conn = DriverManager.getConnection(ds.getUrl(),ds.getUser(),ds.getPassword());
			if (conn != null)
				if(ds.isDebug())
				   System.out.println("Database Connected");
			
			return;
		}catch (SQLException e) {
			System.out.println("Database connection Failed");
			e.printStackTrace();
			return;
		}
	}
	
	private void close(){
		
		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Errore closing connection");
				e.printStackTrace();
			}
	}
	
	public boolean isCorrectExit(){
		return exit;
	}
}
