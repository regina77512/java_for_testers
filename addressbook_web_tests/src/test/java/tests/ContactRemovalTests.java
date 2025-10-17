package tests;

import java.util.ArrayList;
import java.util.Random;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

  @Test
  void canRemoveContact(){
    if (app.contacts().getCount() == 0){
      app.contacts().createContact(new ContactData("", "Сергей", "Колодежнов", "ул.Тухачевского 7", "112122","еук@mail.ru"));
    }
    var oldContacts = app.contacts().getList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    app.contacts().removeContact(oldContacts.get(index));
    var newContacts = app.contacts().getList();
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.remove(index);
    Assertions.assertEquals(newContacts,expectedList);
  }
}
