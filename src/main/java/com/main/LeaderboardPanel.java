package com.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;

import java.awt.*;

public class LeaderboardPanel extends JPanel {
    private JTable leaderboardTable;
    private Image background;

    public LeaderboardPanel() {
        setSize(1366, 700);
        setLayout(null);

        // Load background image
        ImageIcon backgroundImageIcon = new ImageIcon("src/main/resources/home-background.jpg");
        background = backgroundImageIcon.getImage();

        // Buat tabel dengan data dummy
        String[] columnNames = { "Username", "Score" };
        Object[][] data = {
                { "Wizard Master", 1500 },
                { "SpellCaster99", 1350 },
                { "MagicWarrior", 1200 },
                { "Enchanter", 1100 },
                { "RuneMage", 950 },
                { "ShadowWizard", 800 },
                { "ElementalLord", 700 },
                { "ArcaneHero", 600 },
                { "MysticKnight", 500 },
                { "WizardApprentice", 400 }
        };

        // Urutkan data berdasarkan skor dari tertinggi ke terendah
        java.util.Arrays.sort(data, (a, b) -> ((Integer) b[1]).compareTo((Integer) a[1]));

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        leaderboardTable = new JTable(model);

        // Kustomisasi tampilan tabel dengan gaya yang lebih menarik
        leaderboardTable.setOpaque(false);
        leaderboardTable.setBackground(new Color(0, 0, 0, 100));
        leaderboardTable.setForeground(Color.WHITE);
        leaderboardTable.setFont(new Font("Magical Font", Font.BOLD, 18));
        leaderboardTable.setRowHeight(40);

        // Kustomisasi header tabel
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(0, 0, 0, 150));
        header.setForeground(Color.YELLOW);
        header.setFont(new Font("Magical Font", Font.BOLD, 22));

        // Buat JScrollPane untuk tabel
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0, 150), 2, true));
        scrollPane.setBounds(333, 130, 700, 400);

        // Tambahkan judul dengan efek bayangan
        JLabel titleLabel = new JLabel("LEADERBOARD", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100)); // Shadow color
                g2d.drawString(getText(), 3, 53);

                g2d.dispose();
                super.paintComponent(g);
            }
        };
        titleLabel.setFont(new Font("Magical Font", Font.BOLD, 64));
        titleLabel.setForeground(new Color(255, 215, 0)); // Warna emas
        titleLabel.setBounds(333, 50, 700, 80);

        // Tombol kembali dengan gaya magic
        ImageIcon buttonIcon = new ImageIcon("src/main/resources/back_button.png");
        ImageIcon buttonHoverIcon = new ImageIcon("src/main/resources/back_hover.png");

        JButton backButton = new JButton(buttonIcon);
        backButton.setBounds(600, 550, 200, 50);
        backButton.setFont(new Font("Magical Font", Font.BOLD, 18));
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);

        // Tambahkan mouse listener untuk efek hover
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(buttonHoverIcon);
            }

            public void mouseExited(MouseEvent e) {
                backButton.setIcon(buttonIcon);
            }
        });

        // Tambahkan komponen ke panel
        add(titleLabel);
        add(scrollPane);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g.drawImage(background, 0, 0, panelWidth, panelHeight, null);
        }

        // Tambahkan overlay transparan dengan gradien
        Graphics2D g2d = (Graphics2D) g.create();
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0, 100), 0, getHeight(),
                new Color(0, 0, 0, 200));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    // Method untuk mendapatkan tombol kembali
    public JButton getBackButton() {
        return (JButton) getComponents()[2];
    }
}