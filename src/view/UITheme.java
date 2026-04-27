package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

import engine.TurnSummary;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Unit;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public final class UITheme {
    public static final Color BACKGROUND = Color.decode("#1f2420");
    public static final Color PANEL = Color.decode("#f4ead8");
    public static final Color PANEL_DARK = Color.decode("#2f382e");
    public static final Color TEXT = Color.decode("#211d18");
    public static final Color TEXT_LIGHT = Color.decode("#f8f0df");
    public static final Color ACCENT = Color.decode("#b87932");
    public static final Color ACCENT_DARK = Color.decode("#83511f");
    public static final Color SUCCESS = Color.decode("#517f4d");
    public static final Color DANGER = Color.decode("#9f3a32");
    public static final Color TERRAIN = Color.decode("#789c59");
    public static final Color ROAD = Color.decode("#d7bd77");
    public static final Color MUTED = Color.decode("#746657");

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private UITheme() {
    }

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        UIManager.put("Label.font", BODY_FONT);
        UIManager.put("Button.font", BUTTON_FONT);
        UIManager.put("Button.disabledText", Color.decode("#5b5146"));
        UIManager.put("Table.font", BODY_FONT);
        UIManager.put("Table.rowHeight", Integer.valueOf(28));
    }

    public static JPanel pagePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        return panel;
    }

    public static JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(ACCENT_DARK, 1),
                new EmptyBorder(12, 12, 12, 12)));
        return panel;
    }

    public static JPanel titledCard(String title) {
        JPanel panel = card();
        TitledBorder border = BorderFactory.createTitledBorder(new LineBorder(ACCENT_DARK, 1), title);
        border.setTitleFont(HEADING_FONT);
        border.setTitleColor(TEXT);
        panel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 10, 10, 10)));
        return panel;
    }

    public static JPanel mapTile() {
        JPanel panel = new JPanel();
        panel.setBackground(TERRAIN);
        panel.setBorder(new LineBorder(Color.decode("#5f7a45"), 1));
        panel.setPreferredSize(new Dimension(74, 58));
        return panel;
    }

    public static JPanel roadTile(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ROAD);
        panel.setBorder(new LineBorder(ACCENT_DARK, 1));
        JLabel label = label(text);
        centerLabel(label);
        panel.add(label, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(74, 58));
        return panel;
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT_LIGHT);
        return label;
    }

    public static JLabel heading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(HEADING_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel smallLight(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(TEXT_LIGHT);
        return label;
    }

    public static JButton button(String text) {
        JButton button = new JButton(text);
        styleButton(button, Color.decode("#fff8ec"), TEXT);
        return button;
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, ACCENT, Color.WHITE);
        return button;
    }

    public static JButton successButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, SUCCESS, Color.WHITE);
        return button;
    }

    public static JButton dangerButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, DANGER, Color.WHITE);
        return button;
    }

    public static JButton cityButton(String text, boolean controlled) {
        return cityButton(text, controlled ? "Controlled" : "Uncaptured", controlled, false);
    }

    public static JButton cityButton(String text, String status, boolean controlled, boolean underSiege) {
        JButton button = new JButton("<html><center>" + text + "<br>" + status + "</center></html>");
        styleButton(button, underSiege ? ACCENT : controlled ? SUCCESS : DANGER, Color.WHITE);
        button.setPreferredSize(new Dimension(116, 68));
        return button;
    }

    public static void styleButton(AbstractButton button, Color background, Color foreground) {
        button.setUI(new BasicButtonUI());
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setBorder(BorderFactory.createCompoundBorder(new LineBorder(ACCENT_DARK, 1),
                new EmptyBorder(8, 12, 8, 12)));
        button.setMargin(new Insets(8, 12, 8, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void disable(AbstractButton button, String text) {
        button.setText(text);
        button.setEnabled(false);
        button.setBackground(Color.decode("#e2d8c8"));
        button.setForeground(Color.decode("#5b5146"));
        button.setCursor(Cursor.getDefaultCursor());
    }

    public static JScrollPane scroll(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(new LineBorder(ACCENT_DARK, 1));
        scrollPane.getViewport().setBackground(PANEL);
        return scrollPane;
    }

    public static void centerLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    public static String number(double value) {
        return String.format("%.0f", Double.valueOf(value));
    }

    public static String unitType(Unit unit) {
        if (unit instanceof Archer) {
            return "Archer";
        }
        if (unit instanceof Infantry) {
            return "Infantry";
        }
        if (unit instanceof Cavalry) {
            return "Cavalry";
        }
        return "Unit";
    }

    public static String armyName(Army army, int index) {
        String target = army.getTarget() == null || army.getTarget().equals("") ? "-" : army.getTarget();
        return "Army " + (index + 1) + " | " + army.getCurrentLocation() + " | " + army.getUnits().size()
                + " units | target " + target;
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Empire", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Empire", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showTurnSummary(Component parent, TurnSummary summary) {
        if (summary == null) {
            return;
        }
        JTextArea area = new JTextArea(summary.toDisplayText(), 14, 46);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JOptionPane.showMessageDialog(parent, scroll(area), "Empire", JOptionPane.INFORMATION_MESSAGE);
    }
}
