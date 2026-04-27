package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;
import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class ChooseCityToTarget extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Army> armies;
    private Game game;
    private String targetCity;

    public ChooseCityToTarget(String targetCity, Game game, ArrayList<Army> armies) {
        this.targetCity = targetCity;
        this.game = game;
        this.armies = armies;

        setTitle("Target " + targetCity);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        setPreferredSize(new Dimension(580, 420));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Target " + targetCity), BorderLayout.NORTH);

        JPanel list = UITheme.titledCard("Choose Army");
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        if (armies == null || armies.isEmpty()) {
            JLabel empty = UITheme.label("You need to dispatch a field army before targeting a city.");
            list.add(empty);
        } else {
            for (int i = 0; i < armies.size(); i++) {
                JButton button = UITheme.primaryButton(UITheme.armyName(armies.get(i), i));
                button.setAlignmentX(LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
                button.addActionListener(this);
                buttons.add(button);
                list.add(button);
                list.add(Box.createVerticalStrut(8));
            }
        }

        page.add(UITheme.scroll(list), BorderLayout.CENTER);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                game.targetCity(armies.get(i), targetCity);
                UITheme.showInfo(this, "Army " + (i + 1) + " is now marching toward " + targetCity + ".");
                dispose();
                return;
            }
        }
    }
}
