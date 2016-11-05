package it.gioca.torino.manager.gui.toylibrity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import org.eclipse.swt.widgets.Text;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.common.ThemeManager.COLOR;
import it.gioca.torino.manager.db.facade.game.BlockTheGameFacade;
import it.gioca.torino.manager.db.facade.game.request.GameRequest;
import it.gioca.torino.manager.db.facade.game.request.GameRequest.GAMESTATUS;
import it.gioca.torino.manager.db.facade.toylibrary.RetriveAllUsedExitIdFacade;
import it.gioca.torino.manager.db.facade.toylibrary.UpdateIdExit;
import it.gioca.torino.manager.db.facade.users.AddUserFacade;
import it.gioca.torino.manager.db.facade.users.FindIDUserFacade;
import it.gioca.torino.manager.db.facade.users.UserListFacade;
import it.gioca.torino.manager.db.facade.users.request.RequestGetUsers;
import it.gioca.torino.manager.db.facade.users.request.RequestUser;
import it.gioca.torino.manager.gui.util.FormUtil;

public class FinishTheCheckOut extends MainForm {

	private FinishTheCheckOutObjectModel ftcoModel;
	private Combo demostrators;
	private Text newDemostrator;
	private Combo exitId;

	public FinishTheCheckOut(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		
		CheckOutObjectModel coModel = (CheckOutObjectModel)Workflow.getInstace().getObj(getPreviusForm());
		ftcoModel.setBoardGameSelected(coModel.getBoardGameSelected());
		ftcoModel.setList(coModel.getList());
		{
			drawButton(Messages.getString("FinishTheCheckOut.0"), getMenuLaterale(), EBUTTON.END); //avanti
			drawButton(Messages.getString("FinishTheCheckOut.1"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		}
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		gdLayout.numColumns = 2;
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		{
			Group group = FormUtil.createGenGroup(centrale, 1, 2, Messages.getString("FinishTheCheckOut.2"), true);
			FormUtil.createLabel(group, 1, Messages.getString("FinishTheCheckOut.3"));
			FormUtil.createLabel(group, 1, ftcoModel.getBoardGameSelected().getName());
			
			FormUtil.createLabel(group, 1, Messages.getString("FinishTheCheckOut.4"));
			FormUtil.createLabelWithImage(group, 1, ftcoModel.getBoardGameSelected().getThumbnail());
			
			FormUtil.createLabel(group, 1, Messages.getString("FinishTheCheckOut.5"));
			FormUtil.createLabel(group, 1, ftcoModel.getBoardGameSelected().getLanguage());
			
			FormUtil.createLabel(group, 1, Messages.getString("FinishTheCheckOut.6"));
			FormUtil.createLabel(group, 1, ftcoModel.getBoardGameSelected().getOwnerName());
			
			FormUtil.createLabel(group, 1, Messages.getString("FinishTheCheckOut.9"));
			RetriveAllUsedExitIdFacade raei = new RetriveAllUsedExitIdFacade(null);
			exitId = FormUtil.createCombo(group, 1, generateTheValues(raei.getUsedValues(), coModel.getIdExit()));
		}
		{
			Group group = FormUtil.createGenGroup(centrale, 1, 1, Messages.getString("FinishTheCheckOut.7"), true);
			RequestGetUsers request = new RequestGetUsers();
			request.setAll(true);
			UserListFacade ulf = new UserListFacade(request);
			String[] users = ulf.getUsers();
			demostrators = FormUtil.createCombo(group, 1, users);
			for(int i=0; i<users.length; i++){
				if(users[i].equalsIgnoreCase(ftcoModel.getBoardGameSelected().getOwnerName())){
					demostrators.select(i);
					break;
				}
			}
			drawButton(Messages.getString("FinishTheCheckOut.8"), group, EBUTTON.ADD_DEMOSTRATOR);
			newDemostrator = FormUtil.createText(group, "Write HERE");
			newDemostrator.setVisible(false);
			newDemostrator.setBackground(ThemeManager.getColor(COLOR.RED));
			newDemostrator.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					newDemostrator.setText("");
					newDemostrator.setBackground(ThemeManager.getColor(COLOR.YELLOW));
				}
			});
			newDemostrator.addListener(SWT.Traverse, new Listener() {
				
				public void handleEvent(Event arg0) {
					if(arg0.detail == SWT.TRAVERSE_RETURN)
						addDemostrator();
				}
			});
		}
	}
	
	private String[] generateTheValues(List<Integer> numbersToRemove, int idExit){
		
		List<String> numbers = new ArrayList<String>();
		int i=0;
		while(i<Config.MAXINT){
			i++;
			numbers.add(i+"");
		}
		Iterator<String> iter = numbers.iterator();
		while(iter.hasNext()){
			int t = Integer.parseInt(iter.next());
			for(int number: numbersToRemove){
				if(t==number)
					iter.remove();
			}
		}
		GameRequest request = new GameRequest();
		request.setIdExit(idExit);
		request.setIdGame(ftcoModel.getBoardGameSelected().getGameId());
		request.setOwnerId(ftcoModel.getBoardGameSelected().getOwnerID());
		new UpdateIdExit(request);
		
		String[] tmp = new String[numbers.size()];
		return numbers.toArray(tmp);
	}
	
	private void addDemostrator(){
		
		if(newDemostrator==null || newDemostrator.getText().equalsIgnoreCase(""))
			return;
		RequestUser rUser = new RequestUser();
		rUser.setUserName(newDemostrator.getText().toUpperCase());
		new AddUserFacade(rUser);
		
		RequestGetUsers request = new RequestGetUsers();
		request.setAll(true);
		UserListFacade ulf = new UserListFacade(request);
		String[] users = ulf.getUsers();
		demostrators.removeAll();
		demostrators.setItems(users);
		demostrators.select(0);
		newDemostrator.setText("Write HERE");
		newDemostrator.setBackground(ThemeManager.getColor(COLOR.RED));
		newDemostrator.setVisible(false);
	}
	
	private boolean rollBack(){
		
		GameRequest request = new GameRequest();
		request.setIdGame(ftcoModel.getBoardGameSelected().getGameId());
		request.setStatus(GAMESTATUS.FREE);
		request.setOwnerId(ftcoModel.getBoardGameSelected().getOwnerID());
		request.setWithExpansion(ftcoModel.getBoardGameSelected().isWithExpansions());
		BlockTheGameFacade btg = new BlockTheGameFacade(request);
		return btg.isCorrectExit();
	}
	
	private boolean end(){
		
		GameRequest request = new GameRequest();
		request.setIdGame(ftcoModel.getBoardGameSelected().getGameId());
		request.setStatus(GAMESTATUS.OUT);
		request.setOwnerId(ftcoModel.getBoardGameSelected().getOwnerID());
		request.setWithExpansion(ftcoModel.getBoardGameSelected().isWithExpansions());
		String demoName = demostrators.getText();
		RequestUser requestU = new RequestUser();
		requestU.setUserName(demoName);
		FindIDUserFacade fiu = new FindIDUserFacade(requestU);
		request.setDemostratorId(fiu.getId());
		int idExit = Integer.parseInt(exitId.getText());
		request.setIdExit(idExit);
		BlockTheGameFacade btg = new BlockTheGameFacade(request);
		return btg.isCorrectExit();
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case END: end(); Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				case ADD_DEMOSTRATOR: newDemostrator.setVisible(true); break;
				case INDIETRO: if(!rollBack()) return;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}

	private enum EBUTTON{
		
		ADD_DEMOSTRATOR,
		END,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case END: return "end";
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
		
		ftcoModel = (FinishTheCheckOutObjectModel)Workflow.getInstace().getObj(stateName);
	}

}
