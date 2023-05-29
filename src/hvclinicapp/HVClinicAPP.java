package hvclinicapp;
//use of Java Library classes

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * H&V clinic management app
 *
 * @author IB DP student 
 * January, 2023 
 * NetBeans IDE
 */
public class HVClinicAPP extends EasyApp {

    /*
    http://ibcomp.fis.edu/Java/EasyApp.html
    EasyApp class will be used as a super class for classes
    created in this package to use methods for GUI
     */
    public static void main(String[] args) {
        //create the fisrt object - first window for the app
        new HVClinicAPP();
    }

    //class constructor
    public HVClinicAPP() {
        setTitle("H&V clinic management system");
        setBackground(new Color(242, 242, 242));
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        register.setBackground(new Color(163, 155, 143));
        register.setFont(new Font("TimesRoman", 0, 27));
        login.setBackground(new Color(163, 155, 143));
        login.setFont(new Font("TimesRoman", 0, 27));
    }

    //set GUI elements in the window
    //add image in the window
    ImageIcon Icon = new ImageIcon(getClass().getResource("Pictures/title.PNG"));
    JLabel Pic = addJLabel(Icon, 100, 100, 700, 133, this);

    Button register = addButton("Register", 180, 330, 230, 80, this);
    Button login = addButton("Log In", 500, 330, 230, 80, this);

    public void actions(Object source, String command) {
        //define actions for buttons 
        if (source == register) {
            dispose();//close the window
            new register();
        }
        if (source == login) {
            dispose();//close the window
            new Login();
        }
    }
}

