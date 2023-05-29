package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DoctorMenu extends EasyApp {

    private String nameS;

    public DoctorMenu(String NameSurname) {
        setTitle("H&V clinic management system | Welcome, " + NameSurname + "!");
        nameS = NameSurname;
        setBackground(new Color(242, 242, 242));
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        addPatient.setFont(new Font("TimesRoman", 0, 25));
        addAppointment.setFont(new Font("TimesRoman", 0, 25));
        checkCalendar.setFont(new Font("TimesRoman", 0, 25));
        seeAllAppointments.setFont(new Font("TimesRoman", 0, 25));
        searchL.setFont(new Font("TimesRoman", 0, 25));
        searchL.setBackground(new Color(242, 242, 242));
        searchT.setFont(new Font("TimesRoman", 0, 24));

        searchB.setBackground(new Color(163, 155, 143));
        searchB.setFont(new Font("TimesRoman", 0, 25));
        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 25));

        clientProfile.setFont(new Font("TimesRoman", 0, 25));
        clientProfile.setBackground(new Color(242, 242, 242));

        fullName.setFont(new Font("TimesRoman", 0, 22));
        fullName.setBackground(new Color(242, 242, 242));
        fullName.setForeground(new Color(142, 154, 158));
        DateofBirth.setFont(new Font("TimesRoman", 0, 22));
        DateofBirth.setBackground(new Color(242, 242, 242));
        DateofBirth.setForeground(new Color(142, 154, 158));

        personalID.setFont(new Font("TimesRoman", 0, 22));
        personalID.setBackground(new Color(242, 242, 242));
        personalID.setForeground(new Color(142, 154, 158));

        Address.setFont(new Font("TimesRoman", 0, 22));
        Address.setBackground(new Color(242, 242, 242));
        Address.setForeground(new Color(142, 154, 158));

        phoneNnumber.setFont(new Font("TimesRoman", 0, 22));
        phoneNnumber.setBackground(new Color(242, 242, 242));
        phoneNnumber.setForeground(new Color(142, 154, 158));

        patientHistory.setFont(new Font("TimesRoman", 0, 25));
        patientHistory.setBackground(new Color(242, 242, 242));

        TAppointments.setFont(new Font("TimesRoman", 0, 22));
        TAppointments.setBackground(new Color(242, 242, 242));
        TAppointments.setForeground(new Color(142, 154, 158));

        TPayment.setFont(new Font("TimesRoman", 0, 22));
        TPayment.setBackground(new Color(242, 242, 242));
        TPayment.setForeground(new Color(142, 154, 158));

        lastApp.setFont(new Font("TimesRoman", 0, 22));
        lastApp.setBackground(new Color(242, 242, 242));
        lastApp.setForeground(new Color(142, 154, 158));

        ViewAppointments.setFont(new Font("TimesRoman", 0, 25));
        edit.setFont(new Font("TimesRoman", 0, 25));
        edit.setBackground(new Color(163, 155, 143));
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/menu.PNG"));
    JLabel Pic = addJLabel(Icon, 200, 50, 700, 112, this);
//left side
    Button addPatient = addButton("Add patient", 70, 190, 300, 60, this);
    Button addAppointment = addButton("Add an appointment", 70, 270, 300, 60, this);
    Button checkCalendar = addButton("Check Calendar", 70, 350, 300, 60, this);
    Button seeAllAppointments = addButton("All appointments", 70, 430, 300, 60, this);
    Label searchL = addLabel("Search", 70, 520, 300, 40, this);
    TextField searchT = addTextField("", 70, 570, 300, 40, this);
    Button searchB = addButton("Search", 170, 630, 200, 60, this);
    Button back = addButton("Go Back", 880, 670, 180, 60, this);
//right side
    Label clientProfile = addLabel("View the client’s profile", 520, 190, 500, 40, this);
    Label fullName = addLabel("Full name: ", 520, 240, 300, 40, this);
    Label DateofBirth = addLabel("Date of Birth: ", 520, 280, 300, 40, this);
    Label personalID = addLabel("ID: ", 520, 320, 300, 40, this);
    Label Address = addLabel("Address: ", 520, 360, 400, 40, this);
    Label phoneNnumber = addLabel("Phone number: ", 520, 400, 300, 40, this);
    Button edit = addButton("Edit", 880, 280, 180, 60, this);
    Label patientHistory = addLabel("Patient History", 520, 470, 300, 40, this);
    Label TAppointments = addLabel("Total Appointments: ", 520, 510, 400, 40, this);
    Label TPayment = addLabel("Total Amount of Payment: ", 520, 550, 400, 40, this);
    Label lastApp = addLabel("Last appointment:", 520, 590, 400, 40, this);

    Button ViewAppointments = addButton("View Appointments", 520, 660, 300, 60, this);

    public void actions(Object source, String command) {
        if (source == addPatient) {
            new addPatient(nameS, 0, "");//add new patient
            dispose();
        }
        if (source == edit) {
            new addPatient(nameS, 1, id);//change patient personal data
            dispose();
        }
        if (source == addAppointment) {
            if (checkPatient) {
                new addAppointment(nameS, id);
                dispose();
            } else {
                output("Search for the patient whose appointments you want to view!");
            }
        }
        if (source == back) {
            new HVClinicAPP();
            dispose();
        }
        if (source == searchB) {
            searchPatient();
        }
        if (source == ViewAppointments) {
            String patientID = personalID.getText().substring(4);
            System.out.println(patientID);
            if (patientID.equals("")) {
                output("Search for the patient!");
            } else {
                new ViewAppointments(nameS, patientID);
                dispose();
            }
        }
        if (source == checkCalendar) {
            new checkCalendar(nameS);
            dispose();
        }
        if (source == seeAllAppointments) {
            new seeAllAppointments(nameS);
            dispose();
        }
    }
    boolean checkPatient;
    public static String FN = "", DoB = "", id = "", ph = "", add = "";

    private void searchPatient() {
        checkPatient = false;
        String patientID = searchT.getText().trim();
        if (patientID.equals("")) {
            output("Please write patient's ID!");
        } else {
            //use sequential search algorithm to search for patients ID in Patients_info.txt file
            try {
                RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
                while (file.getFilePointer() != file.length()) {
                    /*
                example: patient's record in file
                Full name: Peter Petersen
                Date of Birth: 12.12.2000
                ID: 12345678944
                Phone: 521125454
                Address: Copenhagen St.43
                     */

                    FN = file.readLine();
                    DoB = file.readLine();
                    id = file.readLine();
                    ph = file.readLine();
                    add = file.readLine();
                    if (id.equals("ID: " + patientID)) {
                        checkPatient = true;
                        break;
                    }
                }
                if (checkPatient) {
                    fullName.setText(FN);
                    DateofBirth.setText(DoB);
                    personalID.setText(id);
                    Address.setText(add);
                    phoneNnumber.setText(ph);
                } else {
                    output("Patient, with this ID is not registered.\nPlease, register first.");
                }
//Patient History Part
                double totalPayment = 0;
                String lastDate = "";
                int countAppointments = 0;
                RandomAccessFile file2 = new RandomAccessFile("Patients_Appointments.txt", "rw");
                while (file2.getFilePointer() != file2.length()) {
                    /*
                    Full name: Peter Petersen
                    Date of Birth: 12.12.2000
                    ID: 12345678944
                    22.12.2022
                    15:30
                    Left & Right Heart Study
                    Lisa Castro
                    120.50
                    Cash
                     */
                    file2.readLine();
                    file2.readLine();
                    String id = file2.readLine();
                    String date = file2.readLine();
                    file2.readLine();
                    file2.readLine();
                    file2.readLine();
                    String price = file2.readLine();
                    file2.readLine();
                    System.out.println("id=" + id);
                    System.out.println(patientID);
                    if (id.substring(4).equals(patientID)) {
                        totalPayment = totalPayment + Double.parseDouble(price);
                        lastDate = date;
                        countAppointments++;
                    }
                }
                TAppointments.setText("Total Appointments: " + countAppointments);
                TPayment.setText("Total Amount of Payment: " + totalPayment);
                lastApp.setText("Last appointment: " + lastDate);
            } catch (Exception e) {
                output("File not found!");
            }
        }
    }
}
