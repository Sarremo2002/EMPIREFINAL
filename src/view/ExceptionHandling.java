package view;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.BuildingInCoolDownException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;

public class ExceptionHandling extends JFrame{
    JFrame frame;
    
	public ExceptionHandling (Exception x) throws BuildingInCoolDownException , NotEnoughGoldException , MaxLevelException , MaxRecruitedException , MaxCapacityException , TargetNotReachedException{    
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		exceptionHandler(x);
	    frame.setSize(560, 200);      
	    frame.setLocationRelativeTo(null);  
	    frame.setVisible(true);
	}
	public void exceptionHandler(Exception x){
		if (x instanceof BuildingInCoolDownException)
	        JOptionPane.showMessageDialog(frame, "Building is still in cooldown" , "", JOptionPane.ERROR_MESSAGE);
		if (x instanceof NotEnoughGoldException)
	        JOptionPane.showMessageDialog(frame, "Not enough gold to perform this action" , "", JOptionPane.ERROR_MESSAGE);
		if (x instanceof MaxLevelException)
	        JOptionPane.showMessageDialog(frame, "Maximum level has been reached" , "", JOptionPane.ERROR_MESSAGE);
		if (x instanceof MaxCapacityException)
	        JOptionPane.showMessageDialog(frame, "Army already has the maximum amount of units" , "", JOptionPane.ERROR_MESSAGE);
		if (x instanceof MaxRecruitedException)
	        JOptionPane.showMessageDialog(frame, "You can only recruit one unit per turn from this building" , "", JOptionPane.ERROR_MESSAGE);
	}
}
