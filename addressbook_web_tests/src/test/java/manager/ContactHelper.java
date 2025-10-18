package manager;

import java.util.ArrayList;
import java.util.List;
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

  public void removeContact(ContactData contact) {
    openContactsPage();
    selectContact(contact);
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

  private void selectContact(ContactData contact) {
    click(By.cssSelector(String.format("input[value='%s']", contact.id())));
  }

  public int getCount() {
    openContactsPage();
    return manager.driver.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> getList() {
    openContactsPage();
    var contacts = new ArrayList<ContactData>();
    var trs = manager.driver.findElements(By.cssSelector("tr[name='entry']"));
    for (var tr : trs) {
      var checkbox = tr.findElement(By.name("selected[]"));
      var id = checkbox.getAttribute("value");
      var lastName = tr.findElement(By.cssSelector("td:nth-child(2)")).getText();
      var firstName = tr.findElement(By.cssSelector("td:nth-child(3)")).getText();
      contacts.add(new ContactData().withId(id).withLastName(lastName).withFirstName(firstName));
    }
    return contacts;
  }

  public void modifiyContact(ContactData contact, ContactData modifiedContact) {
    openContactsPage();
    selectContact(contact);
    initContactModification(contact);
    fillContactForm(modifiedContact);
    submitContactModification();
    returnHomePage();
  }

  private void submitContactModification() {
    click(By.name("update"));
  }

  private void initContactModification(ContactData contact) {
    click(By.cssSelector(String.format("a[href*='edit.php?id=%s'] img", contact.id())));
  }
}
