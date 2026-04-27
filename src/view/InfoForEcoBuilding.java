package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.NotEnoughGoldException;

@SuppressWarnings({ "serial", "this-escape" })
public class InfoForEcoBuilding extends JFrame implements ActionListener {
    private JButton upgradeBtn;
    private JButton backBtn;
    private EconomicBuilding ecoBuilding;
    private Game game;
    private City city;
    private boolean returnedToCity;

    public InfoForEcoBuilding(EconomicBuilding building, Game game, City city) {
        this.game = game;
        this.city = city;
        this.ecoBuilding = building;

        setTitle(buildingName() + " Details");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setContentPane(createContent());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                returnToCity();
            }
        });
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title(buildingName()), BorderLayout.NORTH);

        JPanel details = UITheme.titledCard("Building Stats");
        details.setLayout(new GridLayout(5, 2, 12, 8));
        details.add(UITheme.label("Level"));
        details.add(UITheme.label(String.valueOf(ecoBuilding.getLevel())));
        details.add(UITheme.label("Upgrade cost"));
        details.add(UITheme.label(UITheme.number(ecoBuilding.getUpgradeCost()) + " gold"));
        details.add(UITheme.label("Harvest"));
        details.add(UITheme.label(UITheme.number(ecoBuilding.harvest()) + harvestType()));
        details.add(UITheme.label("Status"));
        details.add(UITheme.label(ecoBuilding.isCoolDown() ? "Cooling down" : "Ready"));
        details.add(UITheme.label("Treasury"));
        details.add(UITheme.label(UITheme.number(game.getPlayer().getTreasury()) + " gold"));
        page.add(details, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        backBtn = UITheme.button("Back to City");
        upgradeBtn = UITheme.primaryButton("Upgrade");
        backBtn.addActionListener(this);
        configureUpgradeButton();
        actions.add(backBtn);
        actions.add(upgradeBtn);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    private void configureUpgradeButton() {
        if (ecoBuilding.getLevel() >= 3) {
            UITheme.disable(upgradeBtn, "Max Level");
        } else if (ecoBuilding.isCoolDown()) {
            UITheme.disable(upgradeBtn, "Cooling Down");
        } else if (game.getPlayer().getTreasury() < ecoBuilding.getUpgradeCost()) {
            UITheme.disable(upgradeBtn, "Need " + ecoBuilding.getUpgradeCost() + " gold");
        } else {
            upgradeBtn.addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            returnToCity();
        } else if (e.getSource() == upgradeBtn) {
            try {
                game.getPlayer().upgradeBuilding(ecoBuilding);
                returnToCity();
            } catch (BuildingInCoolDownException exception) {
                UITheme.showError(this, "Building is still in cooldown.");
            } catch (MaxLevelException exception) {
                UITheme.showError(this, "Maximum level has been reached.");
            } catch (NotEnoughGoldException exception) {
                UITheme.showError(this, "Not enough gold to perform this action.");
            }
        }
    }

    private void returnToCity() {
        if (!returnedToCity) {
            returnedToCity = true;
            dispose();
            new CityView(game, city);
        }
    }

    private String buildingName() {
        if (ecoBuilding instanceof Farm) {
            return "Farm";
        }
        if (ecoBuilding instanceof Market) {
            return "Market";
        }
        return "Economic Building";
    }

    private String harvestType() {
        if (ecoBuilding instanceof Farm) {
            return " food";
        }
        if (ecoBuilding instanceof Market) {
            return " gold";
        }
        return "";
    }
}
