package it.gioca.torino.manager.db.facade.users;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;

public class FindInfoOfUserFacade extends ConnectionManager {

	private UserStatus us;
	
	public FindInfoOfUserFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestUser req = (RequestUser)request;
		String ownername, email, realName;
		int status, userId;
		String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 12);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, req.getUserId());
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
					userId = rset.getInt("USERID");
					ownername = rset.getString("USERNAME");
					status = rset.getInt("STATUS");
					email = rset.getString("email");
					realName = rset.getString("realname");
					us = new UserStatus(ownername, status, false, userId);
					us.setEmail(email);
					us.setRealName(realName);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public UserStatus getUs() {
		return us;
	}

}
