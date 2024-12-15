package com.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel implements Runnable {
    private Image backgroundImage;
    private int xPosition; // Posisi X gambar
    private int moveSpeed = 2; // Kecepatan pergerakan gambar

    public BackgroundPanel() {
        // Load gambar menggunakan ImageIcon
        ImageIcon icon = new ImageIcon("src/main/resources/bg-loop.png");
        backgroundImage = icon.getImage();

        // Mulai thread untuk animasi
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            // Gambar background di posisi xPosition
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Gambar dua kali untuk looping (efek infinite scroll)
            g.drawImage(backgroundImage, xPosition, 0, panelWidth, panelHeight, null);
            g.drawImage(backgroundImage, xPosition + panelWidth, 0, panelWidth, panelHeight, null);
        }
    }

    @Override
    public void run() {
        while (true) {
            // Update posisi X
            xPosition -= moveSpeed;

            // Reset posisi jika gambar sepenuhnya keluar dari layar
            if (xPosition <= -getWidth()) {
                xPosition = 0;
            }

            // Repaint panel untuk memperbarui tampilan
            repaint();

            // Delay untuk mengontrol kecepatan animasi
            try {
                Thread.sleep(16); // Sekitar 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}