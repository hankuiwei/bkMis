package com.zc13.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.zc13.bkmis.mapping.CompactClient;
import com.zc13.bkmis.mapping.EBuilds;
import com.zc13.bkmis.mapping.ELp;
import com.zc13.bkmis.mapping.ERooms;


/**
 * 导出excel
 * @author 董道奎
 * Date：Dec 16, 2010
 * Time：4:50:51 PM
 */
public class ExplortExcel {
	
	
	/**
	 * 导出报表公用方法
	 * @param list
	 * @param titleName
	 * @param cellHeader
	 * @param cellValue
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HSSFWorkbook  creatWorkbook(List list,String titleName,String[] cellHeader,String[] cellValue,Object obj) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = cellHeader.length-1;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		//下面这个for循环的作用是给合并的单元格整体外面画上边框
		/*for(int k = 1;k<=length;k++){
			HSSFCell titleCell2 = row.createCell((short) k);
			//titleCell2.setCellValue(new HSSFRichTextString("d"));
			titleCell2.setCellStyle(style);
		}*/
		
		HSSFRow tableheadRow = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh = tableheadRow.createCell((short) 0);
		cellxh.setCellValue(new HSSFRichTextString("序号"));
		cellxh.setCellStyle(style2);
		for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				obj =  list.get(i);
				HSSFRow dataRow = sheet.createRow(i + 3);// 创建正文数据行
				HSSFCell cellone = dataRow.createCell((short) 0) ;
				if("收款信息列表".equals(titleName)&&i==list.size()-1){
					cellone.setCellValue(new HSSFRichTextString("合计")); //合计
				}else{
					cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				}
				cellone.setCellStyle(style3);
				try {
					for(int k=0 ;k<cellValue.length;k++){
						//执行数组中提供的方法名的方法
						Method addMethod = obj.getClass().getMethod(cellValue[k]);
						Object resultString = new Object();
						if("getELp".equals(addMethod.getName())){ //当需要用到别的对象的方法时，特殊处理如下
							ELp elp =  (ELp)addMethod.invoke(obj);
							resultString = elp.getLpName();
						}else if("getEBuilds".equals(addMethod.getName())){
							EBuilds build =  (EBuilds)addMethod.invoke(obj);
							resultString = build.getBuildName();
						}else if("getERooms".equals(addMethod.getName())){
							ERooms rooms =  (ERooms)addMethod.invoke(obj);
							if(rooms==null){
								resultString = "";
							}else{	
								resultString = rooms.getRoomCode();
							}
						}else if("getCompactClient".equals(addMethod.getName())){
							CompactClient client =  (CompactClient)addMethod.invoke(obj);
							resultString = client.getName();
						}else if(("收款信息列表".equals(titleName)||"应收账款信息列表".equals(titleName)) && "getStatus".equals(addMethod.getName())){
							resultString = addMethod.invoke(obj);
							if( "1".equals(resultString)){
								resultString = "已缴";
							}else if( i==list.size()-1){
								resultString = "";
							}else{
								resultString = "未缴";
							}
						}else if("getIsEarnest".equals(addMethod.getName())){
							resultString = addMethod.invoke(obj);
							if("1".equals(resultString)){
								resultString = "已缴";
							}else{
								resultString = "未缴";
							}
						}else if("getBillType".equals(addMethod.getName())){
							resultString = addMethod.invoke(obj);
							if("1".equals(resultString))
								resultString = "发票";
							else if("收款信息列表".equals(titleName)&&i==list.size()-1){
								resultString = "";
							}else{
								resultString = "收据";
							}
						}else if("getUseRecord".equals(addMethod.getName())){
							Method getLastRecord = obj.getClass().getMethod(cellValue[6]);
							Method getThisRecord = obj.getClass().getMethod(cellValue[7]);
							double a = Double.parseDouble(GlobalMethod.ObjToParam(getLastRecord.invoke(obj),"0.0"));
							double b = Double.parseDouble(GlobalMethod.ObjToParam(getThisRecord.invoke(obj),"0.0"));
							resultString = b-a;
						}else if("getPublicRecord".equals(addMethod.getName())){
							Method getLastRecord = obj.getClass().getMethod(cellValue[3]);
							Method getThisRecord = obj.getClass().getMethod(cellValue[4]);
							double a = Double.parseDouble(GlobalMethod.ObjToParam(getLastRecord.invoke(obj),"0.0"));
							double b = Double.parseDouble(GlobalMethod.ObjToParam(getThisRecord.invoke(obj),"0.0"));
							resultString = b-a;
						}else if("getPublicRecord2".equals(addMethod.getName())){
							Method getLastRecord = obj.getClass().getMethod(cellValue[4]);
							Method getThisRecord = obj.getClass().getMethod(cellValue[5]);
							double a = Double.parseDouble(GlobalMethod.ObjToParam(getLastRecord.invoke(obj),"0.0"));
							double b = Double.parseDouble(GlobalMethod.ObjToParam(getThisRecord.invoke(obj),"0.0"));
							resultString = b-a;
						}else{
							resultString = addMethod.invoke(obj);
						}
						if(resultString == null){ //这么做可以防止页面上出现NULL的显示
							resultString = new String("");
						}
						//把读出的数据插入到excel的单元格中并赋予样式
						HSSFCell cell = dataRow.createCell((short) (k+1));
						cell.setCellValue(new HSSFRichTextString(""+resultString));
						cell.setCellStyle(style3);
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*for(int k=0 ;k<method.length;k++){
					String methodName = method[k].getName();//取得方法名的string形式并验证是否需要写入到excel中
					boolean b = validateName(methodName);
					if(b) {
						j++;
						try {
							Method addMethod = obj.getClass().getMethod(methodName);
							Object resultString = addMethod.invoke(obj);
							//把读出的数据插入到excel的单元格中并赋予样式
							HSSFCell cell = dataRow.createCell((short) j);
							cell.setCellValue(new HSSFRichTextString(""+resultString+j));
							cell.setCellStyle(style3);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}*/
					//dataRow.createCell((short) k).setCellValue(new HSSFRichTextString(eLp.method[0]));
			}
			
		}
	
		return workbook;
	}
	/**
	 * 导出excel，结果集是Map
	 * @param list
	 * @param sheetName
	 * @param cellHeader
	 * @param cellAttr
	 * @return
	 */
	public static HSSFWorkbook creatWorkbookMap(List list,String titleName,String[] cellHeader,String[] cellAttr) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = cellHeader.length-1;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		//下面这个for循环的作用是给合并的单元格整体外面画上边框
		/*for(int k = 1;k<=length;k++){
			HSSFCell titleCell2 = row.createCell((short) k);
			//titleCell2.setCellValue(new HSSFRichTextString("d"));
			titleCell2.setCellStyle(style);
		}*/
		
		HSSFRow tableheadRow = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh = tableheadRow.createCell((short) 0);
		cellxh.setCellValue(new HSSFRichTextString("序号"));
		cellxh.setCellStyle(style2);
		for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(list.size()>0){
			
			for (int i = 0; i < list.size(); i++) {
				HashMap map = new HashMap();
				map = (HashMap) list.get(i);
				HSSFRow dataRow = sheet.createRow(i + 3);// 创建其他各数据行
				HSSFCell cellone = dataRow.createCell((short) 0) ;
				cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				cellone.setCellStyle(style3);
				for (int j = 0; j < cellAttr.length; j++) {
					int j1 = j+1;
					HSSFCell cell = dataRow.createCell((short) j1);
					
					cell.setCellValue(new HSSFRichTextString(map.get(cellAttr[j]) == null ? "" : map.get(cellAttr[j]).toString()));
					cell.setCellStyle(style3);
				}
			}
		}
		return workbook;
	}

	
	/**
	 * 收据开发票导出excel
	 * 导出excel，结果集是Map
	 * @param list
	 * @param sheetName
	 * @param cellHeader
	 * @param cellAttr
	 * @return
	 */
	public static HSSFWorkbook creatReceiptWorkbookMap(List list,String titleName,String[] cellHeader,String[] cellAttr) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = cellHeader.length-1;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		//下面这个for循环的作用是给合并的单元格整体外面画上边框
		
		HSSFRow tableheadRow = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh = tableheadRow.createCell((short) 0);
		cellxh.setCellValue(new HSSFRichTextString("序号"));
		cellxh.setCellStyle(style2);
		for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(null!=list && list.size()>=1){
			int flag = 3;
			for (int i = 0; i < list.size(); i++) {
				HashMap map = new HashMap();
				map = (HashMap) list.get(i);
				
				HSSFRow dataRow = sheet.createRow(flag);// 创建其他各数据行
				HSSFCell cellone = dataRow.createCell((short) 0) ;
				cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				cellone.setCellStyle(style3);
				HSSFCell cellsecond = dataRow.createCell((short) 1) ;
				HSSFCell cellthree = dataRow.createCell((short) 2) ;
				HSSFCell cellfore = dataRow.createCell((short) 3) ;
				HSSFCell cellfive = dataRow.createCell((short) 4) ;
				HSSFCell cellsix = dataRow.createCell((short) 5) ;
				cellsecond.setCellValue(new HSSFRichTextString(map.get("clientName") == null ? "" : map.get("clientName").toString()));
				cellthree.setCellValue(new HSSFRichTextString(map.get("reciveCostDate") == null ? "" : map.get("reciveCostDate").toString()));
				cellfore.setCellValue(new HSSFRichTextString(map.get("billNum") == null ? "" : map.get("billNum").toString()));
				cellfive.setCellValue(new HSSFRichTextString(map.get("reciveUserName") == null ? "" : map.get("reciveUserName").toString()));
				cellsix.setCellValue(new HSSFRichTextString(map.get("amount") == null ? "" : String.valueOf(map.get("amount"))));
				
				List secondlist = (List)map.get("itemList");
				if(secondlist != null && secondlist.size()>0){
					int rowhb = secondlist.size();
					for(int m = 0;m<secondlist.size();m++){
						Map mapp = (Map)secondlist.get(m);
						HSSFRow newRow = m==0 ? dataRow :sheet.createRow(flag);
						HSSFCell cellseven = newRow.createCell((short) 6) ;
						HSSFCell celleight = newRow.createCell((short) 7) ;
						HSSFCell cellnine = newRow.createCell((short) 8) ;
						HSSFCell cellten = newRow.createCell((short) 9) ;
						cellseven.setCellValue(new HSSFRichTextString(mapp.get("standardName") == null ? "" : mapp.get("standardName").toString()));
						celleight.setCellValue(new HSSFRichTextString(mapp.get("moneydetail") == null ? "" : String.valueOf(mapp.get("moneydetail"))));
						cellnine.setCellValue(new HSSFRichTextString(mapp.get("invoiceId") == null ? "" : mapp.get("invoiceId").toString()));
						cellten.setCellValue(new HSSFRichTextString(mapp.get("moneydetail") == null ? "" : String.valueOf(mapp.get("moneydetail"))));
						flag ++;
					}
					for(int j =0;j<=5;j++){
						int j1 = j+1;
						sheet.addMergedRegion(new Region(flag, (short) j,rowhb, (short) (j)));//起始行、起始列、结束行、结束列
					}
				}
				
			}
		}
		return workbook;
	}
	
	/**
	 * 收据开发票导出excel
	 * 导出excel，结果集是Map
	 * @param list
	 * @param sheetName
	 * @param cellHeader
	 * @param cellAttr
	 * @return
	 */
	public static HSSFWorkbook createInvoiceWorkbookMap(List list,String titleName,String[] cellHeader,String[] cellAttr) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = cellHeader.length-1;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		//下面这个for循环的作用是给合并的单元格整体外面画上边框
		
		HSSFRow tableheadRow = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh = tableheadRow.createCell((short) 0);
		cellxh.setCellValue(new HSSFRichTextString("序号"));
		cellxh.setCellStyle(style2);
		for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(null!=list && list.size()>=1){
			int flag = 3;
			for (int i = 0; i < list.size(); i++) {
				HashMap map = new HashMap();
				map = (HashMap) list.get(i);
				
				HSSFRow dataRow = sheet.createRow(flag);// 创建其他各数据行
				HSSFCell cellone = dataRow.createCell((short) 0) ;
				cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				cellone.setCellStyle(style3);
				HSSFCell cellsecond = dataRow.createCell((short) 1) ;
				HSSFCell cellthree = dataRow.createCell((short) 2) ;
				HSSFCell cellfore = dataRow.createCell((short) 3) ;
				HSSFCell cellfive = dataRow.createCell((short) 4) ;
				HSSFCell cellsix = dataRow.createCell((short) 5) ;
				cellsecond.setCellValue(new HSSFRichTextString(map.get("clientName") == null ? "" : map.get("clientName").toString()));
				cellthree.setCellValue(new HSSFRichTextString(map.get("operatorName") == null ? "" : map.get("operatorName").toString()));
				cellfore.setCellValue(new HSSFRichTextString(map.get("date") == null ? "" : map.get("date").toString()));
				cellfive.setCellValue(new HSSFRichTextString(map.get("invoiceNum") == null ? "" : map.get("invoiceNum").toString()));
				cellsix.setCellValue(new HSSFRichTextString(map.get("totalInvoiceAmount") == null ? "" : map.get("totalInvoiceAmount").toString()));
				
				List secondlist = (List)map.get("itemList");
				if(secondlist != null && secondlist.size()>0){
					int rowhb = secondlist.size();
					for(int m = 0;m<secondlist.size();m++){
						Map mapp = (Map)secondlist.get(m);
						HSSFRow newRow = m==0 ? dataRow :sheet.createRow(flag);
						HSSFCell cellseven = newRow.createCell((short) 6) ;
						HSSFCell celleight = newRow.createCell((short) 7) ;
						HSSFCell cellnine = newRow.createCell((short) 8) ;
						cellseven.setCellValue(new HSSFRichTextString(mapp.get("invoiceContent") == null ? "" : mapp.get("invoiceContent").toString()));
						cellnine.setCellValue(new HSSFRichTextString(mapp.get("itemName") == null ? "" : mapp.get("itemName").toString()));
						celleight.setCellValue(new HSSFRichTextString(mapp.get("invoiceAmount") == null ? "" : String.valueOf(mapp.get("invoiceAmount"))));
						flag ++;
					}
					for(int j =0;j<=5;j++){
						int j1 = j+1;
						sheet.addMergedRegion(new Region(flag, (short) j,rowhb, (short) (j)));//起始行、起始列、结束行、结束列
					}
				}
				
			}
		}
		return workbook;
	}
	
	
	/**
	 * 根据需要过滤不需要的方法
	 * @param methodName
	 * @return
	 */
	public static boolean validateName(String methodName){
		
		boolean b = true;
		if(!methodName.startsWith("get")){//首先过滤掉那些方法不是以get开头的
			b = false;
		}else if(methodName.startsWith("getId") || methodName.startsWith("getEBuildses")){ //id属性一般都不要求打印
			b = false;
		}
		
		
		return b;
	}
	
	/**
	 * @author luq
	 * 导出excel，结果集是OBJECT
	 * @param list
	 * @param sheetName
	 * @param cellHeader
	 * @param cellAttr
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static HSSFWorkbook creatWorkbookObject(List list,String titleName,String[] cellHeader,String[] cellAttr, Class clazz) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = cellHeader.length-1;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		
		HSSFRow tableheadRow = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh = tableheadRow.createCell((short) 0);
		cellxh.setCellValue(new HSSFRichTextString("序号"));
		cellxh.setCellStyle(style2);
		for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(list.size()>0){
			
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				HSSFRow dataRow = sheet.createRow(i + 3);// 创建其他各数据行
				HSSFCell cellone = dataRow.createCell((short) 0) ;
				cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				cellone.setCellStyle(style3);
				
				for (int j = 0; j < cellAttr.length; j++) {
					int j1 = j+1;
					HSSFCell cell = dataRow.createCell((short) j1);
					
					String firstLetter = cellAttr[j].substring(0, 1).toUpperCase();
					// 获得和属性对应的getXXX()方法的名字
					String getMethodName = "get" + firstLetter + cellAttr[j].substring(1);
					// 获得和属性对应的getXXX()方法
					Method getMethod = clazz.getMethod(getMethodName,new Class[] {});
					Object value = getMethod.invoke(obj, new Object[] {});
					cell.setCellValue(new HSSFRichTextString(value == null ? "" : value.toString()));
					cell.setCellStyle(style3);
				}
			}
		}
		return workbook;
	}
	
	/**
	 * 
	 * @param list
	 * @param titleName
	 * @param cellHeader
	 * @param cellAttr
	 * @param clazz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * Date:2013-5-30 
	 * Time:下午9:40:44
	 */
	public static HSSFWorkbook createFincncialReport(List<Map<String,String>> proRs,List<Map<String,String>> operateRS,String titleName,String[] proAttr,String[] oprateAttr,String[] proTitles) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(titleName);
		//设置默认的宽和高
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 24);
		// 设置样式
		HSSFCellStyle style = workbook.createCellStyle(); //大标题样式
		HSSFCellStyle style2 = workbook.createCellStyle();//表头样式
		HSSFCellStyle style3 = workbook.createCellStyle();//正文样式
		
		style.setFillForegroundColor(HSSFColor.WHITE.index);//背景色无
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//将背景色加入面板
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		// 字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 22);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到样式
		style.setFont(font);
		
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		/*style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 12);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		//大标题
		int length = 9;
		sheet.addMergedRegion(new Region(0, (short) 0,1, (short) (length+1)));//起始行、起始列、结束行、结束列
		HSSFRow row = sheet.createRow((short) 0);// 创建第一行
		HSSFCell titleCell = row.createCell((short) 0);
		titleCell.setCellValue(new HSSFRichTextString(titleName));
		//把样式应用到大标题单元格
		titleCell.setCellStyle(style);
		
		sheet.addMergedRegion(new Region(2, (short) 0,3, (short) (0)));
		HSSFRow tableheadRow0 = sheet.createRow((short) 2);// 创建表头
		HSSFCell cellxh0 = tableheadRow0.createCell((short) 0);
		cellxh0.setCellValue(new HSSFRichTextString("项目"));
		cellxh0.setCellStyle(style2);
		sheet.addMergedRegion(new Region(2, (short) 1,2, (short) (4)));
		HSSFCell cellxh1 = tableheadRow0.createCell((short) 1);
		cellxh1.setCellValue(new HSSFRichTextString("年预算金额"));
		cellxh1.setCellStyle(style2);
		sheet.addMergedRegion(new Region(2, (short) 5,2, (short) (9)));
		HSSFCell cellxh2 = tableheadRow0.createCell((short) 5);
		cellxh2.setCellValue(new HSSFRichTextString("实际金额"));
		cellxh2.setCellStyle(style2);
		sheet.addMergedRegion(new Region(2, (short) 10,3, (short) (10)));
		HSSFCell cellxh3 = tableheadRow0.createCell((short) 10);
		cellxh3.setCellValue(new HSSFRichTextString("占全年"));
		cellxh3.setCellStyle(style2);
		//第二行表头
		HSSFRow tableheadRow1 = sheet.createRow((short) 3);// 创建表头
		sheet.addMergedRegion(new Region(3, (short) 1,3, (short) (1)));
		HSSFCell cellxh4 = tableheadRow1.createCell((short) 1);
		cellxh4.setCellValue(new HSSFRichTextString("管理部门"));
		cellxh4.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 2,3, (short) (2)));
		HSSFCell cellxh5 = tableheadRow1.createCell((short) 2);
		cellxh5.setCellValue(new HSSFRichTextString("工程部门"));
		cellxh5.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 3,3, (short) (3)));
		HSSFCell cellxh6 = tableheadRow1.createCell((short) 3);
		cellxh6.setCellValue(new HSSFRichTextString("招商部门"));
		cellxh6.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 4,3, (short) (4)));
		HSSFCell cellxh7 = tableheadRow1.createCell((short) 4);
		cellxh7.setCellValue(new HSSFRichTextString("合计"));
		cellxh7.setCellStyle(style2);
		
		sheet.addMergedRegion(new Region(3, (short) 5,3, (short) (5)));
		HSSFCell cellxh8 = tableheadRow1.createCell((short) 5);
		cellxh8.setCellValue(new HSSFRichTextString("管理部门"));
		cellxh8.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 6,3, (short) (6)));
		HSSFCell cellxh9 = tableheadRow1.createCell((short) 6);
		cellxh9.setCellValue(new HSSFRichTextString("工程部门"));
		cellxh9.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 7,3, (short) (7)));
		HSSFCell cellxh10 = tableheadRow1.createCell((short) 7);
		cellxh10.setCellValue(new HSSFRichTextString("招商部门"));
		cellxh10.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 8,3, (short) (8)));
		HSSFCell cellxh11 = tableheadRow1.createCell((short) 8);
		cellxh11.setCellValue(new HSSFRichTextString("当月合计"));
		cellxh11.setCellStyle(style2);
		sheet.addMergedRegion(new Region(3, (short) 9,3, (short) (9)));
		HSSFCell cellxh12 = tableheadRow1.createCell((short) 9);
		cellxh12.setCellValue(new HSSFRichTextString("年累计"));
		cellxh12.setCellStyle(style2);
		/*for (int i = 0; i < cellHeader.length; i++) {
			HSSFCell cell = tableheadRow.createCell((short) (i+1));
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
			cell.setCellStyle(style2);
		}*/
		//正文内容样式
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		if(proRs.size()>0){
			for (int i = 0; i < proRs.size(); i++) {
				Map<String,String> map = proRs.get(i);
				HSSFRow dataRow = sheet.createRow(i + 4);// 创建其他各数据行
				//HSSFCell cellone = dataRow.createCell((short) 0) ;
				//cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				//cellone.setCellStyle(style3);
				for (int j = 0; j < proAttr.length; j++) {
					//int j1 = j+1;
					int j1 = j;
					HSSFCell cell = dataRow.createCell((short) j1);
					cell.setCellValue(new HSSFRichTextString(map.get(proAttr[j]) == null ? "" : map.get(proAttr[j]).toString()));
					cell.setCellStyle(style3);
				}
			}
		}
		
		if(operateRS.size()>0){
			for (int i = 0; i < operateRS.size(); i++) {
				Map<String,String> map = operateRS.get(i);
				HSSFRow dataRow = sheet.createRow(i + proTitles.length+5);// 创建其他各数据行
				//HSSFCell cellone = dataRow.createCell((short) 0) ;
				//cellone.setCellValue(new HSSFRichTextString(""+(i+1))); //序号
				//cellone.setCellStyle(style3);
				for (int j = 0; j < oprateAttr.length; j++) {
					//int j1 = j+1;
					int j1 = j;
					HSSFCell cell = dataRow.createCell((short) j1);
					cell.setCellValue(new HSSFRichTextString(map.get(oprateAttr[j]) == null ? "" : map.get(oprateAttr[j]).toString()));
					cell.setCellStyle(style3);
				}
			}
		}

		return workbook;
	}
}
