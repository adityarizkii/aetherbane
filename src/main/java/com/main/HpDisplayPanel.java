package com.main;

import javax.swing.*;
import java.awt.*;

public class HpDisplayPanel extends JPanel {
    private WizardPanel wizardPanel;
    private MonsterPanel monsterPanel;

    public HpDisplayPanel(WizardPanel wizardPanel, MonsterPanel monsterPanel) {
        this.wizardPanel = wizardPanel;
        this.monsterPanel = monsterPanel;

        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Gambar HP Bar Wizard (kiri atas)
        wizardPanel.getWizard().getHp().draw(g, 20, 20, 200, 20);

        // Gambar HP Bar Monster (kanan atas)
        monsterPanel.getMonster().getHp().draw(g, 1146, 20, 200, 20);
    }
}