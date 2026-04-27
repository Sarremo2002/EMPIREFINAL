package view;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.Game;

public class GameOver extends JFrame{
    JFrame GameOver;
    JLabel result;
    public GameOver(Game t){
        GameOver = new JFrame();
        GameOver.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GameOver.setLayout(null);
        GameOver.setLocationRelativeTo(null);
        GameOver.setSize(500, 250);
        if(t.getAvailableCities().size()==t.getPlayer().getControlledCities().size()){
        	result = new JLabel("You Won The Game Congrats You did it!");
        	result.setBounds(200, 25, 150, 25);
        }else if(t.getCurrentTurnCount() > t.getMaxTurnCount()){
            result = new JLabel("Sorry You lost! better luck next time");
            result.setBounds(200, 25, 150, 25);
        }
        GameOver.add(result);
        GameOver.setVisible(true);
    }
   }
