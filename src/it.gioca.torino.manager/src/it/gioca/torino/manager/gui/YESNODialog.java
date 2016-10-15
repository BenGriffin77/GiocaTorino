package it.gioca.torino.manager.gui;

import it.gioca.torino.manager.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class YESNODialog {

	private Shell shell;
	
	public YESNODialog(Shell parent) {
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		shell.setLayout(new GridLayout());

	}
	
	public enum Action{
		YES,
		NO
	};
	
	private Action a = Action.NO;
	
	public Action open(String text){
		
		createTextWidgets(text);
		createControlButtons();
		shell.pack();
		shell.open();
		Display display = shell.getDisplay();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch())
				display.sleep();
		}
		return a;
	}

	private void createControlButtons() {
		
		Composite composite = new Composite(shell, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Button okButton = new Button(composite, SWT.PUSH);
		okButton.setText(Messages.getString("YESNODialog.0")); //$NON-NLS-1$
		okButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				a = Action.YES;
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		Button cancel = new Button(composite, SWT.PUSH);
		cancel.setText(Messages.getString("YESNODialog.1")); //$NON-NLS-1$
		cancel.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				a = Action.NO;
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	}

	private void createTextWidgets(String text) {
		
		Composite composite = new Composite(shell, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.CENTER);
		label.setText(text);
	}
}

