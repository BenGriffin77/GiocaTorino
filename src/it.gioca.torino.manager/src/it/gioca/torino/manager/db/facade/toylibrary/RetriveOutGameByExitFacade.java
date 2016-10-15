package it.gioca.torino.manager.db.facade.toylibrary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class RetriveOutGameByExitFacade extends ConnectionManager {

	private List<String> idsExit;
	
	public RetriveOutGameByExitFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		idsExit = new ArrayList<String>();
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 9);
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset!=null){
				while(rset.next()){
					int idExit = rset.getInt("ID_EXIT");
					idsExit.add(idExit+"");
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public List<String> getIdsExit(){
		return idsExit;
	}
}
