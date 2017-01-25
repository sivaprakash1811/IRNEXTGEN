package org.truven.irnextgen.qa.pages;

import org.truven.irnextgen.qa.wrappers.ProjectSpecificWrappers;

public class AdhocHomePage extends ProjectSpecificWrappers {
	
	public AdhocHomePage mainTab()throws Throwable{
		driver.findElementById(prop.getProperty("Homepage.clickMain.xpath"));
		return new AdhocHomePage();
	}

}
