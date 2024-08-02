package Model;

import java.io.Console;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Enum.UserRole;
import Utils.BashFileManager;

public class Patient extends User {
    private Date dob;
    private boolean isHIVPositive = false;
    private Date diagnosisDate;
    private boolean isOnART = false;
    private Date artStartDate;
    private String countryISOCode;

    public Patient(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, UserRole.PATIENT);
    }

    public Patient(String id, String email, String firstName, String lastName, UserRole role) {
        super(id, email, firstName, lastName, UserRole.PATIENT, null);
    }

    public Patient(String email) {
        super(email, UserRole.PATIENT);
    }

    public Date getDob() {
        return dob;
    }

    public boolean getIsHIVPositive() {
        return isHIVPositive;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public boolean getIsOnART() {
        return isOnART;
    }

    public Date getArtStartDate() {
        return artStartDate;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setIsHIVPositive(boolean isHIVPositive) {
        this.isHIVPositive = isHIVPositive;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public void setIsOnART(boolean isOnART) {
        this.isOnART = isOnART;
    }

    public void setArtStartDate(Date artStartDate) {
        this.artStartDate = artStartDate;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public String[] completeRegistration(Scanner scanner, Console console) {

        try {
            // boolean hivPositive = false;
            System.out.println("\nEnter your personal data");
            System.out.println("First Name: ");
            String firstName = scanner.nextLine();
            this.setFirstName(firstName);
            System.out.println("Last Name: ");
            String lastName = scanner.nextLine();
            this.setLastName(lastName);
            System.out.println("Date of Birth (dd/MM/yyyy): ");
            String dateOfBirth = scanner.nextLine();
            this.setDob(getDateFromString(dateOfBirth));
            System.out.println("Are you HIV+ (yes/no): ");
            String hiv = scanner.nextLine();
            if (hiv.equals("yes")) {
                this.setIsHIVPositive(true);
            } else {
                this.setIsHIVPositive(false);
            }

            if (this.getIsHIVPositive()) {
                System.out.println("Enter your HIV diagnosis date (dd/MM/yyyy): ");
                String dateDiagnosis = scanner.nextLine();
                diagnosisDate = getDateFromString(dateDiagnosis);
                System.out.println("Are you on ART (yes/no): ");
                String onART = scanner.nextLine();

                if (onART.equals("yes")) {
                    this.setIsOnART(true);
                } else {
                    this.setIsOnART(false);
                }

                if (this.getIsOnART()) {
                    System.out.println("Enter your ART start date (dd/MM/yyyy): ");
                    String ARTStartDate = scanner.nextLine();
                    this.setArtStartDate(getDateFromString(ARTStartDate));
                }
            }

            System.out.println("Enter your country country code. eg: RW for Rwanda.: ");
            String countryCode = scanner.nextLine();
            this.setCountryISOCode(countryCode);
            System.out.println("Password: ");
            char[] passwordArray = console.readPassword();
            String password = new String(passwordArray);
            System.out.println("Confirm Password: ");
            char[] confirmPasswordArray = console.readPassword();
            String confirmPassword = new String(confirmPasswordArray);
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Registration failed.");
                return null;
            }

            BashFileManager.completeRegistration(this, password);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

}
