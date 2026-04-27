package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import units.Army;
import units.Unit;

	public class ArmyView extends JFrame {
		JTable table ;
		Army army;
		public ArmyView(ArrayList<Army> e){
	        String [] T = {"target","Distance to target","current location","Status"};
	        JTable s = new JTable();
	        if(e != null){
	        	if(e.size() >= 0){
	        		Army[] r = new Army[e.size()];
	        		for(int i =0 ;i<e.size();i++){
	        			r[i]=e.get(i);
	        		}
	        		Object [][] v= new Object[e.size()][4];
	        		for(int i = 0;i<e.size();i++){
	        			v[i][0] = String.valueOf(e.get(i).getTarget()); 
	        			v[i][1] = String.valueOf(e.get(i).getDistancetoTarget());
	        			v[i][2] = String.valueOf(e.get(i).getCurrentLocation());
	        			v[i][3] = e.get(i).getCurrentStatus();
	        		}
	        		s = new JTable(v,T);
	        		table= s;
	        	}
	        }
	        this.setSize(500,500);
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        this.setLocationRelativeTo(null);
	        JScrollPane scrollPane = new JScrollPane(s);
	        s.setFillsViewportHeight(true);
	        this.add(scrollPane);
	        this.setVisible(true);
	    }
}