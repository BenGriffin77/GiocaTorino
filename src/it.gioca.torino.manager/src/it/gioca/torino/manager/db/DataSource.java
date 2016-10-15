package it.gioca.torino.manager.db;

import java.io.Serializable;

public class DataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String driver;
	
	private String type;
	
	private String url;
	
	private String schema;
	
	private String user;
	
	private String password;
	
	private boolean debug;
	
	private boolean txt;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return "jdbc:"+type+"://"+url+"/"+schema;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isTxt() {
		return txt;
	}

	public void setTxt(boolean txt) {
		this.txt = txt;
	}
}
