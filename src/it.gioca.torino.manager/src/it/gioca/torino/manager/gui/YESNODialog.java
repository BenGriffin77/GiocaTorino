package it.gioca.torino.manager.gui;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class YESNODialog {

	private Shell shell;
	
	private int val = (Display.getCurrent().getActiveShell().getBounds().height) / Config.MAXDIALOGMULTI;
	
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
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		{
			Button okButton = new Button(composite, SWT.BUTTON1);
			okButton.setText(Messages.getString("YESNODialog.0")); //$NON-NLS-1$
			GridData gdText = new GridData();
			gdText.grabExcessHorizontalSpace = true;
			gdText.horizontalAlignment = GridData.FILL;
			gdText.heightHint = val*3;
			gdText.horizontalSpan = 1;
			okButton.setLayoutData(gdText);
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
		}
		
		{
			Button cancel = new Button(composite, SWT.BUTTON1);
			cancel.setText(Messages.getString("YESNODialog.1")); //$NON-NLS-1$
			GridData gd1Text = new GridData();
			gd1Text.grabExcessHorizontalSpace = true;
			gd1Text.horizontalAlignment = GridData.FILL;
			gd1Text.heightHint = val*3;
			gd1Text.horizontalSpan = 1;
			cancel.setLayoutData(gd1Text);
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
	}

	private void createTextWidgets(String text) {
		
		Composite composite = new Composite(shell, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.CENTER);
		
		label.setText(text);
		Font initialFont = label.getFont();
		FontData[] fontDatas = initialFont.getFontData();
		for(FontData fd: fontDatas)
			fd.setHeight(val);
		Font newFont = new Font(composite.getDisplay(), fontDatas);
		label.setFont(newFont);
	}
}

