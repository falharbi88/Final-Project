package edu.depaul.csc472.finalproject.Model;

/**
 * Created by Fahad on 10/7/17.
 */

public class User {
    private String Email;
    private String FirstName;
    private String LastName;
    private String Password;

    public User(String username, String email, String firstName, String lastName, String password) {
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Password = password;
    }

    public User(){

    }

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public String toString() {
        return "User{" +
                "Email='" + Email + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
