package ru.stqa.collections;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollectionTests {

  @Test
  void arrayTests(){
    var array = new String[]{"a", "b", "c"}; // Нельзя изменять размер массива, можно менять только элементы
    // var array = new String[3];
    Assertions.assertEquals(3, array.length);
    array[0] = "a";
    Assertions.assertEquals("a", array[0]);

    array[0] = "d";
    Assertions.assertEquals("d", array[0]);
  }

  @Test
  void listTests(){
    var list = new ArrayList<String>(List.of("a", "b", "c")); // список имеет переменную длину, элементы можно добавлять,добавлять,заменять
    Assertions.assertEquals(3, list.size());
    Assertions.assertEquals("a", list.get(0));

    list.set(0, "d");
    Assertions.assertEquals("d", list.get(0));

  }
}
