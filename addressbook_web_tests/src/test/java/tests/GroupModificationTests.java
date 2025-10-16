package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase{

  @Test
  void canModifyGroup(){
    if (app.groups().getCount() == 0){          //если нет ни одной группы, создаем новую
      app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
    }
    app.groups().modifyGroup(new GroupData().withName("modified name"));      // вызов метода, который модифицирует группу
    //метод modifyGroup, у которого в качестве параметра передается объект типа GroupData, который содержит новый набор свойств
  }

}
