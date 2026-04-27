package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import engine.Game;

@SuppressWarnings("serial")
public class Enter extends JFrame implements ActionListener {
    private final JTextField playerName = new JTextField(18);
    private final JComboBox<String> playerCity = new JComboBox<String>(new String[] { "Cairo", "Rome", "Sparta" });

    public Enter() {
        UITheme.install();
        setTitle("Empire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 360));
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(createEnterPage());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createEnterPage() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 18));

        JPanel header = new JPanel(new BorderLayout(0, 8));
        header.setOpaque(false);
        JLabel title = UITheme.title("Empire");
        UITheme.centerLabel(title);
        JLabel subtitle = UITheme.smallLight("Choose your capital and begin the campaign.");
        UITheme.centerLabel(subtitle);
        header.add(title, BorderLayout.CENTER);
        header.add(subtitle, BorderLayout.SOUTH);
        page.add(header, BorderLayout.NORTH);

        JPanel form = UITheme.titledCard("New Campaign");
        form.setLayout(new GridLayout(2, 2, 12, 12));
        form.add(UITheme.label("Player name"));
        form.add(playerName);
        form.add(UITheme.label("Starting city"));
        form.add(playerCity);
        page.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actions.setOpaque(false);
        javax.swing.JButton start = UITheme.primaryButton("Start Campaign");
        start.addActionListener(this);
        actions.add(start);
        page.add(actions, BorderLayout.SOUTH);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                playerName.requestFocusInWindow();
            }
        });
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        String name = playerName.getText().trim();
        String city = ((String) playerCity.getSelectedItem()).toLowerCase();

        if (name.equals("")) {
            UITheme.showError(this, "Please enter your name before starting.");
            return;
        }

        try {
            dispose();
            new WorldMap(new Game(name, city));
        } catch (IOException exception) {
            UITheme.showError(this, "Could not load game data: " + exception.getMessage());
            new Enter();
        }
    }
}
