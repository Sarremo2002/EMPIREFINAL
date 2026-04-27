package view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.City;
import engine.Game;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;

@SuppressWarnings({"serial", "this-escape"})
public class DefendingArmies extends JFrame implements ActionListener{
    Button newUnit;
    Button newUnit1;
    Button newUnit2;
    Button newUnit3;
    Button newUnit4;
    Button newUnit5;
    Button newUnit6;
    Button newUnit7;
    Button newUnit8;
    Button newUnit9;
    JFrame addArmy;
    JPanel addingArmy;
    private Game game;
    private Army x;
    private City c;
    private int counter;
    public DefendingArmies(Game game , City c , Army x){
        this.game = game;
        this.c = c;
        this.x = x;
        counter = 0;
        addArmy = new JFrame();
        addArmy.setSize(500, 500);
        addArmy.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addArmy.setLayout(new GridLayout());
        addArmy.setLocationRelativeTo(null);
        addArmy.add(addingArmy(x));
        addArmy.setVisible(true);
    }
    public JPanel addingArmy(Army x){
        addingArmy = new JPanel();
        addingArmy.setLayout(new GridLayout(10 , 1));
        for(int i = 0 ; i < 10 ; i++){
                String type = "";
                if(counter < x.getUnits().size()){
                    if (x.getUnits().get(counter) instanceof Archer)
                        type = "Archer";
                    else if (x.getUnits().get(counter) instanceof Infantry)
                        type = "Infantry";
                    else if (x.getUnits().get(counter) instanceof Cavalry)
                        type = "Cavalry";
                    if ( i == 0 && x.getUnits().get(0)!=null){
                        newUnit = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        newUnit.setPreferredSize(new Dimension(300 , 300));
                        addingArmy.add(newUnit);
                        newUnit.addActionListener(this);
                    }else if ( i == 1 && x.getUnits().get(1)!=null){
                        newUnit1 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit1);
                        newUnit1.addActionListener(this);
                    }else if (i == 2 && x.getUnits().get(2)!=null){
                        newUnit2 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit2);
                        newUnit2.addActionListener(this);
                    }else if (i == 3 && x.getUnits().get(3)!=null){
                        newUnit3 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit3);
                        newUnit3.addActionListener(this);
                    }else if (i == 4 && x.getUnits().get(4)!=null){
                        newUnit4 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit4);
                        newUnit4.addActionListener(this);
                    }else if (i == 5 && x.getUnits().get(5)!=null){
                        newUnit5 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit5);
                        newUnit5.addActionListener(this);
                    }else if (i == 6 && x.getUnits().get(6)!=null){
                        newUnit6 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit6);
                        newUnit6.addActionListener(this);
                    }else if (i == 7 && x.getUnits().get(7)!=null){
                        newUnit7 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit7);
                        newUnit7.addActionListener(this);
                    }else if (i == 8 && x.getUnits().get(8)!=null){
                        newUnit8 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit8);
                        newUnit8.addActionListener(this);
                    }else if (i == 9 && x.getUnits().get(9)!=null){
                        newUnit9 = new Button(String.valueOf(counter + 1) + ") " + "Level: " + x.getUnits().get(counter).getLevel() + " " + type + " Soldier Count: " + x.getUnits().get(counter).getCurrentSoldierCount() + "/" + x.getUnits().get(counter).getMaxSoldierCount());
                        addingArmy.add(newUnit9);
                        newUnit9.addActionListener(this);
                    }
                counter++;
            }
        }
        return addingArmy;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newUnit){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(0));
        }
        if (e.getSource() == newUnit1){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(1));
        }
        if (e.getSource() == newUnit2){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(2));
        }
        if (e.getSource() == newUnit3){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(3));
        }
        if (e.getSource() == newUnit4){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(4));
        }
        if (e.getSource() == newUnit5){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(5));
        }
        if (e.getSource() == newUnit6){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(6));
        }
        if (e.getSource() == newUnit7){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(7));
        }
        if (e.getSource() == newUnit8){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(8));
        }
        if (e.getSource() == newUnit9){
            addArmy.dispose();
            new Relocate(game , c , x , x.getUnits().get(9));
        }
    }
}
