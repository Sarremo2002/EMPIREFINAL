package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
        UITheme.install();
        this.setTitle("Empire");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMinimumSize(new Dimension(1024, 680));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(UITheme.BACKGROUND);
        game =t;
        this.setVisible(true);

    }
    public JPanel createStats(){
        JPanel panel = UITheme.card();
        panel.setBackground(UITheme.PANEL_DARK);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 8));
        panel.setSize((int)(this.getWidth() * 0.25),(int)(this.getHeight()*0.5));
        panel.add(stat("Player", game.getPlayer().getName()));
        panel.add(stat("Turn", String.valueOf(game.getCurrentTurnCount()) + "/" + game.getMaxTurnCount()));
        panel.add(stat("Gold", UITheme.number(game.getPlayer().getTreasury())));
        panel.add(stat("Food", UITheme.number(game.getPlayer().getFood())));
        return panel;
    }

    private JPanel stat(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout(4, 0));
        panel.setOpaque(false);
        JLabel titleLabel = UITheme.smallLight(title + ": ");
        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(valueLabel.getFont().deriveFont(java.awt.Font.BOLD));
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }
}
