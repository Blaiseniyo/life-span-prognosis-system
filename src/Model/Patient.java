package Model;

import java.io.Console;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import Enum.UserRole;
import Utils.BashFileManager;
import Utils.DateConverter;

public class Patient extends User {
    private Date dob;
    private boolean isHIVPositive = false;
    private Date diagnosisDate;
    private boolean isOnART = false;
    private Date artStartDate;
    private String countryISOCode;
    private int lifeExpectancy;

    public Patient(String firstName, String lastName, String email) {
        super(firstName, lastName, email, UserRole.PATIENT);
    }

    public Patient(String id, String firstName, String lastName, String email, Date dob, boolean isHIVPositive,
            Date diagnosisDate,
            boolean isOnART, Date artStartDate, String countryISOCode, int lifeExpectancy) {
        super(id, email, firstName, lastName, UserRole.PATIENT);

        this.dob = dob;
        this.isHIVPositive = isHIVPositive;
        this.diagnosisDate = diagnosisDate;
        this.isOnART = isOnART;
        this.artStartDate = artStartDate;
        this.countryISOCode = countryISOCode;
        this.lifeExpectancy = lifeExpectancy;
    }

    public Patient(String id, String email, String firstName, String lastName, UserRole role) {
        super(id, email, firstName, lastName, UserRole.PATIENT);
    }

    public Patient(String email) {
        super(email, UserRole.PATIENT);
    }

    public Patient() {

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

    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getLifeExpectancy() {
        return this.lifeExpectancy;
    }

    @Override
    public void updateProfile(Scanner scanner) {
        try {
            while (true) {
                System.out.println("\n");
                System.out.println("1. Update First Name");
                System.out.println("2. Update Last Name");
                System.out.println("3. Update Email");
                System.out.println("4. Update DOB");
                System.out.println("5. Update Country");
                System.out.println("6. Update HIV Positive");
                System.out.println("7. Update Diagnosis Date");
                System.out.println("8. Update On ART");
                System.out.println("9. Update ART Start Date");
                System.out.println("10. Back");
                System.out.println("11. Save and Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        System.out.print("Enter new First Name: ");
                        String newFirstName = scanner.nextLine();
                        this.setFirstName(newFirstName);
                        break;
                    case 2:
                        System.out.print("Enter new Last Name: ");
                        String newLastName = scanner.nextLine();
                        this.setLastName(newLastName);
                        break;
                    case 3:
                        System.out.print("Enter new Email: ");
                        String newEmail = scanner.nextLine();
                        this.setEmail(newEmail);
                        break;
                    case 4:
                        System.out.print("Enter new DOB: ");
                        String newDob = scanner.nextLine();
                        this.setDob(DateConverter.stringToDate(newDob));
                        break;
                    case 5:
                        System.out.print("Enter new Country: ");
                        String newCountry = scanner.nextLine();
                        this.setCountryISOCode(newCountry);
                        break;
                    case 6:
                        System.out.print("Enter new HIV Positive Status: ");
                        String newHIVPositive = scanner.nextLine();
                        this.setIsHIVPositive(Boolean.valueOf(newHIVPositive));
                        break;
                    case 7:
                        if (!this.getIsHIVPositive()) {
                            System.out.println("You cannot update diagnosis date if you are not HIV+");
                            break;
                        }
                        System.out.print("Enter new Diagnosis Date: ");
                        String newDiagnosisDate = scanner.nextLine();
                        this.setDiagnosisDate(DateConverter.stringToDate(newDiagnosisDate));
                        break;
                    case 8:
                        System.out.print("Enter new On ART: ");
                        String newOnART = scanner.nextLine();
                        this.setIsOnART(Boolean.valueOf(newOnART));
                        break;
                    case 9:
                        if (!this.getIsOnART()) {
                            System.out.println("You cannot update ART start date if you are not on ART");
                            break;
                        }
                        System.out.println("Enter new ART Start Date: ");
                        String newARTStartDate = scanner.nextLine();
                        this.setArtStartDate(DateConverter.stringToDate(newARTStartDate));
                        break;
                    case 10:
                        return;
                    case 11:
                        System.out.println("\n \n Your Updated Profile: \n \n");
                        this.viewProfile();

                        System.out.print("\nDo you want to save the updated profile? Enter 1. Yes  2. No: ");
                        int save = scanner.nextInt();
                        if (save == 1) {
                            System.out.println("Saving...");
                            BashFileManager.updateProfile(this);
                            // BashFileManager.completeRegistration(this, "12345");
                            return;
                        }
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again. Format: dd/MM/yyyy");
            // return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid option. Please try again.");
        }
    }

    @Override()
    public void viewProfile() {
        System.out.println("First Name: \t \t" + this.getFirstName());
        System.out.println("Last Name: \t \t" + this.getLastName());
        System.out.println("Email: \t \t \t" + this.getEmail());
        System.out.println("DOB: \t \t \t" + DateConverter.dateToString(this.getDob()));
        System.out.println("Country: \t \t" + this.getCountryISOCode());
        System.out.println("HIV Positive: \t \t" + this.getIsHIVPositive());
        if (this.getIsHIVPositive()) {
            System.out.println("Diagnosis Date: \t" + DateConverter.dateToString(this.getDiagnosisDate()));
            System.out.println("On ART: \t \t" + this.getIsOnART());
            if (this.getIsOnART()) {
                System.out.println("ART Start Date: \t" + DateConverter.dateToString(this.getArtStartDate()));
            }
        }
        System.out.println("Life Expectancy: \t" + this.computeLifespan() + " years");
    }

    public String[] completeRegistration(Scanner scanner, Console console) {

        try {
            // boolean hivPositive = false;
            System.out.println("\nEnter your personal data");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            this.setFirstName(firstName);
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            this.setLastName(lastName);
            System.out.print("Date of Birth (dd/MM/yyyy): ");
            String stringDateOfBirth = scanner.nextLine();
            Date dateOfBirth = DateConverter.stringToDate(stringDateOfBirth);
            this.setDob(dateOfBirth);
            System.out.print("Are you HIV+ (yes/no): ");
            String hiv = scanner.nextLine();

            if (hiv.equals("yes")) {
                this.setIsHIVPositive(true);
            } else {
                this.setIsHIVPositive(false);
            }

            if (this.getIsHIVPositive()) {
                System.out.print("Enter your HIV diagnosis date (dd/MM/yyyy): ");
                String stringDateDiagnosis = scanner.nextLine();
                Date dateDiagnosis = DateConverter.stringToDate(stringDateDiagnosis);
                this.setDiagnosisDate(dateDiagnosis);
                System.out.print("Are you on ART (yes/no): ");
                String onART = scanner.nextLine();

                if (onART == null || onART.isEmpty() || onART.isBlank()
                        || (!onART.equals("yes") && !onART.equals("no"))) {
                    System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                    return null;
                }

                if (onART.equals("yes")) {
                    this.setIsOnART(true);
                } else {
                    this.setIsOnART(false);
                }

                if (this.getIsOnART()) {
                    System.out.print("Enter your ART start date (dd/MM/yyyy): ");
                    String stringARTStartDate = scanner.nextLine();
                    Date ARTStartDate = DateConverter.stringToDate(stringARTStartDate);
                    this.setArtStartDate(ARTStartDate);
                }
            }

            System.out.print("Enter your country Name. eg: Rwanda: ");
            String countryCode = scanner.nextLine();
            this.setCountryISOCode(countryCode);
            System.out.print("Password: ");
            char[] passwordArray = console.readPassword();
            String password = new String(passwordArray);
            System.out.print("Confirm Password: ");
            char[] confirmPasswordArray = console.readPassword();
            String confirmPassword = new String(confirmPasswordArray);
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Registration failed.");
                return null;
            }
            this.computeLifespan();
            BashFileManager.completeRegistration(this, password);
            return null;
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again. Format: dd/MM/yyyy");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int computeLifespan() {

        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate localDob = this.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int age = Period.between(localDob, currentDate).getYears();

            Double countryLifeExpectancy = BashFileManager.countryLifeExpectancy(this.getCountryISOCode());
            int remainingYears = (int) Math.ceil(countryLifeExpectancy - age);

            if (!this.getIsHIVPositive() || this.getDiagnosisDate() == null) {
                this.setLifeExpectancy(remainingYears);
                return remainingYears; // No HIV, return country life expectancy
            }

            if (!this.getIsOnART() || this.getArtStartDate() == null) {
                int adjustedLifespan = Math.min(5, remainingYears);
                this.setLifeExpectancy(adjustedLifespan);
                return adjustedLifespan;
            }

            // Calculate years between HIV diagnosis and ART start
            LocalDate localHIVDiagnosedDate = this.getDiagnosisDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate localARTDate = this.getArtStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long yearsDelayed = ChronoUnit.YEARS.between(localHIVDiagnosedDate, localARTDate);
            yearsDelayed = Math.max(0, yearsDelayed); // Ensure non-negative

            // Apply reduction for each year delayed
            double reductionFactor = Math.pow(0.9, yearsDelayed + 1);

            // Calculate remaining lifespan
            int adjustedLifespan = (int) Math.ceil(remainingYears * reductionFactor);

            this.setLifeExpectancy(adjustedLifespan);
            return adjustedLifespan;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
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

    public void getIcallender() {
        try {
            int lifeExpectancy = this.computeLifespan();
            Date funeralData = Date
                    .from(LocalDate.now().plusYears(lifeExpectancy).atStartOfDay(ZoneId.systemDefault()).toInstant());

            BashFileManager.iCallenderExport(funeralData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
