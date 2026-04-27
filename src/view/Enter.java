package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import engine.Game;
import  java.lang.NullPointerException;
@SuppressWarnings({"serial", "this-escape"})
public class Enter extends JFrame implements ActionListener{
    JButton ok = new JButton("ok");
    JFrame Enter = new JFrame();
    String pname;
    String pcity;
    JTextField playerName;
    JTextField playerCity;

    public Enter(){
        createEnterPage();


    }

    public JFrame createEnterPage(){

        Enter.setLayout(null);
        Enter.setSize(400,300);
        Enter.setLocationRelativeTo(null);
        Enter.setTitle("Welcome");

        JLabel playerNameWelc = new JLabel("Please Enter Your Name");
        playerNameWelc.setBounds(125, 60, 80, 25);
        playerNameWelc.setSize(180, 10);
        Enter.add(playerNameWelc,BorderLayout.WEST);


        playerName =new JTextField(20);
        playerName.setBounds(150 , 80 , 80 , 25);
        Enter.add(playerName, BorderLayout.CENTER);


        JLabel playerCityWelc = new JLabel("Type a city from Cairo, Sparta, or Rome");
        playerCityWelc.setBounds(80, 110, 80, 25);
        playerCityWelc.setSize(250, 20);
        Enter.add(playerCityWelc,BorderLayout.WEST);


        playerCity =new JTextField(20);
        playerCity.setBounds(150 , 140 , 80 , 25);
        Enter.add(playerCity, BorderLayout.CENTER);
        ok.setBounds(150, 190, 80, 25);
        Enter.add(ok);

        Enter.setResizable(false);
        Enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Enter.setVisible(true);
        ok.addActionListener(this);

        return Enter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok){
            Enter.dispose();
            try {
                pname = playerName.getText().trim();
                pcity = playerCity.getText().trim().toLowerCase();
                if(pname.equals("") || pcity.equals("")){
                    new Enter();
                    return;
                }
                if(pcity .equals("Cairo".toLowerCase()) || pcity .equals("Rome".toLowerCase()) || pcity .equals("Sparta".toLowerCase())){
                    Game r = new Game(pname,pcity);
                    new WorldMap(r);
                }
                else{
                    new Enter();
                }
            }catch (IOException e1){

            }
            catch(NullPointerException el){

            }

        }

    }

}
