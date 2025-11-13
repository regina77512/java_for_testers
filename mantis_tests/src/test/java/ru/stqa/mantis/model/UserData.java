package ru.stqa.mantis.model;

public record UserData(String username, String password)  {

  public UserData() {
    this("", "");
  }

  public UserData withUsername(String username) {
    return new UserData(username, this.password);
  }

  public UserData withPassword(String password) {
    return new UserData(this.username, password);
  }
}
