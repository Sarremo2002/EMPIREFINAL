package view;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import units.Army;
import engine.Game;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;

@SuppressWarnings({"serial", "this-escape"})
public class Attack extends JFrame implements ActionListener{
    JFrame x;
    JFrame frame;
    JFrame battleResults;
    Button attack;
    Button auto_Resolve;
    Button lay_Siege;
    Button res;
    private Game game;
    private Army army;
    private String cityName;
    private int turns = 1;
    public Attack(Game game , Army army , String cityName){
        this.army = army;
        this.cityName = cityName;
        this.game = game;
        x = new JFrame();
        x.setLocationRelativeTo(null);
        x.setSize(500 , 200);
        x.setLayout(null);
        JLabel question = new JLabel("Do you want to attack manually, lay siege, or auto resoolve the battle?");
        question.setBounds(40, 20, 400, 20);
        x.add(question);
        attack = new Button("Attack");
        attack.setBounds(40, 80 , 100, 50);
        attack.addActionListener(this);
        x.add(attack);

        auto_Resolve = new Button("Auto Resolve");
        auto_Resolve.setBounds(330, 80 , 100, 50);
        auto_Resolve.addActionListener(this);
        x.add(auto_Resolve);

        lay_Siege = new Button("Lay Siege");
        lay_Siege.setBounds(185, 80 , 100, 50);
        lay_Siege.addActionListener(this);
        x.add(lay_Siege);
        x.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        x.dispose();
        if(e.getSource() == attack){
            ArrayList<String> log= new ArrayList<String>();
            new BattleView(game, army , 1 , cityName , log);
        }
        if(e.getSource() == auto_Resolve){
            for (int i = 0 ; i < game.getAvailableCities().size(); i++){
                if (game.getAvailableCities().get(i).getName().equals(cityName))
                    try{
                        game.autoResolve(army, game.getAvailableCities().get(i).getDefendingArmy());
                    }
                    catch(FriendlyFireException f){
                        JOptionPane.showMessageDialog(frame, "You cannot attack a friendly unit" , "", JOptionPane.ERROR_MESSAGE);
                    }
                    finally{
                        if (army.getUnits().size() != 0){
                            new BattleWon();
                        }
                        else{
                            new BattleLost();
                            if(game.getPlayer().getControlledArmies().contains(army)){
                                game.getPlayer().getControlledArmies().remove(army);
                            }
                        }

                    }
            }
        }
        if(e.getSource() == lay_Siege){
            for (int i = 0 ; i < game.getAvailableCities().size(); i++){
                if (game.getAvailableCities().get(i).getName().equals(cityName))
                    try{
                        game.getPlayer().laySiege(army, game.getAvailableCities().get(i));
                    }
                    catch(TargetNotReachedException f){
                        JOptionPane.showMessageDialog(frame, "The target city has not been reached yet!" , "", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(FriendlyCityException f){
                        JOptionPane.showMessageDialog(frame, "You cannot attack a friendly unit!" , "", JOptionPane.ERROR_MESSAGE);
                    }
            }
        }
    }
}
