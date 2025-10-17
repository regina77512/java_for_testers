package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GroupCreationTests extends TestBase {

  public static List<GroupData> groupProvider() {
    var result = new ArrayList<GroupData>();
    for (var name : List.of("", "group name")) {
      for (var header : List.of("", "group header")) {
        for (var footer : List.of("", "group footer")) {
          result.add(new GroupData()
              .withName(name)
              .withHeader(header)
              .withFooter(footer));
        }
      }
    }
    for (int i = 0; i < 5; i++) {
      result.add(new GroupData() //вызывается конструктор без параметров, а потом создаются объекты
          .withName(randomString(i * 10)) // с модифицированным именем
          .withHeader(randomString(i * 10)) // хедером
          .withFooter(randomString(i * 10))); // и футером
    }
    return result;
  }

  public static List<GroupData> negativeGroupProvider() {
    var result = new ArrayList<GroupData>(List.of(
        new GroupData("", "group name'", "", "")));
    return result;
  }

  @ParameterizedTest
  @MethodSource("groupProvider")
  public void canCreateMultipleGroups(GroupData group) {
    var oldGroups = app.groups().getList();
    app.groups().createGroup(group);
    var newGroups = app.groups().getList();
    Comparator<GroupData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newGroups.sort(compareById);

    var expectedList = new ArrayList<>(oldGroups);
    expectedList.add(group.withId(newGroups.get(newGroups.size() - 1).id()).withFooter("").withHeader(""));
    expectedList.sort(compareById);
    Assertions.assertEquals(newGroups, expectedList);
  }

  @ParameterizedTest
  @MethodSource("negativeGroupProvider")
  public void canNotCreateMultipleGroups(GroupData group) {
    var oldGroups = app.groups().getList();
    app.groups().createGroup(group);
    var newGroups = app.groups().getList();
    Assertions.assertEquals(newGroups, oldGroups);
  }
}
