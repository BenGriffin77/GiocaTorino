package it.gioca.torino.manager.gui.manage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.gui.manage.ManageGui.EBUTTON;
import it.gioca.torino.manager.gui.util.FormUtil;

public class UnloadGames extends MainForm {
//
	public UnloadGames(String stateName, String title) {
		super(stateName, title);
	}
//
	@Override
	public void createFrom() {
		drawButton(Messages.getString("AddListGui.0"), getMenuLaterale(), EBUTTON.ADD_LIST); //$NON-NLS-1$ //PREPARA LA LIST
		drawButton(Messages.getString("AddListGui.1"), getMenuLaterale(), EBUTTON.LOAD_LIST); //$NON-NLS-1$ //RICHIAMA
		drawButton(Messages.getString("AddListGui.2"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
//		drawButton(Messages.getString("AddListGui.3"), getMenuLaterale(), EBUTTON.UNLOAD); //$NON-NLS-1$
		drawButton(Messages.getString("AddListGui.4"), getMenuLaterale(), EBUTTON.INDIETRO); //$NON-NLS-1$
		drawCentrale();

	}

	private void drawCentrale() {
		Composite centrale =  getMainComposite();
		GridData gdData = new GridData(GridData.FILL_BOTH);
		GridLayout gdLayout = new GridLayout();
		centrale.setLayout(gdLayout);
		centrale.setLayoutData(gdData);
		centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
	}

	@Override
	public void afterCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDataModel() {
		// TODO Auto-generated method stub

	}
	
	private Button drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
//				case SAVE: {saveForm();
//							if(!caricaForm) 
//								Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale()); 
//							break;
//							}
//				case ADD_GAME: findGame(); break;
//				case FORCE_REMOVE_GAME: sendMessage();break;
//				case REMOVE_GAME: removeSelection(); break;
//				case FUNCTIONS_SAVE: saveSelections(); break;
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
		INDIETRO;
		
		public String toString() {
			
			switch(this){
			case MANAGE: return "finalize";
			case LOAD_LIST: return "loadList";
			case SAVE:
			case INDIETRO: return "indietro"; //$NON-NLS-1$
			default: return ""; //$NON-NLS-1$
			}
		};
	}
}
