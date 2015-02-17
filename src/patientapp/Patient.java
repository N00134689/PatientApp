package patientapp;

public class Patient {

    private int id;
    private String name;
    private String address;
    private String mobile;
    private String email;
    private String birthday;
    
        public Patient(int id, String n, String a, String m, String e, String b) {
    this.id = id;
    this.name = n;
    this.address = a;
    this.mobile = m;
    this.email = e;
    this.birthday = b;
    
}
    public Patient(String n, String a, String m, String e, String b) {
        this(-1, n, a, m, e, b);

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
    
}