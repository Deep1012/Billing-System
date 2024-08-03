package restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    JLabel label1,label2,label3;
    JTextField textfield1;
    JPasswordField passwordField2;
    JButton button1,button2,button3;

    Login(){
        super("Restaurant Billing System");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource(""));
        Image i2 = i1.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350,10,100,100);
        add(image);


        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/card.png"));
        Image ii2 = ii1.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(630,350,100,100);
        add(iimage);


        label1 = new JLabel("Welcome to GetBill");
        label1.setForeground(Color.white);
        label1.setFont(new Font("Arial",Font.BOLD,38));
        label1.setBounds(230,125,450,40);
        add(label1);

        label2 = new JLabel("Admin Username -  ");
        label2.setFont(new Font("Arial",Font.BOLD,18));
        label2.setForeground(Color.white);
        label2.setBounds(140,190,375,30);
        add(label2);

        textfield1 = new JTextField(15);
        textfield1.setBounds(325,190,230,30);
        textfield1.setFont(new Font("Arial",Font.BOLD,14));
        add(textfield1);

        label3 = new JLabel("Admin Password - ");
        label3.setFont(new Font("Arial",Font.BOLD,18));
        label3.setForeground(Color.white);
        label3.setBounds(140,250,375,30);
        add(label3);

        passwordField2 = new JPasswordField(15);
        passwordField2.setBounds(325,250,230,30);
        passwordField2.setFont(new Font("arial",Font.BOLD,14));
        add(passwordField2);

        button1 = new JButton("Sign In");
        button1.setFont(new Font("arial",Font.BOLD,14));
        button1.setBounds(330,300,100,30);
        button1.setForeground(Color.white);
        button1.setBackground(Color.black);
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("Clear");
        button2.setFont(new Font("arial",Font.BOLD,14));
        button2.setBounds(440,300,100,30);
        button2.setForeground(Color.white);
        button2.setBackground(Color.black);
        button2.addActionListener(this);
        add(button2);


        ImageIcon iii1 = new ImageIcon(ClassLoader.getSystemResource("icon/cover-1.jpg"));
        Image iii2 = iii1.getImage().getScaledInstance(850,480, Image.SCALE_DEFAULT);
        ImageIcon iii3 = new ImageIcon(iii2);
        JLabel iiimage = new JLabel(iii3);
        iiimage.setBounds(0,0,850,480);
        add(iiimage);





        setLayout(null);
        setSize(850,480);
        setVisible(true);
        setLocation(450,200);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = textfield1.getText();
        String password = passwordField2.getText();

        try{
            if(e.getSource() == button1) {
                try
                {
                    if(username.equals("admin") && password.equals("12345"))
                    {
                        JOptionPane.showMessageDialog(null,"Welcome Admin");
                        new RestaurantMenu();
                        setVisible(false);

                    }
                    else {
                        JOptionPane.showMessageDialog(null," Incorrect ");
                    }

                }catch (Exception a)
                {
                    a.printStackTrace();
                }


            }
            else if(e.getSource() == button2) {
                textfield1.setText("");
                passwordField2.setText("");
            }


        }catch(Exception E){
            E.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new restaurant.Login();
    }
}

