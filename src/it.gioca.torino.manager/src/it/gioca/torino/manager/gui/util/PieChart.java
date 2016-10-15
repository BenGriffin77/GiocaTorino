package it.gioca.torino.manager.gui.util;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

public class PieChart {

	private List<Value> values = new ArrayList<Value>();
	
//	private String title;
	
	private String subTitle;
	
//	public void setTitle(String title){
//		this.title = title;
//	}
	
	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}
	
	public void addValue(String name, double val){
		values.add(new Value(name, val));
	}
	
	private PieDataset createDataset() {
        
		DefaultPieDataset dataset = new DefaultPieDataset();
        for(Value val:values)
        	dataset.setValue(val.getName(), new Double(val.getVal()));
        return dataset;        
    }
	
    private JFreeChart createChart(PieDataset dataset) {
        
        JFreeChart chart = ChartFactory.createPieChart(
        	subTitle,  			// chart title
            dataset,            // data
            true,               // include legend
            true,
            false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;
        
    }
	
	public ChartComposite plot(Composite parent){
		
		JFreeChart chart = createChart(createDataset());
//        Display display = Display.getCurrent();
//        Shell shell = new Shell(display);
//        shell.setSize(600, 400);
//        shell.setLayout(new FillLayout());
//        shell.setText(title);
        ChartComposite frame = new ChartComposite(parent, SWT.NONE, chart, true);
        GridData data = new GridData(GridData.FILL_BOTH);
        frame.setLayoutData(data);
        //frame.setDisplayToolTips(false);
        frame.pack();
        return frame;
//        shell.open();
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch())
//                display.sleep();
//        }

	}
	
	private class Value{
		
		private String name;
		
		private double val;
		
		public Value(String name, double val) {
			this.name = name;
			this.val = val;
		}
		
		public String getName(){
			return name;
		}
		
		public double getVal(){
			return val;
		}
	}
}
