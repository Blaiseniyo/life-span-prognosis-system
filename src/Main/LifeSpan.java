package Main;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import Model.Admin;
import Model.Patient;
import Enum.UserRole;
import Utils.BashFileManager;

public class LifeSpan {
    BashFileManager bashFileManager = new BashFileManager();
    private static boolean isLoggedin = false;
    private static String[] loggedInUserData = null;
    private static UserRole userRole;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        while (true) {
            System.out.println("\nWelcome to LifeSpan Prognosis App");
            System.out.println("\n1. Patient: Login");
            System.out.println("2. Patient: Complete Registration");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner, console);
                    if (isLoggedin) {
                        if (userRole == UserRole.ADMIN) {
                            adminView(scanner);
                        } else {
                            patientView(scanner);
                        }
                    }
                    // initializeRegistration(scanner);
                    break;
                case 2:
                    completeRegistration(scanner, console);
                    break;
                case 3:
                    // login(scanner, console);
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminView(Scanner scanner) {

        try {
        while (true) {
            System.out.println("\nAdmin View");
            System.out.println("1. Initialize Patient Registration");
            System.out.println("2. View Profile");
            System.out.println("3. Update Profile");
            System.out.println("4. Export User Data");
            System.out.println("5. Export User Statistics");
            System.out.println("6. Download iCallendar");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    initializeRegistration(scanner);
                    break;
                case 2:
                    System.out.println("View Profile...");
                    break;
                case 3:
                    System.out.println("Update Profile feature comming son :)");
                    break;
                case 4:
                    BashFileManager.exportData("users_data.txt");
                    break;
                case 5:
                    BashFileManager.exportData("users_analytics.csv");
                    break;
                case 6:
                    System.out.println("Download iCallendar feature comming son :)");
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void patientView(Scanner scanner) {

        while (true) {
            System.out.println("\nPatient View");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download iCallendar");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.println("View Profile...");
                    break;
                case 2:
                    System.out.println("Update Profile feature comming son :)");
                    System.exit(0);
                    break;
                case 3:
                    System.out.println("Download iCallendar feature comming son :)");
                    System.exit(0);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeRegistration(Scanner scanner) {
        System.out.print("Enter patient's email: ");
        String email = scanner.nextLine();
        try {
            String regCode = Admin.initiateRegistration(email);

            if (regCode != null && !regCode.equals("1")) {
                System.out.println(
                        "Patient Registration initialization successful. Patient registration Code: " + regCode);
            } else if (regCode.equals("1")) {
                System.out.println("Patient registration failed. User with this email already exists.");
            } else {
                System.out.println("Error initializing registration.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void login(Scanner scanner, Console console) {

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        char[] passwordArray = console.readPassword("Enter your password: ");
        String password = new String(passwordArray);

        try {

            loggedInUserData = BashFileManager.loginUser(email, password);

            if (loggedInUserData != null) {
                isLoggedin = true;
                userRole = UserRole.valueOf(loggedInUserData[4]);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void completeRegistration(Scanner scanner, Console console) {
        try {
            System.out.print("Enter the registration code: ");
            String regCode = scanner.nextLine();
            String[] patientData = BashFileManager.checkUserRegistrationCode(regCode);

            if (patientData == null) {
                System.out.println("Registration code not found.");
                return;
            }

            String id = patientData[0];
            String email = patientData[1];
            String firstName = patientData[2];
            String lastName = patientData[3];
            UserRole role = UserRole.valueOf(patientData[4]);

            System.out.println("Registration complete. Welcome " + id + " " + email + " " + firstName + " " + lastName
                    + " " + role + " " + "!");
            Patient patient = new Patient(id, email, firstName, lastName, role);

            patient.completeRegistration(scanner, console);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
