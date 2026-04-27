package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import units.Army;
import units.Unit;

@SuppressWarnings({ "serial", "this-escape" })
public class UnitsView extends JFrame {
    public UnitsView(Army army) {
        setTitle("Army Units");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent(army));
        setPreferredSize(new Dimension(620, 460));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent(Army army) {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Army Units"), BorderLayout.NORTH);

        if (army == null || army.getUnits().isEmpty()) {
            page.add(UITheme.label("This army has no units."), BorderLayout.CENTER);
            return page;
        }

        DefaultTableModel model = new DefaultTableModel(
                new String[] { "Type", "Level", "Current Soldiers", "Max Soldiers", "Idle Upkeep", "March Upkeep",
                        "Siege Upkeep" },
                0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < army.getUnits().size(); i++) {
            Unit unit = army.getUnits().get(i);
            model.addRow(new Object[] { UITheme.unitType(unit), String.valueOf(unit.getLevel()),
                    unit.getCurrentSoldierCount(), unit.getMaxSoldierCount(), upkeep(unit.getIdleUpkeep()),
                    upkeep(unit.getMarchingUpkeep()), upkeep(unit.getSiegeUpkeep()) });
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        page.add(UITheme.scroll(table), BorderLayout.CENTER);
        return page;
    }

    private String upkeep(double value) {
        return String.format("%.2f", Double.valueOf(value));
    }
}
