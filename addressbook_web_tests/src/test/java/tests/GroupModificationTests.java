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
    if (app.groups().getCount() == 0){          //если нет ни одной группы, создаем новую
      app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    var oldGroups = app.groups().getList();//загружается список групп из веб-приложения
    var rnd = new Random();
    var index = rnd.nextInt(oldGroups.size());
    var testData = new GroupData().withName("modified name");
    app.groups().modifyGroup(oldGroups.get(index), testData);
    // вызов метода, который модифицирует группу
    // метод modifyGroup, у которого в качестве параметра передается объект типа GroupData, который
    // содержит новый набор свойств
    var newGroups = app.groups().getList();//после модификации загружается новый список групп
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
