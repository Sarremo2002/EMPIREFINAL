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
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;

public class InfoForMiltBuilding extends JFrame implements ActionListener {
	Button UpgradeBtn;
	Button RecruitBtn;
	JFrame frame;
    private MilitaryBuilding MiltBuilding;
    private Game game;
    private City city;
    
	public MilitaryBuilding getMiltBuilding() {
		return MiltBuilding;
	}

	public void setMiltBuilding(MilitaryBuilding miltBuilding) {
		MiltBuilding = miltBuilding;
	}

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

	public InfoForMiltBuilding(MilitaryBuilding r,Game s , City t){
		MiltBuilding = r;
		game = s;
		city=t;
		this.setResizable(true);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(400,200);
		this.setLayout(new GridLayout(2,3));
		if(r instanceof ArcheryRange){
			this.add(new Label("Type : ArcheryRange"));
		}
		else if(r instanceof Barracks){
			this.add(new Label("Type : Barracks"));
		}
		else if(r instanceof Stable){
			this.add(new Label("Type : Stable"));
		}
		this.add(new Label ("Upgrade Cost:"+r.getUpgradeCost()));
		this.add(new Label("Level :"+r.getLevel()));
		UpgradeBtn = new Button();
		UpgradeBtn.setLabel("Upgrade ");
		UpgradeBtn.addActionListener(this);
		this.add(UpgradeBtn);
		this.add(new Label ("Recruit Cost:"+r.getRecruitmentCost()));
		RecruitBtn = new Button();
		RecruitBtn.setLabel("Recruit ");
		RecruitBtn.addActionListener(this);
		this.add(RecruitBtn);
		this.setVisible(true);
		
	} 
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==UpgradeBtn){
			try{ 
				if(getMiltBuilding() instanceof ArcheryRange){
					this.getGame().getPlayer().upgradeBuilding(MiltBuilding);
					this.dispose();
				}
				if(getMiltBuilding() instanceof Stable){
					this.getGame().getPlayer().upgradeBuilding(MiltBuilding);
					this.dispose();
				}
				if(getMiltBuilding() instanceof Barracks){
					this.getGame().getPlayer().upgradeBuilding(MiltBuilding);
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
				CityView newView = new CityView(this.getGame(),this.getCity()); 
			
		}
		else if (e.getSource() == RecruitBtn){
			try{
				if(getMiltBuilding() instanceof ArcheryRange){
					getGame().getPlayer().recruitUnit("Archer" , getCity().getName());
					this.dispose();
				}
				if(getMiltBuilding() instanceof Stable){
					getGame().getPlayer().recruitUnit("Cavalry" , getCity().getName());
					this.dispose();
				}
				if(getMiltBuilding() instanceof Barracks){
					getGame().getPlayer().recruitUnit("Infantry" , getCity().getName());
					this.dispose();
				}
			}
			catch(NotEnoughGoldException x){
			    JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
			}
			catch(MaxRecruitedException y){
			    JOptionPane.showMessageDialog(frame, "You can only recruit three units per turn from this building" , "", JOptionPane.ERROR_MESSAGE);
			} 
			catch(BuildingInCoolDownException x){
			    JOptionPane.showMessageDialog(frame, "Building is still in cooldown" , "", JOptionPane.ERROR_MESSAGE);
			}
				
			this.dispose();
			CityView newView = new CityView(this.getGame(),this.getCity()); 
		
		}
	}
}
