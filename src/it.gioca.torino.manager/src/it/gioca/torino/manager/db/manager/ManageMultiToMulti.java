package it.gioca.torino.manager.db.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class ManageMultiToMulti extends ConnectionManager {

	public ManageMultiToMulti(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {

		RequestMulti req = (RequestMulti)request;
		
		boolean category = req.isCategory();
		List<GameElement> elements = new ArrayList<GameElement>();
		String query = SingletonQuery.getInstance().getQuery("MANAGEMENT", category? 7:2);
		try{
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			GameElement gm;
			if(rset!=null){
				while(rset.next()){
					String tmp = rset.getString("NAMES");
					if(tmp!=null && !tmp.equalsIgnoreCase("")){
						gm = new GameElement();
						gm.setGameId(rset.getInt("ID"));
						gm.setNames(rset.getString("NAMES"));
						elements.add(gm);
					}
				}
			}
			
			for(GameElement ge: elements){
				for(String name: ge.getNames()){
					query = SingletonQuery.getInstance().getQuery("MANAGEMENT", category? 10:5);
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, name);
					rset = pstmt.executeQuery();
					try{
						if(rset!=null){
							boolean added = false;
							while(rset.next()){
								int idDesigner = rset.getInt(category? "ID_CATEGORY":"ID_DESIGNER");
								insertFK(ge.getGameId(), idDesigner, category);
								added=true;
							}
							if(!added){
								int newId = addNewDesigner(name, category);
								insertFK(ge.getGameId(), newId, category);
							}
						}
					}catch(SQLException e){
						continue;
					}
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void insertFK(int gameid, int designerId, boolean category) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("MANAGEMENT", category? 9:4);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, gameid);
		pstmt.setInt(2, designerId);
		pstmt.execute();
	}

	private int addNewDesigner(String name, boolean category) throws SQLException{
		
		String query = SingletonQuery.getInstance().getQuery("MANAGEMENT", category? 11:6);
		pstmt = conn.prepareStatement(query);
		rset = pstmt.executeQuery();
		int max=0;
		if(rset!=null)
			while(rset.next()){
				max = rset.getInt("MAX");
			}
		
		query = SingletonQuery.getInstance().getQuery("MANAGEMENT", category? 8:3);
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, max+1);
		pstmt.setString(2, name);
		pstmt.execute();
		return max+1;
	}
}
