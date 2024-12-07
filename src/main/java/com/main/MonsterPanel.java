package com.main;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class MonsterPanel extends JPanel implements Runnable {
    private SpriteAnimation monsterImage;
    private WizardPanel wizardPanel;
    private int width, height; // w h img
    private int xPosition = 1100, yPosition = -10; // x y position img
    private Monster monster;

    // atribut tambahan
    // ...

    public Monster getMonster() {
        return monster;
    }

    public void setWizardPanel(WizardPanel wizardPanel) {
        this.wizardPanel = wizardPanel;
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, width, height);
        int shrinkX = 150; // Kurangi sisi horizontal
        int shrinkY = 0; // Kurangi sisi vertikal

        return new Rectangle(
                bounds.x + shrinkX,
                bounds.y + shrinkY,
                bounds.width - (2 * shrinkX),
                bounds.height - (2 * shrinkY));
    }

    public MonsterPanel(int width, int height, int maxHp) {
        monsterImage = new SpriteAnimation("src/main/resources/idle_reverse.png", 1386 / 6, 190, 6, 400, 400);
        this.width = width;
        this.height = height;
        this.monster = new Monster("Monster", new HpBar(maxHp, Color.LIGHT_GRAY, Color.RED));
    }

    public void attack() {
        int random = new Random().nextInt(2) + 1;

        if (random == 1) {
            // Tambahkan fireball ke layar
            FirePanel firePanel = new FirePanel(xPosition, yPosition + 30, false, wizardPanel, wizardPanel.getWizard(),
                    2);
            JLayeredPane layeredPane = (JLayeredPane) getParent();
            firePanel.setBounds(0, 300, 1366, 250); // Ukuran sesuai layar
            firePanel.setOpaque(false);
            layeredPane.add(firePanel, JLayeredPane.MODAL_LAYER);
            layeredPane.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (monsterImage != null) {
            // Ambil frame saat ini dari wizardImage (animasi)
            Image currentFrame = monsterImage.getCurrentFrame();
            // Gambar frame saat ini dengan posisi (xPosition, yPosition)
            g.drawImage(currentFrame, xPosition, yPosition, width, height, this);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attack();

            repaint();

            try {
                Thread.sleep(16); // Sekitar 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void play() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
