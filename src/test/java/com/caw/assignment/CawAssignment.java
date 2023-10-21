package com.caw.assignment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.caw.common.CommonActions;

public class CawAssignment extends CommonActions {
	
	public CawAssignment(WebDriver driver, ExtentReports extentReport) {
		super(driver, extentReport);
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	JSONArray mainJsonArray;
	
	public void DataAddAndValidation() {
		try {
			fillAndSubmitData();
			validateDatas();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillAndSubmitData() {
		try {
			ExtentTest fillAndSubmitData = createTest("Fill and submit the data", "Fill and submit the data");
			
			fillAndSubmitData.info("Going to create json data.");
			jsonDataCreation();
			fillAndSubmitData.info("Json data created.");
			
			WebElement tableData = driver.findElement(By.xpath("//*[text()='Table Data']"));
			WebElement inputBox = driver.findElement(By.id("jsondata"));
			WebElement submitData = driver.findElement(By.id("refreshtable"));
			
			fillAndSubmitData.info("Click on Table Data.");
			click(tableData);
			
			fillAndSubmitData.info("Send json value to input box.");
			sendKeys(inputBox, mainJsonArray.toString());
			
			fillAndSubmitData.info("Submit the input.");
			click(submitData);
			
			WebElement crossCheck = driver.findElement(By.xpath("//*[text()='Jennifer']"));
			
			if(isElementPresent(crossCheck)) {
				fillAndSubmitData.pass("data created successfully.");
			}
			else {
				testCaseFailed("some data not added. case were failed.", fillAndSubmitData);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void validateDatas() {
		try {
			for(int i = 0; i < mainJsonArray.length(); i++) {
				String gender = mainJsonArray.getJSONObject(i).getString("gender");
				String name = mainJsonArray.getJSONObject(i).getString("name");
				String age = mainJsonArray.getJSONObject(i).get("age").toString();
				
				ExtentTest checkData = createTest("Check data for " + name, "Check data for " + name);
				
				WebElement webName = driver.findElement(By.xpath("//table[@id='dynamictable']//td[text()='" + name + "']"));
				WebElement webGender = driver.findElement(By.xpath("//table[@id='dynamictable']//td[text()='" + name + "']/preceding-sibling::td[text()='" + gender + "']"));
				WebElement webAge = driver.findElement(By.xpath("//table[@id='dynamictable']//td[text()='" + name + "']/following-sibling::td[text()='" + age + "']"));
				
				if(isElementPresent(webName)) {
					checkData.pass("Name checked successfully. Name > " + name);
				}
				else {
					testCaseFailed("Name was not there. Name > " + name, checkData);
				}
				
				if(isElementPresent(webGender)) {
					checkData.pass("Gender checked successfully. Gender > " + gender);
				}
				else {
					testCaseFailed("Gender was not there. Gender > " + gender, checkData);
				}

				if(isElementPresent(webAge)) {
					checkData.pass("Age checked successfully. Age > " + age);
				}
				else {
					testCaseFailed("Age was not there. Age > " + age, checkData);
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void jsonDataCreation() {
		try {
			mainJsonArray = new JSONArray();
			JSONObject data1 = new JSONObject();
			JSONObject data2 = new JSONObject();
			JSONObject data3 = new JSONObject();
			JSONObject data4 = new JSONObject();
			JSONObject data5 = new JSONObject();
			
			data1.put("name", "Bob");
			data1.put("age", 20);
			data1.put("gender", "male");
			mainJsonArray.put(data1);
			
			data2.put("name", "George");
			data2.put("age", 42);
			data2.put("gender", "male");
			mainJsonArray.put(data2);
			
			data3.put("name", "Sara");
			data3.put("age", 42);
			data3.put("gender", "female");
			mainJsonArray.put(data3);
			
			data4.put("name", "Conor");
			data4.put("age", 40);
			data4.put("gender", "male");
			mainJsonArray.put(data4);
			
			data5.put("name", "Jennifer");
			data5.put("age", 42);
			data5.put("gender", "female");
			mainJsonArray.put(data5);
			
			System.out.println(mainJsonArray.toString());

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
