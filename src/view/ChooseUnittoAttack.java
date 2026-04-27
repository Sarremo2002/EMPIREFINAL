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

import exceptions.FriendlyFireException;
import units.Army;
import units.Unit;

@SuppressWarnings({ "serial", "this-escape" })
public class ChooseUnittoAttack extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<String> log;
    private Unit attacker;
    private Army defender;
    private int turns;

    public ChooseUnittoAttack(Unit attacker, Army defender, int turns, ArrayList<String> log) {
        this.turns = turns;
        this.attacker = attacker;
        this.defender = defender;
        this.log = log;

        setTitle("Choose Target");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        setPreferredSize(new Dimension(620, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Choose Target"), BorderLayout.NORTH);

        JPanel list = UITheme.titledCard("Defending Units");
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        if (defender == null || defender.getUnits().isEmpty()) {
            JLabel empty = UITheme.label("There are no defenders left.");
            list.add(empty);
        } else {
            for (int i = 0; i < defender.getUnits().size(); i++) {
                Unit target = defender.getUnits().get(i);
                JButton button = UITheme.dangerButton(unitLabel(target, i));
                button.setAlignmentX(LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
                button.addActionListener(this);
                buttons.add(button);
                list.add(button);
                list.add(Box.createVerticalStrut(8));
            }
        }
        page.add(UITheme.scroll(list), BorderLayout.CENTER);
        return page;
    }

    private String unitLabel(Unit unit, int index) {
        return "Defender " + (index + 1) + " | " + UITheme.unitType(unit) + " | Level " + unit.getLevel()
                + " | Soldiers " + unit.getCurrentSoldierCount() + "/" + unit.getMaxSoldierCount();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                resolveAttack(i);
                dispose();
                return;
            }
        }
    }

    private void resolveAttack(int index) {
        try {
            int attackingBefore = attacker.getCurrentSoldierCount();
            Unit target = defender.getUnits().get(index);
            int defendingBefore = target.getCurrentSoldierCount();

            log.add("Turn " + turns + ": " + UITheme.unitType(attacker) + " attacked " + UITheme.unitType(target));
            log.add("Before: attacker " + attackingBefore + " soldiers, defender " + defendingBefore + " soldiers");

            attacker.attack(target);
            if (target.getCurrentSoldierCount() != 0 && attacker.getCurrentSoldierCount() != 0) {
                log.add("Turn " + turns + ": defender counterattacked");
                target.attack(attacker);
            }

            log.add("After: attacker " + attacker.getCurrentSoldierCount() + " soldiers, defender "
                    + target.getCurrentSoldierCount() + " soldiers");
            log.add("");
        } catch (FriendlyFireException exception) {
            UITheme.showError(this, "You cannot attack yourself.");
        }
    }
}
