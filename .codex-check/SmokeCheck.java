import engine.Game;

public class SmokeCheck {
    public static void main(String[] args) throws Exception {
        Game game = new Game("Tester", "Cairo");
        System.out.println("cities=" + game.getAvailableCities().size());
        System.out.println("controlled=" + game.getPlayer().getControlledCities().size());
    }
}
