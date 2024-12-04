package com.main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Aetherbane");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1366, 700));

        // Tambahkan background ke layeredPanel
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0,0,1366, 700);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        add(layeredPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}