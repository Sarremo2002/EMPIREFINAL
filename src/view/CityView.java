package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import exceptions.NotEnoughGoldException;
import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class CityView extends MainView implements ActionListener {
    private City city;
    private JButton setArmy;
    private JButton buildStableBtn;
    private JButton buildBarracksBtn;
    private JButton buildArcheryRangeBtn;
    private JButton buildMarketBtn;
    private JButton buildFarmBtn;
    private JButton backToWorld;
    private JButton defendingBtn;
    private JButton armyBtn;
    private JButton farmBtn;
    private JButton marketBtn;
    private JButton stableBtn;
    private JButton barracksBtn;
    private JButton archeryRangeBtn;
    private JButton endTurn;
    private JFrame cityView;

    public CityView(Game game, City city) {
        super(game);
        this.city = city;
        this.cityView = this;
        setTitle(city.getName() + " City");
        setContentPane(createCityView());
        revalidate();
        repaint();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public JFrame getCV() {
        return cityView;
    }

    public void setCV(JFrame cityView) {
        this.cityView = cityView;
    }

    private JPanel createCityView() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(16, 16));
        page.add(createStats(), BorderLayout.NORTH);
        page.add(createCityMap(), BorderLayout.CENTER);
        page.add(createShop(), BorderLayout.EAST);
        page.add(createActionBar(), BorderLayout.SOUTH);
        return page;
    }

    private JPanel createCityMap() {
        JPanel panel = UITheme.titledCard(city.getName() + " District");
        panel.setLayout(new BorderLayout(0, 10));

        JPanel grid = new JPanel(new GridLayout(7, 7, 6, 6));
        grid.setOpaque(false);

        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 7; column++) {
                if (row == 1 && column == 1 && hasMilitaryBuilding(ArcheryRange.class)) {
                    archeryRangeBtn = buildingButton("Archery Range", findMilitaryBuilding(ArcheryRange.class));
                    grid.add(archeryRangeBtn);
                } else if (row == 2 && column == 2 && hasMilitaryBuilding(Barracks.class)) {
                    barracksBtn = buildingButton("Barracks", findMilitaryBuilding(Barracks.class));
                    grid.add(barracksBtn);
                } else if (row == 3 && column == 1 && hasMilitaryBuilding(Stable.class)) {
                    stableBtn = buildingButton("Stable", findMilitaryBuilding(Stable.class));
                    grid.add(stableBtn);
                } else if (row == 2 && column == 4 && hasEconomicBuilding(Market.class)) {
                    marketBtn = buildingButton("Market", findEconomicBuilding(Market.class));
                    grid.add(marketBtn);
                } else if (row == 3 && column == 5 && hasEconomicBuilding(Farm.class)) {
                    farmBtn = buildingButton("Farm", findEconomicBuilding(Farm.class));
                    grid.add(farmBtn);
                } else {
                    grid.add(UITheme.mapTile());
                }
            }
        }

        JLabel hint = UITheme.label("Select a building tile to upgrade or recruit from it.");
        panel.add(grid, BorderLayout.CENTER);
        panel.add(hint, BorderLayout.SOUTH);
        return panel;
    }

    private JButton buildingButton(String name, buildings.Building building) {
        JButton button = UITheme.successButton("<html><center>" + name + "<br>Level " + building.getLevel()
                + (building.isCoolDown() ? "<br>Cooling down" : "") + "</center></html>");
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(118, 76));
        return button;
    }

    public JPanel createShop() {
        JPanel shop = UITheme.titledCard("Build");
        shop.setLayout(new BoxLayout(shop, BoxLayout.Y_AXIS));
        shop.setPreferredSize(new Dimension(320, 0));

        buildFarmBtn = addBuildButton(shop, "Farm", "Food +500 each turn", 1000, hasEconomicBuilding(Farm.class));
        buildMarketBtn = addBuildButton(shop, "Market", "Gold +1000 each turn", 1500, hasEconomicBuilding(Market.class));
        buildArcheryRangeBtn = addBuildButton(shop, "Archery Range", "Recruit archers", 1500,
                hasMilitaryBuilding(ArcheryRange.class));
        buildBarracksBtn = addBuildButton(shop, "Barracks", "Recruit infantry", 2000, hasMilitaryBuilding(Barracks.class));
        buildStableBtn = addBuildButton(shop, "Stable", "Recruit cavalry", 2500, hasMilitaryBuilding(Stable.class));

        return shop;
    }

    private JButton addBuildButton(JPanel panel, String name, String detail, int cost, boolean alreadyBuilt) {
        JButton button = UITheme.primaryButton("<html><b>Build " + name + "</b><br>" + cost + " gold<br>" + detail
                + "</html>");
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        button.setToolTipText(detail);
        if (alreadyBuilt) {
            UITheme.disable(button, name + " built");
        } else if (getGame().getPlayer().getTreasury() < cost) {
            UITheme.disable(button, "Need " + cost + " gold for " + name);
        } else {
            button.addActionListener(this);
        }
        panel.add(button);
        panel.add(javax.swing.Box.createVerticalStrut(8));
        return button;
    }

    private JPanel createActionBar() {
        JPanel actions = UITheme.card();
        actions.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        backToWorld = UITheme.button("Back to World");
        armyBtn = UITheme.button("Field Armies");
        defendingBtn = UITheme.button("Defenders");
        setArmy = UITheme.button("Dispatch Unit");
        endTurn = UITheme.dangerButton("End Turn");

        backToWorld.addActionListener(this);
        armyBtn.addActionListener(this);
        defendingBtn.addActionListener(this);
        setArmy.addActionListener(this);
        endTurn.addActionListener(this);

        actions.add(backToWorld);
        actions.add(armyBtn);
        actions.add(defendingBtn);
        actions.add(setArmy);
        actions.add(endTurn);
        return actions;
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == backToWorld) {
            new WorldMap(getGame());
            dispose();
        } else if (source == buildStableBtn) {
            build("stable");
        } else if (source == buildBarracksBtn) {
            build("barracks");
        } else if (source == buildArcheryRangeBtn) {
            build("archeryrange");
        } else if (source == buildFarmBtn) {
            build("farm");
        } else if (source == buildMarketBtn) {
            build("market");
        } else if (source == farmBtn) {
            openEconomicBuilding(Farm.class);
        } else if (source == marketBtn) {
            openEconomicBuilding(Market.class);
        } else if (source == archeryRangeBtn) {
            openMilitaryBuilding(ArcheryRange.class);
        } else if (source == barracksBtn) {
            openMilitaryBuilding(Barracks.class);
        } else if (source == stableBtn) {
            openMilitaryBuilding(Stable.class);
        } else if (source == endTurn) {
            handleEndTurn();
        } else if (source == armyBtn) {
            new ArmyView(getGame().getPlayer().getControlledArmies());
        } else if (source == defendingBtn || source == setArmy) {
            new DefendingArmies(getGame(), city, city.getDefendingArmy());
        }
    }

    private void build(String type) {
        try {
            getGame().getPlayer().build(type, city.getName());
            reopenCity();
        } catch (NotEnoughGoldException exception) {
            UITheme.showError(this, "Not enough gold to perform this action.");
        }
    }

    private void openEconomicBuilding(Class<?> type) {
        EconomicBuilding building = findEconomicBuilding(type);
        if (building != null) {
            dispose();
            new InfoForEcoBuilding(building, getGame(), city);
        }
    }

    private void openMilitaryBuilding(Class<?> type) {
        MilitaryBuilding building = findMilitaryBuilding(type);
        if (building != null) {
            dispose();
            new InfoForMiltBuilding(building, getGame(), city);
        }
    }

    private void handleEndTurn() {
        ArrayList<String> forcedAttackCities = new ArrayList<String>();
        for (int i = 0; i < getGame().getAvailableCities().size(); i++) {
            City availableCity = getGame().getAvailableCities().get(i);
            if (availableCity.isUnderSiege() && availableCity.getTurnsUnderSiege() == 2) {
                forcedAttackCities.add(availableCity.getName());
            }
        }

        getGame().endTurn();
        Game currentGame = getGame();
        City currentCity = city;
        dispose();
        new CityView(currentGame, currentCity);

        for (int i = 0; i < currentGame.getPlayer().getControlledArmies().size(); i++) {
            Army army = currentGame.getPlayer().getControlledArmies().get(i);
            City armyCity = findAvailableCity(army.getCurrentLocation());
            if (armyCity == null || isControlledCity(armyCity.getName())) {
                continue;
            }
            if (forcedAttackCities.contains(armyCity.getName())) {
                new AbsoluteAttack(currentGame, army, armyCity.getName());
            } else if (!armyCity.isUnderSiege()) {
                new Attack(currentGame, army, armyCity.getName());
            }
        }

        if (currentGame.isGameOver()) {
            new GameOver(currentGame);
        }
    }

    private void reopenCity() {
        Game game = getGame();
        City currentCity = city;
        dispose();
        new CityView(game, currentCity);
    }

    private boolean hasEconomicBuilding(Class<?> type) {
        return findEconomicBuilding(type) != null;
    }

    private boolean hasMilitaryBuilding(Class<?> type) {
        return findMilitaryBuilding(type) != null;
    }

    private EconomicBuilding findEconomicBuilding(Class<?> type) {
        for (int i = 0; i < city.getEconomicalBuildings().size(); i++) {
            EconomicBuilding building = city.getEconomicalBuildings().get(i);
            if (type.isInstance(building)) {
                return building;
            }
        }
        return null;
    }

    private MilitaryBuilding findMilitaryBuilding(Class<?> type) {
        for (int i = 0; i < city.getMilitaryBuildings().size(); i++) {
            MilitaryBuilding building = city.getMilitaryBuildings().get(i);
            if (type.isInstance(building)) {
                return building;
            }
        }
        return null;
    }

    private City findAvailableCity(String name) {
        for (int i = 0; i < getGame().getAvailableCities().size(); i++) {
            City availableCity = getGame().getAvailableCities().get(i);
            if (availableCity.getName().equalsIgnoreCase(name)) {
                return availableCity;
            }
        }
        return null;
    }

    private boolean isControlledCity(String name) {
        for (int i = 0; i < getGame().getPlayer().getControlledCities().size(); i++) {
            if (getGame().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
