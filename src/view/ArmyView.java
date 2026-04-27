package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class ArmyView extends JFrame {
    private JTable table;

    public ArmyView(ArrayList<Army> armies) {
        setTitle("Army Overview");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent(armies));
        setPreferredSize(new Dimension(720, 460));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent(ArrayList<Army> armies) {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Army Overview"), BorderLayout.NORTH);

        if (armies == null || armies.isEmpty()) {
            page.add(UITheme.label("You do not have any field armies yet."), BorderLayout.CENTER);
            return page;
        }

        DefaultTableModel model = new DefaultTableModel(new String[] { "Army", "Target", "Distance", "Location", "Status",
                "Units" }, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < armies.size(); i++) {
            Army army = armies.get(i);
            model.addRow(new Object[] { "Army " + (i + 1), blankTarget(army), distance(army),
                    army.getCurrentLocation(), army.getCurrentStatus(), String.valueOf(army.getUnits().size()) });
        }

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        page.add(UITheme.scroll(table), BorderLayout.CENTER);
        return page;
    }

    private String blankTarget(Army army) {
        return army.getTarget() == null || army.getTarget().equals("") ? "-" : army.getTarget();
    }

    private String distance(Army army) {
        return army.getDistancetoTarget() < 0 ? "-" : String.valueOf(army.getDistancetoTarget());
    }
}
