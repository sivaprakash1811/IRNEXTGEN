package org.truven.irnextgen.qa.wrappers;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.truven.irnextgen.qa.utils.*;

/**
 * This method is to call the Grid Configurations
 * 
 * @author balajih
 *
 */
public class GenericWrappers {

	protected static RemoteWebDriver driver;
	protected static Properties prop;
	public String sUrl, primaryWindowHandles, sHubUrl, sHubPort;
	private String primaryWindowHandle;
	private String ANY;
	

	public GenericWrappers() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./Config/config.properties")));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
			sUrl = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is to Invoke the Browser
	 * 
	 * @author Sivaprakash
	 * @param browser
	 * @throws Throwable
	 * 
	 */
	public void invokeApp(String browser) throws Throwable {
		boolean bReturn = false;

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);
		dc.setPlatform(Platform.WINDOWS);
		try {
			killAllDrivers();
			driver = new RemoteWebDriver(new URL("http://" + sHubUrl + ":" + sHubPort + "/wd/hub"), dc);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(sUrl);

			primaryWindowHandle = driver.getWindowHandle();

			try {
				Reporter.reportStep("The Browser  " + browser + " launched Successfully.", "PASS");
				bReturn = true;
			} catch (Exception e) {
				e.printStackTrace();

				Reporter.reportStep("The Browser  " + browser + " could not be launched Successfully.", "FAIL");
				bReturn = false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method is to load Objects from the Object Property file
	 * 
	 * @author Sivaprakash
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadObjects() throws FileNotFoundException, IOException {
		prop = new Properties();		
		prop.load(new FileInputStream(new File("./object/object.properties")));
	}
	
	/**
	 * This Method is used to Kill the Browser drivers from task manager
	 * 
	 * @author Sivaprakash
	 */
	public void killAllDrivers() {
		try {
			Process p = Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			try {
				p.waitFor();
				Thread.sleep(3000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			p = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe*");
			try {
				p.waitFor();
				Thread.sleep(3000);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to quit the current Browser opened for testing
	 * 
	 * @author Sivaprakash
	 * @throws Throwable
	 */
	public void quitBrowser() throws Throwable {

		try {
			driver.quit();

		} catch (Throwable e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to write data to excel
	 * 
	 * @author Sivaprakash
	 * @throws IOException
	 */
	public void writeData() throws IOException{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("output");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell =row.createCell(0);
		cell.setCellValue("S.No");
		XSSFCell cell1 =row.createCell(1);
		cell1.setCellValue("Description");
		FileOutputStream ops = new FileOutputStream(new File("./data/output.xlsx"));
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
	
	/**
	 * This method will enter the value as text field using Name attribute to
	 * locate
	 * 
	 * @author Sivaprakash
	 * @param nameValue
	 * @param data
	 * @return
	 * @throws Throwable
	 */
	public boolean enterbyName(String nameValue, String data) throws Throwable {
		boolean bReturn = true;
		try {
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);
			Reporter.reportStep("The data " + data + " is entered successfully.", "PASS");
			bReturn = true;
		} catch (Exception e) {
			Reporter.reportStep("The data " + data + " is not entered successfully.", "FAIL");
		}
		return bReturn;
	}
	
	/**
	 * This method will enter the value keyboard buttons
	 *
	 * @author Sivaprakash
	 * @param location
	 * @return
	 * 
	 */
	public void uploadFile(String location) throws Throwable {
		try {
						
			StringSelection stringSelection = new StringSelection(location);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Robot ro = new Robot();
			ro.keyPress(KeyEvent.VK_CONTROL);
			ro.keyPress(KeyEvent.VK_V);
			ro.keyRelease(KeyEvent.VK_V);
			ro.keyRelease(KeyEvent.VK_CONTROL);
			ro.keyPress(KeyEvent.VK_ENTER);
			ro.keyRelease(KeyEvent.VK_ENTER);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
