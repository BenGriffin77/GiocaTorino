package it.gioca.torino.manager.gui.stats;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.facade.history.HistoryItem;
import it.gioca.torino.manager.db.facade.history.RetriveHistoryFacade;
import it.gioca.torino.manager.gui.util.FormUtil;
import it.gioca.torino.manager.gui.util.PieChart;

import java.util.HashMap;
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

public class StatsGui extends MainForm {

	private static final String[] MAX_PLAY_TIME = {"BREVE", "MEDIO", "LUNGO"};
	
	public StatsGui(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		
		drawButton(Messages.getString("ToyLibrary.3"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		fill();
	}
	
	private void fill(){
		RetriveHistoryFacade rhf = new RetriveHistoryFacade(null);
		List<HistoryItem> items = rhf.getItems();
		prepareDataSet(items);
	}

	private void prepareDataSet(List<HistoryItem> items) {
	
		Group group = FormUtil.createAGroup(getMainComposite(), 0, 2, "", true);
		dataSetForGameId(items, group);
		dataSetForTime(items, group);
		dataSetForDimostrator(items, group);
	}

	private void dataSetForDimostrator(List<HistoryItem> items, Group group) {
		
		HashMap<String, Integer> byUser = new HashMap<String,Integer>();
		for(HistoryItem item: items){
			String user = item.getUserName();
			byUser = manageTheHashMap(byUser, user, item.getTimes());
		}
		showThePie(byUser, group, "Giochi per dimostratore");
	}

	private void dataSetForTime(List<HistoryItem> items, Group group) {
		
		HashMap<String, Integer> byTime = new HashMap<String,Integer>();
		String tmpString = null;
		for(HistoryItem item: items){
			int maxPlayTime = item.getMaxPlayTime();
			if(maxPlayTime>Config.MID_TIME)
				tmpString = MAX_PLAY_TIME[2];
			else if(maxPlayTime<Config.MID_TIME && maxPlayTime>Config.SHORT_TIME)
				tmpString = MAX_PLAY_TIME[1];
			else
				tmpString = MAX_PLAY_TIME[0];
			
			byTime = manageTheHashMap(byTime, tmpString, item.getTimes());
		}
		showThePie(byTime, group, "Giochi per durata");
	}

	private void dataSetForGameId(List<HistoryItem> items, Group group){
		
		HashMap<String,Integer> byGameName = new HashMap<String,Integer>();
		
		for(HistoryItem item: items){
			String game = item.getGameName();
			byGameName = manageTheHashMap(byGameName, game, item.getTimes());
		}
		
		showThePie(byGameName, group, "Giochi per titolo");
	}
	
	private void showThePie(HashMap<String,Integer> hash, Composite parent, String title){
		
		PieChart pie = new PieChart();
		for(String gameName: hash.keySet()){
			pie.addValue(gameName, hash.get(gameName));
		}
		pie.setSubTitle(title);
		pie.plot(parent);
	}
	
	private HashMap<String , Integer> manageTheHashMap(HashMap<String,Integer> hash, String key, int times){
		
		if(hash.containsKey(key)){
			int counter = hash.get(key);
			hash.put(key, counter+times);
		}
		else
			hash.put(key, times);
		
		return hash; 
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy;
		dummy = FormUtil.createDummyButton(c, text);
		
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				
				switch(eB){
				case UPDATE: fill(); break;
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
		INDIETRO;
		
		public String toString() {
			
			switch(this){
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
