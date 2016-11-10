package it.gioca.torino.manager.db.facade.game;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.ManageUserRequest;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;

import java.sql.SQLException;

public class ManageUserFacade extends ConnectionManager {

	public ManageUserFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		ManageUserRequest req = (ManageUserRequest) request;
		UserStatus u = req.getUserStatus();
		String query = null;
		switch(u.getStatus()){
		case OK: return;
		case TODELETE: query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 9); break;
		case REENTER:
		case GONE:
		case NOTODAY: query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 10); break;
		}
		try{
			pstmt = conn.prepareStatement(query);
			
			switch(u.getStatus()){
			case TODELETE: pstmt.setInt(1, u.getUserId()); break;
			case REENTER:
			case GONE:
			case NOTODAY: {
				pstmt.setInt(1, u.getStatusById());
				pstmt.setInt(2, u.getUserId());
				break;
			}
			default: return;
			}
			pstmt.executeUpdate();
			exit = true;
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
