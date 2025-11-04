package tests;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.ContactData;
import model.GroupData;
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


  @Test
  void canRemoveContactFromGroup() {
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var groups = app.hbm().getGroupList();
    var group = groups.get(0);
    var contacts = app.hbm().getContactList();
    if (app.hbm().getContactsInGroup(group).isEmpty()) {
      app.contacts().createContact(contacts.get(0), group);
    }
    var oldRelated = app.hbm().getContactsInGroup(group);
    var contactToRemove = oldRelated.get(0);
    app.contacts().removeContactFromGroup(contactToRemove, group);
    var newRelated = app.hbm().getContactsInGroup(group);
    var expectedList = new ArrayList<ContactData>(newRelated);
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newRelated.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(expectedList, newRelated);
  }
}
