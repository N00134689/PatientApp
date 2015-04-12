package PatientApp;

import java.util.List;
import java.util.Scanner;

public class PatientApp {
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Model model;
        int opt = 11;
        
    do {    
        try{
            model = Model.getInstance();
            System.out.println("1. Create new Patient");
            System.out.println("2. Delete Existing Patient");
            System.out.println("3. Edit Exisiting Patient");
            System.out.println("4. View all Patients");
            System.out.println("5. View single Patient");
            System.out.println();
            System.out.println("6. Create new Ward");
            System.out.println("7. Delete Existing Ward");
            System.out.println("8. Edit Exisiting Ward");
            System.out.println("9. View all Wards");
            System.out.println("10. View single Ward");
            System.out.println();
            System.out.println("11. Exit");
            System.out.println();

            opt = getInt(keyboard, "Enter Option: ", 11);

            System.out.println("You Chose Option " +opt);
            switch (opt){
                case 1:{
                    System.out.println("Creating Patient");
                    Patient p = readPatient(keyboard);
                    model.addPatient(p);
                    break;
                }
                case 2:{
                    System.out.println("Deleting Patient");
                    deletePatient(keyboard, model);
                    break;
                }
                case 3:{
                    System.out.println("Editing Patients");
                    editPatient(keyboard, model);
                    break;
                }
                case 4:{
                    System.out.println("Viewing Patients");
                    viewPatients(model);
                    break;
                }
                case 5:{
                    System.out.println("Viewing Single Patient");
                    viewPatient(keyboard, model);
                    break;
                }
                case 6:{
                    System.out.println("Creating Ward");
                    Ward w = readWard(keyboard);
                    model.addWard(w);
                    break;
                }
                case 7:{
                    System.out.println("Deleting Ward");
                    deleteWard(keyboard, model);
                    break;
                }
                case 8:{
                    System.out.println("Editing Wards");
                    editWard(keyboard, model);
                    break;
                }
                case 9:{
                    System.out.println("Viewing Wards");
                    viewWard(model);
                    break;
                }
                case 10:{
                    System.out.println("Viewing Single Ward");
                    viewWards(keyboard, model);
                    break;
                }
            }
        }
        catch (DataAccessException e) {
            System.out.println();
            System.out.println(e.getMessage());
            System.out.println();
            }
        }
        while (opt != 11);
    }
    
    private static void createWard(Scanner keyboard, Model model) throws DataAccessException{
    
        Patient p = readPatient(keyboard);
        if (model.addPatient(p)){
            System.out.println("Patient Added Succesfully");
        }
        else { System.out.println("Patient Not Added Succesfully");
        }
        System.out.println();
    }
    
    private static void deletePatient(Scanner keyboard, Model model) throws DataAccessException{
        String name = getString(keyboard, "Enter name of patient to delete: " );
        Patient p;

        p = model.findPatientByName(name);
        if (p != null){
            if (model.removePatient(p)){
                System.out.println("Patient Deleted");
            }
            else{
                System.out.println("Patient Not Deleted");
            }
        }
        else{
            System.out.println("Patient Not Found");
        }
    }
    

    private static void editPatient(Scanner kb, Model p) throws DataAccessException {
    String name = getString(kb, "Enter name of patient to edit: " );
    Patient p = null;
    
    p = p.findPatientByName(name);
    if (p != null){
        editPatientDetails(kb, p);
        if (p.updatePatient(p)){
        System.out.println("Patient Updated");
    }
        else{
            System.out.println("Patient not updated");
        }
    }
    else{
        System.out.println("Patient not found");
        }
    }
    
    private static void viewPatients(Model mdl) {
        List<Patient> patients = mdl.getPatients();
        System.out.println();
        if (patients.isEmpty()){
            System.out.println("There are no patients in the database");
        }
        else{
            displayPatients(patients, mdl);
    }
        System.out.println();
    }
    
    private static void displayPatients(List <Patient> patients, Model mdl){
    
        System.out.printf("%5s %20s %20s %12s %20s %11s\n", "Id", "Name", "Address", "Mobile", "Email", "Birthday", "Ward");
        for(Patient p : patients){
            Ward w = mdl.findWardByWard_ID(p.getWard_ID());
                System.out.printf("%5s %20s %20s %12s %20s %11s\n",
                        
                        p.getName(),
                        p.getAddress(),
                        p.getMobile(),
                        p.getEmail(),
                        p.getBirthday(),
                        (p != null) ? p.getName() : "");
                        
            }
    }
    
    private static void viewPatient(Scanner keyboard, Model model) throws DataAccessException{
    String name = getString(keyboard, "Enter name of patient to view: " );
    Patient p;

    p = model.findPatientByName(name);
    if (p != null){
        Ward w = model.findWardByWard_ID(p.getWard_ID());
        System.out.println("Name        : " +w.getName());
        System.out.println("Address     : " +w.getAddress());
        System.out.println("Mobile      : " +w.getMobile());
        System.out.println("Email       : " +w.getEmail());
        System.out.println("Birthday    : " +w.getBirthday());
        System.out.println("Ward        : " +((w != null) ? w.getName() : ""));
    }
    else{
        System.out.println("Patient Not Found");
    }
}
    
    private static Patient readPatient(Scanner keyb){
        String name, address, mobile, email, birthday;
        int Ward_ID;
        
        name = getString(keyb, "Enter Name: ");
        address = getString(keyb, "Enter Address: ");
        mobile = getString(keyb, "Enter Mobile: ");
        email = getString(keyb, "Enter Email: ");
        birthday = getString(keyb, "Enter Birthday: ");
        Ward_ID = getInt(keyb, "Enter ward id (enter -1 for no ward): ", -1);
        
        Patient p =
                new Patient(name, address, mobile, email, birthday, Ward_ID);
        
        return p;
    }
    

    private static void editPatientDetails(Scanner keyb, Patient p) {
        String name, address, mobile, email, birthday ;
        int Ward_ID;
        
        name = getString(keyb, "Enter name [" + p.getName()+ "]: ");
        address = getString(keyb, "Enter address [" + p.getAddress()+ "]: ");
        mobile = getString(keyb, "Enter mobile [" + p.getMobile()+ "]: ");
        email = getString(keyb, "Enter email [" + p.getEmail()+ "]: ");
        birthday = getString(keyb, "Enter birthday [" + p.getBirthday()+ "]: ");
        Ward_ID = getInt(keyb, "Enter Ward id (enter -1 for no ward)[" +p.getWard_ID() + "]: ", -1);
        
        if (name.length() !=0){
            p.setName(name);
        }
        if(address.length()!=0){
            p.setAddress(address);
        }
        if (mobile.length() !=0){
            p.setMobile(mobile);
        }
        if (email != p.getEmail()){
            p.setEmail(email);
        }
        if (birthday != p.getBirthday()){
            p.setBirthday(birthday);
        }
        if (Ward_ID != p.getWard_ID()) {
            p.setWard_ID(Ward_ID);
        }
    }
    
    private static void createWard(Scanner keyboard, Model model) throws DataAccessException{
    
        Ward w = readWard(keyboard);
        if (model.addWard(w)){
            System.out.println("Ward Added Succesfully");
        }
        else { System.out.println("Ward Not Added Succesfully");
        }
        System.out.println();
    }
    
    private static void deleteWard(Scanner keyboard, Model model) throws DataAccessException{
        System.out.print("Enter name of ward to delete: ");
        String name = String(keyboard.nextLine());
        Ward w;

        w = model.findWardByName(name);
        if (w != null){
            if (model.removeWard(w)){
                System.out.println("Ward Deleted");
            }
            else{
                System.out.println("Ward Not Deleted");
            }
        }
        else{
            System.out.println("Ward Not Found");
        }
    }
    

    private static void editWard(Scanner kb, Model m) throws DataAccessException {
    System.out.print("Enter Name of ward to edit: ");
    String name = String(kb. nextLine());
    Ward w;
    
    w = m.findWardByName(name);
    if (w != null){
        editWardDetails(kb, w);
        if (m.updateWard(w)){
        System.out.println("Ward Updated");
    }
        else{
            System.out.println("Patient not updated");
        }
    }
    else{
        System.out.println("Ward not found");
        }
    }
    
    private static void viewWard(Model mdl) {
        List<Ward> ward = mdl.getWard();
        System.out.println();
        if (ward.isEmpty()){
            System.out.println("There are no wards in the database");
        }
        else{
        System.out.printf("%5s %10s %20s %12s %10s\n", "Id", "Name", "NumBeds", "Nurse", "Ward");
        for(Ward w : ward){
        //wards.stream().forEach((p) -> {
                System.out.printf("%5s %10s %20s %12s %10s\n",
                        w.getWard_ID(),
                        w.getName(),
                        w.getNumBeds(),
                        w.getNurse(),
                        w.getWard_ID());
            }
    }
        System.out.println();
    }
    
        private static void viewWard(Scanner keyboard, Model model) {
            System.out.print("Enter name of ward to view: ");
            String name = String(keyboard.nextLine());
            Ward w;

            w = model.findWardByName(name);
            if (w != null){
                System.out.println("Name         : " +w.getName());
                System.out.println("NumBeds      : " +w.getNumBeds());
                System.out.println("Contact name : " +w.getNurse());
                
                List <Patient> patientList = model.getPatientsByWard_ID(w.getWard_ID());
                System.out.println();
                if (patientList.isEmpty()){
                    System.out.println("This ward has no patients");
                }
                else{
                    System.out.println("This ward has the following patients");
                    System.out.println();
                    displayWines(patientList, model);
                }
                System.out.println();
            }
                else{
                    System.out.println("Ward Not Deleted");
                }
           
        
    }
    
    private static Ward readWard(Scanner keyb){
        String name, numBeds, nurse;
        int Ward_ID;
        String line = null;
        
        name = getString(keyb, "Enter Name: ");
        numBeds = getString(keyb, "Enter numBeds: ");
        nurse = getString(keyb, "Enter nurse: ");
        Ward_ID = Integer.parseInt(line);
        
        Ward w = new Ward(name, numBeds, nurse, Ward_ID);
        
        return w;
    }
    

    private static void editWardDetails(Scanner keyb, Ward w) {
        String name, numBeds, nurse;
        int Ward_ID;
        String line1, line2;
        
        name = getString(keyb, "Enter name [" + w.getName()+ "]: ");
        numBeds = getString(keyb, "Enter numBeds [" + w.getNumBeds()+ "]: ");
        nurse = getString(keyb, "Enter contact name [" + w.getNurse()+ "]: ");
        line2 = getString(keyb, "Enter Ward id (enter -1 for no ward)[" +w.getWard_ID() + "]: ");
        
        if (name.length() !=0){
            w.setName(name);
        }
        if(numBeds.length()!=0){
            w.setNumBeds(numBeds);
        }
        if (nurse.length() !=0){
            w.setNurse(nurse);
        }
        if (line2.length() != 0) {
            Ward_ID = Integer.parseInt(line2);
            w.setWinery_ID(Ward_ID);
        }
    }
    
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static int getInt(Scanner keyb, String prompt, int defaultValue){
        int opt = defaultValue;
        boolean finished = false;
        
        do {
            try{
                System.out.print(prompt);
                String line = keyb.nextLine();
                if(line.length() > 0){
                opt = Integer.parseInt(line);
                }
                finished = true;
        }
        catch(NumberFormatException e){
            System.out.println("Exception: " + e.getMessage());
            }
        }
        while(!finished);
        
        return opt;
    }

    private static String String(String nextLine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}