package it.gioca.torino.manager.common;

import it.gioca.torino.manager.Config;

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
		// E0FFFF - 224 -255 -255 
		// C7FFFF 199 255 255 
		case CUSTOM1: return new Color(device, Config.CUSTOM1[0], Config.CUSTOM1[1], Config.CUSTOM1[2]);
		case CUSTOM2: return new Color(device, Config.CUSTOM2[0], Config.CUSTOM2[1], Config.CUSTOM2[2]);
		case CUSTOM3: return new Color(device, Config.CUSTOM3[0], Config.CUSTOM3[1], Config.CUSTOM3[2]);
		default: return device.getSystemColor(SWT.COLOR_WHITE);
		}
	}
	
	public static Color getCustomColor(){
	
		switch(Config.SELECT){
		case 1: return getColor(COLOR.CUSTOM1);
		case 2: return getColor(COLOR.CUSTOM2);
		case 3: return getColor(COLOR.CUSTOM3);
		default: return getColor(COLOR.WHITE);
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
		CUSTOM1,
		CUSTOM2,
		CUSTOM3,
		CYAN
	}
}
