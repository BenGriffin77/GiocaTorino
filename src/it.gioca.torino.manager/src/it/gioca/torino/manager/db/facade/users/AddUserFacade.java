package it.gioca.torino.manager.db.facade.users;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;

public class AddUserFacade extends ConnectionManager {

	private int id;
	
	public AddUserFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		try{
			String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI",3);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()){
				int newId = rset.getInt("MAX")+1;
				RequestUser ru = (RequestUser)request;
				query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI",2);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, newId);
				pstmt.setString(2, ru.getUserName());
				pstmt.execute();
				id = newId;
			}
		}	catch (SQLException e) {
				e.printStackTrace();
		}
	}

	public int getId(){
		return id;
	}
}
