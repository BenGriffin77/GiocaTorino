package it.gioca.torino.manager.gui.find.game;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.MainDialog;
import it.gioca.torino.manager.db.facade.game.FindGameFacade;
import it.gioca.torino.manager.db.facade.game.request.RequestFindGame;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.TinyGame;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class FindGameDialog extends MainDialog{

	private Button OK;

	private Table tableItems;
	private Text iDBgg;
	private Text titolo;
	private Text autore;
	private List<BoardGame> items;
	private List<BoardGame> saved;

	public FindGameDialog(Shell parent) {
		super(parent);
	}
	
	@Override
	protected void createTextWidgets(){
		
		FormUtil.createLabel(composite, 1, Messages.getString("FindGameDialog.2"));
		titolo = FormUtil.createText(composite, "");
		FormUtil.createLabel(composite, 1, Messages.getString("FindGameDialog.1"));
		iDBgg = FormUtil.createText(composite, "");
		iDBgg.addListener(SWT.Traverse, new Listener() {
			
			public void handleEvent(Event arg0) {
				if(arg0.detail == SWT.TRAVERSE_RETURN)
					find();
			}
		});
		iDBgg.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				autore.setText("");
				titolo.setText("");
			}
		});
		iDBgg.addListener(SWT.Verify, new Listener() {
		      
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
		        	 }
		        }
		    }
		});
		titolo.addListener(SWT.Traverse, new Listener() {
			
			public void handleEvent(Event arg0) {
				if(arg0.detail == SWT.TRAVERSE_RETURN)
					find();
			}
		});
		titolo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				autore.setText("");
				iDBgg.setText("");
			}
		});
		
		FormUtil.createLabel(composite, 1, Messages.getString("FindGameDialog.3"));
		autore = FormUtil.createText(composite, "");
		autore.addListener(SWT.Traverse, new Listener() {
			
			public void handleEvent(Event arg0) {
				if(arg0.detail == SWT.TRAVERSE_RETURN)
					find();
			}
		});
		autore.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				titolo.setText("");
				iDBgg.setText("");
			}
		});
		
		{
			Group buttonsGroup = FormUtil.createAGroup(composite, 2, 3, "", true);
			drawButton(Messages.getString("FindGameDialog.4"),buttonsGroup, EBUTTON.FIND);
			OK = drawButton(Messages.getString("FindGameDialog.5"),buttonsGroup, EBUTTON.OK);
			OK.setEnabled(false);
			drawButton(Messages.getString("FindGameDialog.6"),buttonsGroup, EBUTTON.INDIETRO);
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
					OK.setEnabled(true);
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
	}
	
	@Override
	protected void find(){
		
		tableItems.removeAll();
		RequestFindGame rfg = new RequestFindGame();
		String id = iDBgg.getText();
		rfg.setGameId(id.equalsIgnoreCase("")? 0:Integer.parseInt(id));
		rfg.setGameName(titolo.getText());
		rfg.setDesigner(autore.getText());
		rfg.setExpansions(false);
		
		FindGameFacade fgf = new FindGameFacade(rfg);
		List<BoardGame> games = fgf.getList();
		if(games.size()>0){
			items = games;
			TableItem ti;
			for(TinyGame tg: games){
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
		}
		for(TableColumn tc: tableItems.getColumns())
			tc.pack();
	}
	
	@Override
	protected void save(){
		
		int[] values = tableItems.getSelectionIndices();
		if(values!=null && values.length>0){
			List<BoardGame> tmpList = new ArrayList<BoardGame>();
			for(int i=0; i< values.length; i++){
				for(BoardGame tg: items){
					if(tg.getGameId()==Integer.parseInt(tableItems.getItem(values[i]).getText(0))){
						tmpList.add(tg);
					}
				}
			}
			items = tmpList;
			saved = items;
		}
	}
	
	
	public List<BoardGame> getGames(){
		return saved;
	}

	@Override
	protected void back() {
		items = new ArrayList<BoardGame>();
	}
}
