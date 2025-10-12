package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

  @Test
  void canRemoveContact(){
    if (!app.contacts().isContactPresent()){
      app.contacts().createContact(new ContactData("Сергей", "Колодежнов", "ул.Тухачевского 7", "112122","еук@mail.ru"));
    }
    app.contacts().removeContact();
  }
}
