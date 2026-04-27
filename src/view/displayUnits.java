package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;

@SuppressWarnings({"serial", "this-escape"})
public class displayUnits extends JFrame{
    public displayUnits(Army e){
       String [] T = {"Type","Level","Current Soldier Count","Max Soldier Count"};
        JTable s = new JTable();
        if(e != null){
           if(e.getUnits().size() >= 0){
               Object [][] v= new Object[e.getUnits().size()][4];
               for(int i = 0;i<e.getUnits().size();i++){
                    if(e.getUnits().get(i)instanceof Archer){
                        v[i][0] = "Archer";
                    }else if(e.getUnits().get(i)instanceof Cavalry){
                        v[i][0]="Cavalry";
                    }else if(e.getUnits().get(i)instanceof Infantry){
                        v[i][0]="Infantry";
                    }
                   v[i][1] = String.valueOf(e.getUnits().get(i).getLevel());
                   v[i][2] = String.valueOf(e.getUnits().get(i).getCurrentSoldierCount());
                   v[i][3] = e.getUnits().get(i).getMaxSoldierCount();
               }
               s = new JTable(v,T);

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
