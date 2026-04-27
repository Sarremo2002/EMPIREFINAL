package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import buildings.ArcheryRange;
import engine.Game;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Unit;

public class ChooseCityToTarget extends JFrame implements ActionListener {
	private ArrayList<Button> r;
	private ArrayList<Army> army;
	private Game game;
	private String b;
	JFrame frame;
	public ChooseCityToTarget(String b , Game game , ArrayList<Army> e){
		this.b = b;
		this.game = game;
        r=new ArrayList<Button>();
		army = e;
        JPanel s = new JPanel();
        s.setLayout(new GridLayout());
        if(e != null){
        	if(e.size() >= 0){
        		for(int i = 0;i<e.size();i++){
        			 Button What= new Button();
        			 What.setLabel("Army " + i);
        			 What.addActionListener(this);
        			 r.add(What);
        		}	
        	}
        	this.add(s);
        }
        for(int i =0;i<e.size();i++){
        	s.add(r.get(i));
        }
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
     
        this.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		if(army!=null){
			for(int i=0 ; i<army.size();i++){
				if(e.getSource()==r.get(i)){
					game.targetCity(army.get(i) , b);
				}
			}
		}
	}
}