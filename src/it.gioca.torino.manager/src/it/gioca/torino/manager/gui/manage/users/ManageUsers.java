package it.gioca.torino.manager.gui.manage.users;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.facade.game.ManageUserFacade;
import it.gioca.torino.manager.db.facade.game.request.ManageUserRequest;
import it.gioca.torino.manager.db.facade.users.FindUserState;
import it.gioca.torino.manager.db.facade.users.request.UserStatus;
import it.gioca.torino.manager.db.facade.users.request.UserStatus.USTATUS;
import it.gioca.torino.manager.gui.YESNODialog;
import it.gioca.torino.manager.gui.YESNODialog.Action;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ManageUsers extends MainForm {

	private Table tableUsers;
	private Text filter;
	private Button deleteUser;
	private List<UserStatus> users;
	private Button notHere;

	public ManageUsers(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		drawButton(Messages.getString("ManageUsers.0"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		buildCentral();
	}
	
	private void buildCentral(){
		
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createAGroup(centrale, 1, 2, "", true);
		{
			FormUtil.createLabel(group, 2, Messages.getString("ManageUsers.2"));
			ColumnType[] columns = {new ColumnType(Messages.getString("ManageUsers.3"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ManageUsers.4"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ManageUsers.8"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ManageUsers.10"), CTYPE.INT)};
			tableUsers = FormUtil.createTable(group, columns);
			tableUsers.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					int sel = tableUsers.getSelectionIndex();
					if(sel!=-1){
						String text = tableUsers.getItem(sel).getText(2);
						if(text.contains("NO") && !tableUsers.getItem(sel).getText(0).equalsIgnoreCase("GIOCA TORINO"))
							deleteUser.setEnabled(true);
						else
							deleteUser.setEnabled(false);
						notHere.setEnabled(true);
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			{
				Group subGroup = FormUtil.createAGroup(group, 1, 1, "", true);
				FormUtil.createLabel(subGroup, 1, Messages.getString("ManageUsers.5"));
				filter = FormUtil.createText(subGroup, "");
				filter.addListener(SWT.Traverse, new Listener() {
					
					public void handleEvent(Event arg0) {
						if(arg0.detail == SWT.TRAVERSE_RETURN)
							fillTheTable();
					}
				});
				drawButton(Messages.getString("ManageUsers.7"), subGroup, EBUTTON.CLEAR);
				{
					Group functions = FormUtil.createAGroup(subGroup, 1, 1, Messages.getString("ManageUsers.6"), true);
					
					deleteUser = drawButton(Messages.getString("ManageUsers.9"), functions, EBUTTON.CANCELLAUTENTE);
					notHere = drawButton(Messages.getString("ManageUsers.12"), functions, EBUTTON.NONPRESENTE);
					notHere.setEnabled(false);
					deleteUser.setEnabled(false);
				}
			}
		}
		fillTheTable();
		centrale.layout();
	}
	
	private void fillTheTable(){
		
		FindUserState rus = new FindUserState(null);
		users = rus.getUsers();
		
		tableUsers.removeAll();
		TableItem ti;
		if(users!=null && users.size()>0){
			for(UserStatus u: users){
				if(filter.getText()!=""){
					String text = filter.getText();
					if(!u.getUsername().toLowerCase().contains(text.toLowerCase()))
						continue;
				}
				ti = new TableItem(tableUsers, SWT.NONE);
				ti.setText(0, u.getUsername());
				ti.setText(1, u.getStatusToString());
				ti.setText(2, u.isHasGame()?"SI":"NO");
				ti.setText(3, u.getUserId()+"");
			}
		}
		for(TableColumn tc: tableUsers.getColumns())
			tc.pack();
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy;
		dummy = FormUtil.createDummyButton(c, text);
		
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				
				switch(eB){
				case CLEAR: filter.setText(""); fillTheTable(); break;
				case CANCELLAUTENTE: deleteUser();break;
				case NONPRESENTE: notHere(); break;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}

	protected void notHere() {
		
		if(tableUsers.getSelectionIndex()==-1) 
			return;
		Shell shell = Display.getCurrent().getActiveShell();
		YESNODialog yn = new YESNODialog(shell);
		TableItem item = tableUsers.getItem(tableUsers.getSelectionIndex());
		int id = Integer.parseInt(item.getText(3));
		if(yn.open(Messages.getString("ManageUsers.13")+" "+item.getText(0)+ " " +Messages.getString("ManageUsers.14"))==Action.YES){
			if(item.getText(2).equalsIgnoreCase("SI")){
				RelocateGames rg = new RelocateGames(shell);
				rg.setUserName(item.getText(0));
				rg.open();
			}
			else{
				for(UserStatus u: users){
					if(u.getUserId() == id){
						ManageUserRequest request = new ManageUserRequest();
						u.setStatus(USTATUS.GONE);
						request.setUserStatus(u);
						new ManageUserFacade(request);
						fillTheTable();
					}
				}
			}
		}
	}

	protected void deleteUser() {
		
		if(tableUsers.getSelectionIndex()==-1) 
			return;
		Shell shell = Display.getCurrent().getActiveShell();
		YESNODialog yn = new YESNODialog(shell);
		if(yn.open(Messages.getString("ManageUsers.11")+" "+tableUsers.getSelection()[0].getText(0) + "?")==Action.YES){
			int id = Integer.parseInt(tableUsers.getSelection()[0].getText(3));
			for(UserStatus u: users){
				if(u.getUserId() == id){
					ManageUserRequest request = new ManageUserRequest();
					u.setStatus(USTATUS.TODELETE);
					request.setUserStatus(u);
					new ManageUserFacade(request);
					fillTheTable();
				}
			}
		}
		deleteUser.setEnabled(false);
		notHere.setEnabled(false);
	}

	@Override
	public void afterCreate() {

	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}

	enum EBUTTON{
		
		CLEAR,
		NONPRESENTE,
		CANCELLAUTENTE,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}
}
