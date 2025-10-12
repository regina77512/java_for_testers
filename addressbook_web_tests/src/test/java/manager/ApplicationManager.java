package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {

  protected WebDriver driver;

  private LoginHelper session;

  private GroupHelper groups;


  public void init(String browser) {
    if (driver == null) {
      if ("firefox".equals(browser)){
        driver = new FirefoxDriver();
      }else if ("chrome".equals(browser)){
          driver = new ChromeDriver();
        }else {
        throw new IllegalArgumentException(String.format("Unknown browser %s", browser));
      }
      Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
      driver.get("http://localhost/addressbook/");
      driver.manage().window().setSize(new Dimension(1076, 640));
      session().login("admin", "secret");
    }
  }

  public LoginHelper session() {
    if (session == null) {
      session = new LoginHelper(this);
    }
    return session;
  }

  public GroupHelper groups() {
    if (groups == null) {
      groups = new GroupHelper(this);
    }
    return groups;
  }

  public boolean isElementPresent(By locator) {
    try {
      driver.findElement(locator);
      return true;
    } catch (NoSuchElementException exception) {
      return false;
    }
  }

  public void createContact(ContactData contact) {
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

  public void openContactPage() {
    if (!isElementPresent(By.xpath("//h1[contains(text(),'Edit / add address book entry')]"))) {
      driver.findElement(By.linkText("add new")).click();
    }
  }
}
