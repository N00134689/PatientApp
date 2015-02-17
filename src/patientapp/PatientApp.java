package patientapp;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PatientApp {

    public static void main(String[] args) throws SQLException {
        Scanner keyboard = new Scanner(System.in);
        Model model = Model.getInstance();


        int opt;
        System.out.println();

        do {
            System.out.println("1. Create new patient entry");
            System.out.println("2. Edit existing patient entry");
            System.out.println("3. Delete existing patient entry");
            System.out.println("4. View all patients");
            System.out.println("5. Exit Program");
            System.out.println();
            System.out.print("Choose Option (1-5) : ");
            String option = keyboard.nextLine();
            opt = Integer.parseInt(option);

            System.out.println("You have chosen option: " + option);

            if (6 <= opt || 0 >= opt) {
                System.out.println("Sorry you cant choose this number");
            } else {

                switch (opt) {
                    case 1: {
                        System.out.println("Creating patient");
                        Patient p = readPatient(keyboard);
                        if(model.addPatient(p)){
                            System.out.println("Patient added");
                        }
                        else{
                            System.out.println("Patient not added");
                        }

                        
                        break;
                    }
                    case 2: {
                        editPatient(keyboard, model);
                        break;
                    }
                    case 3: {
                        deletePatient(keyboard, model);
                        break;
                    }
                    case 4: {
                        viewPatients(model);
                        break;
                    }
                }
            }
        } while (opt != 5);
        System.out.println("Goodbye");

    }

    private static Patient readPatient(Scanner keyb) {
        String name, address, mobile, email, birthday;
        int id;
        String line;

        name = getString(keyb, "Enter name: ");
        address = getString(keyb, "Enter address: ");
        mobile = getString(keyb, "Enter mobile: ");
        email = getString(keyb, "Enter email: ");
        birthday = getString(keyb, "Enter birthday: ");

        Patient p
                = new Patient(name, address, mobile, email, birthday);
        System.out.println(); 
        return p;
        
    }
    
    private static void editPatient(Scanner kb, Model m) {
        System.out.println("Enter the name of the patient to edit: ");
        String name;
        name = (kb.nextLine());
        Patient p;
        
        p = m.findPatientByName(name);
        if (p!= null){
            editPatientDetails(kb, p);
            if(m.updatePatient(p)){
                System.out.println("Patient edited ");
            }
            else{
                System.out.println("Patient not edited ");
            }
        }
        else{
            System.out.println("Patient not found ");
        }
    }

    private static void deletePatient(Scanner kb, Model m) {
        System.out.print("Enter the name of the patient to delete:");
        String n = kb.nextLine();
        Patient p;

        p = m.findPatientByName(n);
        if (p != null) {
            if (m.removePatient(p)) {
                System.out.println("Patient deleted");
            } else {
                System.out.println("Patient not deleted");
            }
        } else {
            System.out.println("Patient not found");
        }
        System.out.println(); 
    }

    private static void viewPatients(Model model) {
        List<Patient> patients = model.getPatients();
        System.out.println("Viewing Patient entries:");
        System.out.println();
        System.out.printf("%5s %20s %20s %15s %12s %20s\n",
        "ID", "Name", "Address", "Mobile", "Email", "Birthday");
        for (Patient pa : patients) {
            System.out.printf("%5d %20s %20s %15s %12s %20s\n",
                    pa.getId(),
                    pa.getName(),
                    pa.getAddress(),
                    pa.getMobile(),
                    pa.getEmail(),
                    pa.getBirthday());
        }
       System.out.println(); 
    }

    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void editPatientDetails(Scanner kb, Patient p) {
        String name, address, mobile, email, birthday;
        
        name = getString(kb, "Enter Name [" + p.getName() + "]: ");
        address = getString(kb, "Enter Address [" + p.getAddress()+ "]: ");
        mobile = getString(kb, "Enter Mobile [" + p.getMobile()+ "]: ");
        email = getString(kb, "Enter Email [" + p.getEmail()+ "]: ");
        birthday = getString(kb, "Enter Birthday [" + p.getBirthday()+ "]: ");
        
        if (name.length() !=0){
            p.setName(name);
        }
        
        if (address.length() !=0){
            p.setAddress(address);
        }
        
        if (mobile.length() !=0){
            p.setMobile(mobile);
        }
        
        if (email.length() !=0){
            p.setEmail(email);
        }
        
        if (birthday.length() !=0){
            p.setBirthday(birthday);
        }
        
        
    }

    
}
