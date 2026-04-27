package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Farm;
import buildings.Market;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.NotEnoughGoldException;

@SuppressWarnings({"serial", "this-escape"})
public class CityView extends MainView implements ActionListener {
        private City city;
        Button setArmy;
        Button BuildStableBtn;
        Button BuildBarracksBtn;
        Button BuildArcheryRangeBtn ;
        Button BuildMarketBtn;
        Button BuildFarmBtn;
        Button BacktoWorld;
        Button DefendingBtn ;
        Button ArmyBtn;
        Button FarmBtn;
        Button MarketBtn;
        Button StableBtn;
        Button BarracksBtn;
        Button ArcheryRangeBtn;
        Button EndTurn;
        JFrame frame;
        public City getCity() {
            return city;
        }
        public void setCity(City city) {
            this.city = city;
        }
        int MiltBuildingNeeded;
        JPanel info;
        Button miltupgradebtn;
        Button backtoworld = new Button();
        JFrame CV;
        public CityView(Game t, City r){
            super(t);
            city = r;
            this.setTitle("City View");
            JPanel s = new JPanel();
            s.setLayout(new GridLayout(2,0));
            s.add(createStats());
            s.add(createShop());
            this.setLayout(new BorderLayout());
            this.add(CairoMap(), BorderLayout.WEST);
            this.add(s,BorderLayout.EAST);

            //this.add(createInfoArmy());

            CV = this;

        }
        public JFrame getCV(){
            return CV;
        }
        public void setCV(JFrame cityView) {
            CV = cityView;
        }


        public JPanel createInfoArmy(){
            JPanel x = new JPanel();
            x.setBackground(Color.YELLOW);
            x.setLayout(new GridLayout(10,3));
             x.setBorder( new MatteBorder(2, 2, 2, 2, Color.BLACK) );

            x.setBounds(0,0,(int)(this.getWidth() * 0.75),this.getHeight());
            for(int i =0;i<10;i++){
                for(int j =0 ;j<4;j++){
                    if(i==0 && j ==0){
                        JLabel t = new JLabel("Armies");
                        x.add(t);
                    }
                    else if(i == 1 && j ==0){
                        JLabel t = new JLabel("Level");
                        x.add(t);
                    }else if(i == 1 && j ==1){
                        JLabel t = new JLabel("Type");
                        x.add(t);
                    }
                    else if(i == 1 && j ==2){
                        JLabel t = new JLabel("Upgrade");
                        x.add(t);
                    }
                    else if(i == 1 && j ==3){
                        JLabel t = new JLabel("Upgrade Cost");
                        x.add(t);
                    }
                    else {
                        JLabel t = new JLabel("");
                        x.add(t);
                    }
                }
            }

        return x;
        }
        public JPanel CairoMap(){
            JPanel x = new JPanel();
            x.setLayout(new GridLayout(7,7));

            boolean ArcheryRangeFlag=false;
            boolean BarracksFlag=false;
            boolean StableFlag=false;
            boolean MarketFlag=false;
            boolean FarmFlag=false;
            if(this.getCity().getMilitaryBuildings().size()>0){
            for(int t =0 ;t<this.getCity().getMilitaryBuildings().size();t++){
                if(this.getCity().getMilitaryBuildings().get(t) instanceof ArcheryRange){
                    ArcheryRangeFlag= true;
                }else if(this.getCity().getMilitaryBuildings().get(t) instanceof Barracks){
                    BarracksFlag= true;
                }else if(this.getCity().getMilitaryBuildings().get(t) instanceof Stable){
                    StableFlag= true;
                }
            }}
            for(int t =0 ;t<this.getCity().getEconomicalBuildings().size();t++){
                if(this.getCity().getEconomicalBuildings().get(t) instanceof Market){
                    MarketFlag= true;
                }else if(this.getCity().getEconomicalBuildings().get(t) instanceof Farm){
                    FarmFlag= true;
                }
            }
            for(int i = 0 ;i<7;i++){
                for(int j =0 ;j<7;j++){
                    if(i ==1 && j==1 && ArcheryRangeFlag){
                        ArcheryRangeBtn = new Button();
                        ArcheryRangeBtn.addActionListener(this);
                        ArcheryRangeBtn.setLabel("ArcheryRange");
                        x.add(ArcheryRangeBtn);
                    }else if(i ==2 && j==2 && BarracksFlag ){
                        BarracksBtn = new Button();
                        BarracksBtn.setLabel("Barracks");
                        BarracksBtn.addActionListener(this);
                        x.add(BarracksBtn);
                    }else if(i ==3 && j==1 && StableFlag){
                        StableBtn = new Button();
                        StableBtn.setLabel("Stable");
                        StableBtn.addActionListener(this);
                        x.add(StableBtn);
                    }else if(i ==2 && j==4 && MarketFlag){
                        MarketBtn = new Button();
                        MarketBtn.setLabel("Market");
                        MarketBtn.addActionListener(this);
                        x.add(MarketBtn);
                    }else if(i ==3 && j==5 && FarmFlag){
                        FarmBtn = new Button();
                        FarmBtn.setLabel("Farm");
                        FarmBtn.addActionListener(this);
                        x.add(FarmBtn);
                    }else if(i ==0 && j==5 ){
                        ArmyBtn = new Button();
                        ArmyBtn.setLabel("Armies");
                        ArmyBtn.addActionListener(this);
                        x.add(ArmyBtn);
                    }else if(i ==0 && j==6 ){
                        DefendingBtn = new Button();
                        DefendingBtn.setLabel("Defending Army");
                        DefendingBtn.addActionListener(this);
                        x.add(DefendingBtn);
                    }else if (i ==0 && j==4 ){
                        setArmy = new Button();
                        setArmy.setLabel("Add Army");
                        setArmy.addActionListener(this);
                        x.add(setArmy);
                    }else if(i ==6 && j==6 ){
                        BacktoWorld = new Button();
                        BacktoWorld.setLabel("Back to World");
                        BacktoWorld.addActionListener(this);
                        x.add(BacktoWorld);
                    }else if(i ==6 && j==5 ){
                        EndTurn = new Button();
                        EndTurn.setLabel("End Turn");
                        EndTurn.addActionListener(this);
                        x.add(EndTurn);
                    }else{
                        Label r = new Label("");
                        x.add(r);
                    }
                }
            }
            return x;
        }
        public JPanel createShop(){
            JPanel x = new JPanel();
            x.setLayout(new GridLayout(2,3));
            boolean ArcheryRangeFlag=false;
            boolean BarracksFlag=false;
            boolean StableFlag=false;
            boolean MarketFlag=false;
            boolean FarmFlag=false;

            for(int t =0 ;t<this.getCity().getMilitaryBuildings().size();t++){
                if(this.getCity().getMilitaryBuildings().get(t) instanceof ArcheryRange){
                    ArcheryRangeFlag= true;
                }else if(this.getCity().getMilitaryBuildings().get(t) instanceof Barracks){
                    BarracksFlag= true;
                }else if(this.getCity().getMilitaryBuildings().get(t) instanceof Stable){
                    StableFlag= true;
                }
            }for(int t =0 ;t<this.getCity().getEconomicalBuildings().size();t++){
                if(this.getCity().getEconomicalBuildings().get(t) instanceof Market){
                    MarketFlag= true;
                }else if(this.getCity().getEconomicalBuildings().get(t) instanceof Farm){
                    FarmFlag= true;
                }
            }


                        Label t = new Label("Shop");
                        x.add(t);
                    if (FarmFlag==false){
                        BuildFarmBtn = new Button();
                        BuildFarmBtn.addActionListener(this);
                        BuildFarmBtn.setLabel("Buy Farm");
                        x.add(BuildFarmBtn);
                    }if(MarketFlag==false){
                        BuildMarketBtn = new Button();
                        BuildMarketBtn.addActionListener(this);
                        BuildMarketBtn.setLabel("Buy Market");
                        x.add(BuildMarketBtn);
                    } if(ArcheryRangeFlag==false){
                        BuildArcheryRangeBtn = new Button();
                        BuildArcheryRangeBtn.addActionListener(this);
                        BuildArcheryRangeBtn.setLabel("Buy Archery Range");
                        x.add(BuildArcheryRangeBtn);
                    }if( BarracksFlag==false){
                        BuildBarracksBtn = new Button();
                        BuildBarracksBtn.addActionListener(this);
                        BuildBarracksBtn.setLabel("Buy Barracks");
                        x.add(BuildBarracksBtn);
                    }if( StableFlag==false){
                        BuildStableBtn = new Button();
                        BuildStableBtn.addActionListener(this);
                        BuildStableBtn.setLabel("Buy Stable");
                        x.add(BuildStableBtn);

                }

            return x;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==BacktoWorld){
                Game v = this.getGame();
                new WorldMap(v);
                this.dispose();

            }
//            else if(e.getSource()==DefendingBtn){
//                ArmyView r = new ArmyView(this.getCity().getDefendingArmy());
//
//
//            }
            else if(e.getSource()==BuildStableBtn){
            try{
                this.dispose();
                getGame().getPlayer().build("stable", getCity().getName());
                }catch(NotEnoughGoldException x){
                    JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
                }finally{
                    new CityView(this.getGame(),this.getCity());
                }

            }
        else if(e.getSource()==BuildBarracksBtn){
            try{
                this.dispose();
                getGame().getPlayer().build("barracks", getCity().getName());
                }catch(NotEnoughGoldException x){
                    JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
                }finally{
                    new CityView(this.getGame(),this.getCity());
                }

            }
        else if(e.getSource()==BuildArcheryRangeBtn){
            try{
                this.dispose();
                getGame().getPlayer().build("archeryrange", getCity().getName());

                }catch(NotEnoughGoldException x){
                    JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
                }finally{
                    new CityView(this.getGame(),this.getCity());
                }

            }else if(e.getSource()==BuildFarmBtn){
                try{
                    this.dispose();
                    getGame().getPlayer().build("farm", getCity().getName());
                    }catch(NotEnoughGoldException t){
                        JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
                    }finally{
                        new CityView(this.getGame(),this.getCity());
                    }

                }
            else if(e.getSource()==BuildMarketBtn){
                    try{
                        this.dispose();
                        getGame().getPlayer().build("market", getCity().getName());
                        }catch(NotEnoughGoldException t){
                            JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
                        }finally{
                            new CityView(this.getGame(),this.getCity());
                        }

                }
            else if(e.getSource()== FarmBtn){
                int index =-1;
                for(int i =0 ;i<getCity().getEconomicalBuildings().size();i++){
                    if(getCity().getEconomicalBuildings().get(i) instanceof Farm){
                        index = i;
                    }
                }
                if(index != -1){

                    this.dispose();
                InfoForEcoBuilding r = new InfoForEcoBuilding(this.getCity().getEconomicalBuildings().get(index),this.getGame(),this.getCity());
                r.setVisible(true);
                }
            }else if(e.getSource()== MarketBtn){
                int index =-1;
                for(int i =0 ;i<getCity().getEconomicalBuildings().size();i++){
                    if(getCity().getEconomicalBuildings().get(i) instanceof Market){
                        index = i;
                    }
                }
                if(index != -1){
                this.dispose();
                InfoForEcoBuilding r = new InfoForEcoBuilding(this.getCity().getEconomicalBuildings().get(index),this.getGame(),this.getCity());
                r.setVisible(true);
                }
            }else if(e.getSource()== ArcheryRangeBtn){
                int index =-1;
                for(int i =0 ;i<getCity().getMilitaryBuildings().size();i++){
                    if(getCity().getMilitaryBuildings().get(i) instanceof ArcheryRange){
                        index = i;
                    }
                }
                if(index != -1){
                this.dispose();
                InfoForMiltBuilding r = new InfoForMiltBuilding(this.getCity().getMilitaryBuildings().get(index),this.getGame(),this.getCity());
                r.setVisible(true);
                }
            }else if(e.getSource()== BarracksBtn){
                int index =-1;
                for(int i =0 ;i<getCity().getMilitaryBuildings().size();i++){
                    if(getCity().getMilitaryBuildings().get(i) instanceof Barracks){
                        index = i;
                    }
                }
                if(index != -1){
                this.dispose();
                InfoForMiltBuilding r = new InfoForMiltBuilding(this.getCity().getMilitaryBuildings().get(index),this.getGame(),this.getCity());
                r.setVisible(true);
                }
            }else if(e.getSource()== StableBtn){
                int index =-1;
                for(int i =0 ;i<getCity().getMilitaryBuildings().size();i++){
                    if(getCity().getMilitaryBuildings().get(i) instanceof Stable){
                        index = i;
                    }
                }
                if(index != -1){
                this.dispose();
                InfoForMiltBuilding r = new InfoForMiltBuilding(this.getCity().getMilitaryBuildings().get(index),this.getGame(),this.getCity());
                r.setVisible(true);
                }
            }
            else if(e.getSource()== EndTurn){
                String cityName = "";
                boolean yes = false;
                for (int k = 0 ; k < getGame().getAvailableCities().size() ; k++){
                    if(getGame().getAvailableCities().get(k).isUnderSiege()){
                        if(getGame().getAvailableCities().get(k).getTurnsUnderSiege() == 2){
                            yes = true;
                            cityName = getGame().getAvailableCities().get(k).getName();
                        }
                    }
                }
                this.getGame().endTurn();
                this.CV.dispose();
                new CityView(this.getGame(),this.getCity());
                for (int i = 0 ; i < getGame().getPlayer().getControlledArmies().size() ; i++){
                    for (int j = 0 ; j < getGame().getAvailableCities().size() ; j++){
                        if (!getGame().getPlayer().getControlledArmies().get(i).getCurrentLocation().equalsIgnoreCase(getCity().getName())){
                            if (getGame().getPlayer().getControlledArmies().get(i).getCurrentLocation() .equals(getGame().getAvailableCities().get(j).getName())){
                                cityName = getGame().getAvailableCities().get(j).getName();
                                if (!getGame().getAvailableCities().get(j).isUnderSiege()){
                                    new Attack(getGame() , getGame().getPlayer().getControlledArmies().get(i) , cityName);
                                }
                            }
                        }
                        if (yes){
                            new AbsoluteAttack(getGame() , getGame().getPlayer().getControlledArmies().get(i) , cityName);
                            yes = false;
                        }
                    }
                }
                if(getGame().isGameOver()){
                    new GameOver(getGame());
                }
            }

            else if(e.getSource() == ArmyBtn){

                new ArmyView(this.getGame().getPlayer().getControlledArmies());
            }
            else if(e.getSource() == DefendingBtn){
                new DefendingArmies(this.getGame() , this.getCity() , this.getCity().getDefendingArmy());
            }
            else if(e.getSource() == setArmy){
                new DefendingArmies(this.getGame() , this.getCity() , this.getCity().getDefendingArmy());
            }

        }}

