package tests;

import java.util.ArrayList;
import java.util.Random;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

  @Test
  void canRemoveContact(){
    if (app.hbm().getContactCount() == 0){
      app.hbm().createContact(new ContactData("", "Сергей", "Колодежнов", "ул.Тухачевского 7"));
    }
    var oldContacts = app.hbm().getContactList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    app.contacts().removeContact(oldContacts.get(index));
    var newContacts = app.hbm().getContactList();
    var expectedList = new ArrayList<>(oldContacts);
    expectedList.remove(index);
    Assertions.assertEquals(newContacts,expectedList);
  }
}
