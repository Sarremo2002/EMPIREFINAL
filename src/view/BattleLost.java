package view;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class BattleLost extends JFrame implements ActionListener{
	JFrame battleResults;
	Button res;
	public BattleLost(){
		battleResults = new JFrame();
		battleResults.setLayout(null);
		battleResults.setLocationRelativeTo(null);
		battleResults.setSize(500, 250);
		JLabel result = new JLabel("You lost this battle!");
		result.setBounds(100, 25, 300, 25);
		battleResults.add(result);
		res = new Button("OK");
		res.setBounds(225, 70 , 50 , 20);
		res.addActionListener(this);
		battleResults.add(res);
		battleResults.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == res){
			battleResults.dispose();
		}
	}
}
