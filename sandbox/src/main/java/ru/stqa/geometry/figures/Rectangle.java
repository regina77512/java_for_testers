package ru.stqa.geometry.figures;

public class Rectangle {

  private double a;
  private double b;

  public Rectangle(double a, double b){
    this.a = a;
    this.b = b;

    if(a < 0 || b < 0){
      throw new IllegalArgumentException("Rectangle side should be non-negative");
    }
  }

  public static void printRectangleArea(double a, double b) {
    String text = String.format("Площадь прямоугольника со сторонами %f и %f = %f", a, b, rectangleArea(a, b));
    System.out.println(text);
  }

  private static double rectangleArea(double a, double b) {
    return a * b;
  }
}
