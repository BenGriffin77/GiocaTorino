package it.gioca.torino.manager.db.facade.users;

import it.gioca.torino.manager.db.ConnectionManager;
import it.gioca.torino.manager.db.IRequestDTO;
import it.gioca.torino.manager.db.SingletonQuery;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserListFacade extends ConnectionManager {

	public UserListFacade(IRequestDTO request) {
		super(request);
	}

	private List<String> users;
	
	@Override
	protected void execute(IRequestDTO request) {
		
		users = new ArrayList<String>();
		
		RequestGetUsers req = (RequestGetUsers)request;
		try{
			String query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", req.isAll()? 4:0);
			if(req.isExit())
				query = SingletonQuery.getInstance().getQuery("DIMOSTRATORI", 5);
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if(rset!=null)
			     while(rset.next()){
			    	 String user = rset.getString("USERNAME");
			    	 users.add(user);
			     }
		}	catch (SQLException e) {
				e.printStackTrace();
		}
	}

	public String[] getUsers(){
		String []dsf = new String[users.size()];
		return users.toArray(dsf);
	}

}
