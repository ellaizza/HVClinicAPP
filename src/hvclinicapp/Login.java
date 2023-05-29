package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends EasyApp {

    public Login() {
        setTitle("H&V clinic management system | Log in");
        setBackground(new Color(242, 242, 242));
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));
        UNGmail.setBackground(new Color(242, 242, 242));
        UNGmail.setFont(new Font("TimesRoman", 0, 22));

        password.setBackground(new Color(242, 242, 242));
        password.setFont(new Font("TimesRoman", 0, 22));
        UNGmailT.setFont(new Font("TimesRoman", 0, 22));
        passwordT.setFont(new Font("TimesRoman", 0, 22));
        passwordT.setEchoChar('*');
        resetPass.setBackground(new Color(242, 242, 242));
        resetPass.setFont(new Font("TimesRoman", 0, 22));
        resetPass.setForeground(new Color(0, 112, 192));

        back.setBackground(new Color(163, 155, 143));
        back.setFont(new Font("TimesRoman", 0, 27));

        login.setBackground(new Color(163, 155, 143));
        login.setFont(new Font("TimesRoman", 0, 27));

    }
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/login.PNG"));
    JLabel Pic = addJLabel(Icon, 100, 70, 700, 112, this);

    Label UNGmail = addLabel("Username or Gmail", 170, 240, 200, 60, this);
    TextField UNGmailT = addTextField("", 390, 245, 330, 46, this);//MananaHaV
    Label password = addLabel("Password", 170, 310, 200, 60, this);
    TextField passwordT = addTextField("", 390, 315, 330, 46, this);//HaVIA

    Label resetPass = addLabel("Reset password", 390, 380, 200, 60, this);
    Button back = addButton("Go Back", 100, 470, 230, 80, this);
    Button login = addButton("Log In", 580, 470, 230, 80, this);

    ImageIcon IconP = new ImageIcon(getClass().getResource("Pictures/lock.png"));

    public void actions(Object source, String command) {
        if (source == back) {
            dispose();
            new HVClinicAPP();
        }
        if (source == resetPass) {
            //method checks username or Gmail, generates new password and sends to Doctor's email
            resetPassword();
        }
        if (source == login) {
            loginCheck();
        }
    }
    String usernameOrEmail;

    private void resetPassword() {
        boolean find = false;
        usernameOrEmail = UNGmailT.getText();
        //data validation - presence check 
        if (usernameOrEmail.equals("")) {
            output("Please input Username or Gmail to resest password!");
        } else {
            //search data in the Doctor.txt file - I use linear search algorithm for txt file
            String nameSurname = "", email = "", username = "", newPass = "";
            try {
                RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
                while (file.getFilePointer() != file.length()) {
                    nameSurname = file.readLine().substring(11);
                    email = file.readLine().substring(7);
                    username = file.readLine().substring(10);
                    file.readLine();

                    if (email.equals(usernameOrEmail) || username.equals(usernameOrEmail)) {
                        find = true;
                        break;
                    }
                }
                if (find == true) {
                    //reset password and send on email

                    /*  Password = random character from name + random character 
                    from surname + 2-digit random number + ASCII random character
                     */
                    //split full name in two parts: name and surname
                    int index = nameSurname.indexOf(" ");
                    String name = nameSurname.substring(0, index);//David
                    String surname = nameSurname.substring(index + 1);//Morgan
                    //generate username
                    int lenN = name.length();
                    int lenS = surname.length();

                    //generate password
                    int num3 = (int) (Math.random() * lenN);
                    int num4 = (int) (Math.random() * lenS);
                    newPass = name.substring(num3, num3 + 1).toUpperCase()
                            + surname.substring(num4, num4 + 1).toUpperCase()
                            + ((int) (Math.random() * 90) + 10);
                    //ASCII code [33, 47]
                    int num5 = (int) (Math.random() * 15) + 33;
                    newPass = newPass + (char) num5;
                    //Display new Password
                    JOptionPane.showMessageDialog(null,
                            "Your new password is:\n" + newPass + "\n\nEmail is already sent!",
                            "Reset Password",
                            JOptionPane.INFORMATION_MESSAGE,
                            IconP);
                    //create void method with parameter
                    SaveChangesInFile(newPass);
                    //send email to Doctor
                    String toSend = "Your new password is:\n" + newPass;
                    String[] to = {email};
                    //Tutorialspoint.com. “JavaMail API - Gmail SMPT Server.”
                    //Www.tutorialspoint.com, www.tutorialspoint.com/javamail_api/javamail_api_gmail_smtp_server.htm.
                    if (EMailSender.sendMail("javapplication22@gmail.com", "hssnyazdynsjqvue",
                            toSend, to)) {
                        output("Email sent successfully!");
                    } else {
                        output("Email not sent!\nTry later.");
                    }
                } else {
                    output("Unfortunately, You are not registered in the "
                            + "system!\nPlease, register at first!");
                }
            } catch (Exception e) {
                output("File not found!");
            }
        }

    }
    //I will use ArrayList for storing the records from  Doctor.txt file

    private void SaveChangesInFile(String newPass) {
        //search and delete record from file
        String usernameOrEmail = UNGmailT.getText();
        ArrayList doctorsData = new ArrayList();
        try {
            RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
            while (file.getFilePointer() != file.length()) {
                String nameSurname = file.readLine();
                String email = file.readLine().substring(7);
                String username = file.readLine().substring(10);
                String pass = file.readLine();
                if (email.equals(usernameOrEmail) || username.equals(usernameOrEmail)) {
                    doctorsData.add(nameSurname);
                    doctorsData.add("Email: " + email);
                    doctorsData.add("Username: " + username);
                    doctorsData.add("Password: " + newPass);
                } else {
                    doctorsData.add(nameSurname);
                    doctorsData.add("Email: " + email);
                    doctorsData.add("Username: " + username);
                    doctorsData.add("Password: " + pass);
                }
            }
            //delete all records in "Doctor.txt"
            file.setLength(0);
            //write Doctor's data from ArrayList back into the file
            for (int i = 0; i < doctorsData.size(); i++) {
                file.writeBytes(doctorsData.get(i) + "\n");
            }
        } catch (Exception e) {
            output("File not found!");
        }
    }
    String nameSurname;

    private void loginCheck() {
        boolean check = false;
        //input data in the program
        String userNorE = UNGmailT.getText();
        String pass = passwordT.getText();
        if (userNorE.equals("")) {
            UNGmailT.setBackground(new Color(184, 68, 85));
        } else if (pass.equals("")) {
            passwordT.setBackground(new Color(184, 68, 85));
        } else if (userNorE.equals("MananaHaV") && pass.equals("HaVIA")) {
            //this user is an Admin
            new admin();
            dispose();
        } else {
            //data varification    
            try {
                RandomAccessFile file = new RandomAccessFile("Doctor.txt", "rw");
                while (file.getFilePointer() != file.length()) {
                    String nameSur = file.readLine().substring(11);
                    String email = file.readLine().substring(7);
                    String username = file.readLine().substring(10);
                    String passw = file.readLine().substring(10);
                    if ((userNorE.equals(email) || userNorE.equals(username)) && pass.equals(passw)) {
                        check = true;
                        nameSurname = nameSur;
                    }
                }
            } catch (Exception e) {
                output("File not found!");
            }
            if (check == true) {
                new DoctorMenu(nameSurname);
                dispose();
            } else {
                output("Username/Email and/or password is incorrect.\nPlease, register or try again!");
            }
        }
    }
}
