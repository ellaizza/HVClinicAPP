package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;

public class seeAllAppointments extends EasyApp {

    String doc;//Lisa Castro

    public seeAllAppointments(String nameS) {
        doc = nameS;
        setTitle(doc + "'s Appointments");
        setBackground(new Color(242, 242, 242));
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        doctor.setText(doc + " Appointments");
        doctor.setFont(new Font("TimesRoman", 0, 22));
        doctor.setBackground(new Color(242, 242, 242));
        appointmentsD.setFont(new Font("TimesRoman", 0, 18));
        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 22));
        createListOfAppointments();
    }
    Label doctor = addLabel("", 50, 50, 300, 50, this);
    List appointmentsD = addList("", 50, 110, 400, 350, this);
    Button back = addButton("Back", 50, 500, 200, 70, this);

    private void createListOfAppointments() {
        appointmentsD.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
            /*
            example of patient's history records in file:
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
                String FN = file.readLine().substring(11);
                String db = file.readLine();
                String id = file.readLine();
                String date = file.readLine();
                String time = file.readLine();
                String tritment = file.readLine();
                String d = file.readLine();
                String price = file.readLine();
                String ToP = file.readLine();
                if (doc.equals(d)) {
                    //this is selected patient
                    appointmentsD.add("Patient: " + FN);
                    appointmentsD.add("DoB: " + db);
                    appointmentsD.add("ID: " + id);
                    appointmentsD.add("Date: " + date);
                    appointmentsD.add("Time: " + time);
                    appointmentsD.add(tritment);
                    appointmentsD.add("Price: " + price);
                    appointmentsD.add(ToP);
                    appointmentsD.add("");
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    public void actions(Object source, String command) {
        if (source == back) {
            new DoctorMenu(doc);
            dispose();
        }
    }
}
