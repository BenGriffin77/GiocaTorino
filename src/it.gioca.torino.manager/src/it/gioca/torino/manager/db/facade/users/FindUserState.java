package it.gioca.torino.manager.db.facade.users;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserState extends ConnectionManager{

	private List<UserStatus> mu;
	
	public FindUserState(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		mu = new ArrayList<UserStatus>();
		String ownername;
		int status, userId;
		boolean hasGame;
		String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 8);
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
					userId = rset.getInt("USERID");
					ownername = rset.getString("USERNAME");
					status = rset.getInt("STATUS");
					hasGame = rset.getInt("COUNT")>0;
					mu.add(new UserStatus(ownername, status, hasGame, userId));
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		for(UserStatus us: mu){
			if(!us.isHasGame()){
				query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 11);
				try{
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, us.getUserId());
					rset = pstmt.executeQuery();
					if(rset!=null)
						while(rset.next()){
							us.setHasGame(rset.getInt("COUNT")>0);
						}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	public List<UserStatus> getUsers(){
		return this.mu;
	}
}
