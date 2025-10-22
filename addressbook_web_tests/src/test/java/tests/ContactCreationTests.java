package tests;

import common.CommonFunctions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ContactCreationTests extends TestBase{

  public static List<ContactData> contactProvider() {
    var result = new ArrayList<ContactData>();
    for (var firstname : List.of("", "Андрей")) {
      for (var lastname : List.of("", "Сидоров")) {
        for (var address : List.of("", "ул. Авроры 121")) {
              result.add(new ContactData()
                  .withLastName(lastname)
                  .withFirstName(firstname)
                  .withAddress(address)
                  .withPhoto(randomFile("src/test/resources/images"))
              );
            }
          }
    }
    for (int i = 0; i < 5; i++){
      result.add(new ContactData()
          .withFirstName(CommonFunctions.randomString(i * 10))
          .withLastName(CommonFunctions.randomString(i * 10))
          .withAddress(CommonFunctions.randomString(i * 10))
          .withPhoto(randomFile("src/test/resources/images"))
      );
    }
    return result;
  }

  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateContact(ContactData contact) {
    var oldContacts = app.contacts().getList();
    app.contacts().createContact(contact);
    var newContacts = app.contacts().getList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newContacts.sort(compareById);
    var expectedList = new ArrayList<>(oldContacts);//копируем старый список
    expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()).withAddress("").withPhoto(""));
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, expectedList);
  }
}


