package manager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ContactData;
import model.GroupData;

public class JdbcHelper extends HelperBase {

  public JdbcHelper(ApplicationManager manager) {
    super(manager);
  }

  public List<GroupData> getGroupList() {
    var groups = new ArrayList<GroupData>();
    try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
        var statement = conn.createStatement();
        var result = statement.executeQuery(
            "SELECT group_id, group_name, group_header, group_footer FROM group_list")) {
      while (result.next()) {  //пока результаты не кончились выполняется код ниже
        groups.add(new GroupData() //формируется объект и добавляется в список
            .withId(result.getString(
                "group_id"))//по названию столбца из запроса получаем значение для этого столбца в соответ-щей строке рез-тов
            .withName(result.getString("group_name"))
            .withHeader(result.getString("group_header"))
            .withFooter(result.getString("group_footer")));

      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return groups;
  }

  public List<ContactData> getContactList() {
    var contacts = new ArrayList<ContactData>();
    try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
        var statement = conn.createStatement();
        var result = statement.executeQuery(
            "SELECT id, firstname, lastname, address FROM addressbook"))
    {
      while (result.next()) {
        contacts.add(new ContactData()
            .withId(result.getString("id"))
            .withFirstName(result.getString("firstname"))
            .withLastName(result.getString("lastname"))
            .withAddress(result.getString("address")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return contacts;
  }
}
