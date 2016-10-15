package it.gioca.torino.manager.db.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;

public class ManageNames extends ConnectionManager {

	public ManageNames(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		List<GameElement> elements = new ArrayList<GameElement>();
		String query = SingletonQuery.getInstance().getQuery("MANAGEMENT", 0);
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
			
			
			query = SingletonQuery.getInstance().getQuery("MANAGEMENT", 1);
			for(GameElement ge: elements){
				for(String name: ge.getNames()){
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, ge.getGameId());
					pstmt.setString(2, name);
					pstmt.execute();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
