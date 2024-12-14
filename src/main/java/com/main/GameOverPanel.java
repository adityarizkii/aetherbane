package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class GameOverPanel extends JPanel {
    private Image background;
    private JButton startGameButton;
    private JButton saveButton;
    private JTextField usernameField;
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton exitButton;
    private boolean isWorth = false;
    private int score;
    private String username;
    private Main mainFrame;
    private JLayeredPane layeredPane;

    public GameOverPanel(Main mainFrame) {
        setSize(1366, 700);
        this.mainFrame = mainFrame;
        layeredPane = new JLayeredPane();
        restartButton = new JButton();
        exitButton = new JButton();
    }

    public void showGameOverPanel() {
        System.out.println("apakah Worth : " + isWorth);
        System.out.println("score : " + score);
        layeredPane.removeAll(); // Hapus semua komponen
        layeredPane.revalidate();
        layeredPane.repaint();
        layeredPane.setPreferredSize(new Dimension(1366, 700));

        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/background-gameover.png");
        background = backgroundImageIcon.getImage();
        JLabel backgroundLabel = new JLabel(new ImageIcon(background));
        backgroundLabel.setPreferredSize(new Dimension(1366, 700));
        backgroundLabel.setBounds(0, 0, background.getWidth(null), background.getHeight(null));

        JLabel enterYourUsername = new JLabel("Enter Your Username");
        enterYourUsername.setBounds(getWidth() / 2 - 150, getHeight() / 2 - 5, 300, 40);
        enterYourUsername.setFont(new Font("Arial", Font.PLAIN, 12));
        enterYourUsername.setForeground(Color.WHITE); // Warna placeholder
        enterYourUsername.setHorizontalAlignment(JLabel.CENTER);

        scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setBounds(getWidth() / 2 - 100, getHeight() / 2 - 150, 200, 50);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        // Input field for username
        usernameField = new JTextField("Enter Your Username");
        usernameField.setBounds(getWidth() / 2 - 150, getHeight() / 2 + 30, 300, 40);
        usernameField.setOpaque(false); // Hindari menggambar latar belakang
        usernameField.setForeground(Color.GRAY); // Warna default untuk placeholder
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setHorizontalAlignment(JTextField.CENTER);

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter Your Username")) {
                    usernameField.setText(""); // Kosongkan teks placeholder
                    usernameField.setForeground(Color.WHITE); // Ubah warna teks menjadi putih
                    setUsername(usernameField.getText());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Enter Your Username"); // Kembalikan teks placeholder
                    usernameField.setForeground(Color.GRAY); // Kembalikan warna placeholder
                }
            }
        });

        Border buttonBorder = BorderFactory.createEmptyBorder();

        ImageIcon saveButtonIcon = new ImageIcon("src/main/resources/save-button.png");
        saveButton = new JButton();
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.setIcon(saveButtonIcon);
        saveButton.setSelectedIcon(saveButtonIcon);
        saveButton.setBorder(buttonBorder);
        saveButton.setBounds(getWidth() / 2 - 65, getHeight() / 2 + 80, 129, 46);

        saveButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                saveButton.setIcon(new ImageIcon("src/main/resources/save-button-hover.png"));
            }

            public void mouseExited(MouseEvent e) {
                saveButton.setIcon(saveButtonIcon);
            }

            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty() && !username.equals("Enter Your Username")) {
                    System.out.println("Leaderboard button clicked by " + username);
                    mainFrame.getLeaderboardPanel().addScoreToLeaderboard(username, getScore());
                    mainFrame.getLeaderboardPanel().refreshLeaderboardData();
                    mainFrame.getCardLayout().show(mainFrame.getCardPanel(), "Leaderboard");
                } else {
                    System.out.println("Leaderboard button clicked without username.");
                    JOptionPane.showMessageDialog(null,
                            "Please enter a valid username before saving your score.",
                            "Invalid Username",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        ImageIcon restartbuttonImageIcon = new ImageIcon("src/main/resources/restart.png");
        // restartButton = new JButton();
        restartButton.setOpaque(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setIcon(restartbuttonImageIcon);
        restartButton.setSelectedIcon(restartbuttonImageIcon);
        restartButton.setBorder(buttonBorder);
        restartButton.setBounds(getWidth() / 2 - 130, getHeight() / 2 + 50, 129, 46);
        restartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                restartButton.setIcon(new ImageIcon("src/main/resources/restart-hover.png"));
            }

            public void mouseExited(MouseEvent e) {
                restartButton.setIcon(restartbuttonImageIcon);
            }

            public void mouseClicked(MouseEvent e) {
                restartButton.setIcon(restartbuttonImageIcon);
                mainFrame.startGame();
            }
        });

        ImageIcon exitbuttonImageIcon = new ImageIcon("src/main/resources/quit.png");
        // exitButton = new JButton();
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setIcon(exitbuttonImageIcon);
        exitButton.setSelectedIcon(exitbuttonImageIcon);
        exitButton.setBorder(buttonBorder);
        exitButton.setBounds(getWidth() / 2, getHeight() / 2 + 50, 129, 46);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(new ImageIcon("src/main/resources/quit-hover.png"));
            }

            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitbuttonImageIcon);
            }

            public void mouseClicked(MouseEvent e) {
                exitButton.setIcon(exitbuttonImageIcon);
                System.exit(0);
            }
        });

        // Add components to layered pane
        layeredPane.add(scoreLabel, JLayeredPane.POPUP_LAYER);

        if (isWorth) {
            layeredPane.add(usernameField, JLayeredPane.POPUP_LAYER);
            layeredPane.add(enterYourUsername, JLayeredPane.POPUP_LAYER);
            layeredPane.add(saveButton, JLayeredPane.POPUP_LAYER);
        } else {
            layeredPane.add(exitButton, JLayeredPane.POPUP_LAYER);
            layeredPane.add(restartButton, JLayeredPane.POPUP_LAYER);
        }
        // layeredPane.add(startGameButton, JLayeredPane.POPUP_LAYER);
        layeredPane.setLayout(null);
        add(layeredPane);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g.drawImage(background, 0, 0, panelWidth, panelHeight, null);
        }
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JButton getRestartButton() {
        return restartButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public boolean isWorth() {
        return isWorth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public void setStartGameButton(JButton startGameButton) {
        this.startGameButton = startGameButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setRestartButton(JButton restartButton) {
        this.restartButton = restartButton;
    }

    public void setExitButton(JButton exitButton) {
        this.exitButton = exitButton;
    }

    public void setWorth(boolean isWorth) {
        this.isWorth = isWorth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Main getMainFrame() {
        return mainFrame;
    }

}
