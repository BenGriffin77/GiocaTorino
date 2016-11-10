package it.gioca.torino.manager.gui.toylibrity;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.facade.game.AllGameListFacede;
import it.gioca.torino.manager.db.facade.game.BlockTheGameFacade;
import it.gioca.torino.manager.db.facade.game.request.AllGameRequest;
import it.gioca.torino.manager.db.facade.game.request.AllGameRequest.RequestTYPE;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.db.facade.game.request.GameRequest.GAMESTATUS;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FreeTheGame extends MainForm {

	private Button conferma;
	private Table tableGames;
	
	private List<BoardGame> games;

	public FreeTheGame(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		{
			conferma = drawButton(Messages.getString("FreeTheGame.0"), getMenuLaterale(), EBUTTON.CONFERMA); //Conferma
			conferma.setEnabled(false);
			drawButton(Messages.getString("FreeTheGame.1"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		}
		showSituation();
	}

	private void showSituation(){
		
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Group group = FormUtil.createGenGroup(centrale, 1, 2, "", true);
		{
			FormUtil.createLabel(group, 2, Messages.getString("ToyLibrary.4"));
			ColumnType[] columns = {new ColumnType(Messages.getString("ToyLibrary.5"), CTYPE.TEXT),
									new ColumnType(Messages.getString("ToyLibrary.6"), CTYPE.IMAGE),
									new ColumnType(Messages.getString("ToyLibrary.7"), CTYPE.TEXT),
									new ColumnType(Messages.getString("FreeTheGame.5"), CTYPE.TEXT),
									new ColumnType(Messages.getString("FreeTheGame.6"), CTYPE.TEXT)};
			tableGames = FormUtil.createSingleTable(group, columns);
			tableGames.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					conferma.setEnabled(true);
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
				}
			});
			{
				Group functions = FormUtil.createAGroup(group, 1, 1, "", true);
				drawButton(Messages.getString("ToyLibrary.8"), functions, EBUTTON.UPDATE);
			}
		}
		fillTheTable();
		centrale.layout();
		}
		
		private void fillTheTable(){
			
			tableGames.removeAll();
			
			
			AllGameRequest request = new AllGameRequest();
			request.setType(RequestTYPE.OUT);
			AllGameListFacede alf = new AllGameListFacede(request);
			games = alf.getGames();
			TableItem ti;
			if(games!=null && games.size()>0){
				for(BoardGame bg: games){
					ti = new TableItem(tableGames, SWT.NONE);
					ti.setText(0, bg.getName());
					if(bg.getThumbnail()!=null)
						ti.setImage(1, FormUtil.setImageInTheTable(bg.getThumbnail()));
					ti.setText(2, bg.getDimostrator());
					ti.setText(3, bg.isWithExpansions()?"SI":"NO");
					ti.setText(4, bg.getId_document()+"");
				}
			}
			for(TableColumn tc: tableGames.getColumns())
				tc.pack();
	}
		
	private void enterTheGame(){
		
		List<BoardGame> toRemove = new ArrayList<BoardGame>();
		TableItem[] items = tableGames.getSelection();
		for(TableItem ti: items){
			String bgName = ti.getText(0);
			int documentId = Integer.parseInt(ti.getText(4));
			for(BoardGame bg: games){
				if(bgName.equalsIgnoreCase(bg.getName()) && bg.getId_document()==documentId){
					bg.setWithExpansions(ti.getText(3).equalsIgnoreCase("SI"));
					bg.setId_document(documentId);
					toRemove.add(bg);
				}
			}
		}
		
		GameRequest request; 
		BlockTheGameFacade btg;
		if(toRemove.size()>0){
			for(BoardGame bg: toRemove){
				request = new GameRequest();
				request.setIdGame(bg.getGameId());
				request.setIdExit(0);
				request.setDimonstratorName(bg.getDimostrator());
//				request.setDemostratorId(bg.getDimostrator());
				request.setOwnerId(bg.getOwnerID());
				request.setStatus(GAMESTATUS.FREE);
				request.setWithExpansion(bg.isWithExpansions());
				btg = new BlockTheGameFacade(request);
				if(!btg.isCorrectExit())
					return;
			}
		}
	}
		
	private Button drawButton(String text, Composite c, final EBUTTON eB){
			
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case CONFERMA: {
						enterTheGame(); 
						fillTheTable(); 
					break;
				}
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
		
		UPDATE,
		CONFERMA,
		INDIETRO;
		
		public String toString() {
				
			switch(this){
			case INDIETRO: return "indietro";
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
