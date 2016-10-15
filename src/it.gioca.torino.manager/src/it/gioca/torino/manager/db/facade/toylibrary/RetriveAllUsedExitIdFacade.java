package it.gioca.torino.manager.db.facade.toylibrary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class RetriveAllUsedExitIdFacade extends ConnectionManager {

	private List<Integer> usedValues;
	
	public RetriveAllUsedExitIdFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		usedValues = new ArrayList<Integer>();
		String query = SingletonQuery.getInstance().getQuery("BOARDGAME_STATUS", 6);
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset!=null)
				while(rset.next()){
					int value = rset.getInt("ID_EXIT");
					if(value==0)
						continue;
					usedValues.add(value);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public List<Integer> getUsedValues(){
		return usedValues;
	}
}
