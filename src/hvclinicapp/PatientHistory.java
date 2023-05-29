package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;

public class PatientHistory extends EasyApp {

    private String NS;

    public PatientHistory(String p) {
        NS = p;
        setTitle("H&V clinic management system | " + p + " History");
        setBackground(new Color(242, 242, 242));
        setSize(500, 610);
        setLocationRelativeTo(null);
        setResizable(false);
        patient.setText(NS + " - History");
        patient.setFont(new Font("TimesRoman", 0, 22));
        patient.setBackground(new Color(242, 242, 242));
        createHistory();
        history.setFont(new Font("TimesRoman", 0, 20));
        back.setFont(new Font("TimesRoman", 0, 22));
    }
    Label patient = addLabel("", 50, 50, 300, 50, this);
    List history = addList("", 50, 110, 400, 350, this);
    Button back = addButton("Back", 50, 500, 200, 70, this);

    private void createHistory() {
        history.removeAll();
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
                if (NS.equals(FN)) {
                    //this is selected patient
                    history.add("DoB: " + db);
                    history.add("ID: " +id);
                    history.add("Date: " +date);
                    history.add("Time: " +time);
                    history.add(tritment);
                    history.add("Doctor: " + d);
                    history.add("Price: " + price);
                    history.add(ToP);
                    history.add("");
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    public void actions(Object source, String command) {
        if (source == back) {
            new admin();
            dispose();
        }
    }
}
