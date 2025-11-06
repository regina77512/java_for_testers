package tests;

import common.CommonFunctions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GroupCreationTests extends TestBase {

/*  public static List<GroupData> groupProvider() throws IOException {
    var result = new ArrayList<GroupData>();
//    for (var name : List.of("", "group name")) {
//      for (var header : List.of("", "group header")) {
//        for (var footer : List.of("", "group footer")) {
//          result.add(new GroupData()
//              .withName(name)
//              .withHeader(header)
//              .withFooter(footer));
//        }
//      }
//    }
    var json = "";
    try (var reader = new FileReader("groups.json");
      var breader = new BufferedReader(reader)
    ){
      var line = breader.readLine();
      while (line != null) {
        json = json + line; //чтение файла построчно
        line = breader.readLine();
      }
    }
    //var json = Files.readString(Paths.get("groups.json")); //файл читается построчно
    //ObjectMapper mapper = new ObjectMapper();
    var mapper = new XmlMapper();
   // var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {});
    var value = mapper.readValue(new File("groups.xml"), new TypeReference<List<GroupData>>() {});
    result.addAll(value);
    return result;
  }*/

  public static Stream<GroupData> randomGroups() {
    Supplier<GroupData> randomGroup = () -> new GroupData()
        .withName(CommonFunctions.randomString(10))
        .withHeader(CommonFunctions.randomString(20))
        .withFooter(CommonFunctions.randomString(30));
    return Stream.generate(randomGroup).limit(1);
  }

  public static List<GroupData> negativeGroupProvider() {
    var result = new ArrayList<GroupData>(List.of(
        new GroupData("", "group name'", "", "")));
    return result;
  }

  @ParameterizedTest
  @MethodSource("randomGroups")
  public void canCreateGroup(GroupData group) {
    var oldGroups = app.hbm().getGroupList();
    app.groups().createGroup(group);
    var newGroups = app.hbm().getGroupList();

    var extraGroups = newGroups.stream().filter(g -> ! oldGroups.contains(g)).toList();
    // строится новый список групп и ищем там группу, которой нет в старом списке
    // в фильтре проверяется, что эл-т не содержится в старом списке
    var newId = extraGroups.get(0).id(); // берем идентификатор этой группы

    var expectedList = new ArrayList<>(oldGroups);
    expectedList.add(group.withId(newId));
    Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
  }

  @ParameterizedTest
  @MethodSource("negativeGroupProvider")
  public void canNotCreateMultipleGroups(GroupData group) {
    var oldGroups = app.hbm().getGroupList();
    app.groups().createGroup(group);
    var newGroups = app.hbm().getGroupList();
    Assertions.assertEquals(newGroups, oldGroups);
  }
}
