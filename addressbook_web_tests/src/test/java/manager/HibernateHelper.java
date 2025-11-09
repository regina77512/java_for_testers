package manager;

import java.util.List;
import java.util.stream.Collectors;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class HibernateHelper extends HelperBase{

  private SessionFactory sessionFactory;

  public HibernateHelper(ApplicationManager manager){
    super(manager);

    sessionFactory = new Configuration()
            .addAnnotatedClass(ContactRecord.class)
            .addAnnotatedClass(GroupRecord.class)
            .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=CONVERT_TO_NULL")
            .setProperty(AvailableSettings.USER, "root")
            .setProperty(AvailableSettings.PASS, "")
            .buildSessionFactory();

  }

  static List<GroupData> convertGroupList(List<GroupRecord> records) {
    return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
  }

  private static GroupData convert(GroupRecord record) { //метод, кот-рый из объекта типа GroupRecord строит объект типа GroupData
    return new GroupData("" + record.id, record.name, record.header, record.footer);
  }

  private static GroupRecord convert(GroupData data) {  //метод, кот-рый из объекта типа GroupData строит объект типа GroupRecord
    var id = data.id();
    if ("".equals(id)) {
      id = "0";
    }
    return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
  }

  public List<GroupData> getGroupList() {
    return sessionFactory.fromSession(session -> {
      return convertGroupList(session.createQuery("from GroupRecord", GroupRecord.class).list());
    });
  }

  public long getGroupCount() { //метод считает количество групп в рез-те вып-я запроса "select ..."
    return sessionFactory.fromSession(session -> {
      return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
    });
  }

  public void createGroup(GroupData groupData) {
    sessionFactory.inSession(session -> {
      session.getTransaction().begin();
      session.persist(convert(groupData));
      session.getTransaction().commit();
    });
  }

  static List<ContactData> convertContactList(List<ContactRecord> records) {
    return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
  }

  private static ContactData convert(ContactRecord record) {
    return new ContactData().withId("" + record.id).withLastName(record.lastname)
        .withFirstName(record.firstname).withAddress(record.address)
        .withHome(record.home).withMobile(record.mobile)
        .withWork(record.work).withSecondary(record.phone2)
        .withEmail(record.email).withEmail2(record.email2).withEmail3(record.email3);
  }

  private static ContactRecord convert(ContactData data) {  //метод, кот-рый из объекта типа ContactData строит объект типа ContactRecord
    var id = data.id();
    if ("".equals(id)) {
      id = "0";
    }
    return new ContactRecord(Integer.parseInt(id), data.lastName(), data.firstName(), data.address());
  }

  public List<ContactData> getContactList() {
    return sessionFactory.fromSession(session -> {
      return convertContactList(session.createQuery("from ContactRecord", ContactRecord.class).list());
    });
  }

  public long getContactCount() { //считает кол-во контактов
    return sessionFactory.fromSession(session -> {
      return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
    });
  }

  public void createContact(ContactData contactData) {
    sessionFactory.inSession(session -> {
      session.getTransaction().begin();
      session.persist(convert(contactData));
      session.getTransaction().commit();
    });
  }

  public List<ContactData> getContactsInGroup(GroupData group) {
    return sessionFactory.fromSession(session -> {
      return convertContactList(session.find(GroupRecord.class, group.id()).contacts);
    });
  }
}