package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangleTests {

  @Test
  void cannotCreateRectangleWithNegativeSide() {
    try {
      new Rectangle(-5, 3);
      Assertions.fail();
    } catch (IllegalArgumentException exception) {
// ok
    }
  }

  @Test
  void testEquality(){
    var r1 = new Rectangle(4,5);
    var r2 = new Rectangle(4, 5);
    Assertions.assertEquals(r1, r2);
  }

  @Test
  void testEquality2(){
    var r1 = new Rectangle(4,5);
    var r2 = new Rectangle(5, 4);
    Assertions.assertEquals(r1, r2);
  }
}