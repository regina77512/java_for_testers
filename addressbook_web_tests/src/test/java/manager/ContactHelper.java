package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper {

  private final ApplicationManager manager;

  public ContactHelper(ApplicationManager manager) {
    this.manager = manager;
  }

  public void createContact(ContactData contact) {
    openContactAddPage();
    manager.driver.findElement(By.name("firstname")).click();
    manager.driver.findElement(By.name("firstname")).sendKeys(contact.firstName());
    manager.driver.findElement(By.name("lastname")).click();
    manager.driver.findElement(By.name("lastname")).sendKeys(contact.lastName());
    manager.driver.findElement(By.name("address")).click();
    manager.driver.findElement(By.name("address")).sendKeys(contact.address());
    manager.driver.findElement(By.name("mobile")).click();
    manager.driver.findElement(By.name("mobile")).sendKeys(contact.mobile());
    manager.driver.findElement(By.name("email")).click();
    manager.driver.findElement(By.name("email")).sendKeys(contact.email());
    manager.driver.findElement(By.xpath("(//input[@name=\'submit\'])[2]")).click();
    returnHomePage();
  }

  public void openContactAddPage() {
    if (!manager.isElementPresent(
        By.xpath("//h1[contains(text(),'Edit / add address book entry')]"))) {
      manager.driver.findElement(By.linkText("add new")).click();
    }
  }

  public boolean isContactPresent() {
    openContactsPage();
    return manager.isElementPresent(By.name("selected[]"));
  }

  private void openContactsPage() {
    if (!manager.isElementPresent(By.xpath("//label[contains(text(), 'Number of results:')]"))) {
      manager.driver.findElement(By.linkText("home")).click();
    }
  }

  public void removeContact() {
    openContactsPage();
    selectContact();
    removeSelectedContact();
    returnHomePage();
  }

  private void returnHomePage() {
    manager.driver.findElement(By.linkText("home page")).click();
  }

  private void removeSelectedContact() {
    manager.driver.findElement(By.name("delete")).click();
  }

  private void selectContact() {
    manager.driver.findElement(By.name("selected[]")).click();
  }
}
