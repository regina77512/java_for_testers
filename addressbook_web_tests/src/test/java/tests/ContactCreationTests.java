package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
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
          for (var mobile : List.of("", "1234567890")) {
            for (var email : List.of("", "test@mail.ru")) {
              result.add(new ContactData()//.withoutAddressAndEmail(firstname, lastname, mobile)
                  .withLastName(lastname).withFirstName(firstname).withAddress(address).withMobile(mobile).withEmail(email)
              );
            }
          }
        }
      }
    }
    for (int i = 0; i < 5; i++){
      result.add(new ContactData()
          //.withoutAddressAndEmail(randomString(i * 5), randomString(i * 5), randomString(i * 5))
          .withFirstName(randomString(i * 5)).withLastName(randomString(i * 5)).withAddress(randomString(i * 5)).withMobile(randomString(i * 5)).withEmail(randomString(i * 5))
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
    expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()).withAddress("").withMobile("").withEmail(""));
    expectedList.sort(compareById);
    Assertions.assertEquals(newContacts, expectedList);

  }
}


