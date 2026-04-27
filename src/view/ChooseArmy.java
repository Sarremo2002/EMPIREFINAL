package view;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.MaxCapacityException;
import units.Army;
import units.Unit;

@SuppressWarnings("this-escape")
public class ChooseArmy extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    ArrayList<Button> r;
    ArrayList<Army> army;
    Unit need;
    JFrame frame;
    @SuppressWarnings("this-escape")
    public ChooseArmy(ArrayList<Army> e, Unit needed){
        r=new ArrayList<Button>();
        army = e;
        need =needed;
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
        if(e != null){
            for(int i =0;i<e.size();i++){
                s.add(r.get(i));
            }
        }
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(need!=null&& army!=null){
            try {
                for(int i=0 ; i<army.size();i++){
                    if(e.getSource()==r.get(i)){
                army.get(i).relocateUnit(need);
                    }}
            } catch (MaxCapacityException e1) {
                JOptionPane.showMessageDialog(frame, "Army already has the maximum amount of units" , "", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
        }

    }



}
