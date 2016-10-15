package it.gioca.torino.manager.gui.toylibrity;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.facade.game.BlockTheGameFacade;
import it.gioca.torino.manager.db.facade.game.FindAvabileGameFacade;
import it.gioca.torino.manager.db.facade.game.request.FindAvaibleGameRequest;
import it.gioca.torino.manager.db.facade.game.request.FindAvaibleGameRequest.TYPE;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.db.facade.game.request.GameRequest.GAMESTATUS;
import it.gioca.torino.manager.db.facade.toylibrary.RetriveAllUsedExitIdFacade;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class CheckOut extends MainForm {

	private Button NEXT;
	private Text iDBgg;
	private Text titolo;
	private Text autore;
	private Table tableItems;
	private Combo languages;

	private TYPE requestType;
	private CheckOutObjectModel coModel;
	
	public CheckOut(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		{
			NEXT = drawButton(Messages.getString("CheckOut.0"), getMenuLaterale(), EBUTTON.NEXT); //avanti
			NEXT.setEnabled(false);
			drawButton(Messages.getString("CheckOut.1"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		}
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createGenGroup(centrale, 1, 2, "", true);
		{
//			Group group = FormUtil.createGenGroup(centrale, 1, 2, "", true);
			FormUtil.createLabel(group, 1, Messages.getString("CheckOut.2"));
			iDBgg = FormUtil.createText(group, "");
			iDBgg.addListener(SWT.Traverse, new Listener() {
				
				public void handleEvent(Event arg0) {
					if(arg0.detail == SWT.TRAVERSE_RETURN){
						requestType = TYPE.ID;
						NEXT.setEnabled(false);
						find();
					}
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
			
			FormUtil.createLabel(group, 1, Messages.getString("CheckOut.3"));
			titolo = FormUtil.createText(group, "");
			titolo.addListener(SWT.Traverse, new Listener() {
				
				public void handleEvent(Event arg0) {
					if(arg0.detail == SWT.TRAVERSE_RETURN){
						requestType = TYPE.NAME;
						NEXT.setEnabled(false);
						find();
					}
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
			
			FormUtil.createLabel(group, 1, Messages.getString("CheckOut.4"));
			autore = FormUtil.createText(group, "");
			autore.addListener(SWT.Traverse, new Listener() {
				
				public void handleEvent(Event arg0) {
					if(arg0.detail == SWT.TRAVERSE_RETURN){
						requestType = TYPE.DESIGNER;
						NEXT.setEnabled(false);
						find();
					}
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
			
			FormUtil.createLabel(group, 1, Messages.getString("CheckOut.5"));
			languages = FormUtil.createCombo(group, 1);
			languages.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					titolo.setText("");
					iDBgg.setText("");
					autore.setText("");
					requestType = TYPE.LANGUAGE;
					NEXT.setEnabled(false);
					find();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			
			FormUtil.createDummyLabel(group, 1);
			drawButton(Messages.getString("CheckOut.6"), group, EBUTTON.FIND);
		}
		{
			Group group2 = FormUtil.createAGroup(centrale, 1, 2, "", true);
			ColumnType[] columsName = {new ColumnType("ID", CTYPE.INT), 
									   new ColumnType("Immagine", CTYPE.IMAGE),
									   new ColumnType("Nome", CTYPE.TEXT),
									   new ColumnType("Lingua", CTYPE.TEXT),
									   new ColumnType("Espansione", CTYPE.TEXT),
									   new ColumnType("Proprietario", CTYPE.TEXT),
									   new ColumnType("P ID", CTYPE.TEXT, -1)
									   };
			tableItems = FormUtil.createTable(group2, 2, columsName);
//			GridData data = new GridData(GridData.FILL_HORIZONTAL);
//			data.horizontalSpan = 2;
////			data.heightHint = tableItems.getShell().getBounds().height;
////			data.heightHint = getMenuLaterale().getBounds().height - group.getBounds().height;
//			tableItems.setLayoutData(data);
			tableItems.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					NEXT.setEnabled(true);
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					if(tableItems.getSelectionIndices().length>0){
						if(tryToBlock())
							Workflow.getInstace().next(stateName, "next", getMainComposite(), getMenuLaterale());
					}
				}
			});
		}
	}
	
	private boolean tryToBlock(){
		
		int sel = tableItems.getSelectionIndex();
		if(sel==-1)
			return false;
		TableItem ti = tableItems.getItem(sel);
		GameRequest request = new GameRequest();
		request.setIdGame(Integer.parseInt(ti.getText(0)));
		request.setStatus(GAMESTATUS.BLOCK);
		request.setOwnerId(Integer.parseInt(ti.getText(6)));
		request.setWithExpansion(ti.getText(4).equalsIgnoreCase("SI"));
		RetriveAllUsedExitIdFacade raue = new RetriveAllUsedExitIdFacade(null);
		coModel.setBoardGameSelected(request.getIdGame());
		coModel.getBoardGameSelected().setWithExpansions(request.isWithExpansion());
		coModel.setIdExit(blockFirstValue(raue.getUsedValues()));
		BlockTheGameFacade btg = new BlockTheGameFacade(request);
		return btg.isCorrectExit();
	}
	
	protected void find(){
		
		tableItems.removeAll();
		FindAvaibleGameRequest request = new FindAvaibleGameRequest();
		String tmp = iDBgg.getText();
		if(!tmp.equalsIgnoreCase(""))
			request.setGameId(Integer.parseInt(iDBgg.getText()));
		request.setGameName(titolo.getText());
		request.setDesigner(autore.getText());
		request.setLanguage(languages.getText());
		request.setRequestType(requestType);
		
		FindAvabileGameFacade fgf = new FindAvabileGameFacade(request);
		List<BoardGame> games = fgf.getGames();
		if(games.size()>0){
			coModel.setList(games);
			TableItem ti;
			for(BoardGame tg: games){
				ti = new TableItem(tableItems, SWT.NONE);
				ti.setText(0, tg.getGameId()+"");
				if(tg.getThumbnail()!=null)
					ti.setImage(1, FormUtil.setImageInTheTable(tg.getThumbnail()));
//					ti.setImage(1, new Image(Display.getCurrent(), new ByteArrayInputStream(tg.getThumbnail())));
				ti.setText(2, tg.getName());
				ti.setText(3, tg.getLanguage());
				ti.setText(4, tg.isWithExpansions()?"SI":"NO");
				ti.setText(5, tg.getOwnerName()+"");
				ti.setText(6, tg.getOwnerID()+"");
			}
		}
		for(TableColumn tc: tableItems.getColumns()){
			if(!tc.getText().equalsIgnoreCase("P ID"))
				tc.pack();
		}
	}
	
	private void setTheFind(){
		
		String tmp = iDBgg.getText();
		if(tmp!=null && !tmp.equalsIgnoreCase("")){
			requestType = TYPE.ID;
			return;
		}
		tmp = titolo.getText();
		if(tmp!=null && !tmp.equalsIgnoreCase("")){
			requestType = TYPE.NAME;
			return;
		}
		tmp = autore.getText();
		if(tmp!=null && !tmp.equalsIgnoreCase("")){
			requestType = TYPE.DESIGNER;
			return;
		}
		requestType = TYPE.FULL;
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case FIND: NEXT.setEnabled(false); setTheFind(); find(); break;
				case NEXT: if(!tryToBlock()) return;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	private enum EBUTTON{
		
		FIND,
		NEXT,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case NEXT: return "next";
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}

	@Override
	public void afterCreate() {

	}

	@Override
	public void setDataModel() {

		coModel = (CheckOutObjectModel)Workflow.getInstace().getObj(stateName);
	}

}
