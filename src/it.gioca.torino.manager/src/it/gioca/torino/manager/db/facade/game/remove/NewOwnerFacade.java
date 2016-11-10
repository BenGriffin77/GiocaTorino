package it.gioca.torino.manager.db.facade.game.remove;

import java.sql.SQLException;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;

public class NewOwnerFacade extends ConnectionManager {

	public NewOwnerFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		
		RequestNewOwner req = (RequestNewOwner)request;
		RequestUser ru = new RequestUser();
		ru.setUserName(req.getNewOwner());
		FindIDUserFacade fiuf = new FindIDUserFacade(ru);
		int ownerId = fiuf.getId();
		
		ru.setUserName(req.getOldOwner());
		fiuf = new FindIDUserFacade(ru);
		int oldOwnerId = fiuf.getId();
		
		for(int idGame: req.getGames()){
			String query  = SingletonQuery.getInstance().getQuery("LEAVE", 3);
			try{
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, ownerId);
				pstmt.setInt(2, oldOwnerId);
				pstmt.setInt(3, idGame);
				pstmt.setInt(4, oldOwnerId);
				pstmt.executeUpdate();
				exit = true;
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

}
