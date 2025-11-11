package ru.stqa.mantis.manager;

import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import ru.stqa.mantis.model.MailMessage;

public class MailHelper extends HelperBase{


  public MailHelper(ApplicationManager manager) {
    super(manager);
  }

  public List<MailMessage> receive(String username, String password) {
    try {
      var session = Session.getInstance(new Properties());
      Store store = session.getStore("pop3"); // получение доступа к хранилищу
      store.connect("localhost", username, password); // установка соединения
      var inbox = store.getFolder("INBOX"); // открываем почтовый ящик
      inbox.open(Folder.READ_ONLY); // открываем ящик в режиме чтения
      var messages = inbox.getMessages(); // читаем почту
      var result = Arrays.stream(messages).map(m -> {
        try {
          return new MailMessage()
              .withFrom(m.getFrom()[0].toString())
              .withContent((String) m.getContent());
        } catch (MessagingException | IOException e) {
          throw new RuntimeException(e);
        }
      }).toList();
      inbox.close(); // закрывается ящик
      store.close(); // закрывается хранилище
      return result;
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

  }
}
