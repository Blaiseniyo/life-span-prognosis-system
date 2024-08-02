
package Model;

import java.io.IOException;
import Enum.UserRole;
import Utils.BashFileManager;

public class Admin extends User {

    public Admin( String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, UserRole.ADMIN);
    }

    public static String initiateRegistration(String email) {
        Patient patient = new Patient(email);
        String uuid = patient.getId();
        try {
            int exitCode = BashFileManager.savePatientInitialData(uuid, email);

            if (exitCode == 0) {
                return uuid;
            } if (exitCode == 1) {
                return "1";
            }
            else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}