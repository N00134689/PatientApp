package patientapp;

public class Patient {

    private int id;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String birthday;
    private int Ward_ID;
    
        public Patient(int id, String n, String a, String m, String e, String b, int wid) {
    this.id = id;
    this.name = n;
    this.address = a;
    this.mobile = m;
    this.email = e;
    this.birthday = b;
    this.Ward_ID = wid;
    
}
    public Patient(String n, String a, String m, String e, String b, int wid) {
        this(-1, n, a, m, e, b, wid);

    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
        public int getWard_ID(){
        return Ward_ID;
    }
    
    public void setWard_ID(int Ward_ID) {
        this.Ward_ID = Ward_ID;
    }

    int getWardId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}