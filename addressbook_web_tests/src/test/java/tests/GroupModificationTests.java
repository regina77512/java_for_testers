package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase{

  @Test
  void canModifyGroup(){
    if (app.hbm().getGroupCount() == 0) {
      app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var oldGroups = app.hbm().getGroupList();//загружается список групп из базы
    var rnd = new Random();
    var index = rnd.nextInt(oldGroups.size());
    var testData = new GroupData().withName("modified name");
    app.groups().modifyGroup(oldGroups.get(index), testData);
    // вызов метода, который модифицирует группу
    // метод modifyGroup, у которого в качестве параметра передается объект типа GroupData, который
    // содержит новый набор свойств
    var newGroups = app.hbm().getGroupList();//после модификации загружается новый список групп
    var expectedList = new ArrayList<>(oldGroups);//строится ожидаемое значение, путем копирования
    expectedList.set(index, testData.withId(oldGroups.get(index).id()));//старого списка и замены в нем одного объекта
    Comparator<GroupData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newGroups.sort(compareById);
    expectedList.sort(compareById);
    Assertions.assertEquals(newGroups, expectedList);
  }

}
