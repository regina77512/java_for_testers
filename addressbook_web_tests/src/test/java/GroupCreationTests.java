import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupCreationTests extends TestBase {

  @Test
  public void canCreateGroup() {
    openGroupsPage();
    createGroup("Group name", "Group header", "Group footer");
  }

  @Test
  public void canCreateGroupWithEmptyName() {
    openGroupsPage();
    driver.findElement(By.linkText("groups")).click();
    createGroup("", "", "");
  }
}
