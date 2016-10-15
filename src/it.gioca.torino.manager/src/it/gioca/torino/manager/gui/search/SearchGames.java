package it.gioca.torino.manager.gui.search;

import java.util.List;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

public class SearchGames extends MainForm {

	private List<FullBoardGame> games;
	
	public SearchGames(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		drawButton(Messages.getString("SearchGames.0"), getMenuLaterale(), EBUTTON.INDIETRO); //Indietro
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		{
			Group group = FormUtil.createGenGroup(getMainComposite(), 1, 2, Messages.getString("SearchGames.1"), true);
		}
	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy;
		dummy = FormUtil.createDummyButton(c, text);
		
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				
				switch(eB){
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}

	@Override
	public void afterCreate() {

	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}

	enum EBUTTON{
		
		SEARCH,
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}
}
