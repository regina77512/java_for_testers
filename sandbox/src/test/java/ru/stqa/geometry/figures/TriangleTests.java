package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

  @Test
  void canCalculateArea() {
    var triangle1 = new Triangle(6.0, 8.0, 10.0);
    double result = triangle1.area();
    Assertions.assertEquals(24, result);
  }

  @Test
  void canCalculatePerimeter() {
    Assertions.assertEquals(15, new Triangle(3.0, 5.0, 7.0).perimeter());
  }

  @Test
  void cannotCreateTriangleWithNegativeSide() {
    try {
      new Triangle(-5, 4, 3);
      Assertions.fail();
    } catch (IllegalArgumentException exception) {
      // ok
    }
  }

  @Test
  void cannotCreateTriangleWithTwoSidesLessThatThirdSide(){
    try {
      new Triangle(2,3, 6);
      Assertions.fail();
    }catch (IllegalArgumentException exception){
      // ok
    }
  }
}
