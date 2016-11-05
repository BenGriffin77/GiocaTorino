package it.gioca.torino.manager.db.facade.users.request;

public class UserStatus {

	private String username;
	private USTATUS status; 
	private boolean hasGame;
	private int userId;
	private String email;
	private String realName;
	
	public enum USTATUS{
		
		OK,
		GONE,
		NOTODAY,
		TODELETE
	}
	
	public int getStatusById(){
		switch(this.status){
		case GONE: return 1;
		case NOTODAY: return 2;
		default: return 0;
		}
	}
	
	public UserStatus(String username, int status, boolean hasGame, int userId) {
		this.username = username;
		this.hasGame = hasGame;
		this.userId = userId;
		switch(status){
		case 0: this.status = USTATUS.OK; return;
		case 1: this.status = USTATUS.GONE; return;
		case 2: this.status = USTATUS.NOTODAY; return;
		}
	}

	public String getUsername() {
		return username;
	}

	public USTATUS getStatus() {
		return status;
	}
	
	public void setStatus(USTATUS status){
		this.status = status;
	}
	
	public String getStatusToString(){
		switch(this.status){
		case GONE: return "Andato via";
		case OK: return "OK";
		case NOTODAY: return "Al memento non presente";
		default: return "";
		}
	}

	public boolean isHasGame() {
		return hasGame;
	}

	public void setHasGame(boolean hasGame){
		this.hasGame = hasGame;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public void setUserName(String userName){
		this.username = userName;
	}

}
