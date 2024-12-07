package com.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class SpriteAnimation extends JPanel implements Runnable {
    private final ImageIcon spriteSheet; // Sprite sheet menggunakan ImageIcon
    private final int frameWidth;       // Lebar setiap frame
    private final int frameHeight;      // Tinggi setiap frame
    private final int frameCount;       // Jumlah frame animasi
    private final int scaledWidth;      // Lebar gambar setelah di-scale
    private final int scaledHeight;     // Tinggi gambar setelah di-scale
    private int currentFrame = 0;       // Frame saat ini
    private int animationSpeed = 100;   // Kecepatan animasi (ms)

    public Image getCurrentFrame() {
        // Hitung koordinat frame saat ini di sprite sheet
        int x = (currentFrame % frameCount) * frameWidth;
        int y = 0;

        // Ambil gambar penuh dan konversi menjadi BufferedImage
        BufferedImage bufferedImage = toBufferedImage(spriteSheet.getImage());

        // Potong frame dari sprite sheet
        return bufferedImage.getSubimage(x, y, frameWidth, frameHeight)
            .getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
    }

    private BufferedImage toBufferedImage(Image img) {
        // Jika gambar sudah BufferedImage, langsung kembalikan
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Buat BufferedImage kosong untuk menampung gambar
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, null); // Gambar ke BufferedImage
        g.dispose(); // Bersihkan resources
        return bufferedImage;
    }

    public SpriteAnimation(String spriteSheetPath, int frameWidth, int frameHeight, int frameCount, int scaledWidth, int scaledHeight) {
        this.spriteSheet = new ImageIcon(spriteSheetPath);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;

        // Jalankan animasi di thread terpisah
        new Thread(this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Gambar hanya bagian dari sprite sheet yang sesuai dengan frame saat ini
        if (spriteSheet != null) {
            // Hitung koordinat frame saat ini di sprite sheet
            int x = (currentFrame % frameCount) * frameWidth;
            int y = 0;

            // Gambar bagian sprite sheet (hanya frame saat ini), disesuaikan ukurannya
            g.drawImage(spriteSheet.getImage(),
                    100, 100, 100 + scaledWidth, 100 + scaledHeight, // Lokasi dan ukuran gambar di panel
                    x, y, x + frameWidth, y + frameHeight,          // Koordinat frame di sprite sheet
                    null);
        }
    }

    @Override
    public void run() {
        while (true) {
            // Update frame saat ini
            currentFrame = (currentFrame + 1) % frameCount;

            // Perbarui tampilan
            repaint();

            // Delay untuk animasi
            try {
                Thread.sleep(animationSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
