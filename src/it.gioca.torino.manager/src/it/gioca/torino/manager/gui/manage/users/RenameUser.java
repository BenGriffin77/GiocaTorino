package it.gioca.torino.manager.gui.manage.users;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.users.FindInfoOfUserFacade;
import it.gioca.torino.manager.db.facade.users.UpdateUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RenameUser extends MainDialog {

	private int userId;
	private Text userName;
	private Text realName;
	private Text email;
	private UserStatus us;
	
	public RenameUser(Shell parent) {
		super(parent);
	}

	@Override
	protected void createTextWidgets() {
		
		RequestUser request = new RequestUser();
		request.setUserId(userId);
		FindInfoOfUserFacade fiou = new FindInfoOfUserFacade(request);
		us = fiou.getUs();
		{
			Group group = FormUtil.createAGroup(composite, 2, 2, "", true);
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.1"));
			userName = FormUtil.createText(group, us.getUsername()==null? "": us.getUsername());
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.2"));
			realName = FormUtil.createText(group, us.getRealName()==null? "": us.getRealName());
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.3"));
			email =  FormUtil.createText(group, us.getEmail()==null? "": us.getEmail());
		}
		{
			Group buttonsGroup = FormUtil.createAGroup(composite, 2, 2, "", true);
			drawButton(Messages.getString("RenameUser.4"),buttonsGroup, EBUTTON.OK);
			drawButton(Messages.getString("RenameUser.5"),buttonsGroup, EBUTTON.INDIETRO);
		}
	}
	
	public void setUserStatus(UserStatus us){
		this.us = us;
	}

	@Override
	protected void find() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void save() {
		
		String userName = this.userName.getText();
		String realName = this.realName.getText();
		String eMail = this.email.getText();
		us.setEmail(eMail);
		us.setRealName(realName);
		us.setUserName(userName);
		RequestUser request = new RequestUser();
		request.setUserStatus(us);
		new UpdateUserFacade(request);
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void impl() {
		// TODO Auto-generated method stub

	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
