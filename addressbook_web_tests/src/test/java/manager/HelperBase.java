package manager;

import java.nio.file.Paths;
import org.openqa.selenium.By;

public class HelperBase {

  protected final ApplicationManager manager;

  public HelperBase(ApplicationManager manager) {
    this.manager = manager;
  }

  protected void click(By locator) {
    manager.driver.findElement(locator).click();
  }

  protected void type(By locator, String text) {
    click(locator);
    manager.driver.findElement(locator).clear();
    manager.driver.findElement(locator).sendKeys(text);
  }

  protected void attach(By locator, String file) {
    manager.driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    //в методе sedKeys в качестве параметра должен быть абсолютный путь к файлу
    //Paths - класс, который работает с путями файла
  }
}
