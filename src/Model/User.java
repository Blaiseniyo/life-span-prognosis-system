package Model;

import Enum.UserRole;

import java.io.IOException;
import java.util.UUID;

import Utils.BashFileManager;

public abstract class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;

    BashFileManager bashFileManager = new BashFileManager();

    public User(String id, String email, String firstName, String lastName, UserRole role, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, String password, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.role = role;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] login(String email, String password) {
        try {
            String[] result = bashFileManager.loginUser(email, password);
            return result;
        } catch (IOException | InterruptedException e) {
            // Handle the exception here
            e.printStackTrace();
            return null;
        }
    }

}