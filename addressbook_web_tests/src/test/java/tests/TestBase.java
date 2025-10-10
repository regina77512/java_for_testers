package tests;

import manager.ApplicationManager;
import model.ContactData;
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

  protected void createContact(ContactData contact) {
    driver.findElement(By.name("firstname")).click();
    driver.findElement(By.name("firstname")).sendKeys(contact.firstName());
    driver.findElement(By.name("lastname")).click();
    driver.findElement(By.name("lastname")).sendKeys(contact.lastName());
    driver.findElement(By.name("address")).click();
    driver.findElement(By.name("address")).sendKeys(contact.address());
    driver.findElement(By.name("mobile")).click();
    driver.findElement(By.name("mobile")).sendKeys(contact.mobile());
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).sendKeys(contact.email());
    driver.findElement(By.xpath("(//input[@name=\'submit\'])[2]")).click();
    driver.findElement(By.linkText("home page")).click();
  }

  protected void openContactPage() {
    if (!isElementPresent(By.xpath("//h1[contains(text(),'Edit / add address book entry')]"))) {
      driver.findElement(By.linkText("add new")).click();
    }
  }
}
