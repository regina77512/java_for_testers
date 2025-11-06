package common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {

  public static String randomString(int n) {
    var rnd = new Random();
    Supplier<Integer> randomNumbers = () -> rnd.nextInt(26); // Генер-ся случ.число от 0 до 26
    var result = Stream.generate(randomNumbers) // Созд-ся поток, используя генератор
        .limit(n)
        .map(i -> 'a' + i) // ф-ция-трансформатор, из числа создает код буквы
        .map(Character::toString) // ф-ция-трансформатор, преобраз-т код в символ
        .collect(Collectors.joining()); // сбор всего в строку
    return result;
  }

/*  public static String randomFile(String dir){// в качестве параметра принимает путь директории,
    var fileNames = new File(dir).list();// а возвращает путь к файлу
    var rnd = new Random();
    var index = rnd.nextInt(fileNames.length);
    return Paths.get(dir, fileNames[index]).toString();
  }*/
}
