package it.gioca.torino.manager.db.facade.users;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;

import java.sql.SQLException;

public class UpdateUserFacade extends ConnectionManager {

	public UpdateUserFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {

		RequestUser req = (RequestUser)request;
		String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 13);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, req.getUserStatus().getUsername());
			pstmt.setString(2, req.getUserStatus().getEmail());
			pstmt.setString(3, req.getUserStatus().getRealName());
			pstmt.setInt(4, req.getUserStatus().getUserId());
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
