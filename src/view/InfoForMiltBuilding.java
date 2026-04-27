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

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;

@SuppressWarnings({ "serial", "this-escape" })
public class InfoForMiltBuilding extends JFrame implements ActionListener {
    private JButton upgradeBtn;
    private JButton recruitBtn;
    private JButton backBtn;
    private MilitaryBuilding miltBuilding;
    private Game game;
    private City city;
    private boolean returnedToCity;

    public InfoForMiltBuilding(MilitaryBuilding building, Game game, City city) {
        this.miltBuilding = building;
        this.game = game;
        this.city = city;

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
        details.setLayout(new GridLayout(6, 2, 12, 8));
        details.add(UITheme.label("Level"));
        details.add(UITheme.label(String.valueOf(miltBuilding.getLevel())));
        details.add(UITheme.label("Upgrade cost"));
        details.add(UITheme.label(UITheme.number(miltBuilding.getUpgradeCost()) + " gold"));
        details.add(UITheme.label("Recruit cost"));
        details.add(UITheme.label(UITheme.number(miltBuilding.getRecruitmentCost()) + " gold"));
        details.add(UITheme.label("Recruited this turn"));
        details.add(UITheme.label(miltBuilding.getCurrentRecruit() + "/" + miltBuilding.getMaxRecruit()));
        details.add(UITheme.label("Status"));
        details.add(UITheme.label(miltBuilding.isCoolDown() ? "Cooling down" : "Ready"));
        details.add(UITheme.label("Treasury"));
        details.add(UITheme.label(UITheme.number(game.getPlayer().getTreasury()) + " gold"));
        page.add(details, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        backBtn = UITheme.button("Back to City");
        upgradeBtn = UITheme.primaryButton("Upgrade");
        recruitBtn = UITheme.successButton("Recruit " + unitName());
        backBtn.addActionListener(this);
        configureUpgradeButton();
        configureRecruitButton();
        actions.add(backBtn);
        actions.add(upgradeBtn);
        actions.add(recruitBtn);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    private void configureUpgradeButton() {
        if (miltBuilding.getLevel() >= 3) {
            UITheme.disable(upgradeBtn, "Max Level");
        } else if (miltBuilding.isCoolDown()) {
            UITheme.disable(upgradeBtn, "Cooling Down");
        } else if (game.getPlayer().getTreasury() < miltBuilding.getUpgradeCost()) {
            UITheme.disable(upgradeBtn, "Need " + miltBuilding.getUpgradeCost() + " gold");
        } else {
            upgradeBtn.addActionListener(this);
        }
    }

    private void configureRecruitButton() {
        if (miltBuilding.isCoolDown()) {
            UITheme.disable(recruitBtn, "Cooling Down");
        } else if (miltBuilding.getCurrentRecruit() >= miltBuilding.getMaxRecruit()) {
            UITheme.disable(recruitBtn, "Recruit Limit");
        } else if (game.getPlayer().getTreasury() < miltBuilding.getRecruitmentCost()) {
            UITheme.disable(recruitBtn, "Need " + miltBuilding.getRecruitmentCost() + " gold");
        } else {
            recruitBtn.addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            returnToCity();
        } else if (e.getSource() == upgradeBtn) {
            try {
                game.getPlayer().upgradeBuilding(miltBuilding);
                UITheme.showInfo(this, buildingName() + " upgraded to level " + miltBuilding.getLevel() + ".");
                returnToCity();
            } catch (BuildingInCoolDownException exception) {
                UITheme.showError(this, "Building is still in cooldown.");
            } catch (MaxLevelException exception) {
                UITheme.showError(this, "Maximum level has been reached.");
            } catch (NotEnoughGoldException exception) {
                UITheme.showError(this, "Not enough gold to perform this action.");
            }
        } else if (e.getSource() == recruitBtn) {
            try {
                game.getPlayer().recruitUnit(unitType(), city.getName());
                UITheme.showInfo(this, unitName() + " recruited in " + city.getName() + ".");
                returnToCity();
            } catch (NotEnoughGoldException exception) {
                UITheme.showError(this, "Not enough gold to perform this action.");
            } catch (MaxRecruitedException exception) {
                UITheme.showError(this, "You can only recruit three units per turn from this building.");
            } catch (BuildingInCoolDownException exception) {
                UITheme.showError(this, "Building is still in cooldown.");
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
        if (miltBuilding instanceof ArcheryRange) {
            return "Archery Range";
        }
        if (miltBuilding instanceof Barracks) {
            return "Barracks";
        }
        if (miltBuilding instanceof Stable) {
            return "Stable";
        }
        return "Military Building";
    }

    private String unitName() {
        if (miltBuilding instanceof ArcheryRange) {
            return "Archer";
        }
        if (miltBuilding instanceof Barracks) {
            return "Infantry";
        }
        if (miltBuilding instanceof Stable) {
            return "Cavalry";
        }
        return "Unit";
    }

    private String unitType() {
        if (miltBuilding instanceof ArcheryRange) {
            return "Archer";
        }
        if (miltBuilding instanceof Barracks) {
            return "Infantry";
        }
        if (miltBuilding instanceof Stable) {
            return "Cavalry";
        }
        return "";
    }
}
