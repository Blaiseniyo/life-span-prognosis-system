// User.java
// UserManager.java
import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class User {
    protected String email;
    protected String password;
    protected UserRole role;

    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
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

    // Abstract method for login process (to be implemented in subclasses if needed)
    public abstract boolean login(String password);
}

// Admin.java
public class Admin extends User {
    public Admin(String email, String password) {
        super(email, password, UserRole.ADMIN);
    }

    @Override
    public boolean login(String password) {
        return this.password.equals(password); // Simple password check
    }
}

// Patient.java
public class Patient extends User {
    public Patient(String email, String password) {
        super(email, password, UserRole.PATIENT);
    }

    @Override
    public boolean login(String password) {
        return this.password.equals(password); // Simple password check
    }
}


public class UserManager {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("1. Admin Initiate Registration");
            System.out.println("2. Patient Complete Registration");
            System.out.println("3. Login");
            System.out.print("Select an option: ");
            String choice = reader.readLine();

            if (choice.equals("1")) {
                System.out.print("Enter user email: ");
                String email = reader.readLine();
                System.out.print("Enter password: ");
                String password = reader.readLine();
                Admin admin = new Admin(email, password);
                // Process for admin registration, for example, save admin to a database or file
                System.out.println("Admin registered successfully.");
            } else if (choice.equals("2")) {
                System.out.print("Enter UUID: ");
                String uuid = reader.readLine();
                System.out.print("Enter Email: ");
                String email = reader.readLine();
                System.out.print("Enter Password: ");
                String password = reader.readLine();
                Patient patient = new Patient(email, password);
                // Process for patient registration, for example, save patient to a database or file
                System.out.println("Patient registered successfully.");
            } else if (choice.equals("3")) {
                System.out.print("Enter email: ");
                String email = reader.readLine();
                System.out.print("Enter password: ");
                String password = reader.readLine();
                // Simulate login process, in practice, you would fetch user from a database
                User user = getUserByEmail(email); // Replace with actual user lookup
                if (user != null && user.login(password)) {
                    System.out.println("Login successful.");
                } else {
                    System.out.println("Login failed.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mock method to simulate user retrieval
    private static User getUserByEmail(String email) {
        // This should interact with a database or similar data store
        // For demonstration, returning a mock Admin or Patient
        return new Admin(email, "adminpassword"); // Placeholder for actual user retrieval
    }
}
