package uk.co.cpsd.javaproject1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class WorldPanel extends JPanel {
    private final World world;

    public WorldPanel(World world) {
        this.world = world;
        setPreferredSize(new Dimension(500, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = getWidth() / world.size;

        for (int x = 0; x < world.size; x++) {
            for (int y = 0; y < world.size; y++) {
                // Draw grass
                if (world.hasGrass(x, y)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }

                g.setColor(Color.GRAY);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }
}
