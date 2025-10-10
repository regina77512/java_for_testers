package model;

public record ContactData(String firstName, String lastName, String address, String mobile, String email) {
  public  ContactData(){
    this("", "", "", "", "");
  }

  public ContactData withoutAddressAndEmail(String firstName, String lastName, String mobile) {
    return new ContactData(firstName, lastName, this.address, mobile, this.email);
  }
}