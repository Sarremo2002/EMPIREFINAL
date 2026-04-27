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
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class Attack extends JFrame implements ActionListener {
    private JFrame dialog;
    private JButton attack;
    private JButton autoResolve;
    private JButton laySiege;
    private Game game;
    private Army army;
    private String cityName;

    public Attack(Game game, Army army, String cityName) {
        this.army = army;
        this.cityName = cityName;
        this.game = game;

        dialog = new JFrame("Battle at " + cityName);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setContentPane(createContent());
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));

        JLabel question = UITheme.title("Battle at " + cityName);
        UITheme.centerLabel(question);
        page.add(question, BorderLayout.NORTH);

        JLabel detail = UITheme.smallLight("Choose how this army should engage the city defenders.");
        UITheme.centerLabel(detail);
        page.add(detail, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        actions.setOpaque(false);
        attack = UITheme.primaryButton("Manual Battle");
        laySiege = UITheme.button("Lay Siege");
        autoResolve = UITheme.dangerButton("Auto Resolve");
        attack.addActionListener(this);
        laySiege.addActionListener(this);
        autoResolve.addActionListener(this);
        actions.add(attack);
        actions.add(laySiege);
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
        } else if (e.getSource() == laySiege) {
            laySiege();
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

    private void laySiege() {
        for (int i = 0; i < game.getAvailableCities().size(); i++) {
            if (game.getAvailableCities().get(i).getName().equals(cityName)) {
                try {
                    game.getPlayer().laySiege(army, game.getAvailableCities().get(i));
                    UITheme.showInfo(this, cityName + " is now under siege.");
                } catch (TargetNotReachedException exception) {
                    UITheme.showError(this, "The target city has not been reached yet.");
                } catch (FriendlyCityException exception) {
                    UITheme.showError(this, "You cannot attack a friendly city.");
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
