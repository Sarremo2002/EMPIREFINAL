package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;

@SuppressWarnings({ "serial", "this-escape" })
public class TargetingCity extends JFrame implements ActionListener {
    private JButton attack;
    private JButton cancel;
    private String cityName;
    private Game game;

    public TargetingCity(Game game, String cityName) {
        this.cityName = cityName;
        this.game = game;

        setTitle("Target City");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));

        JLabel title = UITheme.title(cityName + " Is Uncaptured");
        UITheme.centerLabel(title);
        page.add(title, BorderLayout.NORTH);

        JLabel detail = UITheme.smallLight("Choose a field army to march on this city.");
        UITheme.centerLabel(detail);
        page.add(detail, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        cancel = UITheme.button("Cancel");
        attack = UITheme.primaryButton("Choose Army");
        cancel.addActionListener(this);
        attack.addActionListener(this);
        actions.add(cancel);
        actions.add(attack);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attack) {
            new ChooseCityToTarget(cityName, game, game.getPlayer().getControlledArmies());
        }
        dispose();
    }
}
