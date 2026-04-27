package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;

@SuppressWarnings({ "serial", "this-escape" })
public class GameOver extends JFrame implements ActionListener {
    private JButton close;

    public GameOver(Game game) {
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContent(game));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent(Game game) {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 16));

        boolean won = game.getAvailableCities().size() == game.getPlayer().getControlledCities().size();
        JLabel title = UITheme.title(won ? "Empire United" : "Campaign Lost");
        UITheme.centerLabel(title);
        page.add(title, BorderLayout.NORTH);

        JPanel stats = UITheme.titledCard("Final Campaign");
        stats.setLayout(new GridLayout(3, 2, 12, 8));
        stats.add(UITheme.label("Player"));
        stats.add(UITheme.label(game.getPlayer().getName()));
        stats.add(UITheme.label("Cities controlled"));
        stats.add(UITheme.label(game.getPlayer().getControlledCities().size() + "/" + game.getAvailableCities().size()));
        stats.add(UITheme.label("Final turn"));
        stats.add(UITheme.label(String.valueOf(game.getCurrentTurnCount())));
        page.add(stats, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        actions.setOpaque(false);
        close = UITheme.primaryButton("Close Game");
        close.addActionListener(this);
        actions.add(close);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
        }
    }
}
