package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.NotEnoughGoldException;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;

@SuppressWarnings({"serial", "this-escape"})
public class InfoForEcoBuilding extends JFrame implements ActionListener {
    Button UpgradeBtn;
    JFrame frame;

    EconomicBuilding EcoBuilding;
    Game game;
    City city;
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public EconomicBuilding getEcoBuilding() {
        return EcoBuilding;
    }
    public void setEcoBuilding(EconomicBuilding EconomicalBuilding) {
        EcoBuilding = EconomicalBuilding;
    }
    public InfoForEcoBuilding(EconomicBuilding s,Game t,City u){
        game =t;
        city = u;
        EcoBuilding =s ;
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(400,200);
        this.setLayout(new GridLayout(2,2));
        if(s instanceof Farm){
            this.add(new Label("Type : Farm"));
        }
        else if(s instanceof Market){
            this.add(new Label("Type : Market"));
        }

        this.add(new Label ("Upgrade Cost:"+s.getUpgradeCost()));
        this.add(new Label("Level :"+s.getLevel()));
        UpgradeBtn = new Button();
        UpgradeBtn.setLabel("Upgrade ");
        UpgradeBtn.addActionListener(this);
        this.add(UpgradeBtn);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==UpgradeBtn){
            try{
                if (getEcoBuilding() instanceof Farm){
                    this.getGame().getPlayer().upgradeBuilding(EcoBuilding);
                    this.dispose();
                }
                if (getEcoBuilding() instanceof Market){
                    this.getGame().getPlayer().upgradeBuilding(EcoBuilding);
                    this.dispose();
                }
            }
            catch(BuildingInCoolDownException y){
                JOptionPane.showMessageDialog(frame, "Building is still in cooldown" , "", JOptionPane.ERROR_MESSAGE);
            }
            catch(MaxLevelException x){
                JOptionPane.showMessageDialog(frame, "Maximum level has been reached" , "", JOptionPane.ERROR_MESSAGE);
            }
            catch(NotEnoughGoldException x){
                JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
            }


                this.dispose();
                new CityView(this.getGame(),this.getCity());

        }
    }
    public static void main(String [] args){
    }
}
