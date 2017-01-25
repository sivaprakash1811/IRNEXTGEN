package org.truven.irnextgen.qa.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.truven.irnextgen.qa.wrappers.ProjectSpecificWrappers;

public class Adhoc_TC01 extends ProjectSpecificWrappers {
	@BeforeClass
	public void startTestCase() {
		dataSheetName = "";
		testCaseName = "";
		testDescription = "";
	}
	
	@Test(dataProvider="fetchdata")
	public void loginforSuccess(String keyword1) throws Throwable {
		
		
		
	}
}
