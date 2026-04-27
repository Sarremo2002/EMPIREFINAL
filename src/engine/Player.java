package engine;

import java.util.ArrayList;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Army;
import units.Status;
import units.Unit;

public class Player {
    private String name;
    private ArrayList<City> controlledCities;
    private ArrayList<Army> controlledArmies;
    private double treasury;
    private double food;

    public Player(String name) {
        this.name = name;
        this.controlledCities = new ArrayList<City>();
        this.controlledArmies = new ArrayList<Army>();
    }

    public void recruitUnit(String type, String cityName)
            throws BuildingInCoolDownException, MaxRecruitedException, NotEnoughGoldException {
        String requestedType = type.toLowerCase();
        for (City c : controlledCities) {
            if (c.getName().equalsIgnoreCase(cityName)) {
                for (MilitaryBuilding b : c.getMilitaryBuildings()) {
                    if ((requestedType.equals("archer") && b instanceof ArcheryRange)
                            || (requestedType.equals("cavalry") && b instanceof Stable)
                            || (requestedType.equals("infantry") && b instanceof Barracks)) {

                        if (treasury < b.getRecruitmentCost())
                            throw new NotEnoughGoldException("Not enough gold");
                        Unit u = b.recruit();
                        treasury -= b.getRecruitmentCost();
                        u.setParentArmy(c.getDefendingArmy());
                        c.getDefendingArmy().getUnits().add(u);
                    }
                }
            }
        }

    }

    public void build(String type, String cityName) throws NotEnoughGoldException {
        String requestedType = type.toLowerCase();
        for (City c : controlledCities) {
            if (c.getName().equalsIgnoreCase(cityName)) {
                Building b = null;
                switch (requestedType) {
                case "archeryrange":
                    b = new ArcheryRange();
                    break;
                case "barracks":
                    b = new Barracks();
                    break;
                case "stable":
                    b = new Stable();
                    break;
                case "farm":
                    b = new Farm();
                    break;
                case "market":
                    b = new Market();
                    break;
                default:
                    return;
                }
                if (requestedType.equals("farm") || requestedType.equals("market")) {
                    for (EconomicBuilding e : c.getEconomicalBuildings()) {
                        if (e instanceof Farm && requestedType.equals("farm") || e instanceof Market && requestedType.equals("market"))
                            return;
                    }
                } else {
                    {
                        for (MilitaryBuilding e : c.getMilitaryBuildings()) {
                            if (e instanceof ArcheryRange && requestedType.equals("archeryrange")
                                    || e instanceof Barracks && requestedType.equals("barracks")
                                    || e instanceof Stable && requestedType.equals("stable"))
                                return;
                        }
                    }
                }
                if (treasury < b.getCost())
                    throw new NotEnoughGoldException("not enough gold");
                treasury -= b.getCost();
                if (requestedType.equals("farm") || requestedType.equals("market"))
                    c.getEconomicalBuildings().add((EconomicBuilding) b);
                else {
                    c.getMilitaryBuildings().add((MilitaryBuilding) b);
                }

            }
        }
    }

    public void upgradeBuilding(Building b)
            throws NotEnoughGoldException, BuildingInCoolDownException, MaxLevelException {
        if (b == null)
            throw new IllegalArgumentException("Building cannot be null");
        if (treasury < b.getUpgradeCost())
            throw new NotEnoughGoldException("not enough gold");
        int originalCost = b.getUpgradeCost();
        b.upgrade();
        treasury -= originalCost;
    }

    public void initiateArmy(City city, Unit unit) {
        if (city == null || unit == null)
            throw new IllegalArgumentException("City and unit are required");
        Army army = new Army(city.getName());
        army.getUnits().add(unit);
        city.getDefendingArmy().getUnits().remove(unit);
        unit.setParentArmy(army);
        controlledArmies.add(army);
    }

    public void laySiege(Army army, City city) throws TargetNotReachedException, FriendlyCityException {
        if (controlledCities.contains(city))
            throw new FriendlyCityException("You can't attack a friendly city");
        if (!army.getCurrentLocation().equalsIgnoreCase(city.getName()))
            throw new TargetNotReachedException("Target not reached");
        army.setCurrentStatus(Status.BESIEGING);
        city.setUnderSiege(true);
        city.setTurnsUnderSiege(0);
    }

    public double getTreasury() {
        return treasury;
    }

    public void setTreasury(double treasury) {
        this.treasury = treasury;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public ArrayList<City> getControlledCities() {
        return controlledCities;
    }

    public ArrayList<Army> getControlledArmies() {
        return controlledArmies;
    }

}
