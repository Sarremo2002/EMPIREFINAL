package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import units.Army;

@SuppressWarnings({"serial", "this-escape"})
public class DisplayArmies extends JFrame implements ActionListener{
    ArrayList <Button> buttons = new ArrayList<Button>();
    ArrayList<Army>armies;
    JFrame dispunits;
    public DisplayArmies(ArrayList<Army>e){
        dispunits= new JFrame();
        dispunits.setLayout(new GridLayout(1,10));
        armies = e;
        for(int i =0;i<e.size();i++){
            Button x = new Button();
            x.setLabel("Army"+i);
            x.addActionListener(this);
            buttons.add(x);
            dispunits.add(x);
            this.add(x);
        }
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
    public void actionPerformed (ActionEvent e){
        for(int i =0;i<buttons.size();i++){
            if(e.getSource()==buttons.get(i)){
                this.dispose();
                new displayUnits(armies.get(i));
            }
        }
    }
}
