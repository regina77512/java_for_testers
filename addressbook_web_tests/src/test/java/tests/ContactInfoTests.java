package tests;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactInfoTests extends TestBase {

  @Test
  void testPhones() {
    if (app.hbm().getContactCount() == 0) {
      app.hbm().createContact(
          new ContactData("", "Юлия", "Калашникова", "ул.Ленина 1", "12341", "43211", "22221",
              "33331", "test_phone@mail.ru", "test_phone@gmail.com", "test_phone@ya.ru"));
    }
    var contacts = app.hbm().getContactList();
    var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
        Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
            .filter(s -> s != null && !"".equals(s)) // пропускаются все пустые строки
            .collect(Collectors.joining("\n"))
    ));
    var phones = app.contacts().getPhones();
    Assertions.assertEquals(expected, phones);
  }

  @Test
  void testAddresses() {
    if (app.hbm().getContactCount() == 0) {
      app.hbm().createContact(
          new ContactData("", "Анастасия", "Калашникова", "ул.Ленина 2", "12342", "43212", "22222",
              "33332", "test_address@mail.ru", "test_address@gmail.com", "test_address@ya.ru"));
    }
    var contacts = app.hbm().getContactList();
    var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
        Stream.of(contact.address())
            .filter(s -> s != null && !"".equals(s))
            .collect(Collectors.joining("\n"))
    ));
    var addresses = app.contacts().getAddress();
    Assertions.assertEquals(expected, addresses);
  }

  @Test
  void testEmails() {
    if (app.hbm().getContactCount() == 0) {
      app.hbm().createContact(
          new ContactData("", "Михаил", "Калашников", "ул.Ленина 3", "12343", "43213", "22223",
              "33333", "test_email@mail.ru", "test_email@gmail.com", "test_email@ya.ru"));
    }
    var contacts = app.hbm().getContactList();
    var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
        Stream.of(contact.email(), contact.email2(), contact.email3())
            .filter(s -> s != null && !"".equals(s))
            .collect(Collectors.joining("\n"))
    ));
    var emails = app.contacts().getEmail();
    Assertions.assertEquals(expected, emails);
  }
}
