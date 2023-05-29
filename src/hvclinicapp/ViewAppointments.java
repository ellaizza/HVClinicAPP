package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;

public class ViewAppointments extends EasyApp {

    private String nameS;
    private String id;

    public ViewAppointments(String NAmeSurname, String patientID) {
        nameS = NAmeSurname;
        id = patientID;
        setTitle("H&V clinic management system | " + patientID + "'s appointments!");
        setBackground(new Color(242, 242, 242));
        setSize(580, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 25));
        title.setBackground(new Color(242, 242, 242));
        title.setFont(new Font("TimesRoman", 0, 25));
        appointmentL.setFont(new Font("TimesRoman", 0, 22));
        createAppointmentsList();
    }
    Label title = addLabel("Appointments List", 50, 70, 400, 40, this);
    List appointmentL = addList("", 50, 130, 450, 450, this);
    Button back = addButton("Go Back", 50, 610, 200, 60, this);

    public void actions(Object source, String command) {
        if (source == back) {
            new DoctorMenu(nameS);
            dispose();
        }
    }

    private void createAppointmentsList() {
        appointmentL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
            while (file.getFilePointer() != file.length()) {
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
                String FN = file.readLine();
                String dob = file.readLine();
                String ID = file.readLine();
                String date = file.readLine();
                String t = file.readLine();
                String appName = file.readLine();
                String doctorN = file.readLine();
                String price = file.readLine();
                String pt = file.readLine();

                if (id.equals(ID.substring(4))) {
                    appointmentL.add(FN);
                    appointmentL.add(dob);
                    appointmentL.add(ID);
                    appointmentL.add("Date: " + date);
                    appointmentL.add("Tame: " + t);
                    appointmentL.add("Treatment: " + appName);
                    appointmentL.add("Doctor: " + doctorN);
                    appointmentL.add("Price: " + price);
                    appointmentL.add("Payment type: " + pt);
                    appointmentL.add("");
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }
}
