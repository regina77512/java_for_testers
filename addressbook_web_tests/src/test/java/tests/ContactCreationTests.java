package tests;

import java.util.ArrayList;
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
          for (var mobile : List.of("", "1234567890")) {
            for (var email : List.of("", "test@mail.ru")) {
              result.add(new ContactData(firstname, lastname, address, mobile, email));
            }
          }
        }
      }
    }
    for (int i = 0; i < 5; i++){
      result.add(new ContactData(randomString(i * 10), randomString(i * 10), randomString(i * 10), randomString(i * 10),randomString(i * 10)));
    }
    return result;
  }

  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateContact(ContactData contact) {
    int contactCount = app.contacts().getCount();
    app.contacts().createContact(contact);
    int newContactCount = app.contacts().getCount();
    Assertions.assertEquals(contactCount + 1, newContactCount);
  }
}


