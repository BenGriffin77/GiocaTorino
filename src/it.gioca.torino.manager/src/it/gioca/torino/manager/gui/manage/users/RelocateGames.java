package it.gioca.torino.manager.gui.manage.users;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.game.remove.ListOfGameByUser;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.TinyGame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class RelocateGames extends MainDialog{

	private Table tableItems;
	private String userName;

	public RelocateGames(Shell parent) {
		super(parent);
	}
	
	@Override
	protected void createTextWidgets() {
		
		{
			Group buttonsGroup = FormUtil.createAGroup(composite, 2, 2, "", true);
			drawButton(Messages.getString("RelocateGames.2"),buttonsGroup, EBUTTON.OK);
			drawButton(Messages.getString("RelocateGames.3"),buttonsGroup, EBUTTON.INDIETRO);
		}
		{
			ColumnType[] columsName = {	new ColumnType("ID", CTYPE.INT), 
					new ColumnType("Immagine", CTYPE.IMAGE),
					new ColumnType("Nome", CTYPE.TEXT)};
			tableItems = FormUtil.createTable(composite, 2, columsName);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			data.heightHint = tableItems.getShell().getBounds().height;
			tableItems.setLayoutData(data);
			tableItems.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					if(tableItems.getSelectionIndices().length>0){
						save();
						close();
					}
				}
			});
		}
		fillTheTable();
	}

	private void fillTheTable() {
		
		RequestUser ru = new RequestUser();
		ru.setCheckGame(false);
		ru.setUserName(userName);
		ListOfGameByUser logbu = new ListOfGameByUser(ru);
		TableItem ti;
		for(TinyGame tg: logbu.getGames()){
			ti = new TableItem(tableItems, SWT.NONE);
			ti.setText(0, tg.getGameId()+"");
			if(tg.getThumbnail()!=null){
				try{
					ti.setImage(1, FormUtil.setImageInTheTable(tg.getThumbnail()));
				}catch(Exception e){
					
				}
			}
			ti.setText(2, tg.getName());
		}
		for(TableColumn tc: tableItems.getColumns())
			tc.pack();
	}

	@Override
	protected void find() {
		
	}

	@Override
	protected void save() {
		int[] selects = tableItems.getSelectionIndices();
		if(selects!=null && selects.length !=-1){
			
		}
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void impl() {
		// TODO Auto-generated method stub
		
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
