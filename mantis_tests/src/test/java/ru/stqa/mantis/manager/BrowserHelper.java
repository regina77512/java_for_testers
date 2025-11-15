package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class BrowserHelper extends HelperBase {

  public BrowserHelper(ApplicationManager manager){
    super(manager);
  }

  public void createUser(String username, String email) {
    signupForNewAccount();
    fillSignupForm(username, email);
    submitSignup();
    submitProceed();
  }

  public void finishRegistration(String username, String password, String url) {
    openUrl(url);
    fillUserFormRegistration(username, password);
    submitRegistration();
  }

  private void submitRegistration() {
    click(By.cssSelector("button.btn-success"));
  }

  private void fillUserFormRegistration(String username, String password) {
    type(By.name("realname"), username);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
  }

  private void openUrl(String url) {
    manager.driver().get(url);
  }

  private void submitProceed() {
    click(By.cssSelector("a[href='login_page.php']"));
  }

  private void submitSignup() {
    click(By.xpath("(//input[@value='Signup'])"));
  }

  private void fillSignupForm(String username, String email) {
    type(By.name("username"), username);
    type(By.name("email"), email);
  }

  private void signupForNewAccount() {
    click(By.cssSelector("a[href=\"signup_page.php\"]"));
  }

  public void startCreateUser(String user) {
    loginAdmin();
    selectManage();
    selectUsers();
    submitCreateNewAccount();
    fillUserFormRegistration(user);
    submitCreateUser();
    submitProceed(); //?
  }

  private void fillUserFormRegistration(String user) {
  }

  private void submitCreateUser() {
  }

  private void submitCreateNewAccount() {
  }

  private void selectUsers() {
  }

  private void selectManage() {
  }

  private void loginAdmin() {
  }
}
