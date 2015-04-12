package patientapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatientTableGateway {
     
     private Connection mConnection;
     
     private static final String TABLE_NAME = "patients";
     private static final String COLUMN_ID = "id";
     private static final String COLUMN_NAME = "name";
     private static final String COLUMN_ADDRESS = "address";
     private static final String COLUMN_MOBILE = "mobile";
     private static final String COLUMN_EMAIL = "email";
     private static final String COLUMN_BIRTHDAY = "birthday";
     private static final String COLUMN_WARD_ID = "Ward_ID";
     
    public PatientTableGateway(Connection connection) {
         mConnection = connection;
     }
     public int insertPatient(String n, String a, String m, String e, String b, int wid)throws SQLException {
         String query;
         PreparedStatement stmt;
         int numRowsAffected;
         int id = -1;
         
        query = "INSERT INTO " + TABLE_NAME +" (" +
                COLUMN_NAME + ", " +
                COLUMN_ADDRESS + ", " +
                COLUMN_MOBILE + ", " +
                COLUMN_EMAIL + ", " +
                COLUMN_BIRTHDAY +
                COLUMN_WARD_ID +
                ")VALUES(?,?,?,?,?,?)";

        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, n);
        stmt.setString(2, a);
        stmt.setString(3, m);
        stmt.setString(4, e);
        stmt.setString(5, b);
        if (wid == -1) {
            stmt.setNull(6, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(6, wid);
        }
        
        
        numRowsAffected = stmt.executeUpdate();
        if(numRowsAffected == 1){
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
         return id;
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
     
    public List<Patient> getPatients() throws SQLException {
        String query;       // the SQL query to execute
        Statement stmt;     // the java.sql.Statement object used to execute the
        ResultSet rs;       // the java.sql.ResultSet representing the result of
        List<Patient> patients;   // the java.util.List containing the Patient objects
                            // created for each row in the result of the query
        int id, wardId;             
        String name, address, mobile, email, birthday;
        Patient p;       // a patient object created from a row in the result of
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
            wardId = rs.getInt(COLUMN_WARD_ID);
            if (rs.wasNull()){
                wardId = -1;
            }
            
            p = new Patient(id, name, address, mobile, email, birthday, wardId);
            patients.add(p);
        }

        return patients;
    }
     

      // update Patient function with the Patient p object as a parameter
    boolean updatePatient(Patient p)throws SQLException {
        String query;
        PreparedStatement stmt;
        int numRowsAffected;
        int wid;
        
        query = "UPDATE " + TABLE_NAME +" SET " +
                COLUMN_NAME            + " = ?, " +
                COLUMN_ADDRESS         + " = ?, " +
                COLUMN_MOBILE          + " = ?, " +
                COLUMN_EMAIL           + " = ?, " +
                COLUMN_BIRTHDAY        + " = ?, " +
                COLUMN_WARD_ID         + " = ? " +
                " WHERE " + COLUMN_ID  + " = ?";
        
        // PreparedStatement object to execute the query and put in the new values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getAddress());
        stmt.setString(3, p.getMobile());
        stmt.setString(4, p.getEmail());
        stmt.setString(5, p.getBirthday());
        wid = p.getWard_ID();
        if (wid == -1) {
            stmt.setNull(6, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(6, wid);
        }
        stmt.setInt(7, p.getId());

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was updated in the database
        return (numRowsAffected == 1);
    
     }

    boolean deletePatient(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }