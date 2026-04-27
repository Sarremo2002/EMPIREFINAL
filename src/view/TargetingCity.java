package view;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.Game;

@SuppressWarnings({"serial", "this-escape"})
public class TargetingCity extends JFrame implements ActionListener{
    Button attack;
    Button notAttack;
    private String s;
    private Game game;
    JFrame x;
    public TargetingCity(Game game , String s){
        this.s = s;
        this.game = game;
        x = new JFrame();
        x.setResizable(false);
        x.setLayout(new FlowLayout());
        JLabel y = new JLabel("City Has Not Been Captured Yet! Would you Like to target the selected City?");
        x.add(y);
        notAttack = new Button("no");
        notAttack.addActionListener(this);
        attack = new Button("yes");
        attack.addActionListener(this);
        x.add(notAttack);
        x.add(attack);
        x.setSize(600, 100);
        x.setLocationRelativeTo(null);
        x.setVisible(true);
        x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attack){
            new ChooseCityToTarget(s , game , game.getPlayer().getControlledArmies());
        }

        if (e.getSource() == notAttack){
        }
        x.dispose();
    }
}
