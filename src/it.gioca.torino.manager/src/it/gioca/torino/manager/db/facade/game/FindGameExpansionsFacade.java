package it.gioca.torino.manager.db.facade.game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.game.request.RequestFindGame;
import it.gioca.torino.manager.gui.util.TinyGame;

public class FindGameExpansionsFacade extends ConnectionManager {

	private List<TinyGame> games;
	
	public FindGameExpansionsFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestFindGame req = (RequestFindGame)request;
		int idGame = req.getGameId();
		games = new ArrayList<TinyGame>();
		try{
			String query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", 6);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idGame);
			rset = pstmt.executeQuery();
			while(rset.next()){
				String expansion = rset.getString("EXPANSION");
				StringTokenizer st = new StringTokenizer(expansion, ";");
				query = SingletonQuery.getInstance().getQuery("FINDBOARDGAMES", 0);
				while(st.hasMoreTokens()){
					idGame = Integer.parseInt(st.nextToken());
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, idGame);
					rset = pstmt.executeQuery();
					int expansionId;
					String name;
					while(rset.next()){
						expansionId = rset.getInt("ID");
				    	name = rset.getString("NAME");
				    	games.add(new TinyGame(expansionId, name, null));
					}
				}
			}
		}	catch (SQLException e) {
				e.printStackTrace();
		}
	}

	public List<TinyGame> getExpansions(){
		return games;
	}
}
