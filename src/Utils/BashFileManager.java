package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
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

    public static Double countryLifeExpectancy(String countryISOCode) throws IOException, InterruptedException {
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/countryLifeExpectancy.sh",
                    countryISOCode);
            Process process = proccessBuilder.start();
            String output = readOutput(process.getInputStream());

            int exitCode = process.waitFor();

            return Double.parseDouble(output.trim());
            // return exitCode;
        } catch (IOException | InterruptedException e) {
            throw e;
        }
    }

    // Helper method to read the output from an InputStream
    private static String readOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
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

    public static int iCallenderExport(Date funeralDate) throws IOException, InterruptedException {
        Scanner processScanner = null;
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/icallender.sh", DateConverter.dateToString(funeralDate));

            proccessBuilder.redirectErrorStream(true);
            Process process = proccessBuilder.start();
            int exitCode = process.waitFor();

            processScanner = new Scanner(process.getInputStream());
            // int exitCode = process.waitFor();

            while (processScanner.hasNextLine()) {
                String line = processScanner.nextLine();
                System.out.println(line);
                if (line.startsWith("Debug:")) {
                    System.out.println(line); // Print debug information
                } else if (line.equals("User not found") || line.equals("Invalid password")) {
                    System.out.println(line);
                } else {
                    // String[] userData = line.split("\t");
                    System.out.println("Complete registration successful!");
                }
            }

            return exitCode;
        } catch (IOException | InterruptedException e) {
            throw e;
        }finally {
            if (processScanner != null) {
                processScanner.close();
            }
        }
    }

    public static int exportPatientStatisticsData(String filename) throws IOException, InterruptedException {
        try {
            ProcessBuilder proccessBuilder = new ProcessBuilder("src/BashScripts/exportPatientStatistics.sh", filename);

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

            System.out.println("Completing registration for " + patient.getEmail());

            String dob = DateConverter.dateToString(patient.getDob());
            System.out.println(dob);
            String artStartDate = patient.getArtStartDate() == null ? ""
                    : DateConverter.dateToString(patient.getArtStartDate());
            String diagnosisDate = patient.getDiagnosisDate() == null ? ""
                    : DateConverter.dateToString(patient.getDiagnosisDate());
            String lifeExpectancy = String.valueOf(patient.getLifeExpectancy());
            String completeRegistrationScriptPath = System.getenv("COMPLETE_REGISTRATION_SCRIPT_PATH");

            ProcessBuilder processBuilder = new ProcessBuilder(
                    completeRegistrationScriptPath,
                    patient.getId(),
                    patient.getEmail(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getRole().toString(),
                    password,
                    dob,
                    patient.getCountryISOCode(),
                    String.valueOf(patient.getIsHIVPositive()),
                    diagnosisDate,
                    String.valueOf(patient.getIsOnART()),
                    artStartDate, lifeExpectancy);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            processScanner = new Scanner(process.getInputStream());
            // int exitCode = process.waitFor();

            while (processScanner.hasNextLine()) {
                String line = processScanner.nextLine();
                System.out.println(line);
                if (line.startsWith("Debug:")) {
                    System.out.println(line); // Print debug information
                } else if (line.equals("User not found") || line.equals("Invalid password")) {
                    System.out.println(line);
                } else {
                    // String[] userData = line.split("\t");
                    System.out.println("Complete registration successful!");
                }
            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (processScanner != null) {
                processScanner.close();
            }
        }
    }

    public static int updateProfile(Patient patient) throws IOException, InterruptedException {
        Scanner processScanner = null;

        try {
            String dob = DateConverter.dateToString(patient.getDob());
            String artStartDate = patient.getArtStartDate() == null ? ""
                    : DateConverter.dateToString(patient.getArtStartDate());
            String diagnosisDate = patient.getDiagnosisDate() == null ? ""
                    : DateConverter.dateToString(patient.getDiagnosisDate());
            String lifeExpectancy = String.valueOf(patient.getLifeExpectancy());
            // String updateProfileScriptPath =
            // System.getenv("COMPLETE_REGISTRATION_SCRIPT_PATH");
            String updateProfileScriptPath = "src/BashScripts/updatePatientPofile.sh";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    updateProfileScriptPath,
                    patient.getId(),
                    patient.getEmail(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getRole().toString(),
                    // "12345",
                    dob,
                    patient.getCountryISOCode(),
                    String.valueOf(patient.getIsHIVPositive()),
                    diagnosisDate,
                    String.valueOf(patient.getIsOnART()),
                    artStartDate, lifeExpectancy);

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
                    // String[] userData = line.split("\t");
                    System.out.println("Updated successful!");
                }
            }
            return 1;
        } catch (IOException e) {
            System.out.println("An error occurred during profile update. Please try again.");
            e.printStackTrace();
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
            proccessBuilder.redirectErrorStream(false);
            Process process = proccessBuilder.start();

            processScanner = new Scanner(process.getInputStream());

            if (processScanner.hasNextLine()) {
                String result = processScanner.nextLine();

                if (result.equals("User not found") || result.equals("Invalid password")) {
                    System.out.println("\n \n Invalid email or password. Please try again.");
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
