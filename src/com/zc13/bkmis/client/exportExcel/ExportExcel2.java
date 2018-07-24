package com.zc13.bkmis.client.exportExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 * EXCEL报表工具类.
 * 
 * @author caoyb
 * @version $Revision:$
 */
public class ExportExcel2 {

	private HSSFWorkbook wb = null;

	private HSSFSheet sheet = null;

	/**
	 * @param wb
	 * @param sheet
	 */
	public ExportExcel2(HSSFWorkbook wb, HSSFSheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
	}

	/**
	 * @return the sheet
	 */
	public HSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the wb
	 */
	public HSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @param wb
	 *            the wb to set
	 */
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	/**
	 * 创建通用EXCEL头部
	 * 
	 * @param headString
	 *            头部显示的字符
	 * @param colSum
	 *            该报表的列数
	 */
	public void createNormalHead(String headString, int colSum) {

		HSSFRow row = sheet.createRow(0);

		// 设置第一行
		HSSFCell cell = row.createCell((short) 0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString("南京城区各网点进件统计报表"));

		// 指定合并区域
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建通用报表第二行
	 * 
	 * @param params
	 *            统计条件数组
	 * @param colSum
	 *            需要合并到的列索引
	 */
	public void createNormalTwoRow(String[] params, int colSum) {
		HSSFRow row1 = sheet.createRow(1);
		row1.setHeight((short) 300);

		HSSFCell cell2 = row1.createCell((short) 0);

		cell2.setCellType(HSSFCell.ENCODING_UTF_16);
		cell2.setCellValue(new HSSFRichTextString("统计时间：" + params[0] + "至"
				+ params[1]));

		// 指定合并区域
		sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) colSum));

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		cell2.setCellStyle(cellStyle);

	}

	/**
	 * 设置报表标题
	 * 
	 * @param columHeader
	 *            标题字符串数组
	 */
	public void createColumHeader(String[] columHeader) {

		// 设置列头
		HSSFRow row2 = sheet.createRow(2);

		// 指定行高
		row2.setHeight((short) 600);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		/*
		 * cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
		 * cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		 * cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		 * cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		 * cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		 */

		// 设置单元格背景色
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFCell cell3 = null;

		for (int i = 0; i < columHeader.length; i++) {
			cell3 = row2.createCell((short) i);
			cell3.setCellType(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(new HSSFRichTextString(columHeader[i]));
		}

	}

	/**
	 * 创建内容单元格
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param row
	 *            HSSFRow
	 * @param col
	 *            short型的列索引
	 * @param align
	 *            对齐方式
	 * @param val
	 *            列值
	 */
	public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, short align,
			String val) {
		HSSFCell cell = row.createCell((short) col);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(align);
		cell.setCellStyle(cellstyle);
	}

	/**
	 * 创建合计行
	 * 
	 * @param colSum
	 *            需要合并到的列索引
	 * @param cellValue
	 */
	public void createLastSumRow(int colSum, String[] cellValue) {

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		HSSFCell sumCell = lastRow.createCell((short) 0);

		sumCell.setCellValue(new HSSFRichTextString("合计"));
		sumCell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,
				sheet.getLastRowNum(), (short) colSum));// 指定合并区域

		for (int i = 2; i < (cellValue.length + 2); i++) {
			sumCell = lastRow.createCell((short) i);
			sumCell.setCellStyle(cellStyle);
			sumCell.setCellValue(new HSSFRichTextString(cellValue[i - 2]));

		}

	}

	/**
	 * 输入EXCEL文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void outputExcel(String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HSSFWorkbook createExcel(String fileName) throws NullPointerException,
			IOException {
		String fileNameLocal = "D:\\" + fileName + ".xls";
		System.out.println("fileNameLocal in action: " + fileNameLocal);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("new sheet");
		HSSFRow row = sheet.createRow((short) 0); // 创建第一行,为excel表头
		String[] cellHeader = new String[] {};
		cellHeader = new String[] { "id", "会员号", "密码" }; // 表头
		for (int i = 0; i < cellHeader.length; i++) { // 产生表头
			HSSFCell cell = row.createCell((short) i); // 在第一行上加列
			cell.setCellValue(new HSSFRichTextString(cellHeader[i]));
		}

		for (int i = 0; i < 1000; i++) {
			HSSFRow dataRow = sheet.createRow(i + 1); // 创建其他各数据行
			HSSFCell cell0 = dataRow.createCell((short) 0);
			cell0.setCellValue(i);
			HSSFCell cell1 = dataRow.createCell((short) 1);
			cell1.setCellValue(i);
			HSSFCell cell2 = dataRow.createCell((short) 2);
			cell2.setCellValue(i);
		}
		
		return workbook;
//		try {
//			FileOutputStream fileOut = new FileOutputStream(fileNameLocal);
//			workbook.write(fileOut);
//			fileOut.flush();
//			fileOut.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
