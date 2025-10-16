package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

  public ContactHelper(ApplicationManager manager) {
    super(manager);
  }

  public void createContact(ContactData contact) {
    openContactAddPage();
    fillContactForm(contact);
    submitContactCreation();
    returnHomePage();
  }

  public void removeContact() {
    openContactsPage();
    selectContact();
    removeSelectedContact();
    returnHomePage();
  }

  private void submitContactCreation() {
    click(By.xpath("(//input[@name=\'submit\'])[2]"));
  }

  private void fillContactForm(ContactData contact) {
    type(By.name("firstname"), contact.firstName());
    type(By.name("lastname"), contact.lastName());
    type(By.name("address"), contact.address());
    type(By.name("mobile"), contact.mobile());
    type(By.name("email"), contact.email());
  }

  public void openContactAddPage() {
    if (!manager.isElementPresent(
        By.xpath("//h1[contains(text(),'Edit / add address book entry')]"))) {
      click(By.linkText("add new"));
    }
  }

  public boolean isContactPresent() {
    openContactsPage();
    return manager.isElementPresent(By.name("selected[]"));
  }

  private void openContactsPage() {
    if (!manager.isElementPresent(By.xpath("//label[contains(text(), 'Number of results:')]"))) {
      click(By.linkText("home"));
    }
  }

  private void returnHomePage() {
    click(By.linkText("home page"));
  }

  private void removeSelectedContact() {
    click(By.name("delete"));
  }

  private void selectContact() {
    click(By.name("selected[]"));
  }

  public int getCount() {
    openContactsPage();
    return manager.driver.findElements(By.name("selected[]")).size();
  }
}
