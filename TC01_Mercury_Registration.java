package com.mercury.testScripts;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.mercury.testUtil.TestUtil;
import com.thoughtworks.selenium.webdriven.commands.GetText;

public class TC01_Mercury_Registration extends TestUtil{
	
	private static Logger Log = Logger.getLogger(TC01_Mercury_Registration.class.getName());//

  @Test(dataProvider = "NewtoursRegistration")
  public void TS01_NewTrours_Registration(String homePageTitle, String registrationTitle, String firstname, String lastname, String phone, String email,
		  String address, String city, String state, String pin, String country, String username,
		  String passwd, String confirmpasswd, String loginTitle) throws Exception {
     try {
    	 
      Log.info("##################### : Start -- TS01_NewTrours_Registration  : ################################");	 
    	 /*DOMConfigurator.configure("log4j.xml");*/
	  // Verify the Title of Webpage
      Log.info("Step 1: The title of the Web Page is :" + homePageTitle);
	  getTitle(homePageTitle);
	  // Click on Register link
	  Log.info("Step 2: Click on REGISTER link");
	  ClickRegister("register_link");
	  Log.info("Step 3: After click on Registration link Title is: " + registrationTitle);
	  getTitle(registrationTitle);
	// Enter the Contact Information
	  
	  Log.info("Step 4: Enter the 'FirstName' in the text box as " + firstname);
	  WaitForElementPresent("reg_firstname_txt");
	  typeData("reg_firstname_txt", firstname);
	  Log.info("Step 5: Enter the last name "+ lastname);
	  typeData("reg_lastname_txt", lastname);
	  Log.info("Step 6: Enter the Phone Number " + phone);
	  typeData("reg_phone_txt", phone);
	 // Assert.assertEquals(phone, ClickObject("reg_phone_txt").getText());
	  Log.info("Step 7: Enter the email " + email);
	  typeData("reg_email_txt", email);
	  
	// Enter the Mailing information
	  Log.info("Step 8,9,10,11 : Enter the Addres, city , state and Pin number" + address+city+state+pin);
	  typeData("reg_address_txt", address);
	  typeData("reg_city_txt", city);
	  typeData("reg_state_txt", state);
	  typeData("reg_postalcode_txt", pin);
	  
	  waitFor5Seconds();
	  Log.info("Step 12: Select the Country from dropdown "+ country);
	  selectFromDropdown("reg_country_txt", country);
	// User information
	 WaitForElementPresent("reg_username_txt");
	  Log.info("Step 13, 14, 15 : Enter the Username , password, confirmpasswd " + username+passwd+confirmpasswd);
	  typeData("reg_username_txt", username);
	  typeData("reg_passwd_txt", passwd);
	  typeData("reg_confirpasswd_txt", confirmpasswd);
	  
	// Submit button
	  WaitForElementPresent("reg_submit_btn");
	  Log.info("Step 16: Click on Submit button");
	  ClickObject("reg_submit_btn").click();
	  
	//  WaitForElementPresent("sign_in_link");
	  Assert.assertEquals("sign-in", getText("sign_in_link"));
	  Log.info("##################### : End -- TS01_NewTrours_Registration  : ################################"); 
	  
	 // getTitle(loginTitle);
     } catch (AssertionError e) {
 		// TODO: handle exception
    	 Log.error("Test Fails and taking screen shot.");
    	 takeScreenShot("Error", "Login","TS01_NewTrours_Registration");
    	 
 	}
  }

  @DataProvider(name ="NewtoursRegistration")
  public Object[][] GmailLogin() throws Exception {
     Object[][] returnObj = getExcelData(xlsPath, "registration", "NewtoursRegistration", "NewtoursRegistration1");
    		 return(returnObj); 
  }
  @BeforeClass
  public void beforeClass() {
	  
	  driver = new FirefoxDriver();
	  driver.get("http://newtours.demoaut.com/");
	  driver.manage().window().maximize();
  }

  @AfterClass
  public void afterClass() throws InterruptedException {
	  Log.info("Step 7: Close the Browser"); 
	  Thread.sleep(3000);
	  closeBrowser();
  }

}
