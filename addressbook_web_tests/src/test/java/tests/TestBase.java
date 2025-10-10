package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

  protected static ApplicationManager app;


  @BeforeEach
  public void setUp() {
    if (app == null) {
      app = new ApplicationManager();
      app.init(System.getProperty("browser","firefox"));
    }

  }



  // нужно будет удалить - driver, setup,isElementPresent
  protected static WebDriver driver;

  @BeforeEach
  public void setup() {
    if (driver == null) {
      driver = new FirefoxDriver();
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      driver.get("http://localhost/addressbook/");
      driver.manage().window().setSize(new Dimension(966, 677));
      driver.findElement(By.name("user")).sendKeys("admin");
      driver.findElement(By.name("pass")).sendKeys("secret");
      driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    }
  }

  protected boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException exception) {
      return false;
    }
  }
  //

  protected void createContact(String firstName, String middleName, String lastName) {
    driver.findElement(By.name("firstname")).click();
    driver.findElement(By.name("firstname")).sendKeys(firstName);
    driver.findElement(By.name("middlename")).click();
    driver.findElement(By.name("middlename")).sendKeys(middleName);
    driver.findElement(By.name("lastname")).click();
    driver.findElement(By.name("lastname")).sendKeys(lastName);
    driver.findElement(By.name("mobile")).click();
    driver.findElement(By.name("mobile")).sendKeys("667788");
    driver.findElement(By.xpath("(//input[@name=\'submit\'])[2]")).click();
    driver.findElement(By.linkText("home page")).click();
  }

  protected void openContactPage() {
    if (!isElementPresent(By.xpath("//h1[contains(text(),'Edit / add address book entry')]"))) {
      driver.findElement(By.linkText("add new")).click();
    }
  }
}
