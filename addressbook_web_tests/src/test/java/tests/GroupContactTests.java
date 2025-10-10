package tests;

import org.junit.jupiter.api.Test;

public class GroupContactTests extends TestBase{

  @Test
  public void canCreateContact() {
    openContactPage();
    createContact("Петров", "Петр", "Петрович");
  }
}
