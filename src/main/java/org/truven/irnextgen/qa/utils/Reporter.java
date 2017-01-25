/**
 * 
 */
package org.truven.irnextgen.qa.utils;

import java.io.File;
import org.apache.commons.io.FileUtils;import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**This program is to Capture Test Results along with Screenshots
 * @author Sivaprakash
 *
 */
public class Reporter extends org.truven.irnextgen.qa.wrappers.ProjectSpecificWrappers {

	public static ExtentTest test;
	public static ExtentReports extent;

	public static void reportStep(String desc, String status) throws Throwable{

		long number = (long) Math.floor(Math.random()* 900000000L) + 100000000L;

		try {
			File srcFile = (((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
			FileUtils.copyFile(srcFile, new File("./reports/images/"+number+".png"));
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		if(status.toUpperCase().equals("PASS")){
			test.log(LogStatus.PASS, desc+test.addScreenCapture("./images/"+number+".png"));
		}else if(status.toUpperCase().equals("FAIL")){
			test.log(LogStatus.FAIL, desc+test.addScreenCapture("./images/"+number+".png"));
			//throw new RuntimeException("FAILED");
		}else if(status.toUpperCase().equals("INFO")){
			test.log(LogStatus.INFO, desc);
		}

	}


	public static void startResult(){
		extent = new ExtentReports("./reports/results.html",true);
	}

	public static void startTestCase(){

		test = extent.startTest(testCaseName, testDescription);

	}

	public static void endResult(){
		extent.endTest(test);
	}

	public static void endSuite(){
		extent.flush();
		extent.close();
	}

}
