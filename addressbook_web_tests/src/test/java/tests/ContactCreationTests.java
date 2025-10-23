package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ContactCreationTests extends TestBase{

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
    var value = mapper.readValue(new File("contacts.json"), new TypeReference<List<ContactData>>() {});
    result.addAll(value);
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


