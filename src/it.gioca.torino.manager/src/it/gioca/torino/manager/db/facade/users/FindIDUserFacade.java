package it.gioca.torino.manager.db.facade.users;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;

public class FindIDUserFacade extends ConnectionManager {

	public FindIDUserFacade(IRequestDTO request) {
		super(request);
	}

	private int id;
	
	@Override
	protected void execute(IRequestDTO request) {
		
		try{
			RequestUser ru = (RequestUser)request;
			String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI",1);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, ru.getUserName());
			rset = pstmt.executeQuery();
			if(rset!=null)
			     while(rset.next()){
			    	 id = rset.getInt("USERID");
			     }
		}	catch (SQLException e) {
				e.printStackTrace();
		}
	}

	public int getId(){
		return id;
	}
}
