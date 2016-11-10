package it.gioca.torino.manager.gui.manage;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.common.ThemeManager.COLOR;
import it.gioca.torino.manager.db.facade.game.AddABoardGameListFacade;
import it.gioca.torino.manager.db.facade.game.request.BoardGameRequest;
import it.gioca.torino.manager.gui.find.game.FindGameDialog;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.GAMESTATUS;
import it.gioca.torino.manager.gui.util.TinyGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ManageGui extends MainForm {

	private Table tableGames;
	
//	private Color YELLOW = ThemeManager.getColor(COLOR.YELLOW);
//	private Color CYAN = ThemeManager.getColor(COLOR.CYAN);
//
//	private Button remove;

	private Button uploadGames;

	private Button save;

	private Table tableExpansions;

	private Button functionSave;
	
	private List<BoardGame> boardsGame = new ArrayList<BoardGame>();

	private List<BoardGame> updatedBoardsGame = new ArrayList<BoardGame>();
	
	private Combo languages;
	
	private Text demonstrator;

	private Combo user;
	
	private boolean editedForm = false;
	
	private boolean caricaForm = false;
	
//	private boolean preparaForm = false;

	private Button forceRemove;

	public ManageGui(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		{
			drawButton(Messages.getString("ManageGui.0"), getMenuLaterale(), EBUTTON.ADD_LIST); //$NON-NLS-1$
			drawButton(Messages.getString("ManageGui.11"), getMenuLaterale(), EBUTTON.LOAD_LIST); //$NON-NLS-1$ //RICHIAMA
			drawButton(Messages.getString("ManageGui.1"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
			drawButton(Messages.getString("ManageGui.2"), getMenuLaterale(), EBUTTON.UNLOAD); //$NON-NLS-1$
			drawButton(Messages.getString("ManageGui.3"), getMenuLaterale(), EBUTTON.INDIETRO); //$NON-NLS-1$
		}
	}
	
	
	
	private void checkParamitersSaveButton(){
		
		if(editedForm && save !=null && !save.isDisposed()){
			if(demonstrator!=null && !demonstrator.isDisposed())
				save.setEnabled(demonstrator.getText()!=null && !demonstrator.getText().equalsIgnoreCase("") && boardsGame.size()>0);
			else{
				if(demonstrator!=null && !demonstrator.isDisposed())
					save.setEnabled(boardsGame.size()>0);
			}
		}
		if(editedForm && functionSave!=null && !functionSave.isDisposed())
			functionSave.setEnabled(true);
	}
	
	
	
	private void findGame(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		FindGameDialog fgd = new FindGameDialog(shell);
		fgd.open();
		addItemsToTheTable(fgd.getGames(), true);
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
		if(uploadGames!=null && !uploadGames.isDisposed())
			uploadGames.setEnabled(false);
	}
	
	private void addItemsToTheTable(List<BoardGame> games, boolean newElements) {
		
		if(games==null)
			return;
		boolean canRemoveItems = true;
		if(games.size()>0){
			for(BoardGame game: games){
				TableItem ti = new TableItem(tableGames, SWT.NONE);
				ti.setText(new String[]{game.getName(),game.getGameId()+"",game.getLanguage()});
				if(forceRemove!=null && !forceRemove.isDisposed()){
					if(game.getStatusGame()!=0)
						ti.setBackground(ThemeManager.getColor(COLOR.RED));
				}
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
		checkParamitersSaveButton();
		if(forceRemove!=null && !forceRemove.isDisposed()){
			if(canRemoveItems==true){
				forceRemove.setEnabled(false);
			}
			else{
				forceRemove.setEnabled(true);
			}
		}
	}
	
	private void saveForm() {
		
		String name = null;
		if(demonstrator!=null && !demonstrator.isDisposed())
			name = demonstrator.getText();
		else
			name = user.getText();
			
		BoardGameRequest bgr = new BoardGameRequest();
		if(updatedBoardsGame.size()>0)
			boardsGame.addAll(updatedBoardsGame);
		bgr.setBoardgames(boardsGame);
		bgr.setUserName(name.toUpperCase());
		new AddABoardGameListFacade(bgr);
		if(uploadGames!=null && !uploadGames.isDisposed())
			uploadGames.setEnabled(true);
		save.setEnabled(false);
		editedForm = false;
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
					if(bg.isLoaded()){
						bg.setStatus(GAMESTATUS.DELETE);
						updatedBoardsGame.add(bg);
					}
					i.remove();
				}
			}
		}
		tableGames.remove(index);
		tableExpansions.removeAll();
		editedForm = true;
	}
	
	
	private void sendMessage(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		ForceRemove fr = new ForceRemove(shell);
		fr.setUserName(user.getText());
		fr.open();
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
//				case UNLOAD: cleanCenter(); drawAddList(true, true); break;
//				case MANAGE: cleanCenter(); drawAddList(true); caricaForm = true; break;
				case SAVE: {saveForm();
							if(!caricaForm) 
								Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); 
							break;
							}
//				case LOAD_LIST: cleanCenter(); findList(); caricaForm = false; break;
				case ADD_GAME: findGame(); break;
				case FORCE_REMOVE_GAME: sendMessage();break;
				case REMOVE_GAME: removeSelection(); break;
				case FUNCTIONS_SAVE: saveSelections(); break;
//				case MODIFY: tryToLoadTheList();
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	enum EBUTTON{
		
		FUNCTIONS_SAVE,
		MODIFY,
		SAVE,
		FORCE_REMOVE_GAME,
		REMOVE_GAME,
		LOAD_LIST,
		ADD_GAME,
		ADD_LIST,
		MANAGE,
		UNLOAD,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case MANAGE: return "finalize";
			case MODIFY:
			case SAVE:
			case UNLOAD: return "unload";
			case LOAD_LIST: return "loadList";
			case ADD_LIST: return "newList";
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
