package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class incomes extends EasyApp {

    /*
    This class is responsible for showing the income received by doctors.
    The income is determined by the year and month marked by the admin, 
    as well as by the payment/price of the appointments that the doctor conducted.
    The list of doctors shows the names and surnames of all registered doctors 
    and the income they brought to the clinic as of a given date.
     */
    int doctorQuantity;

    public incomes(int docQ) {
        doctorQuantity = docQ;
        doctorsIncome = new String[doctorQuantity][2];
        setTitle("H&V clinic management system | Admin main page");
        setBackground(new Color(242, 242, 242));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));

        Year.setFont(new Font("TimesRoman", 0, 22));
        Year.setBackground(new Color(242, 242, 242));
        yearC.setFont(new Font("TimesRoman", 0, 18));
        Month.setFont(new Font("TimesRoman", 0, 22));
        Month.setBackground(new Color(242, 242, 242));
        monthC.setFont(new Font("TimesRoman", 0, 18));
        totalIncome.setFont(new Font("TimesRoman", 0, 22));
        totalIncome.setBackground(new Color(242, 242, 242));
        tmvd.setFont(new Font("TimesRoman", 0, 22));
        tmvd.setBackground(new Color(242, 242, 242));

        listOfDoctors.setFont(new Font("TimesRoman", 0, 22));
        listOfDoctorsL.setFont(new Font("TimesRoman", 0, 20));
        listOfDoctors.setBackground(new Color(242, 242, 242));

        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 27));
        //initializes array with Doctors names&surname and 0 income
        initializeDoctorsArray();
        //calculates evry doctor's income by selected month and year
        calculateDoctorsIncome();
        //calculate total income
        calculateTotalIncome();
        //calculate the most profitable doctor
        calculateMostProfitableDoctor();
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/adminViewIncome.PNG"));
    JLabel Pic = addJLabel(Icon, 130, 50, 850, 95, this);

    Label Year = addLabel("Year", 50, 190, 70, 40, this);
    Choice yearC = addChoice("2022|2023|2024|2025", 120, 192, 150, 40, this);
    Label Month = addLabel("Month", 400, 190, 70, 40, this);
    Choice monthC = addChoice("January|February|March|April|May|June|July|August|"
            + "September|October|November|December", 480, 192, 170, 40, this);

    Label totalIncome = addLabel("Total Income:", 470, 330, 300, 60, this);
    Label tmvd = addLabel("The most profitable doctor:", 470, 420, 500, 60, this);

    Label listOfDoctors = addLabel("List of Doctors", 50, 250, 250, 40, this);
    List listOfDoctorsL = addList("", 50, 300, 280, 250, this);

    Button back = addButton("Go back", 70, 580, 210, 60, this);
    //2D array to create list of doctors with incomes according to year and month elected by admin
    String[][] doctorsIncome;

    public void actions(Object source, String command) {
        if (source == back) {
            new admin();
            dispose();
        }
        if (source == yearC || source == monthC) {
            initializeDoctorsArray();
            calculateDoctorsIncome();
            calculateTotalIncome();
            calculateMostProfitableDoctor();
        }
    }

    /*
    Postcondition:
        as a result all Doctors' name&surname are written in doctorsIncome array
        in first column
        and the second column values are "0"
     */
    private void initializeDoctorsArray() {
        try {
            RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
            for (int i = 0; i < doctorQuantity; i++) {
                doctorsIncome[i][0] = file.readLine().substring(11);
                doctorsIncome[i][1] = "0";
                file.readLine();
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void calculateDoctorsIncome() {
        //get Year and Month to calculate doctors income
        String y = yearC.getSelectedItem();//example: 2022
        String m = monthC.getSelectedItem();//example: December
        System.out.println("* " + y + " - " + m);
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
            /*
            example of record in file about one appointment:
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
            while (file.getFilePointer() != file.length()) {
                file.readLine();
                file.readLine();
                file.readLine();
                String date = file.readLine();
                file.readLine();
                file.readLine();
                String doc = file.readLine();
                String price = file.readLine();
                file.readLine();
                //check if the appointment date is equal to selected date
                boolean c = checkDate(date, y, m);
                if (c == true) {
                    //search for doctor and increase his/her income
                    for (int i = 0; i < doctorsIncome.length; i++) {
                        if (doctorsIncome[i][0].equals(doc)) {
                            double doctOldIncome = Double.parseDouble(doctorsIncome[i][1]);
                            double doctPrice = Double.parseDouble(price);
                            doctorsIncome[i][1] = "" + (doctOldIncome + doctPrice);
                        }
                    }
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
        UpdateList();
    }

    private boolean checkDate(String date, String y, String m) {
        //22.12.2022 2022 January
        //split date in Year and Month part
        int index1 = date.indexOf(".");
        int index2 = date.lastIndexOf(".");
        String dateY = date.substring(index2 + 1);//2022
        String dateM = date.substring(index1 + 1, index2);//12
        int month = 0;
        String[] monthArray = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < 12; i++) {
            if (m.equals(monthArray[i])) {
                month = i + 1;
                break;
            }
        }
        String mm;
        if (month <= 9) {
            mm = "0" + month;
        } else {
            mm = "" + month;
        }

        if (dateY.equals(y) && dateM.equals(mm)) {
            return true;
        } else {
            return false;
        }
    }

    private void UpdateList() {
        listOfDoctorsL.removeAll();
        for (int i = 0; i < doctorsIncome.length; i++) {
            listOfDoctorsL.add("Dr. " + doctorsIncome[i][0] + " - " + doctorsIncome[i][1]);
        }
    }

    private void calculateTotalIncome() {
        double totalInc = 0;
        for (int i = 0; i < doctorsIncome.length; i++) {
            totalInc = totalInc + Double.parseDouble(doctorsIncome[i][1]);
        }
        totalIncome.setText("Total Income: " + totalInc);
    }

    private void calculateMostProfitableDoctor() {
        double maxIncome = 0;
        int index = -1;
        for (int i = 0; i < doctorsIncome.length; i++) {
            if (Double.parseDouble(doctorsIncome[i][1]) > maxIncome) {
                maxIncome = Double.parseDouble(doctorsIncome[i][1]);
                index = i;
            }
        }
        if (maxIncome == 0) {
            tmvd.setText("The most profitable doctor: ");
        } else {
            tmvd.setText("The most profitable doctor: " + doctorsIncome[index][0]);
        }
    }
}
