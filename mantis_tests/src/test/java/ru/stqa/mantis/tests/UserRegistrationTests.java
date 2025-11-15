package ru.stqa.mantis.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.UserData;

public class UserRegistrationTests extends TestBase {

  public static Stream<UserData> randomUser() {
    Supplier<UserData> randomUser = () -> new UserData()
        .withUsername(CommonFunctions.randomString(7))
        .withPassword("password");
    return Stream.generate(randomUser).limit(1);
  }

  @ParameterizedTest
  @MethodSource("randomUser")
  void canRegisterUser(UserData userData) throws IOException, InterruptedException {
    var username = userData.username();
    var email = String.format("%s@localhost", username);
    var password = "password";
    app.jamesApi().addUser(email,password); // создать пользователя (адрес) на почтовом сервере (JamesHelper)
    app.browser().createUser(username, email);  // заполняем форму создания и отправляем (браузер)
    app.mail().receive(email, password, Duration.ofSeconds(10)); // ждем почту (MailHelper)
    var url = app.mail().extractUrl(email, password); // извлекаем ссылку из письма
    app.browser().finishRegistration(username, password, url); // проходим по ссылке и завершаем регистрацию (браузер)
    app.http().login(username, password);// проверяем, что пользователь может залогиниться (HttpSessionHelper)
    Assertions.assertTrue(app.http().isLoggedIn());
  }
}