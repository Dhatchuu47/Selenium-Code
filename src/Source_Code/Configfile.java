package Source_Code;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Configfile {

	XSSFWorkbook work;
	XSSFSheet sheet1;
	public Configfile(String excelpath)
	{
		try
		{
			File src = new File(excelpath);
			FileInputStream fis = new FileInputStream(src);
			work = new XSSFWorkbook(fis);
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public String getData(int sheetnumber, int row, int column)
	{
		sheet1 =work.getSheetAt(sheetnumber);
		String str1 =sheet1.getRow(row).getCell(column).getStringCellValue();
		return str1;
	}
	
	
	 public int getrow(int sheetindex)
	 {
		 int row =work.getSheetAt(sheetindex).getLastRowNum();
		 row =row +1;
		 return row;
	 }
}
