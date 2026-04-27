package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import exceptions.MaxCapacityException;
import units.Army;
import units.Unit;

@SuppressWarnings({ "serial", "this-escape" })
public class ChooseArmy extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Army> armies;
    private Unit unit;

    public ChooseArmy(ArrayList<Army> armies, Unit unit) {
        this.armies = armies;
        this.unit = unit;

        setTitle("Choose Army");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        setPreferredSize(new Dimension(560, 420));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Choose Army"), BorderLayout.NORTH);

        JPanel list = UITheme.titledCard("Available Field Armies");
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        if (armies == null || armies.isEmpty()) {
            JLabel empty = UITheme.label("No field armies are available.");
            list.add(empty);
        } else {
            for (int i = 0; i < armies.size(); i++) {
                Army army = armies.get(i);
                JButton button = UITheme.button(UITheme.armyName(army, i));
                button.setAlignmentX(LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
                if (army.getUnits().size() >= army.getMaxToHold()) {
                    UITheme.disable(button, "Army " + (i + 1) + " is full");
                } else {
                    button.addActionListener(this);
                }
                buttons.add(button);
                list.add(button);
                list.add(Box.createVerticalStrut(8));
            }
        }

        page.add(UITheme.scroll(list), BorderLayout.CENTER);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        if (unit == null || armies == null) {
            dispose();
            return;
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                try {
                    armies.get(i).relocateUnit(unit);
                    UITheme.showInfo(this, "Unit added to Army " + (i + 1) + ".");
                    dispose();
                } catch (MaxCapacityException exception) {
                    UITheme.showError(this, "Army already has the maximum amount of units.");
                }
                return;
            }
        }
    }
}
