package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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


@SuppressWarnings({"serial", "this-escape"})
public class WorldMap extends MainView implements ActionListener{
    Button btnCairo = new Button();
    Button btnRome = new Button();
    Button btnSparta = new Button();
    ArrayList<Button> getUnits;
    Button btnforplayerArmies = new Button();
    Button battle;
    JTable table;
    JPanel armyINfo;
    private JFrame WorldMapView;
    public WorldMap(Game game) {
        super(game);
        JPanel p=new JPanel();
        p.setLayout(new GridLayout(2,1));
        p.add(createStats());
        p.add(armyInformation(getGame().getPlayer().getControlledArmies()));
        this.setTitle("World View");
        this.setLayout(new BorderLayout());
        this.add(createMap(),BorderLayout.WEST);
        this.add(p,BorderLayout.CENTER);
        WorldMapView = this;



    }



    public JFrame getWorldMapView() {
        return WorldMapView;
    }



    public void setWorldMapView(JFrame worldMapView) {
        WorldMapView = worldMapView;
    }



    public JPanel createMap(){
        JPanel t = new JPanel();
        t.setBackground(Color.decode("#7cfc00"));
        t.setLayout(new GridLayout(10,10));
        for(int i =0 ; i<10;i++){
            for(int j = 0; j<10;j++){
                if(i ==7 && j==7){
                    btnCairo.setBackground(Color.RED);
                    btnCairo.setLabel("Cairo");
                    Font x = new Font("TimesNewRoman" , 10 , 20);
                    btnCairo.setFont(x);
                    btnCairo.addActionListener(this);
                    t.add(btnCairo);
                }
                else if(i ==3 && j==2){
                    btnRome.setLabel("Rome");
                    btnRome.setBackground(Color.RED);
                    Font x = new Font("TimesNewRoman" , 10 , 20);
                    btnRome.setFont(x);
                    btnRome.addActionListener(this);
                    t.add(btnRome);
                }
                else if(i == 1 && j == 8){
                    btnSparta.setBackground(Color.RED);
                    btnSparta.setLabel("Sparta");
                    Font x = new Font("TimesNewRoman" , 10 , 20);
                    btnSparta.setFont(x);
                    btnSparta.addActionListener(this);
                    t.add(btnSparta);
                }
                else if (i==0 && j==0){
                    btnforplayerArmies.setBackground(Color.RED);
                    btnforplayerArmies.setLabel("Armies Unit");
                    Font x = new Font("TimesNewRoman" , 10 , 20);
                    btnforplayerArmies.setFont(x);
                    btnforplayerArmies.addActionListener(this);
                    t.add(btnforplayerArmies);
                }
                else{
                    JLabel r= new JLabel("         ");
                    Font x = new Font("TimesNewRoman" , 10 , 20);
                    r.setFont(x);
                    t.add(r);

                }
            }
        }
        t.setBounds(0,0,(int)(this.getWidth() * 0.80),this.getHeight());
        return t;
    }

    public JPanel armyInformation(ArrayList<Army> e){
        armyINfo = new JPanel();
        JTable s = new JTable();
        String [] T = {"target","Distance to target","current location","Status","Besiege Turns"};
        if(e != null){
           if(e.size() >= 0){
               Army[] r = new Army[e.size()];
               for(int i =0 ;i<e.size();i++){
                   r[i]=e.get(i);
               }
               Object [][] v= new Object[e.size()][5];
               for(int i = 0;i<e.size();i++){
                   v[i][0] = String.valueOf(e.get(i).getTarget());
                   v[i][1] = String.valueOf(e.get(i).getDistancetoTarget());
                   v[i][2] = String.valueOf(e.get(i).getCurrentLocation());
                   v[i][3] = e.get(i).getCurrentStatus();
                   v[i][4] = "";
                   for(int j = 0 ;j<getGame().getAvailableCities().size();j++){
                        if(getGame().getAvailableCities().get(j).getName().equals(e.get(i).getCurrentLocation())){
                           v[i][4]=String.valueOf(getGame().getAvailableCities().get(j).getTurnsUnderSiege());
                        }
                   }
               }
               s = new JTable(v,T);
               table= s;
           }
        }


        JScrollPane scrollPane = new JScrollPane(s);
        s.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(775 , 600));
        armyINfo.add(scrollPane);
        return armyINfo;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnCairo){
            int indexforcity=-1;
            boolean flagExists = false;
            for (int i = 0 ; i <super.getGame().getPlayer().getControlledCities().size() ; i++){
                if(super.getGame().getPlayer().getControlledCities().get(i).getName().equals("Cairo")){
                flagExists= true;
                indexforcity=i;
                break;
                }
                }if (flagExists == false){
                    new TargetingCity(getGame() , "Cairo");
                    }
                else {
                  getWorldMapView().dispose();
                  Game t=this.getGame();
                  new CityView(t,super.getGame().getPlayer().getControlledCities().get(indexforcity));
                }

        }
        if(e.getSource()==btnRome){
            int indexforcity=-1;
            boolean flagExists = false;
            for (int i = 0 ; i < super.getGame().getPlayer().getControlledCities().size() ; i++){
                if(super.getGame().getPlayer().getControlledCities().get(i).getName().equals("Rome")){
                flagExists= true;
                indexforcity=i;
                break;
                }
                }if (flagExists == false){
                    new TargetingCity(getGame() , "Rome");
                    }
                else {
            getWorldMapView().dispose();
            Game t=this.getGame();
            new CityView(t,super.getGame().getPlayer().getControlledCities().get(indexforcity));
                }

        }
        if(e.getSource()==btnSparta){
            int indexforcity=-1;
            boolean flagExists = false;
            for (int i = 0 ; i < super.getGame().getPlayer().getControlledCities().size() ; i++){
                if(super.getGame().getPlayer().getControlledCities().get(i).getName().equals("Sparta")){
                flagExists= true;
                indexforcity = i;
                break;
                }
                }if (flagExists == false){
                    new TargetingCity(getGame() , "Sparta");
                    }
                else {
            getWorldMapView().dispose();
            Game t=this.getGame();
            new CityView(t,super.getGame().getPlayer().getControlledCities().get(indexforcity));
                }

        }
        if (e.getSource()== btnforplayerArmies){
            new DisplayArmies(this.getGame().getPlayer().getControlledArmies());
        }
    }
}
