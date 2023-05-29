package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class admin extends EasyApp {

    public admin() {
        setTitle("H&V clinic management system | Admin main page");
        setBackground(new Color(242, 242, 242));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));
        listPatients.setFont(new Font("TimesRoman", 0, 22));
        listPatients.setBackground(new Color(242, 242, 242));
        listDoctors.setFont(new Font("TimesRoman", 0, 22));
        listDoctors.setBackground(new Color(242, 242, 242));
        listAppointemnts.setFont(new Font("TimesRoman", 0, 22));
        listAppointemnts.setBackground(new Color(242, 242, 242));

        listPatientsL.setFont(new Font("TimesRoman", 0, 20));
        listDoctorsL.setFont(new Font("TimesRoman", 0, 20));
        listAppointemntsL.setFont(new Font("TimesRoman", 0, 20));

        viewHistory.setBackground(new Color(163, 155, 143));
        viewHistory.setFont(new Font("TimesRoman", 0, 22));
        patientProfile.setBackground(new Color(163, 155, 143));
        patientProfile.setFont(new Font("TimesRoman", 0, 22));

        viewDoctor.setBackground(new Color(163, 155, 143));
        viewDoctor.setFont(new Font("TimesRoman", 0, 22));
        viewAppointment.setBackground(new Color(163, 155, 143));
        viewAppointment.setFont(new Font("TimesRoman", 0, 22));

        viewAppByTimeDate.setBackground(new Color(163, 155, 143));
        viewAppByTimeDate.setFont(new Font("TimesRoman", 0, 22));

        addDoctor.setBackground(new Color(163, 155, 143));
        addDoctor.setFont(new Font("TimesRoman", 0, 22));
        addTreatment.setBackground(new Color(163, 155, 143));
        addTreatment.setFont(new Font("TimesRoman", 0, 22));
        viewIncomes.setBackground(new Color(163, 155, 143));
        viewIncomes.setFont(new Font("TimesRoman", 0, 22));

        Exit.setBackground(new Color(163, 155, 143));
        Exit.setFont(new Font("TimesRoman", 0, 22));

        //create Doctors, Patients and Appointments Lists
        createDoctorsList();
        createPatientsList();
        createAppointmentsList();
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/admin.JPG"));
    JLabel Pic = addJLabel(Icon, 150, 50, 778, 105, this);

    Label listPatients = addLabel("List of Patients", 50, 200, 250, 40, this);
    List listPatientsL = addList("", 50, 240, 250, 250, this);
    Label listDoctors = addLabel("List of Doctors ", 330, 200, 250, 40, this);
    List listDoctorsL = addList("", 330, 240, 250, 250, this);
    Label listAppointemnts = addLabel("List of Appointments", 610, 200, 250, 40, this);
    List listAppointemntsL = addList("", 610, 240, 250, 250, this);

    Button viewHistory = addButton("View history", 50, 510, 250, 60, this);
    Button patientProfile = addButton("Patient profile", 50, 580, 250, 60, this);

    Button viewDoctor = addButton("Doctor profile", 330, 510, 250, 60, this);
    Button viewAppointment = addButton("Doctor's Appointment", 330, 580, 250, 60, this);

    Button viewAppByTimeDate = addButton("View Appointment", 610, 510, 250, 60, this);

    Button addDoctor = addButton("Add Doctor", 900, 180, 180, 60, this);
    Button addTreatment = addButton("Add Treatment", 900, 280, 180, 60, this);
    Button viewIncomes = addButton("View Incomes", 900, 380, 180, 60, this);

    Button Exit = addButton("Log out", 900, 610, 180, 60, this);
    ImageIcon IconPatientInfo = new ImageIcon(getClass().getResource("Pictures/patient.png"));
    ImageIcon IconDoctorInfo = new ImageIcon(getClass().getResource("Pictures/doctor.png"));
    ImageIcon Iconapp = new ImageIcon(getClass().getResource("Pictures/doctor-appointment.png"));

    public void actions(Object source, String command) {
        if (source == Exit) {
            System.exit(0);
        }
        if (source == addDoctor) {
            new addDoctor();
            dispose();
        }
        if (source == addTreatment) {
            new addtreatment();
            dispose();
        }
        if (source == viewIncomes) {
            new incomes(listDoctorsL.getItemCount());
            dispose();
        }
        if (source == patientProfile) {
            viewPatientProfile();
        }
        if (source == viewHistory) {
            String p = listPatientsL.getSelectedItem();//selected patient
            //data validation
            if (p == null) {
                output("Please, select a patient to view his/her history!");
            } else {
                new PatientHistory(p);
                dispose();
            }
        }
        if (source == viewDoctor) {
            viewDoctorProfile();
        }
        if(source == viewAppointment){
            String d = listDoctorsL.getSelectedItem();//selected Doctor
       
        //data validation
        if (d == null) {
            output("Please, select a Doctor to view his/her profile!");
        } else{
            new DoctorAppointments(d);
            dispose();
        }
        }
        if(source == viewAppByTimeDate){
            viewAppByTimeDate();
        }
    }

    private void createDoctorsList() {
        listDoctorsL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
            while (file.length() != file.getFilePointer()) {
                listDoctorsL.add(file.readLine().substring(11));
                file.readLine();
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void createPatientsList() {
        listPatientsL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
            while (file.length() != file.getFilePointer()) {
                listPatientsL.add(file.readLine().substring(11));
                file.readLine();
                file.readLine();
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void createAppointmentsList() {
        listAppointemntsL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
            while (file.length() != file.getFilePointer()) {
                file.readLine();//Full name: David Borger
                file.readLine();//Date of Birth: 12.06.2000
                file.readLine();//ID: 01011034578
                listAppointemntsL.add(file.readLine() + " | " + file.readLine());
                file.readLine();//Hypertrophic cardiomyopathy
                file.readLine();//Lisa Castro
                file.readLine();//80.50
                file.readLine();//Cash
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void viewPatientProfile() {
        String p = listPatientsL.getSelectedItem();//selected patient
        String sum = "";
        //data validation
        if (p == null) {
            output("Please, select a patient to view his/her profile!");
        } else {
            //use sequential search to fined selected patient
            try {
                RandomAccessFile file = new RandomAccessFile("Patients_info.txt", "rw");
                /*
            example of patient's records in file:
            Full name: Peter Petersen
            Date of Birth: 12.12.2000
            ID: 12345678944
            Phone: 521125454
            Address: Copenhagen St.43
                 */
                while (file.length() != file.getFilePointer()) {
                    String FN = file.readLine().substring(11);
                    String db = file.readLine();
                    String id = file.readLine();
                    String ph = file.readLine();
                    String add = file.readLine();
                    if (p.equals(FN)) {
                        //this is selected patient
                        sum = FN + "\n" + db + "\n" + id + "\n" + ph + "\n" + add;
                        break;
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
            //Display Patient's profile
            JOptionPane.showMessageDialog(null,
                    sum,
                    "Patient's Profile:",
                    JOptionPane.INFORMATION_MESSAGE,
                    IconPatientInfo);
        }
    }

    private void viewDoctorProfile() {
        String d = listDoctorsL.getSelectedItem();//selected Doctor
        String sum = "";
        //data validation
        if (d == null) {
            output("Please, select a Doctor to view his/her profile!");
        } else {
            //use sequential search to fined selected patient
            try {
                RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
                /*
            example of Doctor's records in file:
                Full name: David Morgan
                Email: morgan_d@gmail.com
                Username: DavidAO
                Password: Password: Password: IR31-
                 */
                while (file.length() != file.getFilePointer()) {
                    String FN = file.readLine().substring(11);
                    String e = file.readLine();
                    String un = file.readLine();
                    String pass = file.readLine();

                    if (d.equals(FN)) {
                        //this is selected Doctor
                        sum = FN + "\n" + e + "\n" + un + "\n" + pass;
                        break;
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
            //Display Patient's profile
            JOptionPane.showMessageDialog(null,
                    sum,
                    "Doctor's Profile:",
                    JOptionPane.INFORMATION_MESSAGE,
                    IconDoctorInfo);
        }
    }

    private void viewAppByTimeDate() {
         String app = listAppointemntsL.getSelectedItem();//selected appointment
        String sum = "";
        //data validation
        if (app == null) {
            output("Please, select an appointment to view it!");
        } else {
            //use sequential search to fined selected patient
            try {
                RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
                /*
            example of appointment records in file:
                Full name: David Borger
                Date of Birth: 12.06.2000
                ID: 01011034578
                22.12.2022
                15:30
                Hypertrophic cardiomyopathy
                Lisa Castro
                80.50
                Cash            
                 */
                while (file.length() != file.getFilePointer()) {
                    file.readLine();
                    file.readLine();
                    file.readLine();
                    String date = file.readLine();
                    String time = file.readLine();
                    String appointmentN = file.readLine();
                    String doc = file.readLine();
                    file.readLine();
                    file.readLine();
                    if (app.equals(date + " | " + time)) {
                        //this is selected appointment
                        sum = appointmentN + "\n" + "Doctor: " + doc ;
                        break;
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
            //Display Patient's profile
            JOptionPane.showMessageDialog(null,
                    sum,
                    "Appointment:",
                    JOptionPane.INFORMATION_MESSAGE,
                    Iconapp);
        }
    }
}
