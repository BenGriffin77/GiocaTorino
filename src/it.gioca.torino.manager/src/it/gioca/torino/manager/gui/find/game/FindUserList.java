package it.gioca.torino.manager.gui.find.game;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.users.UserListFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class FindUserList extends MainDialog {

	private Combo userCombo;
	private String userSelected = null;

	public FindUserList(Shell parent) {
		super(parent);
	}

	@Override
	protected void createTextWidgets() {
		
		FormUtil.createLabel(composite, 1, Messages.getString("FindUserList.1"));
		userCombo = FormUtil.createCombo(composite, 1, getUsers());
		userCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userSelected = userCombo.getText();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		if(userCombo.getItemCount()>0)
			userSelected = userCombo.getItem(0);
		{
			Group buttonsGroup = FormUtil.createAGroup(composite, 2, 2, "", true);
			drawButton(Messages.getString("FindGameDialog.4"),buttonsGroup, EBUTTON.FIND);
			drawButton(Messages.getString("FindGameDialog.6"),buttonsGroup, EBUTTON.INDIETRO);
		}
	}
	
	private String[] getUsers(){
		
		RequestGetUsers request = new RequestGetUsers();
		request.setAll(false);
		UserListFacade ulf = new UserListFacade(request);
		String[] users = ulf.getUsers();
		return users;
	}
	
	public String getName(){
		
		return userSelected;
	}

	@Override
	protected void find() {
		
		composite.layout();
		close();
	}

	@Override
	protected void save() {
		
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		
	}

}
