package it.gioca.torino.manager.gui;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.Workflow;
import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.gui.YESNODialog.Action;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FirstForm extends MainForm {

	public FirstForm(String stateName, String title) {
		super(stateName, title);
	}

	@Override
	public void createFrom() {
		Composite c = getMainComposite();
		if(getPreviusForm()==null){
			FormUtil.getHeater(c, 2);
			{
				Composite menulaterale = FormUtil.MenuBarLaterale(c);
				drawButton(Messages.getString("FirstForm.0"), menulaterale, EBUTTON.ADD_GAMES); //$NON-NLS-1$
				drawButton(Messages.getString("FirstForm.1"), menulaterale, EBUTTON.MANAGE); //$NON-NLS-1$
				drawButton(Messages.getString("FirstForm.2"), menulaterale, EBUTTON.FIND); //$NON-NLS-1$
				drawButton(Messages.getString("FirstForm.4"), menulaterale, EBUTTON.STATS); //$NON-NLS-1$
				drawButton(Messages.getString("FirstForm.3"), menulaterale, EBUTTON.EXIT); //$NON-NLS-1$
				setMenuLaterale(menulaterale);
			}
			{
				Composite centrale = new Composite(c, SWT.NONE);
				GridData gdData = new GridData(GridData.FILL_BOTH);
				GridLayout gdLayout = new GridLayout();
				centrale.setLayout(gdLayout);
				centrale.setLayoutData(gdData);
				centrale.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
				setMainComposite(centrale);
			}
		}
		else{
			drawButton(Messages.getString("FirstForm.0"), getMenuLaterale(), EBUTTON.ADD_GAMES); //$NON-NLS-1$
			drawButton(Messages.getString("FirstForm.1"), getMenuLaterale(), EBUTTON.MANAGE); //$NON-NLS-1$
			drawButton(Messages.getString("FirstForm.2"), getMenuLaterale(), EBUTTON.FIND); //$NON-NLS-1$
			drawButton(Messages.getString("FirstForm.4"), getMenuLaterale(), EBUTTON.STATS); //$NON-NLS-1$
			drawButton(Messages.getString("FirstForm.3"), getMenuLaterale(), EBUTTON.EXIT); //$NON-NLS-1$
		}
	}

	private void drawButton(String text, Composite c, final EBUTTON eB){
		
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case EXIT: exit(); break;
				default: Workflow.getInstace().next(stateName, eB.toString(), getMainComposite(), getMenuLaterale());
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	}
	
	private void exit(){
		
		Display display = Display.getCurrent();
		Shell shell = display.getActiveShell();
		YESNODialog yn = new YESNODialog(shell);
		if(yn.open(Messages.getString("FirstForm.9")) == Action.YES) //$NON-NLS-1$
			shell.dispose();
	}
	
	enum EBUTTON{
		
		STATS,
		ADD_GAMES,
		MANAGE,
		FIND,
		EXIT;
		
		public String toString() {
			
			switch(this){
			case STATS: return "stats";
			case MANAGE: return "manage"; //$NON-NLS-1$
			case ADD_GAMES: return "addGames"; //$NON-NLS-1$
			case FIND: return "find"; //$NON-NLS-1$
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
