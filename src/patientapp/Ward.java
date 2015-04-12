package patientapp;

public class Ward {

    private int id;
    private String name;
    private String numBeds;
    private String nurse;

    public Ward(int id, String n, String nb, String nu) {
        this.id = id;
        this.name = n;
        this.numBeds = nb;
        this.nurse = nu;
    }

    public Ward(String n, String nb, String nu) {
        this(-1, n, nb, nu);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumBeds() {
        return numBeds;
    }

    public String getNurse() {
        return nurse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumBeds(String numBeds) {
        this.numBeds = numBeds;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

}
