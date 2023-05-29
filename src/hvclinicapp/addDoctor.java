package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class addDoctor extends EasyApp {
    
    public addDoctor() {
        setTitle("H&V clinic management system | Admin Main Page | Add doctor");
        setBackground(new Color(242, 242, 242));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));
        
        listOfDoctors.setFont(new Font("TimesRoman", 0, 22));
        listOfDoctorsL.setFont(new Font("TimesRoman", 0, 20));
        listOfDoctors.setBackground(new Color(242, 242, 242));
        
        fullName.setBackground(new Color(242, 242, 242));
        fullName.setFont(new Font("TimesRoman", 0, 22));
        fullNameT.setFont(new Font("TimesRoman", 0, 22));
        gmail.setBackground(new Color(242, 242, 242));
        gmail.setFont(new Font("TimesRoman", 0, 22));
        gmailT.setFont(new Font("TimesRoman", 0, 22));
        username.setBackground(new Color(242, 242, 242));
        username.setFont(new Font("TimesRoman", 0, 22));
        password.setBackground(new Color(242, 242, 242));
        password.setFont(new Font("TimesRoman", 0, 22));
        generate.setBackground(new Color(163, 155, 143));
        generate.setFont(new Font("TimesRoman", 0, 27));
        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 27));
        register.setBackground(new Color(163, 155, 143));
        register.setFont(new Font("TimesRoman", 0, 27));
        
        view.setBackground(new Color(163, 155, 143));
        view.setFont(new Font("TimesRoman", 0, 27));
        delete.setBackground(new Color(163, 155, 143));
        delete.setFont(new Font("TimesRoman", 0, 27));

        //Create registered doctors list
        createDoctorsList();
    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/adminAddDoctor.JPG"));
    JLabel Pic = addJLabel(Icon, 130, 50, 843, 106, this);
    
    Label listOfDoctors = addLabel("List of Doctors", 50, 190, 250, 40, this);
    List listOfDoctorsL = addList("", 50, 240, 250, 250, this);
    Button view = addButton("View", 50, 510, 250, 70, this);
    Button delete = addButton("Delete", 50, 590, 250, 70, this);
    
    Label fullName = addLabel("Full Name", 400, 200, 200, 60, this);
    TextField fullNameT = addTextField("", 600, 205, 300, 46, this);
    Label gmail = addLabel("Gmail", 400, 270, 200, 60, this);
    TextField gmailT = addTextField("", 600, 275, 300, 46, this);
    Label username = addLabel("Username", 400, 340, 300, 60, this);
    Label password = addLabel("Password", 400, 410, 250, 60, this);
    
    Button generate = addButton("Generate", 750, 370, 230, 70, this);
    Button register = addButton("Register", 780, 490, 230, 70, this);
    
    Button back = addButton("Go Back", 800, 610, 210, 60, this);
    ImageIcon IconDoctorInfo = new ImageIcon(getClass().getResource("Pictures/doctor.png"));
    
    public void actions(Object source, String command) {
        if (source == back) {
            new admin();
            dispose();
        }
        if (source == generate) {
            generateUsernamePass();
        }
        if (source == register) {
            registration();
        }
        if (source == view) {
            viewDoctor();
        }
        if (source == delete) {
            deleteDoctor();
        }
    }
    String n, e, u, p;//Docor's full name, email, esername, password 

    private void generateUsernamePass() {
        //input data in the program
        n = fullNameT.getText().trim();
        e = gmailT.getText().trim();
        //data validation
        if (n.equals("") || e.equals("")) {
            output("Please, input Full name and Email in text fields.");
        } else if (n.indexOf(" ") == -1) {
            output("Please, write name and surname.");
        } else if (!e.endsWith("@gmail.com")) {
            output("Please, input correct gmail.");
        } else {
            /*
            Username = name + 2 characters from surname but capital letters.
            Password = random character from name + random character from surname + 
            2-digit random number + ASCII random character
             */
            //split full name in two parts: name and surname
            int index = n.indexOf(" ");
            String name = n.substring(0, index);//David
            String surname = n.substring(index + 1);//Morgan
            //generate username
            int lenN = name.length();
            int lenS = surname.length();
            int num1 = (int) (Math.random() * lenS);
            int num2 = (int) (Math.random() * lenS);
            u = name + surname.substring(num1, num1 + 1).toUpperCase()
                    + surname.substring(num2, num2 + 1).toUpperCase();

            //generate password
            int num3 = (int) (Math.random() * lenN);
            int num4 = (int) (Math.random() * lenS);
            p = name.substring(num3, num3 + 1).toUpperCase()
                    + surname.substring(num4, num4 + 1).toUpperCase()
                    + ((int) (Math.random() * 90) + 10);
            //ASCII code [33, 47]
            int num5 = (int) (Math.random() * 15) + 33;
            p = p + (char) num5;
            //set username and password in the window
            username.setText("Username       " + u);
            password.setText("Password       " + p);
        }
    }
    
    private void createDoctorsList() {
        listOfDoctorsL.removeAll();
        try {
            RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
            while (file.length() != file.getFilePointer()) {
                listOfDoctorsL.add(file.readLine().substring(11));
                file.readLine();
                file.readLine();
                file.readLine();
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }
    
    private void registration() {
        //send email to user
        String toSend = "Full name: " + n + "\n" + "Username: " + u + "\n" + "Password: " + p + "\n";
        String[] to = {e};
        //Tutorialspoint.com. “JavaMail API - Gmail SMPT Server.”
        //Www.tutorialspoint.com, www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm.
        if (EMailSender.sendMail("javapplication22@gmail.com", "hssnyazdynsjqvue",
                toSend, to)) {
            output("Email sent successfully!");
        } else {
            output("Email not sent!\nTry later.");
        }

        //save all data in "Doctor.txt" file
        //use try catch block to handle errors
        try {
            RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
            file.seek(file.length());//set filepointer at the end of file
            file.writeBytes("Full name: " + n + "\n");
            file.writeBytes("Email: " + e + "\n");
            file.writeBytes("Username: " + u + "\n");
            file.writeBytes("Password: " + p + "\n");
        } catch (Exception e) {
            output("File not found!");
        }
        createDoctorsList();

        //clean used data
        fullNameT.setText("");
        gmailT.setText("");
        username.setText("Username");
        password.setText("Password");
    }
    
    private void viewDoctor() {
        String d = listOfDoctorsL.getSelectedItem();//selected Doctor
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
    
    private void deleteDoctor() {
        int indexOfDoctorToDelete = listOfDoctorsL.getSelectedIndex();
        if (indexOfDoctorToDelete == -1) {
            output("Please, selecte a doctor!");
        } else {
            //create ArrayList to save all doctors all info except that is selected
            ArrayList<String> temp = new ArrayList<String>();
            try {
                RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
                for (int i = 0; i < listOfDoctorsL.getItemCount(); i++) {
                    if (i != indexOfDoctorToDelete) {
                        temp.add(file.readLine());//Name Surname
                        temp.add(file.readLine());//Email
                        temp.add(file.readLine());//Username
                        temp.add(file.readLine());//Password
                    }
                }
                //delete all records from "Doctor.txt" file
                file.setLength(0);

                //write backk all data to "Doctor.txt" file from ArrayList
                for (int i = 0; i < temp.size(); i++) {
                    file.writeBytes(temp.get(i) + "\n");                    
                }
            } catch (Exception e) {
                output("File not found!");
            }
            listOfDoctorsL.remove(indexOfDoctorToDelete);
        }
    }
}
