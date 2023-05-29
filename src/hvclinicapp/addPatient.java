package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class addPatient extends EasyApp {

    private String NS;
    private int addOrEdit;
    private String ID;

    public addPatient(String nameS, int aORe, String id) {
        NS = nameS;
        addOrEdit = aORe;
        ID = id;
        setTitle("H&V clinic management system | Add new patient");
        setBackground(new Color(242, 242, 242));
        setSize(1000, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        fullName.setBackground(new Color(242, 242, 242));
        fullName.setFont(new Font("TimesRoman", 0, 22));
        fullNameT.setFont(new Font("TimesRoman", 0, 22));
        dateOfBirth.setBackground(new Color(242, 242, 242));
        dateOfBirth.setFont(new Font("TimesRoman", 0, 22));
        dateOfBirthT.setFont(new Font("TimesRoman", 0, 22));
        addPersonalID.setBackground(new Color(242, 242, 242));
        addPersonalID.setFont(new Font("TimesRoman", 0, 22));
        addPersonalIDT.setFont(new Font("TimesRoman", 0, 22));
        addPhoneNumber.setBackground(new Color(242, 242, 242));
        addPhoneNumber.setFont(new Font("TimesRoman", 0, 22));
        addPhoneNumberT.setFont(new Font("TimesRoman", 0, 22));
        addAddress.setBackground(new Color(242, 242, 242));
        addAddressT.setFont(new Font("TimesRoman", 0, 22));
        addAddress.setFont(new Font("TimesRoman", 0, 22));
        confirm.setFont(new Font("TimesRoman", 0, 22));
        back.setFont(new Font("TimesRoman", 0, 22));

        //Are we registering a new Patient or we need to edit patient's personal data
        addOrEditPatient();
    }

    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/addPatient.PNG"));
    JLabel Pic = addJLabel(Icon, 188, 70, 624, 74, this);
    Label fullName = addLabel("Full Name", 200, 200, 200, 60, this);
    TextField fullNameT = addTextField("", 400, 203, 300, 46, this);
    Label dateOfBirth = addLabel("Date of Birth", 200, 270, 200, 60, this);
    TextField dateOfBirthT = addTextField("", 400, 272, 300, 46, this);
    Label addPersonalID = addLabel("Personal ID", 200, 340, 200, 60, this);
    TextField addPersonalIDT = addTextField("", 400, 342, 300, 46, this);
    Label addPhoneNumber = addLabel("Phone Number", 200, 410, 200, 60, this);
    TextField addPhoneNumberT = addTextField("", 400, 412, 300, 46, this);
    Label addAddress = addLabel("Address", 200, 480, 200, 60, this);
    TextField addAddressT = addTextField("", 400, 482, 300, 46, this);
    Button back = addButton("Go Back", 100, 600, 230, 80, this);
    Button confirm = addButton("Confirm", 650, 600, 230, 80, this);

    public void actions(Object source, String command) {
        if (source == back) {
            dispose();
            new DoctorMenu(NS);
        }
        if (source == confirm) {
            if (confirm.getLabel().equals("Confirm")) {
                confirm();
            } else {
                edit();
            }
        }
    }

    private void addOrEditPatient() {
        if (addOrEdit == 1) {
            //Edit patient's personal data
            /*
            extract patient's data from "Patients_info.txt" file
            set them in the appropreate fields
            disable full name, date of birth and ID fields
            only Address and phone data can be be changed
            save edited data back to file
             */
            confirm.setLabel("Edit");
            //since full name, date of birth and Id can NOT be changed for securate reason
            fullNameT.setEditable(false);
            dateOfBirthT.setEditable(false);
            addPersonalIDT.setEditable(false);

            try {
                RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
                while (file.getFilePointer() != file.length()) {
                    /*
                    example of patient's records in file:
                    Full name: Peter Petersen
                    Date of Birth: 12.12.2000
                    ID: 12345678944
                    Phone: 521125454
                    Address: Copenhagen St.43
                     */
                    String fn = file.readLine();
                    String dOb = file.readLine();
                    String id = file.readLine();
                    String ph = file.readLine();
                    String add = file.readLine();
                    if (id.equals(ID)) {
                        fullNameT.setText(fn.substring(11));
                        dateOfBirthT.setText(dOb.substring(15));
                        addPersonalIDT.setText(id.substring(4));
                        addPhoneNumberT.setText(ph.substring(7));
                        addAddressT.setText(add.substring(9));
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
        }
    }

    private void confirm() {
        String FN = fullNameT.getText();
        String dOb = dateOfBirthT.getText();
        String id = addPersonalIDT.getText();
        String ph = addPhoneNumberT.getText();
        String add = addAddressT.getText();
        //data validation
        if (FN.equals("") || dOb.equals("") || id.equals("") || ph.equals("") || add.equals("")) {
            output("Please fill in all fields!");
        } else {
            //date of birth format check and range check eg. 24.09.2000
            if (dOb.charAt(2) != '.' || dOb.charAt(5) != '.') {
                output("Date format is incorrect!\nPlease, use dd.mm.yyyy format.");
            } else if (dOb.length() != 10) {
                output("Date format is incorrect.");
            } else {
                //check if date of birth is less than current date
                try {
                    Date d = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
                    Date date = formatter.parse(dOb);
                    if (d.compareTo(date) <= 0) {
                        output("The year of Birth is incorrect!");
                    } else {
                        //check Personal ID: length check and type check
                        if (id.length() != 11) {
                            output("ID number is incorrect!");
                        } else {
                            boolean check = true;
                            for (int i = 0; i < 11; i++) {
                                int asciiCode = (int) (id.charAt(i));
                                if (asciiCode < 48 || asciiCode > 57) {
                                    check = false;
                                }
                            }
                            if (check == false) {
                                output("Use only digits in your ID");
                            } else {
                                //save patient's data in "Patients_info.txt" file
                                try {
                                    RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
                                    file.seek(file.length());//set filepointer at the end of file
                                    file.writeBytes("Full name: " + FN + "\n");
                                    file.writeBytes("Date of Birth: " + dOb + "\n");
                                    file.writeBytes("ID: " + id + "\n");
                                    file.writeBytes("Phone: " + ph + "\n");
                                    file.writeBytes("Address: " + add + "\n");
                                } catch (Exception e) {
                                    output("File not found!");
                                }
                                dispose();
                                new DoctorMenu(NS);
                            }
                        }
                    }
                } catch (Exception e) {
                    output("Incorrect format.");
                }
            }
        }
    }

    ArrayList<String> patientsInfo = new ArrayList<String>();

    private void edit() {
        /*
        save all records in patientsInfo ArrayList temporarily. 
        All records are transferred unchanged, except for the data 
        of the patient whose data we want to change.
        Then clear the file of data and 
        write the updated data from the ArrayList into it again.
         */

        try {
            RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
            while (file.getFilePointer() != file.length()) {
                String fn = file.readLine();
                String dOb = file.readLine();
                String id = file.readLine();
                String ph = file.readLine();
                String add = file.readLine();
                patientsInfo.add(fn);
                patientsInfo.add(dOb);
                patientsInfo.add(id);
                if (id.equals(ID)) {
                    patientsInfo.add("Phone: " + addPhoneNumberT.getText());
                    patientsInfo.add("Address: " + addAddressT.getText());
                } else {
                    patientsInfo.add(ph);
                    patientsInfo.add(add);
                }
            }
            file.setLength(0);
            for (int i = 0; i < patientsInfo.size(); i++) {
                file.writeBytes(patientsInfo.get(i) + "\n");
            }
        } catch (Exception e) {
            output("File not found!");
        }
        dispose();
        new DoctorMenu(NS);
    }

}
