package engine;

import java.io.Serializable;
import java.util.ArrayList;

public class TurnSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    private int turnNumber;
    private double goldGained;
    private double foodGained;
    private double upkeepPaid;
    private double foodBefore;
    private double foodAfter;
    private boolean starvation;
    private ArrayList<String> events;

    public TurnSummary(int turnNumber, double foodBefore) {
        this.turnNumber = turnNumber;
        this.foodBefore = foodBefore;
        this.events = new ArrayList<String>();
    }

    public void addGold(double amount) {
        goldGained += amount;
    }

    public void addFood(double amount) {
        foodGained += amount;
    }

    public void setUpkeepPaid(double upkeepPaid) {
        this.upkeepPaid = upkeepPaid;
    }

    public void setFoodAfter(double foodAfter) {
        this.foodAfter = foodAfter;
    }

    public void setStarvation(boolean starvation) {
        this.starvation = starvation;
    }

    public void addEvent(String event) {
        if (event != null && !event.equals("")) {
            events.add(event);
        }
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public double getGoldGained() {
        return goldGained;
    }

    public double getFoodGained() {
        return foodGained;
    }

    public double getUpkeepPaid() {
        return upkeepPaid;
    }

    public double getFoodBefore() {
        return foodBefore;
    }

    public double getFoodAfter() {
        return foodAfter;
    }

    public boolean isStarvation() {
        return starvation;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public String toDisplayText() {
        StringBuilder text = new StringBuilder();
        text.append("Turn ").append(turnNumber).append(" Summary\n\n");
        text.append("Gold gained: ").append(format(goldGained)).append("\n");
        text.append("Food gained: ").append(format(foodGained)).append("\n");
        text.append("Food upkeep: ").append(format(upkeepPaid)).append("\n");
        text.append("Food: ").append(format(foodBefore)).append(" -> ").append(format(foodAfter)).append("\n");
        if (starvation) {
            text.append("Starvation: yes, attrition was applied\n");
        }
        if (!events.isEmpty()) {
            text.append("\nEvents:\n");
            for (String event : events) {
                text.append("- ").append(event).append("\n");
            }
        }
        return text.toString();
    }

    private String format(double value) {
        return String.format("%.0f", Double.valueOf(value));
    }
}
