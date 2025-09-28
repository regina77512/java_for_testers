package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareTests {

  @Test
  void canCalculateArea() {
    var square1 = new Square(5);
    double result = square1.area();
    Assertions.assertEquals(25.0, result);
  }

  @Test
  void canCalculatePerimeter() {
    Assertions.assertEquals(20.0, new Square(5).perimeter());
  }

  @Test
  void cannotCreateSquareWithNegativeSide(){
    try {
      new Square(-5);
      Assertions.fail();
    }catch (IllegalArgumentException exception){
// ok
    }

  }

}
