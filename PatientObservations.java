import java.sql.*;
import java.util.Scanner;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class PatientObservations {
    private static boolean isRunning = true;
    private static int userid;
    private static Connection conn = null;
    private static Statement st = null;
    private static ResultSet rs = null;

    public static void main(String args[]) throws SQLException {
        // handle input argument
        int hospitalID = -1;
        try {
            hospitalID = Integer.parseInt(args[0]);
            System.out.println("Your input argument is: " + hospitalID);
        } catch (Exception e) {
            System.out.println("Input argument not entered");
        }

        // register driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

        // application interface
        try {
//            conn = DriverManager.getConnection(url, username, password);
//            st = conn.createStatement();
            if (checkID(hospitalID)) {

                while (isRunning) {
                    System.out.println("------ java PatientObservations running -------");
                    System.out.println("(1) enter a patient's observation");
                    System.out.println("(2) quit the program");
                    System.out.println("Please enter the option number:");
                    Scanner sc = new Scanner(System.in);
                    int o = sc.nextInt();
                    switch (o) {
                        case 1:
                            observation();
                            break;
                        case 2:
                            System.out.println("End of program");
                            isRunning = false;
                            break;
                    }
                }
            } else {
                System.out.println("End of program");
            }
        } catch (SQLException e) {
            System.out.println("Exception: problem accessing database");
        } finally {
            // close connections
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
            try {
                st.close();
            } catch (Exception e) { /* ignored */ }
            try {
                conn.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    // helper function to check whether hospitalID valid
    private static boolean checkID(int hidCheck) throws SQLException {

        boolean valid = false;
        while (!valid) {
            try {
                // query table
                rs = st.executeQuery("SELECT hid FROM professional");
            } catch (SQLException e) {
                System.out.println("SQL exception caught when querying hid.");
            }

            // iterate result of the query
            while (rs.next()) {
                int hid = rs.getInt(1);
                if (hidCheck == hid) {
                    userid = hidCheck;
                    System.out.println("Hospital id entered is verified.");
                    valid = true;
                    return true;
                }
            }
            // case input invalid
            if (!valid) {
                System.out.println("The hospital id entered is not present in database. Terminating...");
            }
        }
        return valid;
    }

    // helper function to check whether patient id valid
    private static boolean checkPID(int pidCheck) throws SQLException {
        boolean validPid = false;
        while (!validPid) {
            try {
                // query table
                rs = st.executeQuery("SELECT pid FROM patient WHERE status=\'infected\'");
            } catch (SQLException e) {
                System.out.println("SQL exception caught when querying pid.");
            }

            // iterate result of the query
            while (rs.next()) {
                int thisPid = rs.getInt(1);
                if (thisPid == pidCheck) {
                    System.out.println("Patient id entered is verified.");
                    return true;
                }
            }
        }
        return validPid;
    }

    // option 1
    private static void observation() throws SQLException {

        System.out.println("Please enter the patient id:");
        Scanner sc = new Scanner(System.in);
        int pid = sc.nextInt();

        // check valid patient id
        if (!checkPID(pid)) {
            System.out.println("The patient id entered is incorrect.");
            return;
        }

        // user input observation tect
        System.out.println("Please enter your observation summary");
        String textIn = sc.nextLine();

        // get current date and time
        String sqlDate = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String sqlTime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        // update table
        try{
            st.executeUpdate("INSERT INTO observe VALUES ('" + sqlDate + "','" + sqlTime + "','"+ textIn + "'," + pid + "," + userid + ");");
            System.out.println("New observation record created.");
        }catch (SQLException e){
            System.out.println("Exception caught when updating record to database");
        }
    }
}

