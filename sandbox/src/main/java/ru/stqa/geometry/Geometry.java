package ru.stqa.geometry;

import java.util.List;
import java.util.function.Consumer;
import ru.stqa.geometry.figures.Rectangle;
import ru.stqa.geometry.figures.Square;
import ru.stqa.geometry.figures.Triangle;

public class Geometry {

  public static void main(String[] args) {
    var squares = List.of(new Square(7), new Square(5), new Square(3));
//    for (Square square : squares) {
//      Square.printSquareArea(square);
//    }

    squares.forEach(Square::printSquareArea);//forEach - метод у списка, в который можно передать функцию и эта ф-ция
    // будет применена ко всем эл-там списка, бывает 3 типа ф-ции:
    // - consumer - принимает на вход параметр, но ничего не возвращает
    // - producer - не принимает ничего на вход, но что-то возвращает
    // - настоящая ф-ция - что-то принимает на вход и что-то возвращает

//    Rectangle.printRectangleArea(3.0, 5.0);
//    Rectangle.printRectangleArea(7.0, 9.0);
//
//    Triangle.printTriangleArea(new Triangle(6,8,10));
//    Triangle.printTriangleArea(new Triangle(9,10,17));
//
//    Triangle.printTrianglePerimeter(new Triangle(3,5,7.0));
  }
}
