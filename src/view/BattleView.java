package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import engine.Game;
import units.Army;
import units.Unit;

@SuppressWarnings("this-escape")
public class BattleView extends MainView implements ActionListener {
    private static final long serialVersionUID = 1L;
    private ArrayList<JButton> attackerButtons;
    private JButton endTurn;
    private Army defendingArmy;
    private Army attackingArmy;
    private ArrayList<String> log;
    private int turns;
    private String cityName;

    public BattleView(Game game, Army attackingArmy, int turns, String cityName, ArrayList<String> log) {
        super(game);
        this.turns = turns;
        this.attackingArmy = attackingArmy;
        this.cityName = cityName;
        this.log = log;
        this.defendingArmy = findDefendingArmy(cityName);

        setTitle("Battle at " + cityName);
        setContentPane(createContent());
        revalidate();
        repaint();
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(16, 16));
        page.add(createStats(), BorderLayout.NORTH);
        page.add(logPanel(log), BorderLayout.CENTER);
        page.add(armyPanel("Attackers", attackingArmy, true), BorderLayout.WEST);
        page.add(armyPanel("Defenders", defendingArmy, false), BorderLayout.EAST);
        page.add(buttonSection(), BorderLayout.SOUTH);
        return page;
    }

    private JPanel logPanel(ArrayList<String> entries) {
        JPanel panel = UITheme.titledCard("Battle Log");
        panel.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(new String[] { "Turn " + turns }, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < entries.size(); i++) {
            model.addRow(new Object[] { entries.get(i) });
        }
        if (entries.isEmpty()) {
            model.addRow(new Object[] { "Select one of your units to choose a target." });
        }
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        panel.add(UITheme.scroll(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buttonSection() {
        JPanel panel = UITheme.card();
        panel.setLayout(new BorderLayout());
        endTurn = UITheme.dangerButton("End Battle Turn");
        endTurn.addActionListener(this);
        panel.add(endTurn, BorderLayout.EAST);
        return panel;
    }

    private JPanel armyPanel(String title, Army army, boolean selectable) {
        JPanel panel = UITheme.titledCard(title);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(320, 0));

        if (selectable) {
            attackerButtons = new ArrayList<JButton>();
        }

        if (army == null || army.getUnits().isEmpty()) {
            JLabel empty = UITheme.label("No units remain.");
            panel.add(empty);
            return panel;
        }

        for (int i = 0; i < army.getUnits().size(); i++) {
            Unit unit = army.getUnits().get(i);
            JButton button = selectable ? UITheme.primaryButton(unitLabel(unit, i)) : UITheme.button(unitLabel(unit, i));
            button.setAlignmentX(LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
            if (selectable) {
                button.addActionListener(this);
                attackerButtons.add(button);
            } else {
                button.setEnabled(false);
            }
            panel.add(button);
            panel.add(Box.createVerticalStrut(8));
        }
        return panel;
    }

    private String unitLabel(Unit unit, int index) {
        return "<html>Unit " + (index + 1) + "<br>" + UITheme.unitType(unit) + " level " + unit.getLevel()
                + "<br>Soldiers " + unit.getCurrentSoldierCount() + "/" + unit.getMaxSoldierCount() + "</html>";
    }

    private Army findDefendingArmy(String cityName) {
        for (int i = 0; i < getGame().getAvailableCities().size(); i++) {
            if (getGame().getAvailableCities().get(i).getName().equals(cityName)) {
                return getGame().getAvailableCities().get(i).getDefendingArmy();
            }
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < attackerButtons.size(); i++) {
            if (e.getSource() == attackerButtons.get(i)) {
                new ChooseUnittoAttack(attackingArmy.getUnits().get(i), defendingArmy, turns, log);
                return;
            }
        }

        if (e.getSource() == endTurn) {
            getGame().endTurn();
            dispose();
            UITheme.showTurnSummary(this, getGame().getLastTurnSummary());
            if (attackingArmy.getUnits().size() != 0 && defendingArmy.getUnits().size() != 0) {
                new BattleView(getGame(), attackingArmy, turns + 1, cityName, log);
            } else if (attackingArmy.getUnits().size() == 0) {
                new BattleLost();
                if (getGame().getPlayer().getControlledArmies().contains(attackingArmy)) {
                    getGame().getPlayer().getControlledArmies().remove(attackingArmy);
                }
            } else if (defendingArmy.getUnits().size() == 0) {
                getGame().occupy(attackingArmy, cityName);
                new BattleWon();
            }
        }
    }
}
