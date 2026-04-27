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

import engine.City;
import engine.Game;
import units.Army;
import units.Unit;

@SuppressWarnings({ "serial", "this-escape" })
public class DefendingArmies extends JFrame implements ActionListener {
    private ArrayList<JButton> unitButtons = new ArrayList<JButton>();
    private Game game;
    private Army army;
    private City city;

    public DefendingArmies(Game game, City city, Army army) {
        this.game = game;
        this.city = city;
        this.army = army;

        setTitle(city.getName() + " Defenders");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        setPreferredSize(new Dimension(620, 520));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title(city.getName() + " Defenders"), BorderLayout.NORTH);

        JPanel list = UITheme.titledCard("Select a Unit to Dispatch");
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        if (army == null || army.getUnits().isEmpty()) {
            JLabel empty = UITheme.label("This city has no defending units.");
            list.add(empty);
        } else {
            for (int i = 0; i < army.getUnits().size(); i++) {
                Unit unit = army.getUnits().get(i);
                JButton button = UITheme.button(unitLabel(unit, i));
                button.setAlignmentX(LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
                button.addActionListener(this);
                unitButtons.add(button);
                list.add(button);
                list.add(Box.createVerticalStrut(8));
            }
        }

        page.add(UITheme.scroll(list), BorderLayout.CENTER);
        return page;
    }

    private String unitLabel(Unit unit, int index) {
        return "Unit " + (index + 1) + " | " + UITheme.unitType(unit) + " | Level " + unit.getLevel()
                + " | Soldiers " + unit.getCurrentSoldierCount() + "/" + unit.getMaxSoldierCount();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < unitButtons.size(); i++) {
            if (e.getSource() == unitButtons.get(i)) {
                dispose();
                new Relocate(game, city, army, army.getUnits().get(i));
                return;
            }
        }
    }
}
