package common;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class CommonFunctions {

  public static String randomString(int n) {
    var rnd = new Random();
    var result = "";
    for (int i = 0; i < n; i++){
      result = result + (char)('a' + rnd.nextInt(26));
    }
    return result;
  }

  public static String randomFile(String dir){// в качестве параметра принимает путь директории,
    var fileNames = new File(dir).list();// а возвращает путь к файлу
    var rnd = new Random();
    var index = rnd.nextInt(fileNames.length);
    return Paths.get(dir, fileNames[index]).toString();
  }
}
