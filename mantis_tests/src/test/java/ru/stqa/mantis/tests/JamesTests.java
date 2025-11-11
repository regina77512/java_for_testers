package ru.stqa.mantis.tests;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class JamesTests extends TestBase {

  @Test
  void canCreateUser() throws IOException, InterruptedException {
    app.jamesCli().addUser(String.format("%s@localhost", CommonFunctions.randomString(8)),
        "password");
  }
}
