package tests;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactInfoTests extends TestBase {

  @Test
  void testPhones() {
    var contacts = app.hbm().getContactList();
    var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
        Stream.of(contact.home(), contact.mobile(), contact.work(), contact.secondary())
            .filter(s -> s != null && ! "".equals(s)) // пропускаются все пустые строки
            .collect(Collectors.joining("\n"))
        ));
    var phones = app.contacts().getPhones();
    Assertions.assertEquals(expected, phones);
    }
}
