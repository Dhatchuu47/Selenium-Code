package Source_code;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage {
	WebDriver driver;
	Actions action;
	ExtentReports extent;
	
	
	@DataProvider(name = "data-provider")
	public Object[][] passdate() {
		
		ExtentReports extent= ExtentReports.get(LoginPage.class);
		extent.init("E:\\Workplace\\Maveric_Project\\Extent_log\\Demo2.html", true);

		// Start Test
		extent.startTest("Verify Login Page");
	
		Configfile config = new Configfile("E:\\Workplace\\Maveric_Project\\Data_sheet\\DataDrievnexceel.xlsx");
		extent.log(LogStatus.PASS, "Title verified");
		
		
		int row = config.getrow(0);

		Object[][] data = new Object[row][2];

		for (int i = 0; i < row; i++)
		{
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);

		}

		return data;
	   }

		@Test (priority=1, dataProvider = "data-provider") 	
		public void Loginmethod(String username, String password) throws InterruptedException
		{
			
			ExtentReports.get(LoginPage.class);
			System.setProperty("webdriver.firefox.marionette",
					"C:\\Users\\dhatchanamoorthy.g\\Downloads\\geckodriver-v0.9.0-win64.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get("https://www.icicibank.com/#");
			Utility.captureScreenshot(driver, "ICIC_opened");
			
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(By.xpath("//a[@class = 'btn inline-btn']"));
			action.moveToElement(we).moveToElement(driver.findElement(By.xpath(".//*[@id='IBLogin-child']/ul[1]/li[1]/a"))).click().build().perform();
			
			WebDriverWait wait = new WebDriverWait(driver,50);
        	WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Continue to Login')]")));
			
			driver.findElement(By.xpath(".//*[@id='main']/div[2]/div/div/div[1]/div/div[4]/a")).click();
			

			driver.findElement(By.xpath(".//*[@id='AuthenticationFG.USER_PRINCIPAL']")).sendKeys(username);
			driver.findElement(By.xpath(".//*[@id='AuthenticationFG.ACCESS_CODE']")).sendKeys(password);
			driver.findElement(By.xpath(".//*[@id='VALIDATE_CREDENTIALS1']")).click();
			Utility.captureScreenshot(driver, "ICIC_home");
		 }
		 	
		
			
			@Test (priority=2)		
     	    public void View_Billers() throws InterruptedException
			{
			
			Thread.sleep(5000);
			WebElement drop = driver.findElement(By.xpath(".//*[@id='PAYMENTS__TRANSFER']"));
			action.moveToElement(drop).moveToElement(driver.findElement(By.xpath(".//*[@id='Manage-Billers']"))).click().build().perform();
			driver.findElement(By.xpath(".//*[@id='Manage-Billers_My-Registered-Billers']")).click();
 		    extent.log(LogStatus.INFO, "ICICI Site is displayed successfully");
 		   
			List<WebElement> Table = driver.findElements(By.xpath(".//*[@id='ListOfBillerRetrieved']"));
			Utility.captureScreenshot(driver, "List_Bill");
			int total_size = Table.size();
			System.out.println("Please list before loop"+total_size);
			
			
			
			   for(int i =1;i<total_size;i++)
			   {
				   //Table.get(i).getTagName();
				   System.out.println("Please list"+total_size);
				   extent.log(LogStatus.INFO, "Total beneficiary list successfully");
			   }
			   
			   if(Table.get(total_size).getAttribute(".//*[@id='0']/td[1]'] ").equalsIgnoreCase(""))
				   
			   {
				   String first_beneficiary=Table.get(total_size).getText();
				   System.out.println("Check the first beneficiary name"+first_beneficiary);
				   Assert.assertEquals(first_beneficiary, "DHATCCREDIT" );
			   }
			   else if (Table.get(total_size).getAttribute(".//*[@id='0']/td[2]'] ").equalsIgnoreCase(""))
			   {

				   String second_beneficiary=Table.get(total_size).getText();
				   System.out.println("Check the first beneficiary name"+second_beneficiary);
				   Assert.assertEquals(second_beneficiary, "CASHIN" );
			   }
			   else if (Table.get(total_size).getAttribute(".//*[@id='0']/td[3]'] ").equalsIgnoreCase(""))
			   {

				   String thrid_beneficiary=Table.get(total_size).getText();
				   System.out.println("Check the first beneficiary name"+thrid_beneficiary);
				   Assert.assertEquals(thrid_beneficiary, "GOVINDAN" );
			   }
	 	}
			
			@Test (priority=3)		
        	public void Add_Billers() throws Exception{
				
			WebElement drop = driver.findElement(By.xpath(".//*[@id='PAYMENTS__TRANSFER']"));
			action.moveToElement(drop).moveToElement(driver.findElement(By.xpath(".//*[@id='Manage-Payees']"))).click().build().perform();
			driver.findElement(By.xpath(" .//*[@id='ADD_ICICI_BANK_PAYEE']")).click();
			extent.log(LogStatus.INFO, "User ready to add beneficiary");
			   
			driver.findElement(By.xpath(".//*[@id='CounterPartyCRUDFG.BNF_NAME']")).sendKeys("Dhoni");
			driver.findElement(By.xpath(".//*[@id='CounterPartyCRUDFG.BNF_NICK_NAME']")).sendKeys("Dhoni_bhai");
			driver.findElement(By.xpath(".//*[@id='CounterPartyCRUDFG.BNF_ACCT_NUMBER']")).sendKeys("12346789");
			driver.findElement(By.xpath(".//*[@id='CounterPartyCRUDFG.RE_ACCOUNT_NUMBER']")).sendKeys("123456789");
			driver.findElement(By.xpath(".//*[@id='CONTINUE']")).click();
			
			Thread.sleep(3000);
			extent.log(LogStatus.INFO, "Beneficiary added successfully");

		}
	
	
		@AfterMethod
	    	public void tearDown() throws Exception {
			ExtentReports extent= ExtentReports.get(LoginPage.class);
			Thread.sleep(5000);
			
	     	extent.log(LogStatus.INFO, "Browser closed");
			// close report
			extent.endTest();
			driver.quit();

		}
	
}
