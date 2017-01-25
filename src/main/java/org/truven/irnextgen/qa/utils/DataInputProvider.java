package org.truven.irnextgen.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.truven.irnextgen.qa.wrappers.GenericWrappers;

/**This is to read the test data from the workbook
 * @author Sivaprakash
 *
 */
public class DataInputProvider  extends GenericWrappers{

	public static String[][] getSheet(String dataSheetName) throws IOException{
		String[][] data=null;

		try {
			FileInputStream fis = new FileInputStream(new File("./data/"+dataSheetName+".xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];
			try {
				for (int i = 1; i<rowCount+1;i++){
					XSSFRow row = sheet.getRow(i);
					for (int j=0;j<columnCount;j++){
						try {
							String cellValue = "";
							try {
								cellValue = row.getCell(j).getStringCellValue();
							} catch (NullPointerException e) {
								e.printStackTrace();
							}
							data[i-1][j]=cellValue;
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			workbook.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

}
