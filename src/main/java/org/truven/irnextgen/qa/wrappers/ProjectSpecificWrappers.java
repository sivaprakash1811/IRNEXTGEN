package org.truven.irnextgen.qa.wrappers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.truven.irnextgen.qa.utils.DataInputProvider;
import org.truven.irnextgen.qa.utils.Reporter;



public class ProjectSpecificWrappers extends GenericWrappers{

	protected String browserName;
	protected String dataSheetName;
	protected static String testCaseName;
	protected static String testDescription;

	@BeforeSuite
	public void beforeSuite() throws FileNotFoundException, IOException {
		Reporter.startResult();
		loadObjects();
	}
	@Parameters("browser")
	@BeforeMethod
	public void beforeMethod(String browserName) throws Throwable {
		Reporter.startTestCase();
		invokeApp(browserName);
	}

	@DataProvider(name="fetchdata")
	public Object[][] getdata() throws Throwable {
		return DataInputProvider.getSheet(dataSheetName);
	}
	@AfterMethod
	public void afterMethod() throws Throwable {
		Reporter.endResult();
		quitBrowser();
	}
	@AfterSuite
	public void afterSuite() throws Throwable {
		Reporter.endSuite();
	}

}
