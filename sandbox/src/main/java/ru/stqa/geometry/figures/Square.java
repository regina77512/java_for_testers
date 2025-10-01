package ru.stqa.geometry.figures;

import java.util.Objects;

public class Square {
  double side;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Square square = (Square) o;
    return Double.compare(this.side, square.side) == 0;
  }

  @Override
  public int hashCode() {
    return 1;
  }

  public Square(double side) {
    this.side = side;
    if (side < 0){
      throw new IllegalArgumentException("Square side should be non-negative");
    }
  }

  public static void printSquareArea(Square s) {
    String text = String.format("Площадь квадрата со стороной %f = %f", s.side, s.area());
    System.out.println(text);
  }

  public double area() {
    return this.side * this.side;
  }

  public double perimeter() {
    return 4 * this.side;
  }
}

