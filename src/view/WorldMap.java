package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import engine.Game;
import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class WorldMap extends MainView implements ActionListener {
    private JButton btnCairo;
    private JButton btnRome;
    private JButton btnSparta;
    private JButton btnforplayerArmies;
    private JButton saveBtn;
    private JTable table;
    private JFrame worldMapView;

    public WorldMap(Game game) {
        super(game);
        setTitle("World Map");
        setContentPane(createWorldView());
        worldMapView = this;
        revalidate();
        repaint();
    }

    public JFrame getWorldMapView() {
        return worldMapView;
    }

    public void setWorldMapView(JFrame worldMapView) {
        this.worldMapView = worldMapView;
    }

    private JPanel createWorldView() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(16, 16));
        page.add(createStats(), BorderLayout.NORTH);
        page.add(createMap(), BorderLayout.CENTER);
        page.add(createSidebar(), BorderLayout.EAST);
        return page;
    }

    public JPanel createMap() {
        JPanel panel = UITheme.titledCard("Campaign Map");
        panel.setLayout(new BorderLayout(0, 10));

        JPanel grid = new JPanel(new GridLayout(10, 10, 4, 4));
        grid.setOpaque(false);
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if (row == 7 && column == 7) {
                    btnCairo = cityButton("Cairo");
                    grid.add(btnCairo);
                } else if (row == 3 && column == 2) {
                    btnRome = cityButton("Rome");
                    grid.add(btnRome);
                } else if (row == 1 && column == 8) {
                    btnSparta = cityButton("Sparta");
                    grid.add(btnSparta);
                } else if (row == 0 && column == 0) {
                    btnforplayerArmies = UITheme.primaryButton("<html><center>Field Armies<br>"
                            + getGame().getPlayer().getControlledArmies().size() + "</center></html>");
                    btnforplayerArmies.addActionListener(this);
                    grid.add(btnforplayerArmies);
                } else if (roadText(row, column) != null) {
                    grid.add(UITheme.roadTile(roadText(row, column)));
                } else {
                    grid.add(UITheme.mapTile());
                }
            }
        }

        JLabel hint = UITheme.label("Captured cities open their city view. Uncaptured cities can be targeted by a field army.");
        panel.add(grid, BorderLayout.CENTER);
        panel.add(hint, BorderLayout.SOUTH);
        return panel;
    }

    private JButton cityButton(String cityName) {
        boolean controlled = isControlled(cityName);
        CityStatus status = cityStatus(cityName, controlled);
        JButton button = UITheme.cityButton(cityName, status.text, controlled, status.underSiege);
        button.addActionListener(this);
        return button;
    }

    private String roadText(int row, int column) {
        if (row == 5 && column == 4) {
            return "Cairo-Rome 6";
        }
        if (row == 4 && column == 8) {
            return "Cairo-Sparta 5";
        }
        if (row == 2 && column == 5) {
            return "Rome-Sparta 9";
        }
        return null;
    }

    private CityStatus cityStatus(String cityName, boolean controlled) {
        CityStatus status = new CityStatus();
        status.text = controlled ? "Controlled" : "Uncaptured";
        for (int i = 0; i < getGame().getAvailableCities().size(); i++) {
            if (getGame().getAvailableCities().get(i).getName().equalsIgnoreCase(cityName)
                    && getGame().getAvailableCities().get(i).isUnderSiege()) {
                status.text = "Siege " + getGame().getAvailableCities().get(i).getTurnsUnderSiege();
                status.underSiege = true;
            }
        }
        int armiesHere = 0;
        for (int i = 0; i < getGame().getPlayer().getControlledArmies().size(); i++) {
            if (getGame().getPlayer().getControlledArmies().get(i).getCurrentLocation().equalsIgnoreCase(cityName)) {
                armiesHere++;
            }
        }
        if (armiesHere > 0) {
            status.text += "<br>Armies " + armiesHere;
        }
        return status;
    }

    private boolean isControlled(String cityName) {
        for (int i = 0; i < getGame().getPlayer().getControlledCities().size(); i++) {
            if (getGame().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }

    private JPanel createSidebar() {
        JPanel sidebar = UITheme.titledCard("Armies");
        sidebar.setLayout(new BorderLayout(0, 10));
        sidebar.setPreferredSize(new Dimension(460, 0));

        JLabel summary = UITheme.label("Field armies: " + getGame().getPlayer().getControlledArmies().size());
        sidebar.add(summary, BorderLayout.NORTH);
        sidebar.add(armyInformation(getGame().getPlayer().getControlledArmies()), BorderLayout.CENTER);
        saveBtn = UITheme.primaryButton("Save Game");
        saveBtn.addActionListener(this);
        sidebar.add(saveBtn, BorderLayout.SOUTH);
        return sidebar;
    }

    public JPanel armyInformation(ArrayList<Army> armies) {
        JPanel armyInfo = new JPanel(new BorderLayout());
        armyInfo.setOpaque(false);

        String[] columns = { "Target", "Distance", "Location", "Status", "Siege" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (armies != null) {
            for (int i = 0; i < armies.size(); i++) {
                Army army = armies.get(i);
                model.addRow(new Object[] { safeValue(army.getTarget()), distanceValue(army),
                        army.getCurrentLocation(), army.getCurrentStatus(), siegeValue(army) });
            }
        }

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        armyInfo.add(UITheme.scroll(table), BorderLayout.CENTER);
        return armyInfo;
    }

    private String safeValue(String value) {
        if (value == null || value.equals("")) {
            return "-";
        }
        return value;
    }

    private String distanceValue(Army army) {
        if (army.getDistancetoTarget() < 0) {
            return "-";
        }
        return String.valueOf(army.getDistancetoTarget());
    }

    private String siegeValue(Army army) {
        for (int j = 0; j < getGame().getAvailableCities().size(); j++) {
            if (getGame().getAvailableCities().get(j).getName().equals(army.getCurrentLocation())) {
                int turns = getGame().getAvailableCities().get(j).getTurnsUnderSiege();
                return turns < 0 ? "-" : String.valueOf(turns);
            }
        }
        return "-";
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCairo) {
            openCityOrTarget("Cairo");
        } else if (e.getSource() == btnRome) {
            openCityOrTarget("Rome");
        } else if (e.getSource() == btnSparta) {
            openCityOrTarget("Sparta");
        } else if (e.getSource() == btnforplayerArmies) {
            new DisplayArmies(getGame().getPlayer().getControlledArmies());
        } else if (e.getSource() == saveBtn) {
            saveGame();
        }
    }

    private void openCityOrTarget(String cityName) {
        for (int i = 0; i < getGame().getPlayer().getControlledCities().size(); i++) {
            if (getGame().getPlayer().getControlledCities().get(i).getName().equalsIgnoreCase(cityName)) {
                getWorldMapView().dispose();
                new CityView(getGame(), getGame().getPlayer().getControlledCities().get(i));
                return;
            }
        }
        new TargetingCity(getGame(), cityName);
    }

    private void saveGame() {
        try {
            getGame().save(Game.DEFAULT_SAVE_PATH);
            UITheme.showInfo(this, "Campaign saved to " + Game.DEFAULT_SAVE_PATH + ".");
        } catch (IOException exception) {
            UITheme.showError(this, "Could not save campaign: " + exception.getMessage());
        }
    }

    private static class CityStatus {
        private String text;
        private boolean underSiege;
    }
}
