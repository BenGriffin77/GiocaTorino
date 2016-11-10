
package it.gioca.torino.manager.common;

import java.util.List;

import it.gioca.torino.manager.Config;
import it.gioca.torino.manager.common.ThemeManager.COLOR;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;


public abstract class MainForm {
	
	protected String title;
	
	protected String stateName;
	
	protected String nextState;
	
	protected Label messageBox;
	
	protected boolean toClean = false;
	
	protected static final int width = 360;
	
	protected boolean debug = true;
	
	private String previusForm;
	
	protected Composite mainComposite;
	
	private Composite menuLaterale;
	
	//FIXME: Forse dovrebbe essere sostituito da un oggetto?
	protected String message = "";
	
	public MainForm(String stateName, String title) {
		this.title = title;
		this.stateName = stateName;
	}
	
	public abstract void createFrom();
	
	public abstract void afterCreate();
	
	public abstract void setDataModel();
	
	protected void refreshMessage(){
		
		if(message==null)
			return;
		messageBox.setText(message);
		messageBox.setBackground(ThemeManager.getColor(COLOR.RED));
		messageBox.setVisible(true);
		toClean = true;
	}
	
	public String getTitle(){
		
		return title;
	}

	public String getPreviusForm() {
		return previusForm;
	}

	public void setPreviusForm(String previusForm) {
		this.previusForm = previusForm;
	}

	public Composite getMainComposite() {
		return mainComposite;
	}

	public void setMainComposite(Composite mainComposite) {
		this.mainComposite = mainComposite;
	}

	public void layout(){
		mainComposite.layout();
		menuLaterale.layout();
	}
	
	public void clean(){
		
		for(Control control: mainComposite.getChildren())
			control.dispose();
		for(Control control: menuLaterale.getChildren())
			control.dispose();
	}
	
	protected void cleanCenter(){
		
		for(Control control: mainComposite.getChildren())
			control.dispose();
	}
	
	protected Composite getMenuLaterale(){
		return menuLaterale;
	}

	public void setMenuLaterale(Composite menuLaterale) {
		this.menuLaterale = menuLaterale;
	}
	
	protected int blockFirstValue(List<Integer> values){
		
		for(int i=0; i<Config.MAXINT; i++){
			if(values==null)
				return 1;
			for(int intToRemove: values){
				if(intToRemove == (i+1))
					break;
				return i+1;
			}
		}
		return 1;
	}
	
	protected String[] getArrayFromList(List<String> list){
		String []dsf = new String[list.size()];
		return list.toArray(dsf);
	}
}
