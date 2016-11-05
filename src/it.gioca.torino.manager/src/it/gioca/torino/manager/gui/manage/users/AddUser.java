package it.gioca.torino.manager.gui.manage.users;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.users.AddUserFacade;
import it.gioca.torino.manager.db.facade.users.UpdateUserFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddUser extends MainDialog {

	private Text email;
	private Text realName;
	private Text userName;

	public AddUser(Shell parent) {
		super(parent);
	}

	@Override
	protected void createTextWidgets() {
		
		{
			Group group = FormUtil.createAGroup(composite, 2, 2, "", true);
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.1"));
			userName = FormUtil.createText(group, "");
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.2"));
			realName = FormUtil.createText(group, "");
			FormUtil.createLabel(group, 1, Messages.getString("RenameUser.3"));
			email =  FormUtil.createText(group, "");
		}
		{
			Group buttonsGroup = FormUtil.createAGroup(composite, 2, 2, "", true);
			drawButton(Messages.getString("RenameUser.4"),buttonsGroup, EBUTTON.OK);
			drawButton(Messages.getString("RenameUser.5"),buttonsGroup, EBUTTON.INDIETRO);
		}
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
		UserStatus us = new UserStatus(userName, 0, false, 0);
		us.setEmail(eMail);
		us.setRealName(realName);
		us.setUserName(userName);
		RequestUser ru = new RequestUser();
		ru.setUserStatus(us);
		ru.setUserName(us.getUsername());
		AddUserFacade auf = new AddUserFacade(ru);
		ru.getUserStatus().setUserId(auf.getId());
		new UpdateUserFacade(ru);
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void impl() {
		// TODO Auto-generated method stub

	}

}
