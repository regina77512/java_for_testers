import model.GroupData;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

  @Test
  public void canRemoveGroup() {
    openGroupsPage();
    if (!isGroupPresent()) {
      driver.findElement(By.linkText("groups")).click();
      createGroup(new GroupData("", "", ""));
    }
    removeGroup();
  }
}
