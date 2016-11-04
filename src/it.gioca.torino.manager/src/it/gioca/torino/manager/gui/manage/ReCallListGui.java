package it.gioca.torino.manager.gui.manage;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.facade.game.AddABoardGameListFacade;
import it.gioca.torino.manager.db.facade.game.FindBoardGameListFacade;
import it.gioca.torino.manager.db.facade.game.FindGameExpansionsFacade;
import it.gioca.torino.manager.db.facade.game.request.BoardGameRequest;
import it.gioca.torino.manager.db.facade.game.request.RequestFindGame;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.find.game.FindGameDialog;
import it.gioca.torino.manager.gui.find.game.FindUserList;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.GAMESTATUS;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.TinyGame;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.eclipse.swt.widgets.Text;

public class ReCallListGui extends MainForm {

	private Text demonstrator;
	private Table tableExpansions;
	private Button functionSave;
	private Combo languages;
	private Table tableGames;
	private Button remove;
	private Button save;
	private boolean editedForm;
	
	private List<BoardGame> boardsGame = new ArrayList<BoardGame>();
	private List<BoardGame> updatedBoardsGame = new ArrayList<BoardGame>();

	public ReCallListGui(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		drawButton(Messages.getString("AddListGui.0"), getMenuLaterale(), EBUTTON.ADD_LIST); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.1"), getMenuLaterale(), EBUTTON.LOAD_LIST); //$NON-NLS-1$ //RICHIAMA
		drawButton(Messages.getString("AddListGui.2"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.3"), getMenuLaterale(), EBUTTON.UNLOAD); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.4"), getMenuLaterale(), EBUTTON.INDIETRO); //$NON-NLS-1$
		init();
	}
	
	private void init(){
		drawCentrale();
		getMainComposite().layout();
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		FindUserList ful = new FindUserList(shell);
		ful.open();
		String userName = ful.getName();
		if(userName !=null){
			demonstrator.setText(userName);
			demonstrator.setEnabled(false);
			RequestUser request = new RequestUser();
			request.setUserName(userName);
			FindBoardGameListFacade fbgl = new FindBoardGameListFacade(request);
			addItemsToTheTable(fbgl.getGames(), false);
		}
	}

	private void selectElement(List<TinyGame> expansions){
		
		for(int i=0; i<tableExpansions.getItems().length; i++){
			TableItem ti = tableExpansions.getItem(i);
			int gameId = Integer.parseInt(ti.getText(0));
			for(TinyGame tg: expansions){
				if(gameId == tg.getGameId()){
					tableExpansions.select(i);
					break;
				}
			}
		}
	}
	
	private void updateTables(){
		
		int value = tableGames.getSelectionIndex();
		if(value==-1)
			return;
		if(tableExpansions!=null && !tableExpansions.isDisposed())
			tableExpansions.removeAll();
		
		if(tableExpansions!=null && !tableExpansions.isDisposed()){
			RequestFindGame request = new RequestFindGame();
			int gameId = Integer.parseInt(tableGames.getItem(value).getText(1));
			request.setGameId(gameId);
			
			FindGameExpansionsFacade fgef = new FindGameExpansionsFacade(request);
			List<TinyGame> expansions = fgef.getExpansions();
			if(expansions.size()>0){
				for(TinyGame tg: expansions){
					TableItem ti = new TableItem(tableExpansions, SWT.NONE);
					ti.setText(0, tg.getGameId()+"");
					ti.setText(1,tg.getName());
				}
			}
			for(TableColumn tc: tableExpansions.getColumns())
				tc.pack();
			
			for(BoardGame bg: boardsGame){
				if(gameId == bg.getGameId()){
					if(bg.getExpansions()!= null && bg.getExpansions().size()>0)
						selectElement(bg.getExpansions());
					if(bg.getLanguage()!=null){
						String lang = bg.getLanguage();
						languages.select(FormUtil.getLangId(lang));
					}
					return;
				}
			}
		}
	}

	private void drawCentrale() {
		
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createAGroup(centrale, 1, 2, "", true);
		{
			FormUtil.createLabel(group, 1, Messages.getString("AddListGui.5"));
			{
				demonstrator = FormUtil.createText(group, 1, "");
				demonstrator.setData(demonstrator.getText());
			}
			ColumnType[] columns = {new ColumnType(Messages.getString("AddListGui.6"), CTYPE.TEXT),
					new ColumnType(Messages.getString("AddListGui.7"), CTYPE.TEXT)};
			tableGames = FormUtil.createTable(group, columns);
			tableGames.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					remove.setEnabled(true);
					checkParamitersSaveButton();
					updateTables();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			{
				Group functions = FormUtil.createAGroup(group, 1, 1, Messages.getString("AddListGui.8"), true);
				drawButton(Messages.getString("AddListGui.9"),functions, EBUTTON.ADD_GAME);
				remove = drawButton(Messages.getString("AddListGui.10"),functions, EBUTTON.REMOVE_GAME);
				remove.setEnabled(false);
				save = drawButton(Messages.getString("AddListGui.11"), functions, EBUTTON.SAVE);
				save.setEnabled(false);
			}
			
			Group expansions = FormUtil.createAGroup(group, 2, 2, Messages.getString("AddListGui.12"), true);
			ColumnType[] columnsExpansions = {new ColumnType(Messages.getString("AddListGui.7"), CTYPE.INT),
					new ColumnType(Messages.getString("AddListGui.13"), CTYPE.TEXT)};
			{
				tableExpansions = FormUtil.createTable(expansions, columnsExpansions);
				tableExpansions.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						functionSave.setEnabled(true);
						checkParamitersSaveButton();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				
				Group moreFunctions = FormUtil.createAGroup(expansions, 1, 1, Messages.getString("AddListGui.14"), true);
				functionSave = drawButton(Messages.getString("AddListGui.15"), moreFunctions, EBUTTON.FUNCTIONSAVE);
				functionSave.setEnabled(false);
				languages = FormUtil.createCombo(moreFunctions, 1);
				languages.setData("LANGUAGES");
				languages.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						functionSave.setEnabled(true);
						checkParamitersSaveButton();
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				FormUtil.createLabel(moreFunctions, 1, "");
				FormUtil.createLabel(moreFunctions, 1, "");
			}
		}
	}
	
	private void addItemsToTheTable(List<BoardGame> games, boolean newElements) {
		
		if(games==null)
			return;
		boolean canRemoveItems = true;
		if(games.size()>0){
			for(BoardGame game: games){
				TableItem ti = new TableItem(tableGames, SWT.NONE);
				ti.setText(new String[]{game.getName(),game.getGameId()+"",game.getLanguage()});
				//game.setLanguage("ITALIANO");
				if(newElements)
					boardsGame.add(new BoardGame(game));
				else
					boardsGame = games;
				if(canRemoveItems){
					canRemoveItems = game.getStatusGame()==0;
				}
			}
		}
		for(TableColumn tc: tableGames.getColumns())
			tc.pack();
		checkParamitersSaveButton();
	}
	
	private void checkParamitersSaveButton(){
		
		save.setEnabled(editedForm);
	}
	
	private void findGame(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		FindGameDialog fgd = new FindGameDialog(shell);
		fgd.open();
		addItemsToTheTable(fgd.getGames(), true);
		editedForm = true;
		checkParamitersSaveButton();
	}
	
	private void removeSelection(){
		
		int[] index = tableGames.getSelectionIndices();
		for(int ind: index){
			Iterator<BoardGame> i = boardsGame.iterator();
			TableItem ti = tableGames.getItem(ind);
			int tmpGameId = Integer.parseInt(ti.getText(1));
			while (i.hasNext()) {
				BoardGame bg = i.next();
				if(bg.getGameId()==tmpGameId){
					if(bg.isLoaded()){
						bg.setStatus(GAMESTATUS.DELETE);
						updatedBoardsGame.add(bg);
						i.remove();
					}
				}
			}
		}
		tableGames.remove(index);
		tableExpansions.removeAll();
		editedForm = true;
		checkParamitersSaveButton();
	}
	
	private void saveSelections(){
		
		int selection = tableGames.getSelectionIndex();
		if(selection==-1)
			return;
		TableItem ti = tableGames.getItem(selection);
		int gameId = Integer.parseInt(ti.getText(1));
		BoardGame game = null;
		for(BoardGame bg: boardsGame){
			if(bg.getGameId()==gameId){
				game = bg;
				break;
			}
		}
		game.resetExpansion();
		for(int i: tableExpansions.getSelectionIndices()){
			ti = tableExpansions.getItem(i);
			int id = Integer.parseInt(ti.getText(0));
			String name = ti.getText(1);
			game.addExpansion(new TinyGame(id, name, null));
		}
		String text = languages.getText();
		game.setLanguage(text);
		editedForm=true;
		checkParamitersSaveButton();
	}
	
	private void saveForm() {
		
		String name = null;
		name = demonstrator.getText();
		BoardGameRequest bgr = new BoardGameRequest();
		if(updatedBoardsGame.size()>0)
			boardsGame.addAll(updatedBoardsGame);
		bgr.setBoardgames(boardsGame);
		bgr.setUserName(name.toUpperCase());
		new AddABoardGameListFacade(bgr);
		save.setEnabled(false);
		editedForm = false;
	}

	@Override
	public void afterCreate() {
		
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case LOAD_LIST: cleanCenter(); init(); break;
				case REMOVE_GAME: removeSelection(); break;
				case ADD_GAME: findGame(); break;
				case FUNCTIONSAVE: saveSelections(); break;
				case SAVE: {saveForm();
							Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); 
							break;
				}
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	private enum EBUTTON{
		
		REMOVE_GAME,
		ADD_GAME,
		FUNCTIONSAVE,
		SAVE,
		ADD_LIST,
		LOAD_LIST,
		MANAGE,
		UNLOAD,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case MANAGE: return "finalize";
			case ADD_LIST: return "newList";
			case SAVE:
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}

}
