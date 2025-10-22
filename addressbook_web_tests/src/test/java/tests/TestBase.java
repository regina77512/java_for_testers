package tests;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

  protected static ApplicationManager app;

  @BeforeEach
  public void setUp() {
    if (app == null) {
      app = new ApplicationManager();
      app.init(System.getProperty("browser","firefox"));
    }
  }

  public static String randomFile(String dir){// в качестве параметра принимает путь директории,
    var fileNames = new File(dir).list();// а возвращает путь к файлу
    var rnd = new Random();
    var index = rnd.nextInt(fileNames.length);
    return Paths.get(dir, fileNames[index]).toString();
  }
}
