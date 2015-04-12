package patientapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WardTableGateway {

    private Connection mConnection;

    private static final String TABLE_NAME = "wards";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBEDS = "numBeds";
    private static final String COLUMN_NURSE = "nurse";

    public WardTableGateway(Connection connection) {
        mConnection = connection;
    }

    public int insertWard(String n, String nb, String nu) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = -1;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_NAME + ", " +
                COLUMN_NUMBEDS + ", " +
                COLUMN_NURSE +
                ") VALUES (?, ?, ?)";

        // create a PreparedStatement object to execute the query and insert the values into the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, n);
        stmt.setString(2, nb);
        stmt.setString(3, nu);

        // execute the query and make sure that one and only one row was inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // if one row was inserted, retrieve the id assigned to that row
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);
        }

        // return the id assigned to the row in the database
        return id;
    }

    public boolean deleteWard(int id) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the required SQL DELETE statement with place holders for the id of the row to be remove from the database
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

        // create a PreparedStatement object to execute the query and insert the id into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was deleted from the database
        return (numRowsAffected == 1);
    }

    public List<Ward> getWard() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Ward> wards;               // the java.util.List containing the Ward objects created for each row
                                        // in the result of the query the id of a ward

        String name, numBeds, nurse;
        int id;
        Ward w;                   // a Ward object created from a row in the result of the query

        // execute an SQL SELECT statement to get a java.util.ResultSet representing
        // the results of the SELECT statement
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        // iterate through the result set, extracting the data from each row
        // and storing it in a Manager object, which is inserted into an initially
        // empty ArrayList
        wards = new ArrayList<Ward>();
        while (rs.next()) {
            id = rs.getInt(COLUMN_ID);
            name = rs.getString(COLUMN_NAME);
            numBeds = rs.getString(COLUMN_NUMBEDS);
            nurse = rs.getString(COLUMN_NURSE);

            w = new Ward(id, name, numBeds, nurse);
            wards.add(w);
        }

        // return the list of Manager objects retrieved
        return wards;
    }

    boolean updateWard(Ward w) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the required SQL INSERT statement with place holders for the values to be inserted into the database
        query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_NAME       + " = ?, " +
                COLUMN_NUMBEDS    + " = ?, " +
                COLUMN_NURSE      + " = ? " +
                " WHERE " + COLUMN_ID + " = ?";

        // create a PreparedStatement object to execute the query and insert the new values into the query
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, w.getName());
        stmt.setString(2, w.getNumBeds());
        stmt.setString(3, w.getNurse());
        stmt.setInt(4, w.getId());

        // execute the query
        numRowsAffected = stmt.executeUpdate();

        // return the true if one and only one row was updated in the database
        return (numRowsAffected == 1);
    }

    List<Ward> getWards() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
