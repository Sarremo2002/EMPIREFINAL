package tests;

import java.io.File;

import buildings.ArcheryRange;
import buildings.Farm;
import engine.City;
import engine.Game;
import units.Army;
import units.Status;
import units.Unit;

public class GameRulesTest {
    public static void main(String[] args) throws Exception {
        testSetupAndResources();
        testBuildRecruitTargetAndSaveLoad();
        System.out.println("All game rule tests passed.");
    }

    private static void testSetupAndResources() throws Exception {
        Game game = new Game("Tester", "rome");
        assertEquals(3, game.getAvailableCities().size(), "available cities load from resources");
        assertEquals(3, game.getDistances().size(), "distances load from resources");
        assertEquals(1, game.getPlayer().getControlledCities().size(), "player starts with one city");
        assertEquals(1200, (int) game.getPlayer().getFood(), "player starts with balanced food");
    }

    private static void testBuildRecruitTargetAndSaveLoad() throws Exception {
        Game game = new Game("Tester", "rome");
        City rome = controlledCity(game, "Rome");

        game.getPlayer().build("farm", "Rome");
        assertTrue(rome.getEconomicalBuildings().get(0) instanceof Farm, "farm is added to city");
        game.endTurn();
        assertTrue(game.getLastTurnSummary() != null, "end turn creates a turn summary");
        assertTrue(game.getPlayer().getFood() > 1200, "farm harvest increases food");

        game.getPlayer().build("archeryrange", "Rome");
        assertTrue(rome.getMilitaryBuildings().get(0) instanceof ArcheryRange, "archery range is added to city");
        game.endTurn();
        game.getPlayer().recruitUnit("archer", "Rome");
        assertEquals(1, rome.getDefendingArmy().getUnits().size(), "recruit adds unit to defending army");

        Unit unit = rome.getDefendingArmy().getUnits().get(0);
        game.getPlayer().initiateArmy(rome, unit);
        assertEquals(1, game.getPlayer().getControlledArmies().size(), "unit can create a field army");

        Army army = game.getPlayer().getControlledArmies().get(0);
        game.targetCity(army, "Cairo");
        assertEquals(6, army.getDistancetoTarget(), "targeting uses distance data");
        game.endTurn();
        assertEquals(Status.MARCHING, army.getCurrentStatus(), "army starts marching on end turn");
        assertEquals("onRoad", army.getCurrentLocation(), "marching army moves onto road");
        assertEquals(5, army.getDistancetoTarget(), "marching reduces distance");

        File saveFile = new File("bin/test-save.dat");
        saveFile.deleteOnExit();
        game.save(saveFile.getPath());
        Game loaded = Game.load(saveFile.getPath());
        assertEquals(game.getCurrentTurnCount(), loaded.getCurrentTurnCount(), "save/load keeps turn count");
        assertEquals(game.getPlayer().getControlledArmies().size(), loaded.getPlayer().getControlledArmies().size(),
                "save/load keeps armies");
    }

    private static City controlledCity(Game game, String name) {
        for (City city : game.getPlayer().getControlledCities()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        throw new AssertionError("Missing controlled city: " + name);
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        throw new AssertionError(message + " expected <" + expected + "> but was <" + actual + ">");
    }
}
