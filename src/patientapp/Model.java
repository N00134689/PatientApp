package patientapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model instance = null;

    public static Model getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    List<Patient> patients;
    List<Ward> wards;
    PatientTableGateway patientGateway;
    WardTableGateway wardGateway;

    private Model() throws DataAccessException {
        try {
            Connection conn = DBConnection.getInstance();
            this.patientGateway = new PatientTableGateway(conn);
            this.wardGateway = new WardTableGateway(conn);

            this.patients = this.patientGateway.getPatients();
            this.wards = this.wardGateway.getWards();
        }
        catch (ClassNotFoundException | SQLException ex) {
            throw new DataAccessException("Exception initialising Model object: " + ex.getMessage());
        }
    }

    public boolean addPatient(Patient p) throws DataAccessException {
        boolean result = false;
        try {
            int id = this.patientGateway.insertPatient(
                    p.getName(), p.getAddress(), p.getMobile(),
                    p.getEmail(), p.getBirthday(), p.getWardId()
            );
            if (id != -1) {
                p.setId(id);
                this.patients.add(p);
                result = true;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding patient: " + ex.getMessage());
        }
        return result;
    }

    public boolean removePatient(Patient p) throws DataAccessException {
        boolean removed = false;

        removed = this.patientGateway.deletePatient(p.getId());
        if (removed) {
            removed = this.patients.remove(p);
        }

        return removed;
    }

    public List<Patient> getPatients() {
        return this.patients;
    }

    public List<Patient> getPatientsByWardId(int wardId) {
        List<Patient> list = new ArrayList<>();
        this.patients.stream().filter((p) -> (p.getWardId() == wardId)).forEach((p) -> {
            list.add(p);
        });
        return list;
    }

    boolean updatePatient(Patient p) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.patientGateway.updatePatient(p);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating patient: " + ex.getMessage());
        }

        return updated;
    }

    public boolean addWard(Ward w) throws DataAccessException {
        boolean result = false;
        try {
            int id = this.wardGateway.insertWard(w.getName(), w.getNumBeds(), w.getNurse());
            if (id != -1) {
                w.setId(id);
                this.wards.add(w);
                result = true;
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception adding ward: " + ex.getMessage());
        }
        return result;
    }

    public boolean removeWard(Ward w) throws DataAccessException {
        boolean removed = false;

        try {
            removed = this.wardGateway.deleteWard(w.getId());
            if (removed) {
                removed = this.wards.remove(w);
            }
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception removing ward: " + ex.getMessage());
        }

        return removed;
    }

    public List<Ward> getWards() {
        return this.wards;
    }

    Ward findWardById(int id) {
        Ward w = null;
        int i = 0;
        boolean found = false;
        while (i < this.wards.size() && !found) {
            w = this.wards.get(i);
            if (w.getId() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            w = null;
        }
        return w;
    }

    boolean updateWard(Ward w) throws DataAccessException {
        boolean updated = false;

        try {
            updated = this.wardGateway.updateWard(w);
        }
        catch (SQLException ex) {
            throw new DataAccessException("Exception updating ward: " + ex.getMessage());
        }

        return updated;
    }
}