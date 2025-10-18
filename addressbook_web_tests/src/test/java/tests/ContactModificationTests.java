package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactModificationTests extends TestBase{

  @Test
  void canModifyContact(){
    if (app.contacts().getCount() == 0){
      app.contacts().createContact(new ContactData("", "Юлия", "Калашникова", "ул.Ленина 1", "8888888888","test@gmail.com"));
    }
    var oldContacts = app.contacts().getList();
    var rnd = new Random();
    var index = rnd.nextInt(oldContacts.size());
    var testData = new ContactData().withFirstName("Катя");
    app.contacts().modifiyContact(oldContacts.get(index), testData);
    var newContacts = app.contacts().getList();
    var expectedList = new ArrayList<>(oldContacts);//копируем старый список
    expectedList.set(index, testData.withId(oldContacts.get(index).id())); //заменяем в старом списке один объект
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts,expectedList);
  }

}
