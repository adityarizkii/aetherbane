package com.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private HomeScreenPanel homeScreenPanel;
    private JLayeredPane gamePanel;
    private LeaderboardPanel leaderboardPanel;
    private WizardPanel wizardPanel;
    private MonsterPanel monsterPanel;
    private GameOverPanel gameOverPanel;
    private boolean getGameOver = false;
    private String[] currentSpell = { "imperius", "lumos", "nox", "accio" };
    private JLabel spellLabel;

    public Main() {
        setTitle("Magic Word");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        homeScreenPanel = new HomeScreenPanel();
        homeScreenPanel.getStartGameButton().addActionListener(e -> {
            if (getGameOver) {
                restartGame();
            } else {
                startGame();
            }
        });
        cardPanel.add(homeScreenPanel, "HomeScreen");

        gamePanel = createGamePanel();
        cardPanel.add(gamePanel, "Game");

        gameOverPanel = new GameOverPanel(this);
        gameOverPanel.getRestartButton().addActionListener(e -> restartGame());
        cardPanel.add(gameOverPanel, "GameOver");

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

        monsterPanel = new MonsterPanel(350, 350, 200);
        monsterPanel.setBounds(0, 200, 1366, 250);
        monsterPanel.setBackground(Color.GREEN);
        monsterPanel.setOpaque(false);

        wizardPanel = new WizardPanel(350, 350, 10, this);
        wizardPanel.setBounds(0, 200, 1366, 250);
        wizardPanel.setBackground(Color.CYAN);
        wizardPanel.setOpaque(false);

        wizardPanel.setMonsterPanel(monsterPanel);
        monsterPanel.setWizardPanel(wizardPanel);

        JPanel spellPanel = createSpellPanel();
        spellPanel.setBounds(getWidth() / 2 - 200, 50, 300, 50);

        HpDisplayPanel hpDisplayPanel = new HpDisplayPanel(wizardPanel, monsterPanel);
        hpDisplayPanel.setBounds(0, 0, 1366, 100);
        hpDisplayPanel.setOpaque(false);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(wizardPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(monsterPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(spellPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(hpDisplayPanel, JLayeredPane.PALETTE_LAYER);

        return layeredPane;
    }

    private JPanel createSpellPanel() {
        JPanel spellPanel = new JPanel();
        spellPanel.setLayout(new BorderLayout());
        spellPanel.setBackground(new Color(0, 0, 0, 150));
        spellPanel.setOpaque(true);

        spellLabel = new JLabel("Input Spell!");
        spellLabel.setFont(new Font("Arial", Font.BOLD, 36));
        spellLabel.setForeground(Color.WHITE);
        spellLabel.setHorizontalAlignment(SwingConstants.CENTER);

        spellLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        spellLabel.setBackground(new Color(0, 0, 0, 100));
        spellLabel.setOpaque(true);

        spellPanel.add(spellLabel, BorderLayout.CENTER);

        changeSpellWithAnimation();

        return spellPanel;
    }

    private void changeSpellWithAnimation() {
        Random random = new Random();
        String newSpell = currentSpell[random.nextInt(currentSpell.length)];

        Timer timer = new Timer(500, e -> {
            spellLabel.setText(newSpell);
            spellLabel.setForeground(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void startGame() {
        SoundEffect.stopSound();
        cardLayout.show(cardPanel, "Game");

        wizardPanel.setupKeyboardControl();
        wizardPanel.setFocusable(true);
        wizardPanel.requestFocusInWindow();

        SwingUtilities.invokeLater(() -> {
            wizardPanel.requestFocus();
            System.out.println("Focus owner: " + KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
        });

        monsterPanel.play();
        BackgroundMusic.playBGM("src/main/resources/background_music.wav");
    }

    public void updateSpell() {
        Random random = new Random();
        String newSpell = currentSpell[random.nextInt(currentSpell.length)];
        spellLabel.setText(newSpell);
    }

    public void restartGame() {
        destroyGame();

        gameOverPanel.setWorth(false);
        gamePanel = createGamePanel(); // Membuat panel baru
        cardPanel.add(gamePanel, "Game"); // Menambahkan panel baru ke dalam cardPanel

        cardPanel.revalidate(); // Memastikan perubahan panel diterapkan
        cardPanel.repaint();

        startGame(); // Memulai game baru
    }

    public void showGameOverPanel(int score) {
        BackgroundMusic.stopMusic();
        destroyGame();
        SoundEffect.playSound("src/main/resources/game-over.wav");
        gameOverPanel.setScore(score);
        if (score >= getLeaderboardPanel().getLowerInLeaderboard()) {
            gameOverPanel.setWorth(true);
        } else {
            gameOverPanel.setWorth(false);
        }
        cardLayout.show(cardPanel, "GameOver");
        gameOverPanel.showGameOverPanel();
        getGameOver = true;
        repaint();
        revalidate();
    }

    public void destroyGame() {
        wizardPanel.stopThread();
        monsterPanel.stopThread();
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public HomeScreenPanel getHomeScreenPanel() {
        return homeScreenPanel;
    }

    public JLayeredPane getGamePanel() {
        return gamePanel;
    }

    public LeaderboardPanel getLeaderboardPanel() {
        return leaderboardPanel;
    }

    public WizardPanel getWizardPanel() {
        return wizardPanel;
    }

    public MonsterPanel getMonsterPanel() {
        return monsterPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public JLabel getSpellLabel() {
        return spellLabel;
    }
}
