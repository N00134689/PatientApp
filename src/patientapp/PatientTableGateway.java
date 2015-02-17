package patientapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientTableGateway {
     private static final String TABLE_NAME = "patients";
     private static final String COLUMN_ID = "id";
     private static final String COLUMN_NAME = "name";
     private static final String COLUMN_ADDRESS = "address";
     private static final String COLUMN_MOBILE = "mobile";
     private static final String COLUMN_EMAIL = "email";
     private static final String COLUMN_BIRTHDAY = "birthday";
     
     private Connection mConnection;
     
     public PatientTableGateway(Connection connection) {
         mConnection = connection;
     }
     public List<Patient> getPatients() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
                            // SQL query
        ResultSet rs;       // the java.sql.ResultSet representing the result of
                            // SQL query 
        List<Patient> patients;   // the java.util.List containing the Patient objects
                            // created for each row in the result of the query
        int id;             // the id of a patient
        String name, address, email, birthday;
        String mobile;
        Patient b;       // a PATIENT object created from a row in the result of
                            // the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Programmer object, which is inserted into an initially
        // empty ArrayList
        patients = new ArrayList<Patient>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            name = rs.getString(COLUMN_NAME);
            address = rs.getString(COLUMN_ADDRESS);
            mobile = rs.getString(COLUMN_MOBILE);
            email = rs.getString(COLUMN_EMAIL);
            birthday = rs.getString(COLUMN_BIRTHDAY);
            
            b = new Patient(id, name, address, mobile, email, birthday);
            patients.add(b);
        }

        return patients;
    }
     public boolean removePatient(int id)throws SQLException{
         
        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "DELETE FROM " + TABLE_NAME +" WHERE " + COLUMN_ID + " = ?";

        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected ==1);
     }
     public int insertPatient(String name, String address, String mobile, String email, String birthday)throws SQLException {
        int id = -1;
        PreparedStatement stmt;
        int numRowsAffected;
        String query;
        query = "INSERT INTO " + TABLE_NAME +" (" +
                COLUMN_NAME + ", " +
                COLUMN_ADDRESS + ", " +
                COLUMN_MOBILE + ", " +
                COLUMN_EMAIL + ", " +
                COLUMN_BIRTHDAY +
                ")VALUES(?,?,?,?,?)";

        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, mobile);
        stmt.setString(4, email);
        stmt.setString(5, birthday);
        
        
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1){
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            id = keys.getInt(1);
        }
         return id;
    }
      // updatePatient function with the patient p object as a parameter
    boolean updatePatient(Patient p)throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        
        query = "UPDATE " + TABLE_NAME +" SET " +
                COLUMN_NAME            + " = ?, " +
                COLUMN_ADDRESS         + " = ?, " +
                COLUMN_MOBILE          + " = ?, " +
                COLUMN_EMAIL           + " = ?, " +
                COLUMN_BIRTHDAY        + " = ? " +
                " WHERE " + COLUMN_ID   + " = ?";
        
        // PreparedStatement object to execute the query and put in the new values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getAddress());
        stmt.setString(3, p.getMobile());
        stmt.setString(4, p.getEmail());
        stmt.setString(5, p.getBirthday());
        stmt.setInt(6, p.getId());
        
        // this executes the query
        numRowsAffected = stmt.executeUpdate();
        
        // return true if only one row is updated
        return (numRowsAffected == 1);
    }
}