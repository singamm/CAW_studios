package com.caw.run;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.caw.assignment.CawAssignment;
import com.caw.common.CommonActions;

public class TestRun {
	
	public static WebDriver driver = null;
	public static ExtentReports extentReport;
	
	public static String browser_name = "firefox";

	public static void main(String[] args) {
		CommonActions ca = new CommonActions(driver, extentReport);
		
		extentReport = ca.launchExtentReport();
		driver = ca.getFirefoxDriver();
		
		ca.getServerUrl("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		
		CawAssignment caw = new CawAssignment(driver, extentReport);
		caw.DataAddAndValidation();
		
		ca.closeDriver();
		
		ca.flushExtent();

	}

}
