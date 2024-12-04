package com.main;

import java.awt.*;

public class HpBar {
    private int maxHP;
    private int currentHP;
    private Color backgroundColor;
    private Color foregroundColor;

    public HpBar(int maxHP, Color backgroundColor, Color foregroundColor) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }

    public void reduceHP(int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }

    public void increaseHP(int heal) {
        currentHP = Math.min(maxHP, currentHP + heal);
    }

    public boolean isDead() {
        return currentHP <= 0;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        // Background (total HP space)
        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);

        // Current HP
        g.setColor(foregroundColor);
        int currentWidth = (int) ((double) currentHP / maxHP * width);
        g.fillRect(x, y, currentWidth, height);

        // Border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // HP Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(currentHP + "/" + maxHP, x + 5, y + height - 5);
    }
}