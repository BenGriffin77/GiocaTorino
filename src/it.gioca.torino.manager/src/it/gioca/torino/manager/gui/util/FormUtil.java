package it.gioca.torino.manager.gui.util;

import it.gioca.torino.manager.Messages;
import it.gioca.torino.manager.common.ThemeManager;
import it.gioca.torino.manager.common.ThemeManager.COLOR;

import java.io.ByteArrayInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class FormUtil {
	
	private static Color[] HEATER_COLORS = {
		ThemeManager.getColor(COLOR.BLUE),
		ThemeManager.getColor(COLOR.WHITE)
	};

	private static int[] percents = {
		100
	};
	
	public static int RESIZE_IMAGE_MULTI;
	
	public static int ButtonHeight; 
	
	public static CLabel getHeater(Composite parent, int span){
		
		CLabel label = new CLabel(parent, SWT.CENTER | SWT.SHADOW_IN);
		GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
		labelData.grabExcessHorizontalSpace = true;
		labelData.verticalAlignment = GridData.CENTER;
		labelData.horizontalSpan = span;
		int val = parent.getShell().getBounds().height;
		labelData.heightHint = val / 6;
		label.setLayoutData(labelData);
		label.setText(Messages.getString("FormUtil.0")); //$NON-NLS-1$
		label.setBackground(HEATER_COLORS, percents);
		label.setForeground(ThemeManager.getColor(COLOR.YELLOW));
		Font initialFont = label.getFont();
		FontData[] fontDatas = initialFont.getFontData();
		for(FontData fd: fontDatas)
			fd.setHeight(val / 30);
		Font newFont = new Font(parent.getDisplay(), fontDatas);
		label.setFont(newFont);
		return label;
	}
	
	public static Table createSingleTable(Composite c, int span, ColumnType... column){
		Table table = new Table(c, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = span;
		data.grabExcessVerticalSpace = true;
		table.setLayoutData(data);
		table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableColumn _column;
	    for(int i=0; i<column.length; i++){
    		_column = new TableColumn(table, SWT.LEFT);
		    _column.setText(column[i].getName());
		    switch(column[i].getType()){
		    case INT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.INT_COMPARATOR)); break; 
		    case TEXT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.STRING_COMPARATOR)); break;
		    case IMAGE: break;
		    }
		    _column.pack();
		    if(column[i].getSize()==-1){
		    	_column.setResizable(false);
		    	_column.setWidth(0);
		    }
	        }
        return table;
	}
	
	public static Table createTable(Composite c, int span, ColumnType... column){
		Table table = new Table(c, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = span;
		data.grabExcessVerticalSpace = true;
		table.setLayoutData(data);
		table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableColumn _column;
	    for(int i=0; i<column.length; i++){
    		_column = new TableColumn(table, SWT.LEFT);
		    _column.setText(column[i].getName());
		    switch(column[i].getType()){
		    case INT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.INT_COMPARATOR)); break; 
		    case TEXT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.STRING_COMPARATOR)); break;
		    case IMAGE: break;
		    }
		    _column.pack();
		    if(column[i].getSize()==-1){
		    	_column.setResizable(false);
		    	_column.setWidth(0);
		    }
	        }
        return table;
	}
	
	public static Table createSpecTable(Composite c, int span, ColumnType... column){
		Table table = new Table(c, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = span;
		data.grabExcessVerticalSpace = true;
		table.setLayoutData(data);
		table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableColumn _column;
	    for(int i=0; i<column.length; i++){
    		_column = new TableColumn(table, SWT.LEFT);
		    _column.setText(column[i].getName());
		    switch(column[i].getType()){
		    case INT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.INT_COMPARATOR)); break; 
		    case TEXT: _column.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.STRING_COMPARATOR)); break;
		    case IMAGE: break;
		    }
		    _column.pack();
		    if(column[i].getSize()==-1){
		    	_column.setResizable(false);
		    	_column.setWidth(0);
		    }
	        }
        return table;
	}
	
	public static Table createTable(Composite c, ColumnType... column){
		
		return createTable(c, 1, column);
	}
	
	public static Table createSingleTable(Composite c, ColumnType... column){
		return createSingleTable(c, 1, column);
	}
	
	public static Composite MenuBarLaterale(Composite parent) {
		
		Composite group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout());
		GridData ldata = new GridData(GridData.FILL_VERTICAL);
		int val = parent.getShell().getBounds().width;
		ldata.widthHint = val / 5;
		group.setLayoutData(ldata);
		group.setBackground(ThemeManager.getColor(COLOR.BLUE));
		return group;
	}
	
	public static Button createDummyButton(Composite c, String text,int span, int bhight){
		
		Button dummy = new Button(c, SWT.BUTTON1);
		GridData gdText = new GridData();
		gdText.grabExcessHorizontalSpace = true;
		gdText.horizontalAlignment = GridData.FILL;
		gdText.heightHint = bhight;
		gdText.horizontalSpan = span;
		dummy.setLayoutData(gdText);
		dummy.setText(text);
		return dummy;
	}
	
	public static Group createGenGroup(Composite c, int span, int columns, String name, boolean sameWidth){
		
		Group group = new Group(c, SWT.NONE); 
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = columns;
		groupLayout.makeColumnsEqualWidth = sameWidth;
		groupData.horizontalSpan = span;
		group.setLayout(groupLayout);
		group.setLayoutData(groupData);
		group.setBackground(ThemeManager.getColor(COLOR.WHITE));
		group.setText(name);
		
		return group;
	}
	
	public static Group createAGroup(Composite c, int span, int columns, String name, boolean sameWidth){
		
		Group group = new Group(c, SWT.NONE); 
		GridData groupData = new GridData(GridData.FILL_BOTH);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = columns;
		groupLayout.makeColumnsEqualWidth = sameWidth;
		groupData.horizontalSpan = span;
		groupData.grabExcessHorizontalSpace = true;
		groupData.grabExcessVerticalSpace = true;
		group.setLayout(groupLayout);
		group.setLayoutData(groupData);
		group.setBackground(ThemeManager.getColor(COLOR.WHITE));
		group.setText(name);
		
		return group;
	}
	
	public static Label createLabel(Composite c, int span, String name){
		
		Label label = new Label(c, SWT.NONE);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		groupData.horizontalSpan = span;
		label.setLayoutData(groupData);
		label.setBackground(ThemeManager.getColor(COLOR.WHITE));
		label.setText(name);
		
		return label;
	}
	
	public static Label createLabelWithImage(Composite c, int span, byte[] thumbnail){
		
		Label label = new Label(c, SWT.NONE);
		GridData groupData = new GridData(GridData.FILL_BOTH);
		groupData.horizontalSpan = span;
		label.setLayoutData(groupData);
		label.setBackground(ThemeManager.getColor(COLOR.WHITE));
		if(thumbnail!=null)
			label.setImage(new Image(Display.getCurrent(), new ByteArrayInputStream(thumbnail)));
		return label;
	}
	
	public static Label createDummyLabel(Composite c, int span){
		
		return createLabel(c, span, "");
	}
	
	public static Text createText(Composite parent, String name){
		
		return createText(parent, 1, name);
	}
	
	public static Text createText(Composite parent,int span, String name){
		
		Text text = new Text(parent, SWT.BORDER);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		groupData.horizontalSpan = span;
		text.setLayoutData(groupData);
		text.setText(name);
		
		return text;
	}
	
	public static Label createLabelH(Composite c, int span, String name){
		
		Label label = new Label(c, SWT.NONE);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		groupData.horizontalSpan = span;
		label.setLayoutData(groupData);
		label.setBackground(ThemeManager.getColor(COLOR.WHITE));
		label.setText(name);
		
		return label;
	}
	
	public static Combo createCombo(Composite c, int span, String[] items){
		
		Combo combo = new Combo(c, SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = span;
		combo.setLayoutData(data);
		combo.setItems(items);
		if(items !=null && items.length>0)
			combo.select(0);
		return combo;
	}
	
	public static Combo createCombo(Composite c, int span){
		
		return createCombo(c, span, languages);
	}
	
	private static final String[] languages={
		"ITALIANO",
		"INGLESE",
		"FRANCESCE",
		"TEDESCO",
		"SPAGNOLO"
	};
	
	public static final int getLangId(String lang){
		
		for(int i=0; i<languages.length; i++){
			if(lang.equalsIgnoreCase(languages[i]))
				return i;
		}
		return 0;
	}
	
	public static Button createDummyButton(Composite c, String text){
		
		return createDummyButton(c, text,1, FormUtil.ButtonHeight);
	}
	
	public static Button createDummyButton(Composite c, int span, String text){
		
		return createDummyButton(c, text,span, FormUtil.ButtonHeight);
	}
	
	public static Button createCheckDummyButton(Composite c, String text){
		
		return createCheckDummyButton(c, text,1, FormUtil.ButtonHeight);
	}
	
	public static Button createCheckDummyButton(Composite c, int span, String text){
		
		return createCheckDummyButton(c, text,span, FormUtil.ButtonHeight);
	}
	
	public static Button createCheckDummyButton(Composite c, String text,int span, int bhight){
		
		Button dummy = new Button(c, SWT.CHECK);
		GridData gdText = new GridData();
		gdText.grabExcessHorizontalSpace = true;
		gdText.horizontalAlignment = GridData.FILL;
		gdText.heightHint = bhight;
		gdText.horizontalSpan = span;
		dummy.setBackground(ThemeManager.getColor(COLOR.WHITE));
		dummy.setLayoutData(gdText);
		dummy.setText(text);
		return dummy;
	}
	
	public static Image setImageInTheTable(byte[] thumbnail){
		ImageData data = new ImageData(new ByteArrayInputStream(thumbnail));
		int X = data.width;
		int Y = data.height;
		data = data.scaledTo(X/RESIZE_IMAGE_MULTI, Y/RESIZE_IMAGE_MULTI);
		return new Image(Display.getCurrent(), data);
	}
}
