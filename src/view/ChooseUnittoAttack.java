package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.FriendlyFireException;
import units.Army;
import units.Unit;

@SuppressWarnings({"serial", "this-escape"})
public class ChooseUnittoAttack extends JFrame implements ActionListener {
    ArrayList<Button> theselection;
    private ArrayList<String> log;
    private Unit attack;
    private Army defend;
    JFrame frame;
    JFrame x;
    JPanel defendingArmy;
    private int turns;
    public ChooseUnittoAttack(Unit Attack, Army defending , int g , ArrayList<String> log){
        turns = g;
        attack = Attack;
        defend= defending;
        this.log = log;
        x = new JFrame();
        x.setSize(800, 800);
        x.setLayout(null);
        x.setLocationRelativeTo(null);
        x.add(defendingArmy());
        x.setVisible(true);
    }
    public JPanel defendingArmy() throws NullPointerException{
        theselection = new ArrayList<Button>();
        defendingArmy = new JPanel();
        defendingArmy.setSize(600 , 600);
        defendingArmy.setLayout(new GridLayout(5 , 2));
            if(defend.getUnits().size() >= 0){
               for(int i = 0;i<defend.getUnits().size();i++){
                    Button what= new Button();
                    what.setLabel("Unit " + i);
                    what.addActionListener(this);
                    theselection.add(what);
               }
            }
            for(int i =0;i<defend.getUnits().size();i++){
               defendingArmy.add(theselection.get(i));
            }
        return defendingArmy;
    }
    public void actionPerformed (ActionEvent e){
        try {
            x.dispose();
            for(int i=0 ; i<defend.getUnits().size();i++){
                if(e.getSource()==theselection.get(i)){
                    int CSCA = attack.getCurrentSoldierCount();
                    Unit target = defend.getUnits().get(i);
                    int CSCD = target.getCurrentSoldierCount();
                    log.add("Turn: " + turns + " Before Battle: Attacking Unit Has " + CSCA + " Soldiers");
                    log.add("Turn: " + turns + " Before Battle: Defending Unit Has " + CSCD + " Soldiers");
                    log.add("");
                    attack.attack(target);
                    if (target.getCurrentSoldierCount() != 0 && attack.getCurrentSoldierCount() != 0){
                        log.add("Turn: " + turns + " Counterattack: Defending Unit Strikes Back");
                        target.attack(attack);
                    }
                    CSCA = attack.getCurrentSoldierCount();
                    CSCD = target.getCurrentSoldierCount();
                    log.add("Turn: " + turns + " After Battle: Attacking Unit Has " + CSCA + " Soldiers");
                    log.add("Turn: " + turns + " After Battle: Defending Unit Has " + CSCD + " Soldiers");
                    log.add("");
                }
            }
            }catch (FriendlyFireException e1) {
                JOptionPane.showMessageDialog(frame, "You cannot attack yourself" , "", JOptionPane.ERROR_MESSAGE);
            }

    }

}

