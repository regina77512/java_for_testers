package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

  @Test
  void canRemoveContact(){
    if (app.contacts().getCount() == 0){
      app.contacts().createContact(new ContactData("Сергей", "Колодежнов", "ул.Тухачевского 7", "112122","еук@mail.ru"));
    }
    int contactCount = app.contacts().getCount();
    app.contacts().removeContact();
    int newContactCount = app.contacts().getCount();
    Assertions.assertEquals(contactCount - 1, newContactCount);
  }
}
