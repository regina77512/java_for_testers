package ru.stqa.mantis.tests;

import java.time.Duration;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;


// не доделан
public class UserCreationTests extends TestBase {

  public static Stream<String> randomUser() {
    return Stream.of(CommonFunctions.randomString(8));
  }

  @ParameterizedTest
  @MethodSource("randomUser")
  void canCreateUser(String user) {
    var email = String.format("%s@localhost", user);
    var password = "password";
    app.jamesApi().addUser(email,password); // создать пользователя (адрес) на почтовом сервере (JamesHelper)
    app.browser().startCreateUser(user);  // заполняем форму создания и отправляем (браузер)
    var messages = app.mail().receive(email, password, Duration.ofSeconds(10)); // ждем почту (MailHelper)
    var url = app.mail().extractUrl(email, password); // извлекаем ссылку из письма
    app.browser().finishRegistration(user, password, url); // проходим по ссылке и завершаем регистрацию (браузер)
    app.http().login(user, password);// проверяем, что пользователь может залогиниться (HttpSessionHelper)
    Assertions.assertTrue(app.http().isLoggedIn());
  }
}
