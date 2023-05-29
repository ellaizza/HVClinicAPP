package hvclinicapp;

import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class register extends EasyApp {

    public register() {
        setTitle("H&V clinic management system | Register in the program");
        setBackground(new Color(242, 242, 242));
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
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
        next.setBackground(new Color(163, 155, 143));
        next.setFont(new Font("TimesRoman", 0, 27));
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 19));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 15));
    }

    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/register.PNG"));
    JLabel Pic = addJLabel(Icon, 100, 70, 700, 100, this);

    Label fullName = addLabel("Full Name", 200, 200, 200, 60, this);
    TextField fullNameT = addTextField("", 400, 205, 300, 46, this);
    Label gmail = addLabel("Gmail", 200, 270, 200, 60, this);
    TextField gmailT = addTextField("", 400, 275, 300, 46, this);
    Label username = addLabel("Username", 200, 340, 300, 60, this);
    Label password = addLabel("Password", 200, 410, 250, 60, this);

    Button generate = addButton("Generate", 550, 370, 230, 80, this);
    Button back = addButton("Go Back", 100, 490, 230, 80, this);
    Button next = addButton("Next", 580, 490, 230, 80, this);

    public void actions(Object source, String command) {
        if (source == back) {
            dispose();
            new HVClinicAPP();
        }
        if (source == generate) {
            generateUsernamePass();
        }
        if (source == next) {
            //save all data in txt file and send to user's email
            finalisationRegistration();
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

    private void finalisationRegistration() {
        //send email to user
        String toSend = "Full name: " + n + "\n" + "Username: " + u + "\n" + "Password: " + p + "\n";
        String[] to = {e};
        System.out.println("** " + e);
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
        //go to Login page
        new Login();
        dispose();
    }
}
