package it.gioca.torino.manager.gui.manage;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.common.ThemeManager.COLOR;
import it.gioca.torino.manager.db.facade.game.remove.ListOfGameByUser;
import it.gioca.torino.manager.db.facade.game.remove.RemoveGames;
import it.gioca.torino.manager.db.facade.game.remove.RequestNewOwner;
import it.gioca.torino.manager.db.facade.users.UserListFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class UnloadGames extends MainForm {
private Combo user;
private Table tableGames;
private Button forceRemove;
private Button remove;

	//
	public UnloadGames(String stateName, String title) {
		super(stateName, title);
	}
//
	@Override
	public void createFrom() {
		drawButton(Messages.getString("AddListGui.0"), getMenuLaterale(), EBUTTON.ADD_LIST); //$NON-NLS-1$ //PREPARA LA LIST
		drawButton(Messages.getString("AddListGui.1"), getMenuLaterale(), EBUTTON.LOAD_LIST); //$NON-NLS-1$ //RICHIAMA
		drawButton(Messages.getString("AddListGui.2"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.4"), getMenuLaterale(), EBUTTON.INDIETRO); //$NON-NLS-1$
		drawCentrale();

	}

	private void drawCentrale() {
		
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createGenGroup(centrale, 1, 2, "", true);
		{
			FormUtil.createLabel(group, 1, Messages.getString("ManageGui.7"));
			RequestGetUsers request = new RequestGetUsers();
			request.setExit(true);
			UserListFacade ulf = new UserListFacade(request);
			String[] users = ulf.getUsers();
			user = FormUtil.createCombo(group, 1, users);
			user.setData("USER");
			user.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String userName = user.getText();
					RequestUser request = new RequestUser();
					request.setUserName(userName);
					tableGames.removeAll();
					ListOfGameByUser fogbu = new ListOfGameByUser(request);
					addItemsToTheTable(fogbu.getGames(), false);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
		}
		ColumnType[] columns = {new ColumnType(Messages.getString("ManageGui.8"), CTYPE.TEXT),
				new ColumnType(Messages.getString("ManageGui.9"), CTYPE.TEXT)};
		tableGames = FormUtil.createSingleTable(group, columns);
		
		{
			Group functions = FormUtil.createAGroup(group, 1, 1, Messages.getString("ManageGui.4"), true);
			forceRemove = drawButton(Messages.getString("ManageGui.19"), functions, EBUTTON.FORCE_REMOVE_GAME);
			remove = drawButton(Messages.getString("ManageGui.6"),functions, EBUTTON.REMOVE_GAME);
			remove.setEnabled(false);
			forceRemove.setEnabled(false);
		}
		centrale.layout();
	}
	
	private void addItemsToTheTable(List<BoardGame> games, boolean newElements) {
		
		if(games==null)
			return;
		boolean canRemoveItems = true;
		if(games.size()>0){
			for(BoardGame game: games){
				TableItem ti = new TableItem(tableGames, SWT.NONE);
				ti.setText(new String[]{game.getName(),game.getGameId()+""});
				if(game.getStatusGame()!=0)
					ti.setBackground(ThemeManager.getColor(COLOR.RED));
				if(canRemoveItems){
					canRemoveItems = game.getStatusGame()==0;
				}
			}
		}
		for(TableColumn tc: tableGames.getColumns())
			tc.pack();
		if(canRemoveItems==true){
			forceRemove.setEnabled(false);
			remove.setEnabled(true);
		}
		else{
			forceRemove.setEnabled(true);
			remove.setEnabled(false);
		}
	}

	@Override
	public void afterCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
//				case SAVE: {saveForm();
//							if(!caricaForm) 
//								Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); 
//							break;
//							}
//				case ADD_GAME: findGame(); break;
//				case FORCE_REMOVE_GAME: sendMessage();break;
//				case REMOVE_GAME: removeSelection(); break;
//				case FUNCTIONS_SAVE: saveSelections(); break;
				case FORCE_REMOVE_GAME: sendMessage();break;
				case REMOVE_GAME: removeUser(); break;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	protected void removeUser() {
		
		String userName = user.getText();
		RequestNewOwner rno = new RequestNewOwner();
		rno.setNewOwner(userName);
		RemoveGames rg = new RemoveGames(rno);
		if(rg.isCorrectExit()){
			tableGames.removeAll();
			RequestGetUsers request = new RequestGetUsers();
			request.setExit(true);
			UserListFacade ulf = new UserListFacade(request);
			String[] users = ulf.getUsers();
			user.setItems(users);
		}
	}
	private void sendMessage(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		ForceRemove fr = new ForceRemove(shell);
		fr.setUserName(user.getText());
		fr.open();
	}
	
	private enum EBUTTON{
		
		REMOVE_GAME,
		ADD_GAME,
		FUNCTIONSAVE,
		SAVE,
		ADD_LIST,
		LOAD_LIST,
		MANAGE,
		FORCE_REMOVE_GAME,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case MANAGE: return "finalize";
			case LOAD_LIST: return "loadList";
			case SAVE:
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}
}
