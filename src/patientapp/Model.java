package patientapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {

    private static Model instance = null;

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private PatientTableGateway gateway;
    private List<Patient> patients;

    private Model() {

        try {
            Connection conn = DBConnection.getInstance();
            gateway = new PatientTableGateway(conn);

            this.patients = this.gateway.getPatients();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Patient> getPatients() {
        return new ArrayList<Patient>(this.patients);
    }

    public boolean addPatient(Patient p) throws SQLException {
        boolean result = false;
        int id = this.gateway.insertPatient(
                p.getName(),
                p.getAddress(),
                p.getMobile(),
                p.getEmail(),
                p.getBirthday());
        
        if (id != -1) {
            p.setId(id);
            this.patients.add(p);
            result = true;
        }
        return result;
    }

    public boolean removePatient(Patient p) {
        boolean removed = false;
        try {
            removed = this.gateway.removePatient(p.getId());
            if (removed) {
                removed = this.patients.remove(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }

    public Patient findPatientByName(String name) {
        Patient b = null;
        int i = 0;
        boolean found = false;
        while (i < this.patients.size() && !found) {
            b = this.patients.get(i);
            if (b.getName().equals(name)) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }
    
     boolean updatePatient(Patient p) {
        boolean updated = false;
        try{
            updated = this.gateway.updatePatient(p);
        }
         catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updated;
    }

}
