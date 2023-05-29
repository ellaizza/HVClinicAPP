package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class addAppointment extends EasyApp {

    private String NS;
    private String ID;

    public addAppointment(String nameS, String id) {
        NS = nameS;
        ID = id;
        setTitle("H&V clinic management system | Add new appointment");
        setBackground(new Color(242, 242, 242));
        setSize(1000, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        cash.setBackground(new Color(242, 242, 242));
        cash.setFont(new Font("TimesRoman", 0, 22));
        fullName.setBackground(new Color(242, 242, 242));
        fullName.setFont(new Font("TimesRoman", 0, 22));
        dateOfBirth.setBackground(new Color(242, 242, 242));
        dateOfBirth.setFont(new Font("TimesRoman", 0, 22));
        personalID.setBackground(new Color(242, 242, 242));
        personalID.setFont(new Font("TimesRoman", 0, 22));
        addAppointmentDate.setBackground(new Color(242, 242, 242));
        addAppointmentDate.setFont(new Font("TimesRoman", 0, 22));
        addAppointmentDateT.setFont(new Font("TimesRoman", 0, 22));
        addAppointmentTime.setBackground(new Color(242, 242, 242));
        addAppointmentTime.setFont(new Font("TimesRoman", 0, 22));
        addAppointmentTimeT.setFont(new Font("TimesRoman", 0, 22));
        typeOfTheTreatment.setBackground(new Color(242, 242, 242));
        typeOfTheTreatment.setFont(new Font("TimesRoman", 0, 22));
        typeOfTheTreatmentC.setFont(new Font("TimesRoman", 0, 18));
        doctor.setBackground(new Color(242, 242, 242));
        doctor.setFont(new Font("TimesRoman", 0, 22));
        doctorT.setFont(new Font("TimesRoman", 0, 22));
        priceOfTheAppointment.setBackground(new Color(242, 242, 242));
        priceOfTheAppointment.setFont(new Font("TimesRoman", 0, 22));
        priceOfTheAppointmentL.setFont(new Font("TimesRoman", 0, 22));
        priceOfTheAppointmentL.setBackground(new Color(242, 242, 242));
        paymentMethod.setFont(new Font("TimesRoman", 0, 22));
        paymentMethod.setBackground(new Color(242, 242, 242));
        back.setFont(new Font("TimesRoman", 0, 22));
        confirm.setFont(new Font("TimesRoman", 0, 22));
        //set patients personal data in the window
        fullName.setText(DoctorMenu.FN);
        dateOfBirth.setText(DoctorMenu.DoB);
        personalID.setText(DoctorMenu.id);
        doctorT.setText(NS);
        //create list of treatments
        createTreatmentsList();
        setPrice();
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/addAppointment.PNG"));
    JLabel Pic = addJLabel(Icon, 170, 50, 670, 77, this);

    Label fullName = addLabel("Full Name:", 200, 150, 400, 50, this);
    Label dateOfBirth = addLabel("Date of Birth:", 200, 200, 400, 50, this);
    Label personalID = addLabel("Personal ID:", 200, 250, 400, 50, this);
    Label addAppointmentDate = addLabel("Appointment Date:", 200, 300, 270, 50, this);
    TextField addAppointmentDateT = addTextField("", 470, 300, 300, 45, this);

    Label addAppointmentTime = addLabel("Appointment Time:", 200, 350, 270, 50, this);
    TextField addAppointmentTimeT = addTextField("", 470, 350, 300, 45, this);

    Label typeOfTheTreatment = addLabel("Type of the Treatment:", 200, 400, 270, 50, this);
    Choice typeOfTheTreatmentC = addChoice("", 467, 402, 300, 45, this);

    Label doctor = addLabel("Doctor", 200, 450, 270, 50, this);
    TextField doctorT = addTextField("", 470, 450, 300, 45, this);

    Label priceOfTheAppointment = addLabel("Price of the Appointment:", 200, 500, 270, 50, this);
    Label priceOfTheAppointmentL = addLabel("", 470, 500, 300, 45, this);

    Label paymentMethod = addLabel("Payment Method:", 200, 560, 270, 50, this);
    Choice cash = addChoice("Cash|Credit card", 500, 570, 200, 30, this);

    Button back = addButton("Go Back", 50, 650, 230, 80, this);
    Button confirm = addButton("Confirm", 730, 650, 230, 80, this);

    public void actions(Object source, String command) {
        if (source == back) {
            dispose();
            new DoctorMenu(NS);
        }
        if (source == confirm) {
            confirmAppointment();
        }
        if (source == typeOfTheTreatmentC) {
            setPrice();
        }
    }

    private void confirmAppointment() {
        boolean c1 = true, c2 = true;

        //iput all data in the program
        String pName = fullName.getText();
        String pDoB = dateOfBirth.getText();
        String pID = personalID.getText();
        String pDate = addAppointmentDateT.getText();//22.12.2022
        String pTime = addAppointmentTimeT.getText();//15:30
        String pTreatment = typeOfTheTreatmentC.getSelectedItem();
        String pDoctor = doctorT.getText();
        String pPrice = priceOfTheAppointmentL.getText();//80.50
        String pPayment = cash.getSelectedItem();
        //data validation - check date format
        int lengthD = pDate.length(); //lengthD = 10
        int index1D = pDate.indexOf(".");
        int index2D = pDate.lastIndexOf(".");
        if (lengthD != 10 || index1D == -1 || index2D == -1 || index1D == index2D) {
            c1 = false;
        }
        //data validation - check time format
        int lengthT = pTime.length(); //lengthT=5
        int indexT = pTime.indexOf(":");
        if (lengthT != 5 || indexT == -1) {
            c2 = false;
        }

        if (c1 && c2) {
            try {
                RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
                file.seek(file.length());
                file.writeBytes(pName + "\n");
                file.writeBytes(pDoB + "\n");
                file.writeBytes(pID + "\n");
                file.writeBytes(pDate + "\n");
                file.writeBytes(pTime + "\n");
                file.writeBytes(pTreatment + "\n");
                file.writeBytes(pDoctor + "\n");
                file.writeBytes(pPrice + "\n");
                file.writeBytes(pPayment + "\n");
            } catch (Exception e) {
                output("File not found!");
            }
            dispose();
            new DoctorMenu(NS);
        } else {
            output("Incorrect input!");
        }
    }

    private void createTreatmentsList() {
        try {
            RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
            while (file.getFilePointer() != file.length()) {
                /*
               Example fo file records for one treatment
               Left & Right Heart Study
               50
               120.50             
                 */
                typeOfTheTreatmentC.add(file.readLine());
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void setPrice() {
        //search selected appointment in "Treatment.txt" file and
        //set appointments price 
        try {
            RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
            while (file.getFilePointer() != file.length()) {
                /*
               Example fo file records for one treatment
               Left & Right Heart Study
               50
               120.50             
                 */
                if (file.readLine().equals(typeOfTheTreatmentC.getSelectedItem())) {
                    file.readLine();
                    priceOfTheAppointmentL.setText(file.readLine());
                    break;
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }
}
