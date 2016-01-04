package com.zlzkj.app.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zlzkj.core.sql.Row;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class ExcelTransport {
	
	/**
	  * Read data from a excel file
	  */
	 public static List<Object>  readExcel(String excelFileName, int sheetNum, String keys){
	     Workbook workbook = null;

	     try {
	     workbook = Workbook.getWorkbook(new File(excelFileName));
	     } catch (Exception e) {

	  //   throw new Exception("file to import not found!");

	     }

	     Sheet sheet = workbook.getSheet(sheetNum);
	     Cell cell = null;

	     int rowCount=sheet.getRows();
	     String[] key = keys.split(",");
	     List<Object> list = new ArrayList<Object>();
	     
	     for (int i = 1; i <rowCount; i++) {
	    	 Map dbo = new HashMap();
	    	 for (int j = 0; j <key.length; j++) {
	    		cell=sheet.getCell(j, i);
	    		if(cell.getType()==CellType.NUMBER){
	    			dbo.put(key[j], ((NumberCell)cell).getValue());
	    		}
	    		else if(cell.getType()==CellType.DATE){
	    			dbo.put(key[j], ((DateCell)cell).getDate());
	    		}
	    		else{
	    			dbo.put(key[j], (cell.getContents()));
	    		}
	    	}
	    	list.add(dbo);
	     }
	     workbook.close();
	     return list;
	 }
	 
	 /**
	  * 创建一个Excel文件
	  * @param excelFileName	Excel文件名
	  * @param sheetName		Excel页名
	  * @param headerNames		标题名（用逗号分隔）
	  * @param keys				键名（用逗号分隔）
	  * @param list				数据
	  * @return
	  * @throws Exception
	  */
	 public static void createMainExcelFile(String excelFileName, String sheetName, String headerNames, String keys, List<Row> list) throws Exception{
		String filePath = "D:\\" + excelFileName;
		 File file = new File(filePath);
		 try{
			 WritableWorkbook wwb = Workbook.createWorkbook(file);
			   WritableSheet ws = wwb.createSheet(sheetName,0);

			   String[] aheaderName = headerNames.split(",");
			   String[] akey = keys.split(",");
			   if(akey.length != aheaderName.length)
				   throw new CustomerException("键名个数和标题个数必须一样!");
			   
			   ws.mergeCells(0, 0, aheaderName.length-1, 0);//合并单元格
				  
			   ws.setRowView(0,400); //将第一行的高度设为400
			   //第一行内容加边框
			   WritableFont   wf   =   new   WritableFont(WritableFont.TIMES,16,WritableFont.BOLD,false); 
			   WritableCellFormat   wcf   =   new   WritableCellFormat(wf); 
			   wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
			   wcf.setAlignment(jxl.format.Alignment.CENTRE);
			  // wcf.setBackground(Colour.GREY_25_PERCENT);
			   Label title = new Label(0,0,"项目列表",wcf);
			   ws.addCell(title);
			   
			   for(int col = 0; col < aheaderName.length; col++){
				   Label header = new Label(col, 1, aheaderName[col]);
				   ws.setColumnView(col,25);
				   ws.addCell(header);
			   }
			   
			   try {
				   for(int row=0; row<list.size(); row++)
				   {
					   Map obj = (Map) list.get(row);
					   for(int col=0; col<akey.length; col++)
					   {
						   Label body = new Label(col, row+2, StringUtil.objectToString(obj.get(akey[col])));
						   ws.setRowView(row,400);
						   ws.addCell(body);
					   }
				   }
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
			   wwb.write();
			   wwb.close();
		 }catch(IOException e){
			   e.printStackTrace();
		 }
		 catch(WriteException e){
			   e.printStackTrace();
		 }
	 }
	 
}
