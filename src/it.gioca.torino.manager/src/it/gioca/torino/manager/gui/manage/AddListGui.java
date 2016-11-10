package it.gioca.torino.manager.gui.manage;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.common.ThemeManager.COLOR;
import it.gioca.torino.manager.db.facade.game.AddABoardGameListFacade;
import it.gioca.torino.manager.db.facade.game.FindGameExpansionsFacade;
import it.gioca.torino.manager.db.facade.game.request.BoardGameRequest;
import it.gioca.torino.manager.db.facade.game.request.RequestFindGame;
import it.gioca.torino.manager.db.facade.users.UserListFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;
import it.gioca.torino.manager.gui.find.game.FindGameDialog;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.TinyGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
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

public class AddListGui extends MainForm {

	private Text demonstrator;
	
	private Color YELLOW = ThemeManager.getColor(COLOR.YELLOW);
	private Color CYAN = ThemeManager.getColor(COLOR.CYAN);

	private boolean editedForm;

	private Table tableGames;

	private Table tableExpansions;

	private Combo languages;
	
	private List<BoardGame> boardsGame = new ArrayList<BoardGame>();

	private Button save;

	private Button functionSave;

	private Button remove;

	private Combo demonstratorCombo;
	
	private boolean selectedName;

	public AddListGui(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		drawButton(Messages.getString("AddListGui.1"), getMenuLaterale(), EBUTTON.LOAD_LIST); //$NON-NLS-1$ //RICHIAMA
		drawButton(Messages.getString("AddListGui.2"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.3"), getMenuLaterale(), EBUTTON.UNLOAD); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.4"), getMenuLaterale(), EBUTTON.INDIETRO); //$NON-NLS-1$
		drawCentrale();
	}

	private String[] getUsers(){
		
		RequestGetUsers request = new RequestGetUsers();
		request.setAll(true);
		UserListFacade ulf = new UserListFacade(request);
		String[] users = ulf.getUsers();
		return users;
	}
	
	private void drawCentrale() {
		boardsGame = new ArrayList<BoardGame>();
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
				FormUtil.createLabel(group, 1, "");
				demonstratorCombo = FormUtil.createCombo(group, 1, getUsers());
				demonstrator.setData(demonstrator.getText());
				demonstrator.addFocusListener(new FocusListener() {
					
					@Override
					public void focusLost(FocusEvent arg0) {
						demonstrator.setBackground(YELLOW);
						checkParametersSaveButton();
						selectedName = false;
					}
					
					@Override
					public void focusGained(FocusEvent arg0) {
						demonstrator.setBackground(CYAN);
					}
				});
				demonstratorCombo.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						demonstrator.setText("");
						selectedName = true;
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				FormUtil.createLabel(group, 1, "");
				Button b = drawButton(Messages.getString("AddListGui.16"),group, EBUTTON.ADDLIST);
				b.setEnabled(false);
			}
			ColumnType[] columns = {new ColumnType(Messages.getString("AddListGui.6"), CTYPE.TEXT),
					new ColumnType(Messages.getString("AddListGui.7"), CTYPE.TEXT)};
			tableGames = FormUtil.createTable(group, columns);
			tableGames.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					remove.setEnabled(true);
					languages.setEnabled(true);
					checkParametersSaveButton();
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
						checkParametersSaveButton();
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
				languages.setEnabled(false);
				languages.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						functionSave.setEnabled(true);
						setLag(languages.getText());
						checkParametersSaveButton();
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
	
	protected void setLag(String text) {
		
		int value = tableGames.getSelectionIndex();
		if(value==-1)
			return;
		int gameId = Integer.parseInt(tableGames.getItem(value).getText(1));
		for(BoardGame bg: boardsGame){
			if(gameId == bg.getGameId()){
				bg.setLanguage(text);
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
		editedForm = true;
		for(TableColumn tc: tableGames.getColumns())
			tc.pack();
		checkParametersSaveButton();
	}
	
	private void findGame(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		FindGameDialog fgd = new FindGameDialog(shell);
		fgd.open();
		addItemsToTheTable(fgd.getGames(), true);
		checkParametersSaveButton();
	}
	
	private void removeSelection(){
		
		int[] index = tableGames.getSelectionIndices();
		Iterator<BoardGame> i = boardsGame.iterator();
		for(int ind: index){
			TableItem ti = tableGames.getItem(ind);
			int tmpGameId = Integer.parseInt(ti.getText(1));
			while (i.hasNext()) {
				BoardGame bg = i.next();
				if(bg.getGameId()==tmpGameId){
					i.remove();
				}
			}
		}
		tableGames.remove(index);
		tableExpansions.removeAll();
		editedForm = true;
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case REMOVE_GAME: removeSelection(); break;
				case ADD_GAME: findGame(); break;
				case FUNCTIONSAVE: saveSelections(); break;
				case SAVE: {saveForm();
							Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); 
							break;
				}
				case ADDLIST: addFromList(); break;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	protected void addFromList() {
		ScriptEngine jruby = new ScriptEngineManager().getEngineByName("jruby");
		// Assign the Java objects that you want to share
		try {           
            jruby.eval(new BufferedReader(new FileReader("Scripts/gt.rb")));
            jruby.put("-f", "gt2015.csv");
            System.out.println("result: " +jruby.get("res"));

       } catch (FileNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (ScriptException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
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
		checkParametersSaveButton();
	}
	
	private void saveForm() {
		
		String name = null;
		name = demonstrator.getText();
		if(name==null || name.equalsIgnoreCase(""))
			name = demonstratorCombo.getText();
		BoardGameRequest bgr = new BoardGameRequest();
		bgr.setBoardgames(boardsGame);
		bgr.setUserName(name.toUpperCase());
		new AddABoardGameListFacade(bgr);
		save.setEnabled(false);
		editedForm = false;
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
	
	private void checkParametersSaveButton(){
		
		if(editedForm){
//			if(demonstrator!=null && !demonstrator.isDisposed())
			boolean tmp = ((demonstrator.getText()!=null && !demonstrator.getText().equalsIgnoreCase("") && boardsGame.size()>0) ||
					selectedName && boardsGame.size()>0);
				save.setEnabled(tmp);
		}
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
		ADDLIST,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case UNLOAD: return "unload";
			case MANAGE: return "finalize";
			case LOAD_LIST: return "loadList";
			case SAVE:
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}
	
	@Override
	public void afterCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}

}
