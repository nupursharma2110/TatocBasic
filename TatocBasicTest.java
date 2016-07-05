package com.qait.testBasicTatoc;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TatocBasic {
	WebDriver webdriver;

	@BeforeClass
	public void intatiateBrowser() {
		webdriver = new FirefoxDriver();             
		webdriver.get("http://10.0.1.86");
	}

	@Test(description= "Verify homePage title", priority=1)
	public void verifyHomepageTitle() {
		String expectedTitle= "TAP Utility Server";
		String actualTitle = webdriver.getTitle();

		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test(description= "Verify welcome Page title", priority=2)
	public void verifyCourseSelectionPageTitle() {
		webdriver.findElement(By.partialLinkText("tatoc")).click(); 
		String expectedTitle= "Welcome - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}



	//Grid Gate
	@Test(description= "Verify Grid gate title",priority=3)
	public void verifyGridGatePageTitle() {
		webdriver.findElement(By.linkText("Basic Course")).click();
		String expectedTitle= "Grid Gate - Basic Course - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

	}

	@Test(description= "Whether red box exists",priority=4)
	public void testRedButtonDisplayed(){
		Boolean expected = true;
		Boolean actual = webdriver.findElement(By.cssSelector(".redbox")).isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether green box exists",priority=5)
	public void greencolorboxdisplayed(){
		Boolean actual , expected ;
		expected = true ;
		WebElement box1 = webdriver.findElement(By.cssSelector(".greenbox"));
		actual = box1.isDisplayed();
		Assert.assertEquals(actual, expected);
		webdriver.findElement(By.className("greenbox")).click();
	}


	//Frame Dungeon
	@Test(description= "Frame Dungeon Page Displayed",priority=6)
	public void testFrameDungeonTitle(){
		String expectedTitle = "Frame Dungeon - Basic Course - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Verifying color box exists",priority=7)
	public void verifycolorboxdisplayed(){

		Boolean actual , expected ;
		expected = true ;
		webdriver.switchTo().frame("main");
		WebElement box1 = webdriver.findElement(By.cssSelector("#answer"));
		actual = box1.isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether clicking repaint box2 actully repaints the box",priority=8)
	public void testBox2RepaintWorks(){
		webdriver.switchTo().parentFrame();
		webdriver.switchTo().frame("main");
		webdriver.switchTo().frame("child");
		WebElement box2= webdriver.findElement(By.cssSelector("#answer"));
		String color1 = box2.getAttribute("class");
		webdriver.switchTo().parentFrame();
		webdriver.findElement(By.partialLinkText("Repaint Box 2")).click();
		webdriver.switchTo().frame("child");
		box2 = webdriver.findElement(By.cssSelector("#answer"));
		String color2 =  box2.getAttribute("class");
		webdriver.switchTo().parentFrame();
		Assert.assertNotEquals(color1, color2, "repaint works");
	}

	@Test(description= "Proceeds to next Page when the colors are same",priority=9)
	public void testDragAroundTitle() {
		String color1, color2;
		boolean color=true;
		do{
			color1 = webdriver.findElement(By.cssSelector("#answer")).getAttribute("class");
			webdriver.switchTo().frame("child");
			color2 = webdriver.findElement(By.cssSelector("#answer")).getAttribute("class");
			if(!(color1.equals(color2))) {
				webdriver.switchTo().parentFrame();
				webdriver.findElement(By.partialLinkText("Repaint Box 2")).click();
			}
			else {
				webdriver.switchTo().parentFrame();
				break;
			}

		} while(color);
		Assert.assertTrue(color1.equals(color2));
		webdriver.findElement(By.partialLinkText("Proceed")).click();
	}


	//Drag Around
	@Test(description= "Whether moves to Drag Around page",priority=10)
	public void verifyDragAroundPage() {
		String expectedTitle= "Drag - Basic Course - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Whether Drag box displayed",priority=11)
	public void verifyDragBoxdisplayed(){

		Boolean actual , expected ;
		expected = true ;
		WebElement dragbox = webdriver.findElement(By.cssSelector("#dragbox"));
		actual = dragbox.isDisplayed();
		Assert.assertEquals(actual, expected);
	}

	@Test(description= "Whether Drop Box displayed",priority=12)
	public void verifydropboxdisplayed(){ 
		Boolean actual , expected ;
		expected = true ;
		WebElement dropbox = webdriver.findElement(By.cssSelector("#dropbox"));
		actual = dropbox.isDisplayed();
		Assert.assertEquals(actual, expected);

	}

	@Test(description= "Whether Drag box lies within drop box",priority=13)
	public void verifyDragging()
	{
		Actions action = new Actions(webdriver);
		WebElement From = webdriver.findElement(By.id("dragbox"));
		WebElement To = webdriver.findElement(By.id("dropbox"));
		action.dragAndDrop(From, To).build().perform();

		Point drag= From.getLocation();
		int xdragbox = drag.getX();
		int ydragbox = drag.getY();

		Point drop= To.getLocation();
		int xdropbox = drop.getX();
		int ydropbox = drop.getY();

		String cond = null ; 
		if (ydragbox >= ydropbox && xdragbox >= xdropbox)
			cond = "true" ; 

		Assert.assertEquals(cond,"true");

		webdriver.findElement(By.partialLinkText("Proceed")).click();
	}


	//Popup - Basic Course - T.A.T.O.C
	@Test(description= "Whether Proceeded to Windows Page",priority=14)
	public void popupWindowsPage() {
		String expectedTitle= "Windows - Basic Course - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}


	@Test(description= "Whether moves to Popup Window, text box is there and text value is set ",priority=15)
	public void verifyPopUpWindow() {
		webdriver.findElement(By.xpath("//div[@class='page']/a[text()='Launch Popup Window']")).click();
		String expectedTitle= "Popup - Basic Course - T.A.T.O.C";


		String handle = webdriver.getWindowHandle();
		for(String webhand: webdriver.getWindowHandles()) {
			webdriver.switchTo().window(webhand);
		}
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);

		WebElement  textbox = webdriver.findElement(By.cssSelector("#name"));
		Boolean actual = textbox.isDisplayed();
		Boolean expected = true ; 
		Assert.assertEquals(actual, expected);

		webdriver.findElement(By.cssSelector("#name")).sendKeys("your name");
		webdriver.findElement(By.cssSelector("#submit")).click();

		webdriver.switchTo().window(handle);
		String name;
		if (webdriver instanceof JavascriptExecutor) {
			name= (String)((JavascriptExecutor)webdriver).executeScript("return name");
		} else {
			throw new IllegalStateException("This driver does not support JavaScript!");
		}
		Assert.assertEquals(name, "your name");

		webdriver.findElement(By.linkText("Proceed")).click();
	}

	//Cookie Handling
	@Test(description= "Whether Proceeded to Cookie Handling Page",priority=16)
	public void CookieHandlingPage() {
		String expectedTitle= "Cookie Handling - Basic Course - T.A.T.O.C";
		String actualTitle = webdriver.getTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(description= "Whether cookie is set and Proceed",priority=17) 
	public void verifyCookieSet(){
		webdriver.findElement(By.partialLinkText("Generate Token")).click();
		String token=webdriver.findElement(By.cssSelector("#token")).getText();
		String[] tokenarr = token.split(": ");
		Cookie cookie = new Cookie("Token",tokenarr[1]);
		String expectedCookieValue="Token="+tokenarr[1];
		webdriver.manage().addCookie(cookie);

		String actualCookieValue= (String)((JavascriptExecutor)webdriver).executeScript("return document.cookie");
		Assert.assertEquals(actualCookieValue, expectedCookieValue);
		webdriver.findElement(By.partialLinkText("Proceed")).click();
	}


	@AfterTest
	public void closing(){
		//webdriver.quit();
	}

}
