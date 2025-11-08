package tests;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactInfoTests extends TestBase {

  @Test
  void testPhones() {
    var contacts = app.hbm().getContactList();
    var contact = contacts.get(0);
    var phones = app.contacts().getPhones(contact);
    var expected = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
        .filter(s -> s != null && ! "".equals(s)) // пропускаются все пустые строки
        .collect(Collectors.joining("\n"));
    Assertions.assertEquals(expected, phones);
  }
}
