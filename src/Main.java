
import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Model.Admin;
import Model.Patient;
import Enum.UserRole;
import Utils.BashFileManager;
import Utils.DateConverter;

public class Main {
    BashFileManager bashFileManager = new BashFileManager();
    private static boolean isLoggedin = false;
    private static String[] loggedInUserData = null;
    private static UserRole userRole;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        System.out.println("\n Welcome to LifeSpan Prognosis App");
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Patient: Complete Registration");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
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
                        break;
                    case 2:
                        completeRegistration(scanner, console);
                        break;
                    case 3:
                        // login(scanner, console);
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        // System.exit(0);
                        break;
                }
            } catch (Exception e) {
                System.out.println("You made an invalid choice. Please try again later.");
                System.exit(0);
                break;
            }
        }
    }

    private static void adminView(Scanner scanner) {

        try {

            Admin admin = new Admin(loggedInUserData[2], loggedInUserData[3], loggedInUserData[1]);

            while (true) {
                System.out.println("\n \n Admin View \n \n");
                System.out.println("1. Initialize Patient Registration");
                System.out.println("2. View Profile");
                System.out.println("3. Export User Data");
                System.out.println("4. Export User Statistics");
                System.out.println("5. Logout");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        initializeRegistration(scanner);
                        break;
                    case 2:
                        System.out.println("\n View Profile \n");
                        admin.viewProfile();
                        break;
                    case 3:
                        BashFileManager.exportData("users_data.csv");
                        break;
                    case 4:
                        BashFileManager.exportPatientStatisticsData("statistics.csv");
                        break;
                    case 5:
                        isLoggedin = false;
                        return;
                    case 6:
                        System.out.println("Exiting...");
                        isLoggedin = false;
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

    public Date getDateFromString(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    private static void patientView(Scanner scanner) {

        try {

            Date dob = DateConverter.stringToDate(loggedInUserData[6]);
            Boolean isHIVPositive = Boolean.valueOf(loggedInUserData[8]);
            Date diagnosisDate = isHIVPositive ? DateConverter.stringToDate(loggedInUserData[9]) : null;
            Boolean isOnART = Boolean.valueOf(loggedInUserData[10]);
            Date artStartDate = isOnART ? DateConverter.stringToDate(loggedInUserData[11]) : null;
            Patient patient = new Patient(loggedInUserData[0], loggedInUserData[2], loggedInUserData[3],
                    loggedInUserData[1], dob, isHIVPositive, diagnosisDate, isOnART, artStartDate, loggedInUserData[7],
                    0);

            while (true) {
                System.out.println("\n \n Patient View \n");
                System.out.println("1. View Profile");
                System.out.println("2. Update Profile");
                System.out.println("3. Download iCalendar");
                System.out.println("4. logout");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        System.out.println("\n View Profile \n");
                        patient.viewProfile();
                        break;
                    case 2:
                        patient.updateProfile(scanner);
                        break;
                    case 3:
                        patient.getIcallender();
                        break;
                    case 4:
                        isLoggedin = false;
                        return;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {

        }
    }

    private static void initializeRegistration(Scanner scanner) {
        System.out.print("\nEnter patient's email: ");
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

        System.out.print("\nEnter your email: ");
        String email = scanner.nextLine();

        char[] passwordArray = console.readPassword("\nEnter your password: ");
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
            System.out.print(" \nEnter the registration code: ");
            String regCode = scanner.nextLine();
            String[] patientData = BashFileManager.checkUserRegistrationCode(regCode);

            if (patientData == null) {
                System.out.println("\n Registration code not found.");
                return;
            }

            String id = patientData[0];
            String email = patientData[1];
            String firstName = patientData[2];
            String lastName = patientData[3];
            UserRole role = UserRole.valueOf(patientData[4]);

            // Patient newPatient = new Patient(firstName, lastName, email, id, role);

            // System.out.println("Registration complete. Welcome " + id + " " + email + " "
            // + firstName + " " + lastName
            // + " " + role + " " + "!");
            Patient patient = new Patient(id, email, firstName, lastName, role);

            patient.completeRegistration(scanner, console);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
