package it.gioca.torino.manager.gui.toylibrity;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.db.facade.game.AllGameListFacede;
import it.gioca.torino.manager.db.facade.game.BlockTheGameFacade;
import it.gioca.torino.manager.db.facade.game.request.AllGameRequest;
import it.gioca.torino.manager.db.facade.game.request.AllGameRequest.RequestTYPE;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.db.facade.game.request.GameRequest.GAMESTATUS;
import it.gioca.torino.manager.db.facade.toylibrary.FindCategory;
import it.gioca.torino.manager.db.facade.toylibrary.RequestFind;
import it.gioca.torino.manager.db.facade.toylibrary.RetriveAllUsedExitIdFacade;
import it.gioca.torino.manager.db.facade.toylibrary.SearchType;
import it.gioca.torino.manager.gui.YESNODialog;
import it.gioca.torino.manager.gui.YESNODialog.Action;
import it.gioca.torino.manager.gui.util.BoardGame;
import it.gioca.torino.manager.gui.util.ColumnType;
import it.gioca.torino.manager.gui.util.ColumnType.CTYPE;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.LoggerPoints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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

public class ToyLibrary extends MainForm {

	private Table tableGames;
	private Button OUT;
	private Button IN;
	private List<BoardGame> games;
	private CheckOutObjectModel coModel;
	private Text filter;
	private Combo categoryFiler;
	private Combo designersFilter;
	private Combo lenghtsFilter;
	
	private static final String[] LENGHTS = {"BREVE", "MEDIA", "LUNGA"};
	private String[] designers = {""};
	private String[] categories = {""};
	private String[] players = {""};
	private Combo playersFilter;

	public ToyLibrary(String stateName, String title) {
		super(stateName, title);
	}

	
	@Override
	public void createFrom() {
		{
			//drawButton(Messages.getString("ToyLibrary.0"), getMenuLaterale(), EBUTTON.NOW); //Situazione
			drawButton(Messages.getString("ToyLibrary.1"), getMenuLaterale(), EBUTTON.EXIT_GAME); //Uscita
			drawButton(Messages.getString("ToyLibrary.2"), getMenuLaterale(), EBUTTON.ENTER_GAME); //Entrata
			drawButton(Messages.getString("ToyLibrary.3"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
			showSituation();
		}		
	}
	
	private void showSituation(){
	
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createAGroup(centrale, 1, 2, "", false);
		{
			FormUtil.createLabel(group, 2, Messages.getString("ToyLibrary.4"));
			ColumnType[] columns = {new ColumnType(Messages.getString("ToyLibrary.5"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ToyLibrary.15"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ToyLibrary.6"), CTYPE.IMAGE),
									new ColumnType(Messages.getString("ToyLibrary.7"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ToyLibrary.11"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ToyLibrary.13"), CTYPE.INT),
									new ColumnType(Messages.getString("ToyLibrary.18"), CTYPE.TEXT)};
			tableGames = FormUtil.createTable(group, columns);
			
			{
				Group functions = FormUtil.createAGroup(group, 1, 1, "", true);
				FormUtil.createLabel(functions, 1, "Filtro:");
				filter = FormUtil.createText(functions, "");
				filter.addTraverseListener(new TraverseListener() {
					
					@Override
					public void keyTraversed(TraverseEvent arg0) {
						if(arg0.detail == SWT.TRAVERSE_RETURN)
							fillTheTable();
					}
				});
				drawButton(Messages.getString("ToyLibrary.8"), functions, EBUTTON.UPDATE);
				OUT = drawButton(Messages.getString("ToyLibrary.9"), functions, EBUTTON.OUT, true);
				OUT.setSelection(true);
				IN = drawButton(Messages.getString("ToyLibrary.10"), functions, EBUTTON.IN, true);
				OUT.setSelection(false);
				
				FormUtil.createLabel(functions, 1, Messages.getString("ToyLibrary.14"));
				categoryFiler = FormUtil.createCombo(functions, 1, categories);
				categoryFiler.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						String category = categoryFiler.getText();
						tableGames.removeAll();
						TableItem ti;
						List<String> newFilter = new ArrayList<String>();
						for(BoardGame bg: games){
							if(bg.containsCategory(category)){
								ti = new TableItem(tableGames, SWT.NONE);
								ti.setText(0, bg.getName());
								if(bg.getThumbnail()!=null)
									ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
								ti.setText(2, bg.getStatusGame()==0? "IN LUDOTECA":bg.getDimostrator());
								ti.setText(3, bg.getOwnerName());
								ti.setText(4, bg.getId_document()+"");
								newFilter.addAll(bg.getDesigners());
							}
						}
						designersFilter.setItems(getArrayFromList(newFilter));
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				
				FormUtil.createLabel(functions, 1, Messages.getString("ToyLibrary.15"));
				designersFilter = FormUtil.createCombo(functions, 1, designers);
				designersFilter.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						String designer = designersFilter.getText();
						tableGames.removeAll();
						TableItem ti;
						List<String> newFilter = new ArrayList<String>();
						for(BoardGame bg: games){
							if(bg.containsDesigner(designer)){
								ti = new TableItem(tableGames, SWT.NONE);
								ti.setText(0, bg.getName());
								if(bg.getThumbnail()!=null)
									ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
								ti.setText(2, bg.getStatusGame()==0? "IN LUDOTECA":bg.getDimostrator());
								ti.setText(3, bg.getOwnerName());
								ti.setText(4, bg.getId_document()+"");
								newFilter.addAll(bg.getCategories());
							}
						}
						categoryFiler.setItems(getArrayFromList(newFilter));
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				
				FormUtil.createLabel(functions, 1, Messages.getString("ToyLibrary.16"));
				lenghtsFilter = FormUtil.createCombo(functions, 1, LENGHTS);
				lenghtsFilter.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						int idLenght = lenghtsFilter.getSelectionIndex();
						int min=0, max=0;
						switch(idLenght){
						case 0: min =0; 				   max = Config.SHORT_TIME; break;
						case 1: min = Config.SHORT_TIME;   max = Config.MID_TIME; break;
						case 2: min = Config.MID_TIME;     max = 1000000; break;
						}
						tableGames.removeAll();
						TableItem ti;
						for(BoardGame bg: games){
							if(checkTime(min, max, bg)){
								ti = new TableItem(tableGames, SWT.NONE);
								ti.setText(0, bg.getName());
								if(bg.getThumbnail()!=null)
									ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
								ti.setText(2, bg.getStatusGame()==0? "IN LUDOTECA":bg.getDimostrator());
								ti.setText(3, bg.getOwnerName());
								ti.setText(4, bg.getId_document()+"");
							}
						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
				//players
				FormUtil.createLabel(functions, 1, Messages.getString("ToyLibrary.17"));
				playersFilter = FormUtil.createCombo(functions, 1, players);
				playersFilter.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						int value = Integer.parseInt(playersFilter.getText());
						tableGames.removeAll();
						TableItem ti;
						for(BoardGame bg: games){
							if(value >= bg.getMinPlayers() && value <= bg.getMaxPlayers()){
								ti = new TableItem(tableGames, SWT.NONE);
								ti.setText(0, bg.getName());
								if(bg.getThumbnail()!=null)
									ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
								ti.setText(2, bg.getStatusGame()==0? "IN LUDOTECA":bg.getDimostrator());
								ti.setText(3, bg.getOwnerName());
								ti.setText(4, bg.getId_document()+"");
							}
						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
					}
				});
			}
			tableGames.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					manage();
				}

			});
		}
		fillTheTable();
		centrale.layout();
	}
	
	protected void manage() {
		if(tableGames.getSelectionIndex()==-1)
			return;
		TableItem ti = tableGames.getItem(tableGames.getSelectionIndex());
		String gameName = ti.getText(0);
		String ownerName = ti.getText(3);
		for(BoardGame bg: games){
			if(bg.getName().equalsIgnoreCase(gameName) && bg.getOwnerName().equalsIgnoreCase(ownerName)){
				switch(bg.getStatusGame()){
				case 0: tryToReserve(bg); break;
				case 2: freeTheGame(bg); LoggerPoints.getInstace().save(" Nome gioco: "+bg.getName()+" - Id Documento: "+bg.getExitId()); break;
				}
				
			}
		}
	}
	
	private void tryToReserve(BoardGame bg){
		
		GameRequest request = new GameRequest();
		request.setIdGame(bg.getGameId());
		request.setStatus(GAMESTATUS.BLOCK);
		request.setOwnerId(bg.getOwnerID());
		request.setWithExpansion(bg.isWithExpansions());
		RetriveAllUsedExitIdFacade raue = new RetriveAllUsedExitIdFacade(null);
		coModel.setBoardGameSelected(bg);
		coModel.getBoardGameSelected().setWithExpansions(request.isWithExpansion());
		coModel.setIdExit(blockFirstValue(raue.getUsedValues()));
		BlockTheGameFacade btg = new BlockTheGameFacade(request);
		if(btg.isCorrectExit())
			Workflow.getInstace().next(stateName, "next", getMainComposite(), getMenuLaterale());
	}
	
	private void freeTheGame(BoardGame bg) {
		
		GameRequest request; 
		BlockTheGameFacade btg;
		Shell shell = Display.getCurrent().getActiveShell();
		YESNODialog yn = new YESNODialog(shell);
		if(yn.open(Messages.getString("ToyLibrary.12")+" "+bg.getExitId())==Action.YES){
			request = new GameRequest();
			request.setIdGame(bg.getGameId());
			request.setIdExit(0);
			request.setDimonstratorName(bg.getDimostrator());
			request.setDemostratorId(0);
			request.setOwnerId(bg.getOwnerID());
			request.setStatus(GAMESTATUS.FREE);
			request.setWithExpansion(bg.isWithExpansions());
			btg = new BlockTheGameFacade(request);
			if(btg.isCorrectExit())
				fillTheTable();
		}
	}


	private void fillTheTable(){
		
		tableGames.removeAll();
		AllGameRequest request = new AllGameRequest();
		if((OUT.getSelection() && IN.getSelection()) || (!OUT.getSelection() && !IN.getSelection()))
			request.setType(RequestTYPE.ALL);
		if(OUT.getSelection() && !IN.getSelection())
			request.setType(RequestTYPE.OUT);
		if(!OUT.getSelection() && IN.getSelection())
			request.setType(RequestTYPE.IN);
		AllGameListFacede alf = new AllGameListFacede(request);
		games = alf.getGames();
		TableItem ti;
		if(games!=null && games.size()>0){
			for(BoardGame bg: games){
				if(filter.getText()!=""){
					String text = filter.getText();
					if(!bg.getName().toLowerCase().contains(text.toLowerCase()) && !findIntoAlternativeNames(bg, text.toLowerCase()))
						continue;
				}
				ti = new TableItem(tableGames, SWT.NONE);
				ti.setText(0, bg.getName());
				if(bg.getThumbnail()!=null)
					ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
				int status = bg.getStatusGame();
				ti.setText(2, status==0? "IN LUDOTECA":bg.getDimostrator());
				ti.setText(3, bg.getOwnerName());
				ti.setText(4, bg.getId_document()+"");
				ti.setText(5, bg.getLanguage());
				if(status!=0)
					ti.setBackground(ThemeManager.getCustomColor());
			}
		}
		for(TableColumn tc: tableGames.getColumns())
			tc.pack();
		loadFilter();
	}
	
	private boolean findIntoAlternativeNames(BoardGame bg, String key){
		
		for(String name: bg.getAlternativeNames())
			if(name.toLowerCase().contains(key))
				return true;
		return false;
	}
	
	private void loadFilter() {
		
		RequestFind request = new RequestFind();
		request.addType(SearchType.CATEGORY);
		request.addType(SearchType.DESINER);
		request.addType(SearchType.TIME);
		request.addType(SearchType.PLAYERS);
		for(BoardGame bg : games)
			request.addGameId(bg.getGameId());
		FindCategory fg = new FindCategory(request);
		categoryFiler.removeAll();
		categories = fg.getCategories();
		categoryFiler.setItems(categories);
		designersFilter.removeAll();
		designers = fg.getDesigners();
		designersFilter.setItems(designers);
		if(fg.getMinMaxPlayer()[0]!=1000)
			createArrayPlayer(fg.getMinMaxPlayer()[0], fg.getMinMaxPlayer()[1]);
		for(BoardGame bg : games){
			int idGame = bg.getGameId();
			bg.setCategories(fg.getCatById(idGame));
			bg.setDesigners(fg.getDesById(idGame));
			bg.setTimes(fg.getTimeById(idGame));
			bg.setPlayers(fg.getPlayerById(idGame));
		}
	}
	
	private void createArrayPlayer(int min, int max){
		
		int size = max - min +1;
		String[] playersArray = new String[size];
		for(int i=0; i<playersArray.length; i++){
			playersArray[i]=min+"";
			min++;
		}
		playersFilter.setItems(playersArray);
	}

	private boolean checkTime(int min, int max, BoardGame bg){
		
		int minBg = bg.getMinTime();
		int maxBg = bg.getMaxTime();
		return (minBg >= min && maxBg <= max);
	}

	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		return drawButton(text, c, eB, false);
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB, boolean checkButton){
		
		Button dummy;
		if(!checkButton)
			dummy = FormUtil.createDummyButton(c, text);
		else
			dummy = FormUtil.createCheckDummyButton(c, text);
		
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				
				switch(eB){
				case IN:
				case OUT: fillTheTable(); break;
//				case NOW: showSituation(); break;
				case UPDATE: fillTheTable(); break;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
	
	enum EBUTTON{
		
		OUT,
		IN,
//		NOW,
		UPDATE,
		EXIT_GAME,
		ENTER_GAME,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case ENTER_GAME: return "enter_game";
			case EXIT_GAME: return "exit_game";
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
		coModel = (CheckOutObjectModel)Workflow.getInstace().getObj(stateName);
	}

}
