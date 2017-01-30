package com.mercury.testUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.apache.log4j.*;
import org.testng.annotations.BeforeSuite;

public class TestUtil {
	public static WebDriver driver;
	public static String ScreenShotfilePath = System.getProperty("user.dir")+"\\src\\screenShots\\";
	public static Properties props;
	public static Properties logprop;
	public static Properties configprops;
	public static FileInputStream fis;
	public static FileInputStream configfis;
	public static String propertyFilePath = System.getProperty("user.dir")+"/src/test/resources/com/mercury/properties/OR.properties";
	public static String confgiProperties = System.getProperty("user.dir")+"/src/test/resources/com/mercury/config/config.properties";
	public static String logProperties = System.getProperty("user.dir")+"/src/test/resources/com/mercury/properties/log4j.properties";
	public static String xlsPath = System.getProperty("user.dir")+"/src/test/resources/testData/TestData.xls";
	
	@BeforeSuite
	public void ReadProperties() throws IOException{
		
		props = new Properties();
		fis = new FileInputStream(propertyFilePath);
		props.load(fis);
	}
	
    public void ReadLogFile() throws FileNotFoundException{
		
    	logprop = new Properties();
		configfis = new FileInputStream(logProperties);
		try {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void ReadConfigproperties() throws FileNotFoundException{
		
		configprops = new Properties();
		configfis = new FileInputStream(confgiProperties);
		try {
			props.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WebElement ClickObject(String xpathkey) throws IOException {
		try {
			
			return driver.findElement(By.xpath(props.getProperty(xpathkey)));
			
		} catch (Throwable t) {
			System.out.println("error:\t" + xpathkey);
			return null;
		}
	}
	
	public String  getText(String Xpath){
		return driver.findElement(By.xpath(props.getProperty(Xpath))).getText();
	}
	public void getIframe() {
		List<WebElement> ele = driver.findElements(By.tagName("frame"));
	    System.out.println("Number of frames in a page :" + ele.size());
	    for(WebElement el : ele){
	      //Returns the Id of a frame.
	        System.out.println("Frame Id :" + el.getAttribute("id"));
	      //Returns the Name of a frame.
	        System.out.println("Frame name :" + el.getAttribute("name"));
	    }
	}
	
	/*public void waitForElementPopulate(String xpathkey) throws Exception {

		int i = 0;
		String expdata = null;
		while (i <= 20) {
			System.out.println(expdata + "\t" + i);
			Thread.sleep(2000L);
			new Actions(driver).moveToElement(ClickObject(xpathkey)).click().perform();
			ClickObject(xpathkey).click();
			Thread.sleep(2000L);
			expdata = ClickObject(xpathkey).getText().trim();
			System.out.println(expdata);
			if (expdata != null) {
				System.out.println(expdata);
				break;
			} else {
				Thread.sleep(500L);
				i++;
			}
		}
	}*/


	public WebElement WaitForElementPresent(String xpathkey) throws Exception {

		WebElement present = null;
		int i = 0;
		while (i <= 10) {
			 Thread.sleep(1000L);
              present = ClickObject(xpathkey);
			if (present != null) {
				break;
			} else {
				System.out.println("i is: "+i);
				i++;
			}
		}
		return present;
	}
	

	public void waitFor5Seconds() throws Exception {

		int i = 0;
		while (i <= 5) {
			Thread.sleep(1000L);
			System.out.println("i is: "+i);
			i++;
		}
	}

	public void closeBrowser(){
		driver.close();
	}
	public void quitAllBrowser(){
		driver.quit();
	}
	public void typeData(String xpathkey, String value) throws Exception {

		try {
			
			ClickObject(xpathkey).sendKeys(value);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	public void ClickRegister(String Xpath){
		
		driver.findElement(By.xpath(props.getProperty(Xpath))).click();
	}
	
	public void ClickSignin(String Xpath){
		driver.findElement(By.xpath(props.getProperty(Xpath))).click();
	}
	
	public void selectFromDropdown(String xpathkey, String value) throws Exception{
		/*waitFor5Seconds();*/
		try {
			new Select(ClickObject(xpathkey)).selectByVisibleText(value);   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}
	public void getTitle(String title){
		try {
			  String titleofpage = driver.getTitle();
			  Assert.assertEquals(titleofpage, title);
		} catch (AssertionError e) {
			// TODO: handle exception
			Assert.fail();
		}
		 
	}
	public void takeScreenShot(String folderName,String Modname,String fileName ) throws IOException
	{
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    org.apache.commons.io.FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+folderName+"//"+Modname+"//"+fileName+".png"));	   
		   
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		selenium.captureScreenshot(System.getProperty("user.dir")+"//screenshots//"+folderName+"//"+Modname+"//"+fileName+".png");
	 }
	
    public void testLogin(String EmailAddress, String password) throws Exception {
    	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    	   
    	    if (ClickObject("Login_EmailAddress_label") != null && ClickObject("Login_Password_lable") != null) {
			typeData("Loginpage_EmailAddress_txt", EmailAddress);
			typeData("Loginpage_password_txt", password);
			Thread.sleep(1000);
			ClickObject("Login_button").submit();
			System.out.println("Clicked on Login button");
			
		}else{
			
			takeScreenShot("Error", "FailedTests", "Login");
		}
    }
    
    
		/*public WebElement Fluentwait(final String xpathkey){
			
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(15, TimeUnit.SECONDS)
					
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			WebElement foo= wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver){
				return driver.findElement(By.xpath(props.getProperty(xpathkey)));
			}
			});
			return  foo;
		};*/
	
public String[][] getExcelData(String xlPath, String shtName, String tbName, String tbName1) throws Exception{
	    
	    String[][] tabArray=null;
	    
	    Workbook workbk = Workbook.getWorkbook(new File(xlPath));
	    Sheet sht = workbk.getSheet(shtName);
	    
	    int sRow,sCol, eRow, eCol,ci,cj;
	    
	    Cell tableStart=sht.findCell(tbName);
	    sRow=tableStart.getRow();
	    sCol=tableStart.getColumn();
	    
	    Cell tableEnd= sht.findCell(tbName1);
	    eRow=tableEnd.getRow();
	    eCol=tableEnd.getColumn();
	    
	    System.out.println("startRow="+sRow+", endRow="+eRow+", " + "startCol="+sCol+", endCol="+eCol);
	    
	    tabArray=new String[eRow-sRow-1][eCol-sCol-1];
	    ci=0;
	    for (int i=sRow+1;i<eRow;i++,ci++){
	      cj=0;
	      /*System.out.println("Row"+i);
	      System.out.println("Column"+sCol);*/
	      for (int j=sCol+1;j<eCol;j++,cj++){
	    	  /*System.out.println("Row1"+i);
	    	  System.out.println("Column1"+j);*/
	    	  tabArray[ci][cj]=sht.getCell(j,i).getContents();
	      }
	    }
	    return(tabArray);
	  }
}


