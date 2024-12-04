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
       
    }
}