package units;

import java.io.Serializable;

import exceptions.FriendlyFireException;

public abstract class Unit implements Serializable {
    private static final long serialVersionUID = 1L;

    private int level;
    private int maxSoldierCount;
    private int currentSoldierCount;
    private double idleUpkeep;
    private double marchingUpkeep;
    private double siegeUpkeep;
    private Army parentArmy;

    public Unit(int level,int maxSoldierConunt,double idleUpkeep, double marchingUpkeep,double siegeUpkeep) {
        this.level=level;
        this.maxSoldierCount=maxSoldierConunt;
        this.currentSoldierCount=maxSoldierConunt;
        this.idleUpkeep=idleUpkeep;
        this.marchingUpkeep=marchingUpkeep;
        this.siegeUpkeep=siegeUpkeep;

    }
public  void attack(Unit target) throws FriendlyFireException {
    if(target == null)
        throw new IllegalArgumentException("Target unit cannot be null");
    if(getParentArmy() != null && getParentArmy()==target.getParentArmy())
        throw new FriendlyFireException("Cannot attack a friendly unit");
}
protected int calculateDamage(double factor) {
    if(factor <= 0 || currentSoldierCount <= 0)
        return 0;
    int damage = (int)(factor * currentSoldierCount);
    return Math.max(1, damage);
}
public int getCurrentSoldierCount() {
    return currentSoldierCount;
}
public void setCurrentSoldierCount(int currentSoldierCount) {
    this.currentSoldierCount = currentSoldierCount;
    if(this.currentSoldierCount <= 0)
        this.currentSoldierCount =  0;
}
public int getLevel() {
    return level;
}
public int getMaxSoldierCount() {
    return maxSoldierCount;
}

public double getIdleUpkeep() {
    return idleUpkeep;
}
public double getMarchingUpkeep() {
    return marchingUpkeep;
}

public double getSiegeUpkeep() {
    return siegeUpkeep;
}
public Army getParentArmy() {
    return parentArmy;
}
public void setParentArmy(Army army) {
    this.parentArmy=army;

}



}
