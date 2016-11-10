package it.gioca.torino.manager.gui.manage;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.game.remove.ListOfGameByUser;
import it.gioca.torino.manager.db.facade.game.remove.NewOwnerFacade;
import it.gioca.torino.manager.db.facade.game.remove.RequestNewOwner;
import it.gioca.torino.manager.db.facade.users.UserListFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ForceRemove extends MainDialog {

	private Table tableItems;
	
	private String userName;

	private Button SAVE;

	private Button CHANGEOWNER;

	private Group buttonsGroup;

	private Combo newOwner;

	private Button SAVENEWOWNER;
	
	public ForceRemove(Shell parent) {
		super(parent);
	}
	
	@Override
	protected void createTextWidgets() {
	
		ColumnType[] columsName = {new ColumnType(Messages.getString("ForceRemove.4"), CTYPE.INT), 
				   	new ColumnType(Messages.getString("ForceRemove.0"), CTYPE.IMAGE),
				   	new ColumnType(Messages.getString("ForceRemove.1"), CTYPE.TEXT),
					new ColumnType(Messages.getString("ForceRemove.2"), CTYPE.TEXT),
					new ColumnType(Messages.getString("ForceRemove.3"), CTYPE.TEXT)};
		tableItems = FormUtil.createTable(composite, 1, columsName);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = tableItems.getShell().getBounds().height;
		tableItems.setLayoutData(data);
		tableItems.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem[] items = tableItems.getSelection();
				if(items!=null && items.length>0)
					CHANGEOWNER.setEnabled(true);
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		find();
		{
			buttonsGroup = FormUtil.createAGroup(composite, 1, 3, "", true);
			SAVE = drawButton(Messages.getString("FindGameDialog.5"),buttonsGroup, EBUTTON.OK);
			SAVE.setEnabled(false);
			CHANGEOWNER = drawButton(Messages.getString("ForceRemove.8"), buttonsGroup, CUSTOM_EBUTTON.CHANGEOWNER);
			CHANGEOWNER.setEnabled(false);
			drawButton(Messages.getString("FindGameDialog.6"),buttonsGroup, EBUTTON.INDIETRO);
		}
	}

	@Override
	protected void find() {
		
		RequestUser request = new RequestUser();
		request.setUserName(userName);
		request.setCheckGame(true);
		ListOfGameByUser fogbu = new ListOfGameByUser(request);
		TableItem ti;
		for(BoardGame bg: fogbu.getGames()){
			ti = new TableItem(tableItems, SWT.NONE);
			ti.setText(0, bg.getGameId()+"");
			if(bg.getThumbnail()!=null){
				try{
					ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
//					ti.setImage(1, new Image(Display.getCurrent(), new ByteArrayInputStream(bg.getThumbnail())));
				}catch(Exception e){
					
				}
			}
			ti.setText(2, bg.getName());
			ti.setText(3, bg.getDimostrator());
			switch(bg.getStatusGame()){
			case 1: ti.setText(4, Messages.getString("ForceRemove.5")); break;
			case 2: ti.setText(4, Messages.getString("ForceRemove.6")); break;
			}
		}
		for(TableColumn tc: tableItems.getColumns())
			tc.pack();
	}
	
	private void moveOwner(){
		
		newOwner = FormUtil.createCombo(buttonsGroup, 2, getUsers());
		SAVENEWOWNER = drawButton(Messages.getString("ForceRemove.9"), buttonsGroup, CUSTOM_EBUTTON.SAVENEWOWNER);
		buttonsGroup.layout();
	}
	
	private String[] getUsers(){
		
		RequestGetUsers request = new RequestGetUsers();
		request.setAll(true);
		UserListFacade ulf = new UserListFacade(request);
		String[] users = ulf.getUsers();
		return users;
	}
	
	private void saveNewOwner(){
		
		if(newOwner==null || newOwner.isDisposed() || newOwner.getSelectionIndex()==-1)
			return;
		
		TableItem[] items = tableItems.getSelection();
		if(items==null || items.length==0)
			return;
		List<Integer> gamesIds = new ArrayList<Integer>();
		for(TableItem ti: items)
			gamesIds.add(Integer.parseInt(ti.getText(0)));
		RequestNewOwner request = new RequestNewOwner();
		request.setNewOwner(newOwner.getText());
		request.setOldOwner(userName);
		request.setGames(gamesIds);
		NewOwnerFacade nof = new NewOwnerFacade(request);
		if(nof.isCorrectExit()){
			SAVENEWOWNER.dispose();
			newOwner.dispose();
		}
	}
	
	protected Button drawButton(String text, Composite c, final CUSTOM_EBUTTON ec) {
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(ec){
				case SAVENEWOWNER: saveNewOwner(); break;
				case CHANGEOWNER: moveOwner();break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	private enum CUSTOM_EBUTTON{
		CHANGEOWNER,
		SAVENEWOWNER
	}

	@Override
	protected void save() {

	}

	@Override
	protected void back() {

	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}

	@Override
	protected void impl() {
		// TODO Auto-generated method stub
		
	}
	
}
