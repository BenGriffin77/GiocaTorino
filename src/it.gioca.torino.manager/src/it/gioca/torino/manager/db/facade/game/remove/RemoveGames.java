package it.gioca.torino.manager.db.facade.game.remove;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;

public class RemoveGames extends ConnectionManager{

	public RemoveGames(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestNewOwner req = (RequestNewOwner)request;
		RequestUser ru = new RequestUser();
		ru.setUserName(req.getNewOwner());
		FindIDUserFacade fiuf = new FindIDUserFacade(ru);
		int ownerId = fiuf.getId();
		
		String query  = SingletonQuery.getInstance().getQuery("LEAVE", 1);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, ownerId);
			pstmt.executeUpdate();
			
			query  = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 6);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, ownerId);
			pstmt.executeUpdate();
			
			exit = true;
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
