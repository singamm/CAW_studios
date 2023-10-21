package com.caw.common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class CommonActions {
public WebDriver driver = null;
	
	public ExtentReports extentReport;
	public static ExtentSparkReporter sparkReporter;
	public ExtentTest test;
	
	public static String reportPath = "C:\\Users\\NarasingamMPK\\Documents\\Automation\\Automation_Report\\Reports.html";
	
	public static String screenshotPath = "C:\\Users\\NarasingamMPK\\Documents\\Automation\\Automation_Report\\Screenshots";
	
	
	public CommonActions(WebDriver driver, ExtentReports extentReport) {
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	@SuppressWarnings("deprecation")
	public WebDriver getFirefoxDriver() {
		try {
			ExtentTest browserLaunch = createTest("Launch firefox driver", "Launch firefox driver");
			
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\NarasingamMPK\\Documents\\Automation\\Automation_Drivers\\geckodriver.exe");
			browserLaunch.info("launch the firefox driver.");
			driver = new FirefoxDriver();
			
			browserLaunch.info("maximize the firefox driver window.");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public void getServerUrl(String url) {
		try {
			ExtentTest browserLaunch = createTest("Launch server url > [" + url + "]", "Launch server url> [" + url + "]");
			browserLaunch.info("get the server url[" + url + "]");
			driver.get(url);
			Thread.sleep(5000);
			
			WebElement pageLoaded = driver.findElement(By.xpath("//h1[text()='Dynamic HTML TABLE Tag']"));
			
			if(isElementPresent(pageLoaded)) {
				browserLaunch.info("page loaded successfully.");
			}
			else {
				browserLaunch.info("page not loaded. kindly check.");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ExtentReports launchExtentReport() {
		try {
			sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setDocumentTitle("Automation Report For Caw");
			sparkReporter.config().setReportName("Automation Report");
			
			extentReport = new ExtentReports();
			extentReport.attachReporter(sparkReporter);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return extentReport;
	}
	
	public void closeDriver() {
		try {
			driver.quit();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isElementPresent(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",element);
			return element.isDisplayed();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void sendKeys(WebElement element, String value) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",element);
			element.clear();
			element.sendKeys(value);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void click(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			element.click();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ExtentTest createTest(String testCaseName, String testCaseDesc) {
		ExtentTest testCase = null;
		try {
			testCase = extentReport.createTest(testCaseName, testCaseDesc);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return testCase;
	}
	
	public void testCaseFailed(String details, ExtentTest extentTest) {
		try {
			String screenShotPath = takeScreenshot();
			extentTest.fail(details);
			extentTest.addScreenCaptureFromPath(screenShotPath);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String takeScreenshot() {
		try {
			String dateTime = getCurrentDateAndTime();
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(screenshotPath + "\\" + dateTime + ".png"));
			return screenshotPath + "\\" + dateTime + ".png";
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getCurrentDateAndTime() {
		DateFormat dateFormat = null;
		Date date = null;
		try {
			dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
			date = new Date();
			System.out.println(dateFormat.format(date));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return dateFormat.format(date);
	}
	
	public void flushExtent() {
		try {
			extentReport.flush();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
