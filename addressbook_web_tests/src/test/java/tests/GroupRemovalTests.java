package tests;

import manager.ApplicationManager;
import model.GroupData;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

  @Test
  public void canRemoveGroup() {
    app.openGroupsPage();
    if (!app.isGroupPresent()) {
      ApplicationManager.driver.findElement(By.linkText("groups")).click();
      app.createGroup(new GroupData("", "", ""));
    }
    app.removeGroup();
  }
}
