package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// belum ada width
public class WizardPanel extends JPanel implements Runnable {
    private SpriteAnimation wizardImage;
    MonsterPanel monsterPanel;
    private int width, height; // w h img
    private int xPosition = 300, yPosition = -10; // x y position img
    private Wizard wizard;
    private Main main;

    // atribut tambahan
    private int xInput = 0; // speed maju mundur yang di-input
    private int moveSpeed = 3; // speed mundur
    private int jumpVelocity; // Kecepatan saat melompat
    private int gravity = 2; // Gaya gravitasi
    private boolean isJumping = false; // Status lompat
    private boolean running = true; // Flag untuk menentukan apakah thread harus berjalan

    public Wizard getWizard() {
        return wizard;
    }

    public void setMonsterPanel(MonsterPanel monsterPanel) {
        this.monsterPanel = monsterPanel;
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, width, height);
        int shrinkX = 150; // Kurangi sisi horizontal
        int shrinkY = 90; // Kurangi sisi vertikal

        return new Rectangle(
                bounds.x + shrinkX,
                bounds.y + shrinkY,
                bounds.width - (2 * shrinkX),
                bounds.height - (2 * shrinkY));
    }

    public WizardPanel(int width, int height, int maxHp, Main main) {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        wizardImage = new SpriteAnimation("src/main/resources/idle.png", 1386 / 6, 190, 6, 400, 400);
        this.main = main;
        this.width = width;
        this.height = height;
        this.wizard = new Wizard("Wizard", new HpBar(maxHp, Color.LIGHT_GRAY, Color.GREEN));
    }

    public void showInputSpell() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this); // Mengambil JFrame induk
        String input = JOptionPane.showInputDialog(parentWindow, "Cast your spell:");

        if (input != null && !input.trim().isEmpty()) {
            // Ambil teks dari spellLabel
            JLayeredPane layeredPane = (JLayeredPane) getParent();
            JPanel spellPanel = (JPanel) layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER)[2];
            JLabel spellLabel = (JLabel) spellPanel.getComponent(0);
            String currentSpellText = spellLabel.getText();

            // Periksa apakah input cocok dengan currentSpell
            if (input.equals(currentSpellText)) {
                // Tambahkan fireball ke layar
                FirePanel firePanel = new FirePanel(xPosition + 64, yPosition, true, monsterPanel,
                        monsterPanel.getMonster(), this.getWizard(), 2);
                firePanel.setBounds(0, 300, 1366, 250); // Ukuran sesuai layar
                firePanel.setOpaque(false);
                layeredPane.add(firePanel, JLayeredPane.MODAL_LAYER);
                layeredPane.repaint();

                SoundEffect.playSound("src/main/resources/attack.wav");

                // Perbarui spell setelah input berhasil
                main.updateSpell();
            }
        }
    }

    // public void showInputSpell() {
    // Window parentWindow = SwingUtilities.getWindowAncestor(this); // Mengambil
    // JFrame induk
    // String input = JOptionPane.showInputDialog(parentWindow, "cast your spell");
    // if (input != null && !input.trim().isEmpty()) {
    // // Tambahkan fireball ke layar
    // FirePanel firePanel = new FirePanel(xPosition + 64, yPosition, true,
    // monsterPanel,
    // monsterPanel.getMonster(), this.getWizard(), 2);
    // JLayeredPane layeredPane = (JLayeredPane) getParent();
    // firePanel.setBounds(0, 300, 1366, 250); // Ukuran sesuai layar
    // firePanel.setOpaque(false);
    // layeredPane.add(firePanel, JLayeredPane.MODAL_LAYER);
    // layeredPane.repaint();

    // SoundEffect.playSound("src/main/resources/attack.wav");
    // }
    // }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (wizardImage != null) {
            // Ambil frame saat ini dari wizardImage (animasi)
            Image currentFrame = wizardImage.getCurrentFrame();
            // Gambar frame saat ini dengan posisi (xPosition, yPosition) dan ukuran
            // (128x128)
            g.drawImage(currentFrame, xPosition, yPosition, width, height, this);
        }
    }

    @Override
    public void run() {
        while (running) { // Jalankan hanya jika 'running' bernilai true
            // Update posisi x manual melalui input a d
            if (!isJumping) {
                xPosition += xInput;
            }

            // Update posisi X otomatis
            xPosition -= moveSpeed;

            // Reset posisi jika wizard tertinggal
            if (xPosition <= -64) {
                xPosition = 300;
            }

            // Logika lompat
            if (isJumping) {
                yPosition += jumpVelocity; // Tambahkan kecepatan lompat ke posisi y
                jumpVelocity += gravity; // Gravitasi memperlambat lompat
                xPosition += 10; // Tambahkan pergerakan ke kanan

                // Periksa apakah sudah mencapai tanah
                if (yPosition >= -10) { // Posisi default
                    yPosition = -10; // Kembali ke tanah
                    isJumping = false; // Hentikan lompat
                    jumpVelocity = 0; // Reset kecepatan lompat
                }
            }

            if (wizard.getCurrentHp() <= 0) {
                isDead(getWizard().getScore());
                stopThread(); // Menghentikan thread
                break; // Keluar dari loop untuk menghentikan eksekusi
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

    public void setupKeyboardControl() {
        addKeyListener(new KeyAdapter() {
            private StringBuilder typedSpell = new StringBuilder();

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                typedSpell.append(c);

                // Periksa apakah input pemain cocok dengan spell
                if (typedSpell.toString().equals(main.getSpellLabel().getText())) {
                    // Spell selesai diketik
                    typedSpell.setLength(0); // Reset input pemain
                    main.updateSpell(); // Perbarui spell
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> {
                        xInput = -6;
                        wizardImage = new SpriteAnimation("src/main/resources/run.png", 1386 / 6, 190, 6, 400, 400);
                    } // Gerak ke kiri
                    case KeyEvent.VK_D -> {
                        System.out.println("D");
                        xInput = 6;
                        wizardImage = new SpriteAnimation("src/main/resources/run.png", 1386 / 6, 190, 6, 400, 400);
                    } // Gerak ke kanan
                    case KeyEvent.VK_W -> {
                        if (!isJumping) { // Hanya mulai lompat jika tidak sedang melompat
                            isJumping = true; // Tandai sedang melompat
                            jumpVelocity = -30; // Berikan kecepatan awal lompat (ke atas)
                            wizardImage = new SpriteAnimation("src/main/resources/jump.png", 462 / 2, 190, 2, 400, 400);
                        }
                    }
                    case KeyEvent.VK_SLASH -> {
                        xInput = 0; // agar saat d ditahan dan buka form karakter tidak maju terus
                        showInputSpell();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W -> {
                        xInput = 0;
                        wizardImage = new SpriteAnimation("src/main/resources/idle.png", 1386 / 6, 190, 6, 400, 400);
                    } // Hentikan gerakan
                }
            }
        });

        new Thread(this).start();
    }

    public void isDead(int score) {
        SwingUtilities.invokeLater(() -> {
            JLayeredPane parent = (JLayeredPane) getParent();
            Main mainFrame = (Main) SwingUtilities.getWindowAncestor(parent);

            mainFrame.showGameOverPanel(score);
        });
    }

    public void stopThread() {
        running = false; // Ubah flag menjadi false untuk menghentikan loop
    }

}
