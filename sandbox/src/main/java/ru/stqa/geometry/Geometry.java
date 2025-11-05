package ru.stqa.geometry;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import ru.stqa.geometry.figures.Square;

public class Geometry {

  public static void main(String[] args) {
    Supplier<Square> randomSquare = () -> new Square(new Random().nextDouble()*100.0);
    var squares = Stream.generate(randomSquare).limit(5);

    squares.peek(Square::printArea).forEach(Square::printPerimeter);
  // forEach - метод у списка, в который можно передать функцию и эта ф-ция
    // будет применена ко всем эл-там списка, бывает 3 типа ф-ции:
    // 1. consumer - принимает на вход параметр, но ничего не возвращает
    // 2. producer - не принимает ничего на вход, но что-то возвращает
    // 3. настоящая ф-ция - что-то принимает на вход и что-то возвращает
  // peek - эта ф-ция берет эл-т из потока и применяет к нему какой-то consumer, но при этом объект продолжает двигаться по потоку
// forEach - терминальный консьюмер, который потребляет все, что осталось и дальше объекты не идут
// а peek работает с объектами в движущемся потоке



//    Rectangle.printRectangleArea(3.0, 5.0);
//    Rectangle.printRectangleArea(7.0, 9.0);
//
//    Triangle.printTriangleArea(new Triangle(6,8,10));
//    Triangle.printTriangleArea(new Triangle(9,10,17));
//
//    Triangle.printTrianglePerimeter(new Triangle(3,5,7.0));
  }
}
