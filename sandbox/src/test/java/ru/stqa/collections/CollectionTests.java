package ru.stqa.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    var list = new ArrayList<String>(List.of("a", "b", "c", "a")); // список имеет переменную длину, элементы можно добавлять,добавлять,заменять
    Assertions.assertEquals(4, list.size());
    Assertions.assertEquals("a", list.get(0));

    list.set(0, "d");
    Assertions.assertEquals("d", list.get(0));
  }

  @Test
  void setTests() {
    var set = new HashSet<>(List.of("a", "b", "c", "a")); // мн-во, состоящее из 3-х строк (a, b, c)
    Assertions.assertEquals(3, set.size());
    var element = set.stream().findAny().get();

    set.add("d");
    Assertions.assertEquals(4, set.size());
  }

  @Test
  void testMap() {
    var digits = new HashMap<Character, String>();
    digits.put('1', "one");
    digits.put('2', "two");
    digits.put('3', "three");

    Assertions.assertEquals("one", digits.get('1'));
    digits.put('1', "один");
    Assertions.assertEquals("один", digits.get('1'));
  }
}
