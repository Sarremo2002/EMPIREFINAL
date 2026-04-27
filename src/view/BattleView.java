package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import units.Army;
import engine.Game;

@SuppressWarnings("this-escape")
public class BattleView extends MainView implements ActionListener{
    private static final long serialVersionUID = 1L;
    ArrayList<Button> k , g;
    Button endTurn;
    Button yes;
    JFrame c;
    JPanel defendingArmy;
    private Army newArmy;
    private Army x;
    private ArrayList<String>log;
    private int turns;
    private int selectedAttackerIndex = -1;
    String cityName;
    @SuppressWarnings("this-escape")
    public BattleView(Game game , Army x , int turns , String cityName , ArrayList <String> log){
        super(game);
        this.turns = turns;
        this.x = x;
        this.cityName = cityName;
        this.log = log;
        this.setLayout(new BorderLayout());
        this.setTitle("Battle View");
        this.add(createStats() , BorderLayout.NORTH);
        this.add(defendingArmy(cityName) , BorderLayout.EAST);
        this.add(attackingArmy(x),BorderLayout.WEST);
        this.add(buttonSection() , BorderLayout.SOUTH);
        this.add(log(log),BorderLayout.CENTER);
    }
    public JPanel log(ArrayList<String> e){
        JPanel log = new JPanel();
        log.setPreferredSize(new Dimension(1200, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        String [] T = {"Log"};
        JTable s = new JTable();
        Object [][] v= new Object[e.size()][1];
               for(int i = 0;i<e.size();i++){
                   v[i][0] = e.get(i);
               }
               s = new JTable(v,T);

        JScrollPane scrollPane = new JScrollPane(s);
        s.setFillsViewportHeight(true);
       log.add(scrollPane);
       return log;
    }

    public JPanel buttonSection(){
        JPanel buttonSection = new JPanel();
        buttonSection.setLayout(new GridLayout(1 , 1));
        endTurn = new Button("End Turn");
        endTurn.addActionListener(this);
        buttonSection.add(endTurn , BorderLayout.SOUTH);
        return buttonSection;
    }
    public JPanel attackingArmy(Army e) throws NullPointerException{
        g = new ArrayList<Button>();
        JPanel attackingArmy = new JPanel();
        attackingArmy.setLayout(new GridLayout(5 , 2));
        if(e.getUnits().size() >= 0){
           for(int i = 0;i<e.getUnits().size();i++){
                Button what2= new Button();
                what2.setLabel("unit " + i);
                what2.addActionListener(this);
                g.add(what2);
           }
       }
        for(int i =0;i<e.getUnits().size();i++){
           attackingArmy.add(g.get(i));
        }
        return attackingArmy;
    }

    public JPanel defendingArmy(String s) throws NullPointerException{
        k = new ArrayList<Button>();
        for(int i = 0 ; i < super.getGame().getAvailableCities().size() ; i++)
            if (super.getGame().getAvailableCities().get(i).getName() .equals(s)){
                newArmy = super.getGame().getAvailableCities().get(i).getDefendingArmy();
            }
        defendingArmy = new JPanel();
        defendingArmy.setSize(600 , 600);
        defendingArmy.setLayout(new GridLayout(5 , 2));
        if(newArmy.getUnits().size() > 0){
           for(int i = 0;i<newArmy.getUnits().size();i++){
                Button what= new Button();
                what.setLabel("Unit " + (i+1));
                what.addActionListener(this);
                k.add(what);
           }
        }
            for(int i =0;i<newArmy.getUnits().size();i++){
               defendingArmy.add(k.get(i));
            }
        return defendingArmy;
    }

    public void frame (Army e ,int x){
        selectedAttackerIndex = x;
        c = new JFrame();
        c.setSize(500, 300);
        c.setLayout(new GridLayout(3, 2));
        c.setLocationRelativeTo(null);
                JLabel level = new JLabel("Unit Level: " + e.getUnits().get(x).getLevel());
                c.add(level);
                JLabel soldierCount= new JLabel("Current Soldier Count: " + e.getUnits().get(x).getCurrentSoldierCount());
                c.add(soldierCount);
                JLabel upkeep = new JLabel("Unit Upkeep: " + e.getUnits().get(x).getSiegeUpkeep());
                c.add(upkeep);
                JLabel attack = new JLabel("Do you want to attack using this unit?");
                c.add(attack);
                yes = new Button("Yes");
                yes.addActionListener(this);
                c.add(yes);

        c.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0 ; i<x.getUnits().size();i++){
            if(e.getSource()==g.get(i)){
                frame(x , i);
            }
        }
        if (e.getSource() == endTurn){
            super.getGame().endTurn();
            this.dispose();
//            if(getGame().isGameOver()){
//                GameOver t = new GameOver(getGame());
//            }
            if (x.getUnits().size() != 0 && newArmy.getUnits().size() != 0){
                for(int i = 0 ; i < this.getGame().getAvailableCities().size() ; i++)
                    if (this.getGame().getAvailableCities().get(i).getName() .equals(cityName)){
                        new BattleView(this.getGame() , x , turns + 1 , cityName , log);
                    }
            }
            else if(x.getUnits().size() == 0){
                new BattleLost();
                if(getGame().getPlayer().getControlledArmies().contains(x)){
                    getGame().getPlayer().getControlledArmies().remove(x);
                }
            }
            else if(newArmy.getUnits().size() == 0){
                this.getGame().occupy(x, cityName);
                new BattleWon();
            }
        }
        if (e.getSource() == yes){
            c.dispose();
            if (selectedAttackerIndex >= 0 && selectedAttackerIndex < x.getUnits().size()) {
                new ChooseUnittoAttack(x.getUnits().get(selectedAttackerIndex), newArmy , turns , log);
            }
        }
    }
}
