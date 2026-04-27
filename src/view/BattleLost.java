package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "this-escape" })
public class BattleLost extends JFrame implements ActionListener {
    private JFrame battleResults;
    private JButton ok;

    public BattleLost() {
        battleResults = new JFrame("Battle Lost");
        battleResults.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        battleResults.setContentPane(createContent());
        battleResults.pack();
        battleResults.setLocationRelativeTo(null);
        battleResults.setVisible(true);
    }

    private JPanel createContent() {
        JPanel page = UITheme.pagePanel();
        page.setLayout(new BorderLayout(0, 14));
        JLabel result = UITheme.title("Battle Lost");
        UITheme.centerLabel(result);
        JLabel detail = UITheme.smallLight("The attacking army has been defeated.");
        UITheme.centerLabel(detail);
        page.add(result, BorderLayout.NORTH);
        page.add(detail, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        actions.setOpaque(false);
        ok = UITheme.primaryButton("OK");
        ok.addActionListener(this);
        actions.add(ok);
        page.add(actions, BorderLayout.SOUTH);
        return page;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            battleResults.dispose();
        }
    }
}
