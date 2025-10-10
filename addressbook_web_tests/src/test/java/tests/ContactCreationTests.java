package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase{

  @Test
  public void canCreateContact() {
    openContactPage();
    createContact(new ContactData("Петр", "Самойлов", "ул. Партизанская 1", "12345", "test@ya.ru"));
  }

  @Test
  public void canCreateContactWithoutAddressAndEmail() {
    openContactPage();
    createContact(new ContactData().withoutAddressAndEmail("Андрей", "Дьяченко", "12345678"));
  }

}


