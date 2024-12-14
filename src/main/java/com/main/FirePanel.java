package com.main;

import java.awt.*;

import javax.swing.*;

public class FirePanel extends JPanel implements Runnable {
    private Image fireImage;
    private Image explodeImage;
    private int xPosition, yPosition;
    private int width = 128, height = 128;
    private int moveSpeed = 20;
    private JPanel enemyPanel;
    private boolean isExploding = false;
    private boolean isLeftToRight;
    private Character character;
    private Character enemyCharacter;
    private int demage;

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, width, height);
        int shrinkX = 61; // Kurangi sisi horizontal
        int shrinkY = 60;  // Kurangi sisi vertikal
    
        return new Rectangle(
            bounds.x + shrinkX,
            bounds.y + shrinkY,
            bounds.width - (2 * shrinkX),
            bounds.height - (2 * shrinkY)
        );
    }

    public boolean checkCollision() {
        Rectangle fireBounds = getBounds();
        Rectangle enemyBounds = enemyPanel.getBounds();

        return fireBounds.intersects(enemyBounds);
    }

    public FirePanel(int startX, int startY, boolean isLeftToRight, JPanel enemyPanel, Character enemyCharacter, Character character, int demage) {
        this.isLeftToRight = isLeftToRight;
        this.character = character;
        this.demage = demage;
        this.enemyCharacter = enemyCharacter;

        ImageIcon fireIcon = new ImageIcon("src/main/resources/fire.png");
        fireImage = fireIcon.getImage();

        ImageIcon explodeIcon = new ImageIcon("src/main/resources/explode.png");
        explodeImage = explodeIcon.getImage();

        xPosition = startX;
        yPosition = startY;
        this.enemyPanel = enemyPanel;

        // Mulai thread untuk animasi
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isExploding && explodeImage != null) {
            // Draw explosion image
            g.drawImage(explodeImage, (isLeftToRight ? xPosition + 0 : xPosition - 0), yPosition, width, height, null);
        } else if (fireImage != null) {
            // Draw fire image
            g.drawImage(fireImage, xPosition, yPosition, width, height, null);
        }
    }

    @Override
    public void run() {
        while (true) {
            // gerakkan api ototmatis
            if(isLeftToRight){
                xPosition += moveSpeed;
            } else {
                xPosition -= moveSpeed;
            }

            // System.out.println("fire : " + getBounds() + " monster : " + enemyPanel.getBounds() + " || " + checkCollision());
            
            if( (isLeftToRight ? xPosition > 1366 : xPosition < 0) || checkCollision()) {
                if (checkCollision()) {
                    isExploding = true; // Trigger explosion state

                    enemyCharacter.takeDamage(demage);
                    character.setScore(character.getScore() + 2);

                    // System.out.println(character.getScore());

                    repaint();

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    SwingUtilities.invokeLater(() -> {
                        Container parent = getParent(); // Ambil parent dari FirePanel
                        if (parent != null) { // Pastikan parent tidak null
                            parent.remove(this); // Hapus FirePanel dari parent
                            parent.revalidate(); // Perbarui tata letak
                            parent.repaint();    // Perbarui tampilan
                        }
                    });
                } else {
                    // Hapus FirePanel dari parent setelah keluar layar
                    SwingUtilities.invokeLater(() -> {
                        Container parent = getParent(); // Ambil parent dari FirePanel
                        if (parent != null) { // Pastikan parent tidak null
                            parent.remove(this); // Hapus FirePanel dari parent
                            parent.revalidate(); // Perbarui tata letak
                            parent.repaint();    // Perbarui tampilan
                        }
                    });
                }
                break;
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
