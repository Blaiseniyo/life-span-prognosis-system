package Model;

import Enum.UserRole;

import java.io.IOException;
import java.util.Scanner;
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

    public User(String id, String email, String firstName, String lastName, UserRole role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public UserRole getRole() {
        return this.role;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public abstract void viewProfile();

    public abstract void updateProfile(Scanner scanner);
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