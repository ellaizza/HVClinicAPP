package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class checkCalendar extends EasyApp {

    private String nameS;

    public checkCalendar(String nS) {
        setTitle("H&V clinic management system | Welcome. " + nS + "!");
        nameS = nS;
        setBackground(new Color(242, 242, 242));
        setSize(1100, 760);
        setLocationRelativeTo(null);
        setResizable(false);
        appointmentList.setFont(new Font("TimesRoman", 0, 25));
        appointmentList.setBackground(new Color(242, 242, 242));
        appList.setFont(new Font("TimesRoman", 0, 22));
        left.setFont(new Font("TimesRoman", 0, 25));
        left.setBackground(new Color(242, 242, 242));
        monthL.setFont(new Font("TimesRoman", 0, 27));
        monthL.setBackground(new Color(242, 242, 242));
        right.setFont(new Font("TimesRoman", 0, 25));
        right.setBackground(new Color(242, 242, 242));
        info.setFont(new Font("TimesRoman", 0, 25));
        back.setFont(new Font("TimesRoman", 0, 25));
        today.setFont(new Font("TimesRoman", 0, 22));
        today.setBackground(new Color(242, 242, 242));
        for (int i = 0; i < 7; i++) {
            calDays[i] = addLabel(days[i], 50 + i * 70, 310, 70, 50, this);
            calDays[i].setFont(new Font("TimesRoman", 0, 23));
            calDays[i].setBackground(new Color(163, 155, 143));
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                calendarLabels[i][j] = addLabel("", 50 + j * 70, 370 + i * 50, 70, 50, this);
                calendarLabels[i][j].setFont(new Font("TimesRoman", 0, 22));
            }
        }
        manageDate();
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/calendar.PNG"));
    JLabel Pic = addJLabel(Icon, 150, 50, 793, 125, this);
    ImageIcon IconApp = new ImageIcon(getClass().getResource("Pictures/doctor-appointment.png"));
    String days[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String monthF[] = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};
    //Calendar GUI
    Button left = addButton("<<", 50, 240, 70, 50, this);
    Label monthL = addLabel("", 150, 240, 220, 50, this);
    Button right = addButton(">>", 380, 240, 70, 50, this);
    Label[] calDays = new Label[7];
    Label[][] calendarLabels = new Label[6][7];
    Label today = addLabel("", 50, 690, 300, 50, this);
    //Right side
    Label appointmentList = addLabel("Appointment List", 650, 240, 300, 50, this);
    List appList = addList("", 650, 300, 350, 250, this);
    Button info = addButton("Info", 800, 570, 200, 60, this);
    Button back = addButton("back", 850, 680, 200, 60, this);

    int monthFirstDayIndex;//first day index in days[] array for month thay is displayed

    private void manageDate() {
        Date d = new Date();
        System.out.println(d);//Tue Jan 10 14:23:13 GET 2023
        //split d in 4 parts: Weekday Month Day Year
        int index1 = d.toString().indexOf(" ");
        int index2 = d.toString().indexOf(" ", index1 + 1);
        int index3 = d.toString().indexOf(" ", index2 + 1);

        String weekday = d.toString().substring(0, index1);//Tue
        String month = d.toString().substring(index1 + 1, index2);//Jan
        String day = d.toString().substring(index2 + 1, index3);//10
        String year = d.toString().substring(d.toString().length() - 4);//2023
        today.setText(day + " " + month + " " + year);

        //search for weekday index
        int weekdayIndex = -1;
        for (int i = 0; i < 7; i++) {
            if (weekday.equals(days[i])) {
                weekdayIndex = i;
                break;
            }
        }
        monthFirstDayIndex = weekdayIndex + 1 - Integer.parseInt(day) % 7;
        int daysInthisMonth = 0;
        String monthName = "";
        for (int i = 0; i < 12; i++) {
            if (month.equals(months[i])) {
                if (i == 1) {//this is February
                    if (Integer.parseInt(year) % 4 == 0) {//leap year
                        daysInthisMonth = 29;
                    } else {
                        daysInthisMonth = 28;
                    }
                } else {
                    daysInthisMonth = daysInMonth[i];
                }
                monthName = monthF[i];
                break;
            }
        }
        setCalendar(year, daysInthisMonth, monthName);
    }

    private void setCalendar(String year, int daysInMonth, String monthFullName) {
        //clean the Valendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                calendarLabels[i][j].setText("");
                calendarLabels[i][j].setBackground(new Color(242, 242, 242));
            }
        }
        //Display Month in Calendar
        monthL.setText(monthFullName + ", " + year);
        //set days in calendar
        int countDays = 1;
        for (int i = monthFirstDayIndex; i < 7; i++) {
            calendarLabels[0][i].setText(" " + countDays);
            countDays++;
        }

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (countDays <= daysInMonth) {
                    calendarLabels[i][j].setText(" " + countDays);
                    countDays++;
                }
            }
        }
        FirstDayOldMonth = monthFirstDayIndex;
        daysInOldMonth = daysInMonth;
    }
    int FirstDayOldMonth, daysInOldMonth;

    public void actions(Object source, String command) {
        if (source == back) {
            new DoctorMenu(nameS);
            dispose();
        }
        if (source == left) {
            left();
        }
        if (source == right) {
            right();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (source == calendarLabels[i][j]) {//check appointments for this day
                    if (!calendarLabels[i][j].getText().equals("")) {
                        //remove all selections on the Calendar board 
                        for (int x = 0; x < 6; x++) {
                            for (int y = 0; y < 7; y++) {
                                calendarLabels[x][y].setBackground(new Color(242, 242, 242));
                            }
                        }

                        calendarLabels[i][j].setBackground(Color.red);
                        String d = calendarLabels[i][j].getText().trim();
                        if (d.length() == 1) {
                            d = "0" + d;
                        }
                        String month = monthL.getText().substring(0, monthL.getText().indexOf(","));
                        String year = monthL.getText().substring(monthL.getText().indexOf(",") + 2);
                        for (int k = 0; k < 12; k++) {
                            if (month.equals(monthF[k])) {
                                if (k <= 9) {
                                    checkAppointments(d + ".0" + (k + 1) + "." + year);
                                } else {
                                    checkAppointments(d + "." + (k + 1) + "." + year);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (source == info) {
            appointmentInfo();
        }
    }

    private void checkAppointments(String d) {
        appList.removeAll();
        /*
        Full name: Peter Petersen
        Date of Birth: 12.12.2000
        ID: 12345678944
        15.01.2023
        10:00
        Echocardiogram
        Lisa Castro
        240
        Cash
         */
        String pNS, dob, id, date, time, t, dNS, c, tp;
        try {
            RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
            while (file.length() != file.getFilePointer()) {
                pNS = file.readLine();//Full name: David Borger
                dob = file.readLine();//Date of Birth: 12.06.2000
                id = file.readLine();//ID: 01011034578
                date = file.readLine();
                time = file.readLine();
                t = file.readLine();//Hypertrophic cardiomyopathy
                dNS = file.readLine();//Lisa Castro
                c = file.readLine();//80.50
                tp = file.readLine();//Cash
                if (date.equals(d)) {
                    appList.add(time + " | " + dNS);
                }
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }

    private void left() {
        String month, year, monthName;
        int daysInthisMonth = 0;

        String monthYear = monthL.getText();//January, 2023
        System.out.println(monthYear);
        String mmmm = monthYear.substring(0, monthYear.indexOf(","));//January
        String yyyy = monthYear.substring(monthYear.indexOf(",") + 2);//2023
        int indexMMMM = -1;//index of mmmm
        for (int i = 0; i < 12; i++) {
            if (mmmm.equals(monthF[i])) {
                indexMMMM = i;
            }
        }
        //calculate the month and year of the previous month
        if (indexMMMM == 0) {
            year = "" + (Integer.parseInt(yyyy) - 1);
            month = "Dec";
            monthName = "December";
            indexMMMM = 11;
            daysInthisMonth = 31;
        } else {
            year = yyyy;
            month = months[indexMMMM - 1];
            indexMMMM = indexMMMM - 1;
            monthName = monthF[indexMMMM];
            if (indexMMMM == 1) {
                if (Integer.parseInt(yyyy) % 4 == 0) {
                    daysInthisMonth = 29;
                } else {
                    daysInthisMonth = 28;
                }
            } else {
                daysInthisMonth = daysInMonth[indexMMMM];
            }
        }
        monthFirstDayIndex = monthFirstDayIndex - daysInthisMonth % 7;
        if (monthFirstDayIndex < 0) {
            monthFirstDayIndex = monthFirstDayIndex + 7;
        }
        setCalendar(year, daysInthisMonth, monthName);
    }

    private void right() {
        String month, year, monthName;
        int daysInthisMonth = 0;

        String monthYear = monthL.getText();//January, 2023
        String mmmm = monthYear.substring(0, monthYear.indexOf(","));//January
        String yyyy = monthYear.substring(monthYear.indexOf(",") + 2);//2023
        int indexMMMM = -1;//index of mmmm
        for (int i = 0; i < 12; i++) {
            if (mmmm.equals(monthF[i])) {
                indexMMMM = i;
            }
        }

        //calculate the month and year of the next month
        if (indexMMMM == 11) {
            year = "" + (Integer.parseInt(yyyy) + 1);
            month = "Jan";
            monthName = "January";
            indexMMMM = 0;
            daysInthisMonth = 31;
        } else {
            year = yyyy;
            month = months[indexMMMM + 1];
            indexMMMM = indexMMMM + 1;
            monthName = monthF[indexMMMM];
            if (indexMMMM == 1) {
                if (Integer.parseInt(yyyy) % 4 == 0) {
                    daysInthisMonth = 29;
                } else {
                    daysInthisMonth = 28;
                }
            } else {
                daysInthisMonth = daysInMonth[indexMMMM];
            }
        }

        //   FirstDayOldMonth, daysInOldMonth;
        monthFirstDayIndex = (FirstDayOldMonth + daysInOldMonth % 7) % 7;
        setCalendar(year, daysInthisMonth, monthName);
    }

    private void appointmentInfo() {
        String app = appList.getSelectedItem();
        //data validation
        if (app == null) {
            output("Please, select an appointment to view it's Info.");
        } else {
            System.out.println(app);
            String info = "";
            //10:00 | Lisa Castro
            String pNS, dob, id, date, time, t, dNS, c, tp;
            String sum = "";
            try {
                RandomAccessFile file = new RandomAccessFile("Patients_Appointments.txt", "rw");
                while (file.length() != file.getFilePointer()) {
                    pNS = file.readLine();//Full name: David Borger
                    dob = file.readLine();//Date of Birth: 12.06.2000
                    id = file.readLine();//ID: 01011034578
                    date = file.readLine();
                    time = file.readLine();
                    t = file.readLine();//Hypertrophic cardiomyopathy
                    dNS = file.readLine();//Lisa Castro
                    c = file.readLine();//80.50
                    tp = file.readLine();//Cash
                    if (app.equals(time + " | " + dNS)) {
                        info = pNS + "\n" + dob + "\n" + id + "\nDate: " + date
                                + "\nTime: " + time + "\nTreatment: " + t + "\nDoctor: "
                                + dNS + "\nCost: " + c + "\n" + tp;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        info,
                        "Appointment Info",
                        JOptionPane.INFORMATION_MESSAGE,
                        IconApp);
            } catch (Exception e) {
                output("File not found!");
            }
        }
    }
}
