package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;
import exceptions.FriendlyFireException;
import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class AbsoluteAttack extends JFrame implements ActionListener {
    private JFrame dialog;
    private JButton attack;
    private JButton autoResolve;
    private Game game;
    private Army army;
    private String cityName;

    public AbsoluteAttack(Game game, Army army, String cityName) {
        this.army = army;
        this.cityName = cityName;
        this.game = game;

        dialog = new JFrame("Forced Attack");
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setContentPane(createContent());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));

        JLabel title = UITheme.title(cityName + " must be attacked");
        UITheme.centerLabel(title);
        page.add(title, BorderLayout.NORTH);

        JLabel detail = UITheme.smallLight("The siege has reached its limit. Resolve the battle now.");
        UITheme.centerLabel(detail);
        page.add(detail, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actions.setOpaque(false);
        attack = UITheme.primaryButton("Manual Battle");
        autoResolve = UITheme.dangerButton("Auto Resolve");
        attack.addActionListener(this);
        autoResolve.addActionListener(this);
        actions.add(attack);
        actions.add(autoResolve);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
        if (e.getSource() == attack) {
            new BattleView(game, army, 1, cityName, new ArrayList<String>());
        } else if (e.getSource() == autoResolve) {
            autoResolveBattle();
        }
    }

    private void autoResolveBattle() {
        for (int i = 0; i < game.getAvailableCities().size(); i++) {
            if (game.getAvailableCities().get(i).getName().equals(cityName)) {
                try {
                    game.autoResolve(army, game.getAvailableCities().get(i).getDefendingArmy());
                } catch (FriendlyFireException exception) {
                    UITheme.showError(this, "You cannot attack a friendly unit.");
                } finally {
                    showBattleResult();
                }
                return;
            }
        }
    }

    private void showBattleResult() {
        if (army.getUnits().size() != 0) {
            new BattleWon();
        } else {
            new BattleLost();
            if (game.getPlayer().getControlledArmies().contains(army)) {
                game.getPlayer().getControlledArmies().remove(army);
            }
        }
    }
}
