package ru.stqa.geometry.figures;

public class Triangle {
double a;
double b;
double c;

  public Triangle(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }


  public static void printTriangleArea(Triangle triangle1) {
    String text = String.format("Площадь треугольника со сторонами %f, %f и %f = %f", triangle1.a, triangle1.b, triangle1.c, triangle1.area());
    System.out.println(text);
  }

  public static void printTrianglePerimeter(Triangle triangle1) {
    String text = String.format("Периметр треугольника со сторонами %f, %f и %f = %f", triangle1.a, triangle1.b, triangle1.c, triangle1.perimeter());
    System.out.println(text);
  }


  public double area() {
    return Math.sqrt(semiperimeter() * (semiperimeter() - this.a) * (semiperimeter() - this.b) * (semiperimeter() - this.c));
  }

  private double semiperimeter() {
    return ((this.a + this.b + this.c)/2);
  }

  public double perimeter() {
    return (this.a + this.b + this.c);
  }
}


