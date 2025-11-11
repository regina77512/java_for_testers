package ru.stqa.mantis.manager;

import jakarta.mail.Flags;
import jakarta.mail.Flags.Flag;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import ru.stqa.mantis.model.MailMessage;

public class MailHelper extends HelperBase{


  public MailHelper(ApplicationManager manager) {
    super(manager);
  }

  public List<MailMessage> receive(String username, String password, Duration duration) {
    var start = System.currentTimeMillis();
    while (System.currentTimeMillis() < start + duration.toMillis()) {
      try {
        var inbox = getInbox(username, password);
        inbox.open(Folder.READ_ONLY);
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
        inbox.getStore().close(); // закрывается хранилище
        if (result.size() > 0) {
          return result;
        }
      } catch (MessagingException e) {
        throw new RuntimeException(e);
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    throw new RuntimeException("No mail");
  }

  @NotNull
  private static Folder getInbox(String username, String password) {
    try {
      var session = Session.getInstance(new Properties());
      Store store = session.getStore("pop3"); // получение доступа к хранилищу
      store.connect("localhost", username, password); // установка соединения
      var inbox = store.getFolder("INBOX"); // открываем почтовый ящик
      return inbox;
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public void drain(String username, String password) {
    try {
      var inbox = getInbox(username, password);
      inbox.open(Folder.READ_WRITE);
      Arrays.stream(inbox.getMessages())
          .forEach(m -> {
            try {
              m.setFlag(Flags.Flag.DELETED, true);
            } catch (MessagingException e) {
              throw new RuntimeException(e);
            }
          });
          inbox.close();
          inbox.getStore().close();
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
