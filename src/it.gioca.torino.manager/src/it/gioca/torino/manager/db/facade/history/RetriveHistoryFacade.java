package it.gioca.torino.manager.db.facade.history;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class RetriveHistoryFacade extends ConnectionManager {

	private List<HistoryItem> items;
	
	public RetriveHistoryFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {

		items = new ArrayList<HistoryItem>();
		String query = SingletonQuery.getInstance().getQuery("HISTORY", 3);
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			HistoryItem item;
			if(rset!=null)
				while(rset.next()){
					item = new HistoryItem();
					item.setGameName(rset.getString("NAME"));
					item.setUserName(rset.getString("USERNAME"));
					item.setTimes(rset.getInt("TIMES"));
					item.setMaxPlayTime(rset.getInt("MAXTIME"));
					item.setMinPlayTime(rset.getInt("MINTIME"));
					items.add(item);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public List<HistoryItem> getItems(){
		return items;
	}
}
