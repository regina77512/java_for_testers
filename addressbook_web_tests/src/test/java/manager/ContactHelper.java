package manager;

import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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

  public void createContact(ContactData contact, GroupData group) {
    openContactAddPage();
    fillContactForm(contact);
    selectGroup(group);
    submitContactCreation();
    returnHomePage();
  }

  private void selectGroup(GroupData group) {
    new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
  }

  @Step
  public void removeContact(ContactData contact) {
    openContactsPage();
    selectContact(contact);
    removeSelectedContact();
    returnHomePage();
  }

public void removeContactFromGroup(ContactData contact, GroupData group) {
    openContactsPage();
    selectGroupInContactPage(group);
    selectContact(contact);
    removeSelectedContactFromGroup();
    //returnHomePage();
  }

  public void addContactToGroup(ContactData contact, GroupData group) {
    openContactsPage();
    selectContact(contact);
    selectGroupToAddContact(group);
    addContactInGroup();
  }

  private void addContactInGroup() {
    click(By.name("add"));
  }

  private void selectGroupToAddContact(GroupData group) {
    new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
  }

  private void selectGroupInContactPage(GroupData group) {
      //click(By.name("group"));
      new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
  }

  private void removeSelectedContactFromGroup() {
    click(By.name("remove"));
  }

  private void submitContactCreation() {
    click(By.xpath("(//input[@name=\'submit\'])[2]"));
  }

  private void fillContactForm(ContactData contact) {
    type(By.name("firstname"), contact.firstName());
    type(By.name("lastname"), contact.lastName());
    type(By.name("address"), contact.address());
    //attach(By.name("photo"), contact.photo());
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

  public String getPhones(ContactData contact) {
    return manager.driver.findElement(By.xpath(
        String.format("//input[@id='%s']/../../td[6]", contact.id()))).getText();
  }

  public Map<String, String> getPhones() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id");
      var phones = row.findElements(By.tagName("td")).get(5).getText();
      result.put(id, phones);
    }
    return result;
  }

  public String getAddress(ContactData contact) {
    return manager.driver.findElement(By.xpath(
        String.format("//input[@id='%s']/../../td[4]", contact.id()))).getText();
  }

  public Map<String, String> getAddress() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id");
      var addresses = row.findElements(By.tagName("td")).get(3).getText();
      result.put(id, addresses);
    }
    return result;
  }

  public String getEmail(ContactData contact) {
    return manager.driver.findElement(By.xpath(
        String.format("//input[@id='%s']/../../td[5]", contact.id()))).getText();
  }

  public Map<String, String> getEmail() {
    var result = new HashMap<String, String>();
    List<WebElement> rows = manager.driver.findElements(By.name("entry"));
    for (WebElement row : rows) {
      var id = row.findElement(By.tagName("input")).getAttribute("id");
      var emails = row.findElements(By.tagName("td")).get(4).getText();
      result.put(id, emails);
    }
    return result;
  }
}
