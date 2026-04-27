package view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import units.Army;
import units.Unit;
import engine.City;
import engine.Game;
import exceptions.MaxCapacityException;

public class Relocate extends JFrame implements ActionListener{
	JFrame x;
	JFrame frame;
	Button addToArmy;
	Button createArmy;
	Game game;
	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	private Army v;
	private Unit y;
	private City c;
	public Relocate(Game game , City c ,Army v, Unit y) {
		this.game = game;
		this.c = c;
		this.v = v;
		this.y = y;
		x = new JFrame();
		x.setSize(500, 200);
		x.setLayout(null);
		x.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		x.setLocationRelativeTo(null);
		JLabel j = new JLabel("Do you want to dispatch a new army or add the selected unit to it?");
		j.setBounds(50, 50, 400, 20);
		x.add(j);
		addToArmy = new Button("Add To Army");
		addToArmy.setPreferredSize(new Dimension(50, 30));
		addToArmy.setBounds(100, 100, 80, 25);
		addToArmy.addActionListener(this);
		x.add(addToArmy);
		createArmy = new Button("Create Army");
		createArmy.setBounds(300, 100, 80, 25);
		createArmy.setPreferredSize(new Dimension(50, 30));
		createArmy.addActionListener(this);
		x.add(createArmy);
		x.setVisible(true);	
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addToArmy){
			ChooseArmy yes = new ChooseArmy(this.getGame().getPlayer().getControlledArmies(),y);
		}
		if (e.getSource() == createArmy){
			game.getPlayer().initiateArmy(c,y);
			this.dispose();
		}
		x.dispose();
	}

}
