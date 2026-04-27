package buildings;
public abstract class EconomicBuilding extends Building {
    private static final long serialVersionUID = 1L;

    public EconomicBuilding(int cost,int upgradeCost) {
        super(cost,upgradeCost);
    }
    public abstract int harvest();
}
