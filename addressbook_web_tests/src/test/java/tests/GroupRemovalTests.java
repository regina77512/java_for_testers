package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupRemovalTests extends TestBase {

  @Test
  public void canRemoveGroup() {
    if (app.groups().getCount() == 0) {
      app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var oldGroups = app.groups().getList();//функция, которая возвращает список объектов типа GroupData
    var rnd = new Random();
    var index = rnd.nextInt(oldGroups.size());
    app.groups().removeGroup(oldGroups.get(index));
    var newGroups = app.groups().getList();
    var expectedList = new ArrayList<>(oldGroups);
    expectedList.remove(index);
    Assertions.assertEquals(newGroups, expectedList);
  }

  @Test
  void canRemoveAllGroupsAtOnce(){
    if (app.groups().getCount() == 0) {
      app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    app.groups().removeAllGroups();
    Assertions.assertEquals(0, app.groups().getCount());
  }
}
