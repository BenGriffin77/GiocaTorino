package it.gioca.torino.manager;


import it.gioca.torino.manager.common.MainForm;
import it.gioca.torino.manager.db.manager.ManageMultiToMulti;
import it.gioca.torino.manager.db.manager.RequestMulti;
import it.gioca.torino.manager.gui.YESNODialog;
import it.gioca.torino.manager.gui.YESNODialog.Action;
import it.gioca.torino.manager.gui.util.FormUtil;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Launcher {

	public Launcher() {
		Config.load();
	}
	
	public void draw(){
		Object instance = Workflow.getInstace().getFirstInstance();
		drawBase(instance);
	}
	
	public void drawBase(Object instanceClass){
		if(!(instanceClass instanceof MainForm))
			return;
		MainForm form = (MainForm)instanceClass;
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setMaximized(true);
		FormUtil.ButtonHeight = shell.getBounds().height /20;
		int value = shell.getBounds().width;
		if(value<1000)
			FormUtil.RESIZE_IMAGE_MULTI=4;
		if(value>1000 && value<3000)
			FormUtil.RESIZE_IMAGE_MULTI=2;
		if(value>3000)
			FormUtil.RESIZE_IMAGE_MULTI=1;
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				YESNODialog yn = new YESNODialog(shell);
				event.doit = yn.open(Messages.getString("Launcher.0")) == Action.YES; //$NON-NLS-1$
		      }
		    });
		shell.setText(Messages.getString("Launcher.1")); //$NON-NLS-1$
		shell.setImage(new Image(Display.getCurrent(), "IMG/LogoGT.png")); //$NON-NLS-1$
		Composite c = createContents(shell);
		form.setMainComposite(c);
		form.createFrom();
		shell.open();
		
		while(!shell.isDisposed()){
			if(!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
	}
	
	private Composite createContents(Composite parent) {

		GridData gdData = new GridData();
		GridLayout gdLayout = new GridLayout();
		gdLayout.numColumns = 2;
		parent.setLayout(gdLayout);
		parent.setLayoutData(gdData);
		parent.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		return parent;
	}
	
	private void prepareTheTables(){
		
//		new ManageNames(null); // caricamento dei nomi alternativi
//		new ManageDesigner(null); // caricamento dei designer
		RequestMulti request = new RequestMulti();
//		request.setCategory(true);
//		new ManageMultiToMulti(request);
		request.setCategory(false);
		new ManageMultiToMulti(request);
	}
	
	public static void main(String[] args) {

		Launcher l = new Launcher();
		if(args !=null && args.length>0){
			l.prepareTheTables();
			return;
		}
		
		l.draw();
	}

}
