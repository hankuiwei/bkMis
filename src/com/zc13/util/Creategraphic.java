package com.zc13.util;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * 
 * @author 赵玉龙
 * Date：Dec 30, 2010
 * Time：8:44:28 AM
 * 该类用于生成图
 */
public class Creategraphic {

	public static PieDataset createDateset(List list){
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(int i = 0;i<list.size();i++){
			Object obj[] = (Object[])list.get(i);
			int amount = (Integer)obj[1];
			if(null == obj[1]){
				amount = 0;
			}
			dataset.setValue(obj[0].toString(), amount);
		}
		return dataset;
	}
	
	public static PieDataset createDateset2(List list){
			
			DefaultPieDataset dataset = new DefaultPieDataset();
			
			for(int i = 0;i<list.size();i++){
				Object obj[] = (Object[])list.get(i);
				double amount = (Double)obj[1];
				if(null == obj[1]){
					amount = 0;
				}
				dataset.setValue(obj[0].toString(), amount);
			}
			return dataset;
	}
	public static JFreeChart createChart(String title,PieDataset dataset,String path,String fileName){
		
		JFreeChart chart = ChartFactory.createPieChart(title,dataset,true,true,false);
		//设置title等提示信息的能够显示中文
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("", Font.BOLD, 12));
		PiePlot plot2 = (PiePlot)chart.getPlot();
	    plot2.setLabelFont(new Font("黑体", Font.BOLD, 12));
	    plot2.setLabelGenerator(new StandardPieSectionLabelGenerator( 
	    		("{0} 数量是：{1} 占百分比：{2})"), NumberFormat.getNumberInstance(), 
	    		new DecimalFormat("0.00%"))); 
	    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	    
	    File temporaryPath = new File(path);
	    if (!temporaryPath.exists()) {
	    	temporaryPath.mkdirs();
	    }
		FileOutputStream out2 = null;
		try {
			out2 = new FileOutputStream(temporaryPath + "\\"+fileName);
			ChartUtilities.writeChartAsJPEG(out2, chart, 500, 300);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				out2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return chart;
	}
	
	public static JFreeChart createChart2(String title,PieDataset dataset,String path,String fileName){
		
		JFreeChart chart = ChartFactory.createPieChart(title,dataset,true,true,false);
		//设置title等提示信息的能够显示中文
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("", Font.BOLD, 12));
		PiePlot plot2 = (PiePlot)chart.getPlot();
	    plot2.setLabelFont(new Font("黑体", Font.BOLD, 12));
	    plot2.setLabelGenerator(new StandardPieSectionLabelGenerator( 
	    		("{0}缴费总金额是：{1} 占百分比：{2})"), NumberFormat.getNumberInstance(), 
	    		new DecimalFormat("0.00%"))); 
	    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	    
	    File temporaryPath = new File(path);
	    if (!temporaryPath.exists()) {
	    	temporaryPath.mkdirs();
	    }
		FileOutputStream out2 = null;
		try {
			out2 = new FileOutputStream(temporaryPath + "\\"+fileName);
			ChartUtilities.writeChartAsJPEG(out2, chart, 1000, 300);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				out2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return chart;
	}
	//生成能源图
	public static PieDataset createEngDateset(List list){
		
		DefaultPieDataset dpd = new DefaultPieDataset ();
		if(list != null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				Object obj[] = (Object[])list.get(i);
				dpd.setValue(obj[0].toString(), (Double)obj[1]);
			}
		}
		return dpd;
	}
	public static JFreeChart createEngChart(String title,PieDataset dataset,String path,String fileName){
		
		JFreeChart chart = ChartFactory.createPieChart(title,dataset,true,true,false);
		//设置title等提示信息的能够显示中文
		chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		chart.getLegend().setItemFont(new Font("", Font.BOLD, 12));
		PiePlot plot2 = (PiePlot)chart.getPlot();
	    plot2.setLabelFont(new Font("黑体", Font.BOLD, 12));
	    plot2.setLabelGenerator(new StandardPieSectionLabelGenerator( 
	    		("{0} 使用总量是：{1} 占百分比：{2})"), NumberFormat.getNumberInstance(), 
	    		new DecimalFormat("0.00%"))); 
	    ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	    
	    File temporaryPath = new File(path);
	    if (!temporaryPath.exists()) {
	    	temporaryPath.mkdirs();
	    }
		FileOutputStream out2 = null;
		try {
			out2 = new FileOutputStream(temporaryPath + "\\"+fileName);
			ChartUtilities.writeChartAsJPEG(out2, chart, 550, 300);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				out2.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return chart;
	}
}
