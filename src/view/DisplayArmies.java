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

import units.Army;

@SuppressWarnings({ "serial", "this-escape" })
public class DisplayArmies extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Army> armies;

    public DisplayArmies(ArrayList<Army> armies) {
        this.armies = armies;

        setTitle("Field Armies");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(createContent());
        setPreferredSize(new Dimension(620, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        page.add(UITheme.title("Field Armies"), BorderLayout.NORTH);

        JPanel list = UITheme.titledCard("Select an Army to View Units");
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        if (armies == null || armies.isEmpty()) {
            JLabel empty = UITheme.label("You do not have any field armies yet.");
            list.add(empty);
        } else {
            for (int i = 0; i < armies.size(); i++) {
                JButton button = UITheme.primaryButton(UITheme.armyName(armies.get(i), i));
                button.setAlignmentX(LEFT_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
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
                new UnitsView(armies.get(i));
                return;
            }
        }
    }
}
