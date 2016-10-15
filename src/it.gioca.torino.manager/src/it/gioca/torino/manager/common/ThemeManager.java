package it.gioca.torino.manager.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ThemeManager {
	
	private ThemeManager() {
		
	}
	
	public static Color getColor(COLOR ID){
		
		Display device = Display.getCurrent();
		
		switch(ID){
		case WHITE: return device.getSystemColor(SWT.COLOR_WHITE);
		case BLUE: return device.getSystemColor(SWT.COLOR_BLUE);
		case RED: return device.getSystemColor(SWT.COLOR_RED);
		case YELLOW: return device.getSystemColor(SWT.COLOR_YELLOW);
		case GREEN: return device.getSystemColor(SWT.COLOR_GREEN);
		case DARK_YELLOW: return device.getSystemColor(SWT.COLOR_DARK_YELLOW);
		case CYAN: return device.getSystemColor(SWT.COLOR_CYAN);
		default: return device.getSystemColor(SWT.COLOR_WHITE);
		}
	}
	
	public enum COLOR{
		WHITE,
		RED,
		BLUE,
		YELLOW,
		GREEN,
		HEATERBLUE,
		DARK_YELLOW,
		CYAN
	}
}
