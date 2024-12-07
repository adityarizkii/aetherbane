package com.main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class HomeScreenPanel extends JPanel {
    private Image background;
    private JButton startGameButton;
    private JButton leaderboardButton;

    public HomeScreenPanel() {
        setSize(1366, 700);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 700));

        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/home-background.jpg");
        background = backgroundImageIcon.getImage();
        JLabel backgroundLabel = new JLabel(new ImageIcon(background));
        backgroundLabel.setPreferredSize(new Dimension(1366, 700));
        backgroundLabel.setBounds(0, 0, background.getWidth(null), background.getHeight(null));

        ImageIcon buttonImageIcon = new ImageIcon("src/main/resources/play_button.png");
        startGameButton = new JButton();
        Border buttonBorder = BorderFactory.createEmptyBorder();
        startGameButton.setOpaque(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBorderPainted(false);
        startGameButton.setIcon(buttonImageIcon);
        startGameButton.setSelectedIcon(buttonImageIcon);
        startGameButton.setBorder(buttonBorder);
        startGameButton.setBounds(getWidth() / 2 - 100, getHeight() / 2 + 50, 129, 46);

        ImageIcon leaderboardIcon = new ImageIcon("src/main/resources/leaderboard_button.png");
        leaderboardButton = new JButton();
        leaderboardButton.setOpaque(false);
        leaderboardButton.setContentAreaFilled(false);
        leaderboardButton.setBorderPainted(false);
        leaderboardButton.setIcon(leaderboardIcon);
        leaderboardButton.setSelectedIcon(leaderboardIcon);
        leaderboardButton.setBorder(buttonBorder);
        leaderboardButton.setBounds(0 + 90, 0 + 50, 97, 31);

        // Mouse events for button
        startGameButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startGameButton.setIcon(new ImageIcon("src/main/resources/play_button_hover.png"));
            }

            public void mouseExited(MouseEvent e) {
                startGameButton.setIcon(buttonImageIcon);
            }

            public void mouseClicked(MouseEvent e) {
                startGameButton.setIcon(buttonImageIcon);
                System.out.println("Start game button clicked!");
            }
        });

        leaderboardButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                leaderboardButton.setIcon(new ImageIcon("src/main/resources/leaderboard_hover.png"));
            }

            public void mouseExited(MouseEvent e) {
                leaderboardButton.setIcon(leaderboardIcon);
            }

            public void mouseClicked(MouseEvent e) {
                leaderboardButton.setIcon(leaderboardIcon);
                System.out.println("Start game button clicked!");
            }
        });

        // Add the button and background to the layered pane
        layeredPane.add(startGameButton, JLayeredPane.POPUP_LAYER);
        layeredPane.add(leaderboardButton, JLayeredPane.POPUP_LAYER);
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

    public JButton getLeaderboardButton() {
        return leaderboardButton;
    }

    // Method to access the start button
    public JButton getStartGameButton() {
        return startGameButton;
    }
}
