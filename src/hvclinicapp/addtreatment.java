package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class addtreatment extends EasyApp {

    public addtreatment() {
        setTitle("H&V clinic management system | Admin Main Page | Add Treatment");
        setBackground(new Color(242, 242, 242));
        setSize(1100, 690);
        setLocationRelativeTo(null);
        setResizable(false);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));

        listOfTreatments.setFont(new Font("TimesRoman", 0, 22));
        listOfTreatmentsL.setFont(new Font("TimesRoman", 0, 20));
        listOfTreatments.setBackground(new Color(242, 242, 242));

        Name.setBackground(new Color(242, 242, 242));
        Name.setFont(new Font("TimesRoman", 0, 22));
        NameT.setFont(new Font("TimesRoman", 0, 22));
        Duration.setBackground(new Color(242, 242, 242));
        Duration.setFont(new Font("TimesRoman", 0, 22));
        DurationT.setFont(new Font("TimesRoman", 0, 22));
        Min.setBackground(new Color(242, 242, 242));
        Min.setFont(new Font("TimesRoman", 0, 22));
        Price.setBackground(new Color(242, 242, 242));
        Price.setFont(new Font("TimesRoman", 0, 22));
        PriceT.setFont(new Font("TimesRoman", 0, 22));
        view.setBackground(new Color(163, 155, 143));
        view.setFont(new Font("TimesRoman", 0, 27));
        delete.setBackground(new Color(163, 155, 143));
        delete.setFont(new Font("TimesRoman", 0, 27));
        register.setBackground(new Color(163, 155, 143));
        register.setFont(new Font("TimesRoman", 0, 27));
        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 27));
        //create List of Treatments
        listOfTreatments();
    }

    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/adminAddTreatment.JPG"));
    JLabel Pic = addJLabel(Icon, 130, 50, 841, 106, this);
    ImageIcon IconTreatment = new ImageIcon(getClass().getResource("Pictures/treatment.png"));
    Label listOfTreatments = addLabel("List of Treatments", 50, 190, 250, 40, this);
    List listOfTreatmentsL = addList("", 50, 240, 300, 250, this);
    Button view = addButton("View Info", 50, 510, 300, 70, this);
    Button delete = addButton("Delete", 50, 590, 300, 70, this);

    Label Name = addLabel("Name", 450, 200, 150, 60, this);
    TextField NameT = addTextField("", 600, 205, 440, 46, this);
    Label Duration = addLabel("Duration", 450, 270, 150, 60, this);
    TextField DurationT = addTextField("", 600, 275, 100, 46, this);
    Label Min = addLabel("minutes", 707, 270, 150, 60, this);
    Label Price = addLabel("Price", 450, 340, 150, 60, this);
    TextField PriceT = addTextField("", 600, 345, 150, 46, this);

    Button register = addButton("Register", 750, 430, 210, 60, this);
    Button back = addButton("Go back", 840, 570, 210, 60, this);

    public void actions(Object source, String command) {
        if (source == back) {
            new admin();
            dispose();
        }
        if (source == register) {
            registerTreatment();
        }
        if (source == view) {
            viewTreatment();
        }
        if (source == delete) {
            deleteTreatment();
        }
    }

    private void registerTreatment() {
        String name = NameT.getText().trim();
        String duration = DurationT.getText().trim();
        String price = PriceT.getText().trim();
        //data validation
        if (name.equals("") || duration.equals("") || price.equals("")) {
            output("Please, fill in all fields!");
        } else {
            //duration should be integer type number
            try {
                int durat = Integer.parseInt(duration);
                double pr = Double.parseDouble(price);
                //save treatment info in "Treatment.txt" file
                try {
                    RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
                    file.seek(file.length());
                    file.writeBytes(name + "\n");
                    file.writeBytes(duration + "\n");
                    file.writeBytes(price + "\n");
                } catch (Exception e) {
                    output("File not found!");
                }
                listOfTreatments();
                NameT.setText("");
                DurationT.setText("");
                PriceT.setText("");
            } catch (NumberFormatException e) {
                output("Please, enter numbers for duration and price!");
            }
        }
    }

    private void listOfTreatments() {
        listOfTreatmentsL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
            while (file.getFilePointer() != file.length()) {
                listOfTreatmentsL.add(file.readLine());
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void viewTreatment() {
        String t = listOfTreatmentsL.getSelectedItem();//selected treatment
        String sum = "";
        //data validation
        if (t == null) {
            output("Please, select a treatment to view it's info!");
        } else {
            //use sequential search to fined selected patient
            try {
                RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
                /*
            example of patient's records in file:
                Left & Right Heart Study
                50
                120.50
                 */
                while (file.length() != file.getFilePointer()) {
                    String N = file.readLine();
                    String dur = file.readLine();
                    String price = file.readLine();

                    if (t.equals(N)) {
                        //this is selected patient
                        sum = N + "\nDuration: " + dur + " sec\nPrice: " + price + "\n";
                        break;
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
            //Display Treatment info
            JOptionPane.showMessageDialog(null,
                    sum,
                    "Treatment:",
                    JOptionPane.INFORMATION_MESSAGE,
                    IconTreatment);
        }
    }
    ArrayList<String> treatmentList = new ArrayList<String>();

    /*
    Temporarily store all the records from the "Treatment.txt" file in this ArrayList, 
    except for the records belonging to the treatment to be deleted. 
    Then clean the file/delete all records from file;
    and write from the ArrayList elements back to the file. 
    The selected treatment will be deleted.
     */
    private void deleteTreatment() {
        String t = listOfTreatmentsL.getSelectedItem();//selected treatment       
        //data validation
        if (t == null) {
            output("Please, select a treatment to delete it!");
        } else {

            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you really want to delete this maintenance?",
                    "Delete treatment!",
                    JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                //use sequential search to fined selected patient
                try {
                    RandomAccessFile file = new RandomAccessFile("Treatment.txt", "rw");
                    /*
                    example of patient's records in file:
                    Left & Right Heart Study
                    50
                    120.50
                     */
                    while (file.length() != file.getFilePointer()) {
                        String N = file.readLine();
                        String dur = file.readLine();
                        String price = file.readLine();
                        if (!N.equals(t)) {
                            treatmentList.add(N);
                            treatmentList.add(dur);
                            treatmentList.add(price);
                        }
                    }
                    //remove all records from file
                    file.setLength(0);

                    //write records in file
                    for (int i = 0; i < treatmentList.size(); i++) {
                        file.writeBytes(treatmentList.get(i) + "\n");
                    }
                } catch (Exception e) {
                    output("File not found!");
                }
                //update list
                listOfTreatments();
            }
        }
    }
}
