package com.main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HomeScreenPanel homeScreenPanel;
    private JLayeredPane gamePanel;
    private LeaderboardPanel leaderboardPanel;
    private WizardPanel wizardPanel;
    private MonsterPanel monsterPanel;

    public Main() {
        setTitle("Magic Word");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homeScreenPanel = new HomeScreenPanel();
        homeScreenPanel.getStartGameButton().addActionListener(e -> startGame());
        cardPanel.add(homeScreenPanel, "HomeScreen");

        gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "Game");

        leaderboardPanel = new LeaderboardPanel();
        leaderboardPanel.getBackButton().addActionListener(e -> {
            cardLayout.show(cardPanel, "HomeScreen");
        });
        cardPanel.add(leaderboardPanel, "Leaderboard");

        homeScreenPanel.getLeaderboardButton().addActionListener(e -> {
            cardLayout.show(cardPanel, "Leaderboard");
        });

        add(cardPanel);

        setVisible(true);
    }

    private JLayeredPane createGamePanel() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 700));

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0, 0, 1366, 700);

        monsterPanel = new MonsterPanel(350, 350, 10);
        monsterPanel.setBounds(0, 200, 1366, 250);
        monsterPanel.setBackground(Color.GREEN);
        monsterPanel.setOpaque(false);

        wizardPanel = new WizardPanel(350, 350, 10);
        wizardPanel.setBounds(0, 200, 1366, 250);
        wizardPanel.setBackground(Color.CYAN);
        wizardPanel.setOpaque(false);

        wizardPanel.setMonsterPanel(monsterPanel);
        monsterPanel.setWizardPanel(wizardPanel);

        HpDisplayPanel hpDisplayPanel = new HpDisplayPanel(wizardPanel, monsterPanel);
        hpDisplayPanel.setBounds(0, 0, 1366, 100);
        hpDisplayPanel.setOpaque(false);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(wizardPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(monsterPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(hpDisplayPanel, JLayeredPane.PALETTE_LAYER);

        return layeredPane;
    }

    private void startGame() {
        cardLayout.show(cardPanel, "Game");

        wizardPanel.setupKeyboardControl();
        wizardPanel.setFocusable(true);
        wizardPanel.requestFocusInWindow();

        SwingUtilities.invokeLater(() -> {
            wizardPanel.requestFocus();
            System.out.println("Focus owner: " + KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
        });

        monsterPanel.play();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}