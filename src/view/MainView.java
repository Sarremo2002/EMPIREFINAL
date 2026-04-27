package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import engine.Game;
@SuppressWarnings({"serial", "this-escape"})
public abstract class MainView extends JFrame {

    private Game game;

    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public MainView(Game t){
        this.setTitle("Empire");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        game =t;

    }
    public JPanel createStats(){
        JPanel t = new JPanel();
        t.setBackground(Color.PINK);
        t.setLayout(new GridLayout(2,4));
        t.setSize((int)(this.getWidth() * 0.25),(int)(this.getHeight()*0.5));
        JLabel PlyrNm=new JLabel("Player Name: ");
        t.add(PlyrNm);
        JLabel PlayerName=new JLabel(game.getPlayer().getName());
        t.add(PlayerName);
        JLabel Trn=new JLabel("Turn Count :");
        t.add(Trn);
        String TurN = String.valueOf(game.getCurrentTurnCount());
        JLabel TurnCount=new JLabel(TurN);
        t.add(TurnCount);
        JLabel Gold=new JLabel("Gold :");
        t.add(Gold);
        JLabel CurrentGold =new JLabel(String.valueOf(game.getPlayer().getTreasury()));
        t.add(CurrentGold);
        JLabel Food =new JLabel("Food :");
        t.add(Food);
        JLabel CurrentFood =new JLabel(String.valueOf(game.getPlayer().getFood()));
        t.add(CurrentFood);
        return t;
    }
}
