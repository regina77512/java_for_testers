package ru.stqa.geometry.figures;

import java.util.Objects;

public class Triangle {
double a;
double b;
double c;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Triangle triangle = (Triangle) o;
    return Double.compare(this.a, triangle.a) == 0
        && Double.compare(this.b, triangle.b) == 0
        && Double.compare(this.c, triangle.c) == 0
        ||
        Double.compare(this.b, triangle.a) == 0
            && Double.compare(this.a, triangle.b) == 0
            && Double.compare(this.c, triangle.c) == 0
        ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, b, c);
  }

  public Triangle(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;

    if (a < 0 || b < 0 || c < 0){
      throw new IllegalArgumentException("Triangle side should be non-negative");
    }

    if (((a + b) < c) || ((b + c) < a) || ((c + a) < b)){
      throw new IllegalArgumentException("The sum of any two sides of a triangle cannot be less than the third");
    }
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
    return (perimeter()/2);
  }

  public double perimeter() {
    return (this.a + this.b + this.c);
  }
}


