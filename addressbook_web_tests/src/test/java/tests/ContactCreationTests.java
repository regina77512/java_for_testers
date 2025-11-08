package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ContactCreationTests extends TestBase {

  public static List<ContactData> contactProvider() throws IOException {
    var result = new ArrayList<ContactData>();
//    for (var firstname : List.of("", "Андрей")) {
//      for (var lastname : List.of("", "Сидоров")) {
//        for (var address : List.of("", "ул. Авроры 121")) {
//              result.add(new ContactData()
//                  .withLastName(lastname)
//                  .withFirstName(firstname)
//                  .withAddress(address)
//                  .withPhoto(CommonFunctions.randomFile("src/test/resources/images"))
//              );
//            }
//          }
//    }
    ObjectMapper mapper = new ObjectMapper();
    var value = mapper.readValue(new File("contacts.json"), new TypeReference<List<ContactData>>() {
    });
    result.addAll(value);
    return result;
  }

  public static List<ContactData> singleRandomContact() {
    return List.of(new ContactData()
        .withLastName(CommonFunctions.randomString(10))
        .withFirstName(CommonFunctions.randomString(10))
        .withAddress(CommonFunctions.randomString(10)));
    //.withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
  }

  @ParameterizedTest
  @MethodSource("singleRandomContact")
  public void canCreateContact(ContactData contact) {
    var oldContacts = app.hbm().getContactList();
    app.contacts().createContact(contact);
    var newContacts = app.hbm().getContactList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    var maxId = newContacts.get(newContacts.size() - 1).id();

    var expectedList = new ArrayList<>(oldContacts);//копируем старый список
    expectedList.add(contact.withId(maxId));
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, expectedList);
  }

  @Test
  void canCreateContactInGroup() {
    var contact = new ContactData()
        .withLastName(CommonFunctions.randomString(10))
        .withFirstName(CommonFunctions.randomString(10))
        .withAddress(CommonFunctions.randomString(10));
    //  .withPhoto(CommonFunctions.randomFile("src/test/resources/images"));
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var group = app.hbm().getGroupList();
    var rndGroup = new Random();
    var indexGroup = rndGroup.nextInt(group.size());
    var oldRelated = app.hbm().getContactsInGroup(group.get(indexGroup));
    app.contacts().createContact(contact, group.get(indexGroup));
    var newRelated = app.hbm().getContactsInGroup(group.get(indexGroup));
    var maxId = newRelated.get(newRelated.size() - 1).id();
    var expectedList = new ArrayList<ContactData>(oldRelated);
    expectedList.add(contact.withId(maxId));
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newRelated.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(newRelated, expectedList);
  }

  @Test
  void canAddContactToGroup() {
    if (app.hbm().getContactCount() == 0){
      app.hbm().createContact(new ContactData("", "Юлия", "Калашникова", "ул.Ленина 1", "", "", "",
          ""));
    }
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var contacts = app.hbm().getContactList();
    var groups = app.hbm().getGroupList();
    ContactData contactToAdd = null;
    GroupData groupToAdd = null;
    for (var group : groups){ // Пробегаем по группам
      var contactsInGroup = app.hbm().getContactsInGroup(group); // Получаем список контактов, которые есть в группе
      for (var contact : contacts) { // Пробегаем по контактам в группе
        if (!contactsInGroup.contains(contact)) { // Проверяем, есть ли контакт в текущей группе
          contactToAdd = contact;
          groupToAdd = group;
          break;
        }
      }
      if (contactToAdd != null) {
        break;
      }
    }
    if (contactToAdd == null) { // Если все контакты уже во всех группах, то создаем новую группу
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
      groups = app.hbm().getGroupList();
      groupToAdd = groups.get(groups.size() - 1); // Берем последнюю группу (новая)
      contactToAdd = contacts.get(0); // Берем первый контакт и добавляем его
    }

    var oldRelated = app.hbm().getContactsInGroup(groupToAdd).size();
    app.contacts().addContactToGroup(contactToAdd, groupToAdd);
    var newRelated = app.hbm().getContactsInGroup(groupToAdd).size();
    Assertions.assertEquals(oldRelated + 1, newRelated);
  }
}