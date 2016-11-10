package it.gioca.torino.manager.db.facade.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestFindHistory;

public class UserHistoryFindFacade extends ConnectionManager {

	private String[] users;
	
	public UserHistoryFindFacade(IRequestDTO request) {
		super(request);
	}

	@Override
	protected void execute(IRequestDTO request) {
		
		RequestFindHistory req = (RequestFindHistory)request;
		List<String> list = new ArrayList<String>();
		String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 14);
		try{
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, req.getGameId());
			rset = pstmt.executeQuery();
			String userName;
			if(rset!=null)
				while(rset.next()){
					userName = rset.getString("USERNAME");
					list.add(userName);
				}
			String[] toClicle = req.getUsers();
			boolean owner = false;
			int counter=0;
			if(list.size()>0){
				for(String user: list){
					for(int i=0; i<toClicle.length; i++){
						String controUser = toClicle[i];
						if(!owner){
							if(controUser.equalsIgnoreCase(req.getOwner())){
								String tmp = toClicle[counter];
								toClicle[counter] = "P* "+user;
								toClicle[i] = tmp;
								counter++;
								owner = true;
								continue;
							}
						}
						else{
							if(controUser.equalsIgnoreCase(user)){
								String tmp = toClicle[counter];
								toClicle[counter] = "D* "+user;
								toClicle[i] = tmp;
								counter++;
							}
						}
					}
				}
			}
			Arrays.sort(toClicle, counter, toClicle.length);
			users = toClicle;
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public String[] getUsers(){
		return users;
	}
	
}
