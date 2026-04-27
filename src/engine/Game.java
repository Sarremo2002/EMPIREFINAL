package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import exceptions.FriendlyFireException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_SAVE_PATH = "saves/empire-save.dat";

    private Player player;
    private ArrayList<City> availableCities;
    private ArrayList<Distance> distances;
    private final int maxTurnCount = 50;
    private int currentTurnCount;
    private TurnSummary lastTurnSummary;

    @SuppressWarnings("this-escape")
    public Game(String playerName, String playerCity) throws IOException {
        player = new Player(playerName);
        player.setTreasury(5000);
        player.setFood(1200);
        availableCities = new ArrayList<City>();
        distances = new ArrayList<Distance>();
        currentTurnCount = 1;
        loadCitiesAndDistances();
        for (City c : availableCities) {
            if (c.getName().toLowerCase().equals(playerCity.toLowerCase())) {
                player.getControlledCities().add(c);
            }
        }
        if (playerCity.toLowerCase().equals("cairo")) {
            loadArmy("Rome", "rome_army.csv");
            loadArmy("Sparta", "sparta_army.csv");
        } else if (playerCity.toLowerCase().equals("rome")) {
            loadArmy("Cairo", "cairo_army.csv");
            loadArmy("sparta", "sparta_army.csv");
        } else {
            loadArmy("Rome", "rome_army.csv");
            loadArmy("Cairo", "cairo_army.csv");
        }

    }
    private void loadCitiesAndDistances() throws IOException {

        BufferedReader br = openDataReader("distances.csv");
        String currentLine = br.readLine();
        ArrayList<String> names = new ArrayList<String>();

        while (currentLine != null) {

            String[] content = currentLine.split(",");
            if (!names.contains(content[0])) {
                availableCities.add(new City(content[0]));
                names.add(content[0]);
            }
            if (!names.contains(content[1])) {
                availableCities.add(new City(content[1]));
                names.add(content[1]);
            }
            distances.add(new Distance(content[0], content[1], Integer.parseInt(content[2])));
            currentLine = br.readLine();

        }
        br.close();
    }

    public void loadArmy(String cityName, String path) throws IOException {

        BufferedReader br = openDataReader(path);
        String currentLine = br.readLine();
        Army resultArmy = new Army(cityName);
        while (currentLine != null) {
            String[] content = currentLine.split(",");
            if (content.length != 2) {
                br.close();
                throw new IOException("Invalid army row: " + currentLine);
            }
            String unitType = content[0].toLowerCase();
            int unitLevel = Integer.parseInt(content[1]);
            Unit u = null;
            if (unitType.equals("archer")) {

                if (unitLevel == 1)
                    u = (new Archer(1, 60, 0.4, 0.5, 0.6));

                else if (unitLevel == 2)
                    u = (new Archer(2, 60, 0.4, 0.5, 0.6));
                else
                    u = (new Archer(3, 70, 0.5, 0.6, 0.7));
            } else if (unitType.equals("infantry")) {
                if (unitLevel == 1)
                    u = (new Infantry(1, 50, 0.5, 0.6, 0.7));

                else if (unitLevel == 2)
                    u = (new Infantry(2, 50, 0.5, 0.6, 0.7));
                else
                    u = (new Infantry(3, 60, 0.6, 0.7, 0.8));
            } else if (unitType.equals("cavalry")) {
                if (unitLevel == 1)
                    u = (new Cavalry(1, 40, 0.6, 0.7, 0.75));

                else if (unitLevel == 2)
                    u = (new Cavalry(2, 40, 0.6, 0.7, 0.75));
                else
                    u = (new Cavalry(3, 60, 0.7, 0.8, 0.9));
            }
            if (u == null) {
                br.close();
                throw new IOException("Unknown unit type: " + content[0]);
            }
            resultArmy.getUnits().add(u);
            u.setParentArmy(resultArmy);
            currentLine = br.readLine();
        }
        for (City c : availableCities) {
            if (c.getName().toLowerCase().equals(cityName.toLowerCase()))
                c.setDefendingArmy(resultArmy);
        }
        br.close();
    }

    public void targetCity(Army army, String targetName) {

        String from = army.getCurrentLocation();
        if (army.getCurrentLocation().equals("onRoad"))
            from = army.getTarget();
        for (Distance d : distances) {
            if ((d.getFrom().equals(from) || d.getFrom().equals(targetName))
                    && (d.getTo().equals(from) || d.getTo().equals(targetName))) {
                army.setTarget(targetName);
                int distance = d.getDistance();
                if (army.getCurrentLocation().equals("onRoad"))
                    distance += army.getDistancetoTarget();
                army.setDistancetoTarget(distance);
            }
        }

    }

    public void endTurn() {
        TurnSummary summary = new TurnSummary(currentTurnCount + 1, player.getFood());
        currentTurnCount++;
        double totalUpkeep = 0;
        for (City c : player.getControlledCities()) {
            for (MilitaryBuilding b : c.getMilitaryBuildings()) {

                b.setCoolDown(false);
                b.setCurrentRecruit(0);

            }
            for (EconomicBuilding b : c.getEconomicalBuildings()) {

                b.setCoolDown(false);
                if (b instanceof Market) {
                    summary.addGold(b.harvest());
                    summary.addEvent(c.getName() + " market produced " + b.harvest() + " gold.");
                    player.setTreasury(player.getTreasury() + b.harvest());
                }
                else if (b instanceof Farm) {
                    summary.addFood(b.harvest());
                    summary.addEvent(c.getName() + " farm produced " + b.harvest() + " food.");
                    player.setFood(player.getFood() + b.harvest());
                }
            }
            totalUpkeep+=c.getDefendingArmy().foodNeeded();
        }
        for (Army a : player.getControlledArmies()) {
            if (!a.getTarget() .equals("") && a.getCurrentStatus() == Status.IDLE) {
                a.setCurrentStatus(Status.MARCHING);
                a.setCurrentLocation("onRoad");
                summary.addEvent("Army marching toward " + a.getTarget() + ".");
            }
            if(a.getDistancetoTarget()>0 &&!a.getTarget().equals(""))
            a.setDistancetoTarget(a.getDistancetoTarget() - 1);
            if (a.getDistancetoTarget() == 0) {
                a.setCurrentLocation(a.getTarget());
                summary.addEvent("Army arrived at " + a.getTarget() + ".");
                a.setTarget("");
                a.setCurrentStatus(Status.IDLE);
            }
            totalUpkeep +=  a.foodNeeded();

        }
        summary.setUpkeepPaid(totalUpkeep);
        if (totalUpkeep <= player.getFood())
            player.setFood(player.getFood() - totalUpkeep);
        else {
            summary.setStarvation(true);
            summary.addEvent("Food was not enough for upkeep. Attrition hit your armies.");
            player.setFood(0);
            for (City c : player.getControlledCities()) {
                int losses = applyAttrition(c.getDefendingArmy());
                if (losses > 0)
                    summary.addEvent(c.getName() + " defenders lost " + losses + " soldiers to starvation.");
            }
            for (Army a : player.getControlledArmies()) {
                int losses = applyAttrition(a);
                if (losses > 0)
                    summary.addEvent("Field army at " + a.getCurrentLocation() + " lost " + losses + " soldiers to starvation.");
            }
        }

        for (City c : availableCities) {
            if (c.isUnderSiege()) {
                if(c.getTurnsUnderSiege() < 3){
                c.setTurnsUnderSiege(c.getTurnsUnderSiege() + 1);
                summary.addEvent(c.getName() + " siege advanced to turn " + c.getTurnsUnderSiege() + ".");

                }
                else{
                    // player should choose to attack
                    c.setUnderSiege(false);
                    summary.addEvent(c.getName() + " siege limit reached. Attack must be resolved.");
                    summary.setFoodAfter(player.getFood());
                    lastTurnSummary = summary;
                    return;
                }
                int losses = applyAttrition(c.getDefendingArmy());
                if (losses > 0)
                    summary.addEvent(c.getName() + " defenders lost " + losses + " soldiers under siege.");
            }
        }
        summary.setFoodAfter(player.getFood());
        lastTurnSummary = summary;

    }

    private int applyAttrition(Army army) {
        int totalLosses = 0;
        for (int i = army.getUnits().size() - 1; i >= 0; i--) {
            Unit u = army.getUnits().get(i);
            int before = u.getCurrentSoldierCount();
            int loss = Math.max(1, (int) (u.getCurrentSoldierCount() * 0.1));
            u.setCurrentSoldierCount(u.getCurrentSoldierCount() - loss);
            totalLosses += before - u.getCurrentSoldierCount();
            army.handleAttackedUnit(u);
        }
        return totalLosses;
    }

    private BufferedReader openDataReader(String path) throws IOException {
        InputStream input = Game.class.getClassLoader().getResourceAsStream(path);
        if (input == null)
            input = Game.class.getClassLoader().getResourceAsStream("resources/" + path);
        if (input != null)
            return new BufferedReader(new InputStreamReader(input));

        File resourceFile = new File("resources", path);
        if (resourceFile.isFile())
            return new BufferedReader(new InputStreamReader(new FileInputStream(resourceFile)));

        return new BufferedReader(new FileReader(path));
    }

    public void save(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null)
            parent.mkdirs();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(this);
        out.close();
    }

    public static Game load(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        Game game = (Game) in.readObject();
        in.close();
        return game;
    }

    public static boolean hasDefaultSave() {
        return new File(DEFAULT_SAVE_PATH).isFile();
    }

    public void autoResolve(Army attacker, Army defender) throws FriendlyFireException {
        int turn = 1;
        while (attacker.getUnits().size() != 0 && defender.getUnits().size() != 0) {
            Unit unit1 = attacker.getUnits().get((int) (Math.random() * attacker.getUnits().size()));
            Unit unit2 = defender.getUnits().get((int) (Math.random() * defender.getUnits().size()));
            if (turn == 1)
                unit1.attack(unit2);
            else
                unit2.attack(unit1);
            turn = turn == 1 ? 0 : 1;

        }
        if (attacker.getUnits().size() != 0)
            occupy(attacker, defender.getCurrentLocation());

    }

    public void occupy(Army a, String cityName) {
        for (City c : availableCities) {
            if (c.getName().equals(cityName)) {
                if (!player.getControlledCities().contains(c))
                    player.getControlledCities().add(c);
                player.getControlledArmies().remove(a);
                c.setDefendingArmy(a);
                c.setUnderSiege(false);
                c.setTurnsUnderSiege(-1);
                a.setCurrentStatus(Status.IDLE);
                a.setCurrentLocation(cityName);
                a.setTarget("");
                a.setDistancetoTarget(-1);
            }
        }
    }

    public boolean isGameOver() {
        return player.getControlledCities().size() == availableCities.size() || currentTurnCount > maxTurnCount;
    }

    public ArrayList<City> getAvailableCities() {
        return availableCities;
    }

    public ArrayList<Distance> getDistances() {
        return distances;
    }

    public int getMaxTurnCount() {
        return maxTurnCount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCurrentTurnCount() {
        return currentTurnCount;
    }

    public void setCurrentTurnCount(int currentTurnCount) {
        this.currentTurnCount = currentTurnCount;
    }

    public TurnSummary getLastTurnSummary() {
        return lastTurnSummary;
    }

}
