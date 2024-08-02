package Utils;

import java.io.IOException;
import java.util.Scanner;

import Enum.UserRole;
import Model.Patient;

public class BashFileManager {

    public static int savePatientInitialData(String uuid, String email) throws IOException, InterruptedException {
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/initializePatient.sh", uuid, email,
                    UserRole.PATIENT.toString());

            Process process = proccessBuilder.start();
            int exitCode = process.waitFor();

            return exitCode;
        } catch (IOException | InterruptedException e) {
            throw e;
        }
    }

    public static int exportData(String filename) throws IOException, InterruptedException {
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/exportFile.sh", filename);

            Process process = proccessBuilder.start();
            int exitCode = process.waitFor();

            return exitCode;
        } catch (IOException | InterruptedException e) {
            throw e;
        }
    }

    public static int completeRegistration(Patient patient, String password) throws IOException, InterruptedException {
        Scanner processScanner = null;
        try {

            System.out.println(patient.getId());
            System.out.println(patient.getEmail());
            System.out.println(patient.getRole().toString());
            System.out.println(patient.getLastName());
            System.out.println(password);
            System.out.println(
                    patient.getDob() != null
                            ? "" + patient.getDob().getDay() + "/" + patient.getDob().getMonth() + "/"
                                    + patient.getDob().getYear()
                            : "");
            System.out.println(patient.getCountryISOCode());
            System.out.println(String.valueOf(patient.getIsHIVPositive()));
            System.out.println(
                    patient.getDiagnosisDate() != null
                            ? "" + patient.getDiagnosisDate().getDay() + "/" + patient.getDiagnosisDate().getMonth()
                                    + "/" + patient.getDiagnosisDate().getYear()
                            : "");
            System.out.println(String.valueOf(patient.getIsOnART()));
            System.out.println(
                    patient.getArtStartDate() != null
                            ? "" + patient.getArtStartDate().getDay() + "/" + patient.getArtStartDate().getMonth() + "/"
                                    + patient.getArtStartDate().getYear()
                            : "");

            // List<String> commands = new ArrayList<String>();
            // commands.add("src/BashScripts/completeUserRegistration.sh");
            // commands.add(patient.getId());
            // commands.add(patient.getEmail());
            // commands.add(patient.getFirstName());
            // commands.add(patient.getLastName());
            // commands.add(patient.getRole().toString());
            // commands.add(password);
            // commands.add(
            // patient.getDob() != null
            // ? "" + patient.getDob().getDay() + "/" + patient.getDob().getMonth() + "/"
            // + patient.getDob().getYear()
            // : "");
            // commands.add(patient.getCountryISOCode());
            // commands.add(String.valueOf(patient.getIsHIVPositive()));
            // commands.add(
            // patient.getDiagnosisDate() != null
            // ? "" + patient.getDiagnosisDate().getDay() + "/" +
            // patient.getDiagnosisDate().getMonth()
            // + "/" + patient.getDiagnosisDate().getYear()
            // : "");
            // commands.add(String.valueOf(patient.getIsOnART()));
            // commands.add(
            // patient.getArtStartDate() != null
            // ? "" + patient.getArtStartDate().getDay() + "/" +
            // patient.getArtStartDate().getMonth() + "/"
            // + patient.getArtStartDate().getYear()
            // : "");

            // ProcessBuilder proccessBuilder = new ProcessBuilder(commands.toArray(new
            // String[commands.size()]));
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "src/BashScripts/completeUserRegistration.sh",
                    patient.getId(),
                    patient.getEmail(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getRole().toString(),
                    password,
                    patient.getDob() != null
                            ? patient.getDob().getDay() + "/" + patient.getDob().getMonth() + "/"
                                    + patient.getDob().getYear()
                            : "",
                    patient.getCountryISOCode(),
                    String.valueOf(patient.getIsHIVPositive()),
                    patient.getDiagnosisDate() != null
                            ? patient.getDiagnosisDate().getDay() + "/" + patient.getDiagnosisDate().getMonth() + "/"
                                    + patient.getDiagnosisDate().getYear()
                            : "",
                    String.valueOf(patient.getIsOnART()),
                    patient.getArtStartDate() != null
                            ? patient.getArtStartDate().getDay() + "/" + patient.getArtStartDate().getMonth() + "/"
                                    + patient.getArtStartDate().getYear()
                            : "");

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            processScanner = new Scanner(process.getInputStream());
            // int exitCode = process.waitFor();

            while (processScanner.hasNextLine()) {
                String line = processScanner.nextLine();
                if (line.startsWith("Debug:")) {
                    System.out.println(line); // Print debug information
                } else if (line.equals("User not found") || line.equals("Invalid password")) {
                    System.out.println(line);
                } else {
                    String[] userData = line.split("\t");
                    System.out.println("Login successful!");
                    System.out.println("User Information:");
                    System.out.println("ID: " + userData[0]);
                    System.out.println("Email: " + userData[1]);
                    System.out.println("Name: " + userData[2]);
                    System.out.println("Date of Birth: " + userData[3]);
                    System.out.println("Gender: " + userData[4]);
                }
            }
            return 1;
        } catch (IOException e) {
            throw e;
        } finally {
            if (processScanner != null) {
                processScanner.close();
            }
        }
    }

    public static String[] checkUserRegistrationCode(String registrationsCode)
            throws IOException {
        Scanner processScanner = null;
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/verifyRegistrationCode.sh",
                    registrationsCode);
            proccessBuilder.redirectErrorStream(true);

            Process process = proccessBuilder.start();

            processScanner = new Scanner(process.getInputStream());

            if (processScanner.hasNextLine()) {

                String result = processScanner.nextLine();

                if (result.equals("Registrations code not found")) {
                    System.out.println(result);
                    return null;
                }

                String[] userData = result.split("\t");
                return userData;
            }

            System.out.println("An error occurred during registration verification.");
            return null;
        } catch (IOException e) {
            throw e;
        } finally {
            if (processScanner != null) {
                processScanner.close();
            }
        }
    }

    public static String[] loginUser(String email, String password) throws IOException, InterruptedException {
        Scanner processScanner = null;
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/loginUser.sh", email, password);
            proccessBuilder.redirectErrorStream(true);
            Process process = proccessBuilder.start();

            processScanner = new Scanner(process.getInputStream());

            if (processScanner.hasNextLine()) {
                String result = processScanner.nextLine();

                if (result.equals("User not found") || result.equals("Invalid password")) {
                    System.out.println(result);
                    return null;
                }

                String[] userData = result.split("\t");
                return userData;
            }
            System.out.println("An error occurred during authentication.");
            return null;

            // while (processScanner.hasNextLine()) {
            // String line = processScanner.nextLine();
            // if (line.startsWith("Debug:")) {
            // System.out.println(line); // Print debug information
            // } else if (line.equals("User not found") || line.equals("Invalid password"))
            // {
            // System.out.println(line);
            // } else {
            // String[] userData = line.split("\t");
            // System.out.println("Login successful!");
            // System.out.println("User Information:");
            // System.out.println("ID: " + userData[0]);
            // System.out.println("Email: " + userData[1]);
            // System.out.println("Name: " + userData[2]);
            // System.out.println("Date of Birth: " + userData[3]);
            // System.out.println("Gender: " + userData[4]);
            // }
            // }

            // return null;

        } catch (IOException e) {
            throw e;
        } finally {
            if (processScanner != null) {
                processScanner.close();
            }
        }
    }
}
