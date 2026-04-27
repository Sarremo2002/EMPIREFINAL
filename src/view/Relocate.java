package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.City;
import engine.Game;
import units.Army;
import units.Unit;

@SuppressWarnings({ "serial", "this-escape" })
public class Relocate extends JFrame implements ActionListener {
    private JButton addToArmy;
    private JButton createArmy;
    private Game game;
    private Unit unit;
    private City city;

    public Relocate(Game game, City city, Army sourceArmy, Unit unit) {
        this.game = game;
        this.city = city;
        this.unit = unit;

        setTitle("Dispatch Unit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Dispatch Unit"), BorderLayout.NORTH);

        JPanel details = UITheme.titledCard("Selected Unit");
        details.setLayout(new GridLayout(3, 2, 12, 8));
        details.add(UITheme.label("Type"));
        details.add(UITheme.label(UITheme.unitType(unit)));
        details.add(UITheme.label("Level"));
        details.add(UITheme.label(String.valueOf(unit.getLevel())));
        details.add(UITheme.label("Soldiers"));
        details.add(UITheme.label(unit.getCurrentSoldierCount() + "/" + unit.getMaxSoldierCount()));
        page.add(details, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        addToArmy = UITheme.button("Add to Existing Army");
        createArmy = UITheme.primaryButton("Create New Army");
        if (game.getPlayer().getControlledArmies().isEmpty()) {
            UITheme.disable(addToArmy, "No Field Armies");
        } else {
            addToArmy.addActionListener(this);
        }
        createArmy.addActionListener(this);
        actions.add(addToArmy);
        actions.add(createArmy);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addToArmy) {
            new ChooseArmy(game.getPlayer().getControlledArmies(), unit);
            dispose();
        } else if (e.getSource() == createArmy) {
            game.getPlayer().initiateArmy(city, unit);
            UITheme.showInfo(this, "A new army has been created outside " + city.getName() + ".");
            dispose();
        }
    }
}
