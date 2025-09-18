package ru.stqa.geometry;

import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;

public class Geometry {

  public static void main(String[] args) {
    Square.printSquareArea(new Square(7));
    Square.printSquareArea(new Square(5));
    Square.printSquareArea(new Square(3));
    
    Rectangle.printRectangleArea(3.0, 5.0);
    Rectangle.printRectangleArea(7.0, 9.0);

    Triangle.printTriangleArea(new Triangle(6,8,10));
    Triangle.printTriangleArea(new Triangle(9,10,17));

    Triangle.printTrianglePerimeter(new Triangle(3,5,7.0));


  }

}
