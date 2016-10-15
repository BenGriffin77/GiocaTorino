package it.gioca.torino.manager.common;

import it.gioca.torino.manager.common.ThemeManager.COLOR;
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

public abstract class MainDialog {

	private Shell shell;
	
	public MainDialog(Shell parent) {
		
		shell = new Shell(parent, SWT.SHELL_TRIM);
		shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		shell.setLayout(new GridLayout());
		shell.setLayoutData(new GridData());
	}
	
	protected Composite composite;
	
	protected void setComposite(){
		
		composite = new Composite(shell, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setBackground(ThemeManager.getColor(COLOR.WHITE));
		composite.setLayout(layout);
	}
	
	protected abstract void createTextWidgets();
	
	protected abstract void find();
	
	protected abstract void save();
	
	protected abstract void back();
	
	public void open(){
		
		setComposite();
		createTextWidgets();
		
		shell.open();
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
	}
	
	public void close(){
		
		shell.close();
	}
	
	protected enum EBUTTON{
		
		NONE,
		FIND,
		OK,
		INDIETRO;
	}
	
	protected Button drawButton(String text, Composite c, final EBUTTON eB){
		
		if(eB == EBUTTON.NONE)
			return null;
		Button dummy = FormUtil.createDummyButton(c, text);
		dummy.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent arg0) {
				
				switch(eB){
				case NONE: return;
				case FIND: find(); break;
				case OK: save(); close();break;
				case INDIETRO: back(); close(); break;
				}
			}
			
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		return dummy;
	}
}
