package simple.ExtentReportTest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Driver 
{

	
	static ExtentReports extent=null;
	static ExtentTest test = null;
	static WebDriver driver=null;
	
	
	@Parameters({"String"})
	@Test()
	public String sample(String str) throws IOException, InterruptedException
	{
		test=extent.startTest("Sample test started");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
		
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.google.com");
		//Thread.sleep(5000);
		//driver.close();
		System.out.println(driver.getTitle());
		if(driver.getTitle()!=driver.getTitle())
		{
			System.out.println(driver.getTitle());
			writeResult(test, "pass", true);
			return "pass";
		}
		else {
			return null;
		}
		
	}
	
	
	@BeforeSuite
	public ExtentReports startreport()
	{
		extent=new ExtentReports(System.getProperty("user.dir")+"\\Results\\Report.html",true);
		File f=new File(System.getProperty("user.dir")+"\\extent-config.xml");
		
		extent.addSystemInfo("Host Name", System.getProperty("user.name"));
		extent.addSystemInfo("OS", System.getProperty("os.name"));
		extent.addSystemInfo("OS version", System.getProperty("os.version"));
		extent.addSystemInfo("Environment", "QA");
		
		extent.loadConfig(f);
		
		
		return extent;
		
		
		
	}
	
	@AfterSuite
	public void endReport()
	{
		extent.endTest(test);
		extent.flush();
	}
	
	public void writeResult(ExtentTest test, String status,boolean screenshotrequired) throws IOException
	{
		if(screenshotrequired)
		{
			switch(status.toLowerCase())
			{
			case "pass":
			test.log(LogStatus.PASS	, "The method is passed"+test.addScreenCapture(captureScreenshot(driver)));
			break;
			case "fail":
			test.log(LogStatus.FAIL, "The method got failed"+test.addScreenCapture(captureScreenshot(driver)));
			break;
			default :
			break;
			}
		
		}else
		{
			switch(status.toLowerCase())
			{
			case "pass":
			test.log(LogStatus.PASS	, "The method is passed");
			break;
			case "fail":
			test.log(LogStatus.FAIL, "The method got failed");
			break;
			default :
			break;
			}
		}
	}
		
	
	public String captureScreenshot(WebDriver driver) throws IOException
	{
		File fobj=null;
		String screenshotLoc=System.getProperty("user.dir")+"\\Results\\Screenshots\\sample.png";
		TakesScreenshot ts=(TakesScreenshot) driver;
		fobj=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(fobj, new File(screenshotLoc));
		return screenshotLoc;
		
	}
}
