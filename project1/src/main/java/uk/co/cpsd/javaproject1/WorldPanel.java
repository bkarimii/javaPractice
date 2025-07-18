package uk.co.cpsd.javaproject1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.JPanel;

public class WorldPanel extends JPanel {
    private final World world;
    private final Font font;
    public WorldPanel(World world) {
        this.world = world;
        setPreferredSize(new Dimension(600, 650));
        this.font=new Font("Arial", Font.BOLD, 18);
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
        
        
        world.animals().forEach(animal->{
            
            g.setColor(animal.getColor());
            g.fillRect(animal.getX() * cellSize+10, animal.getY() * cellSize+10, cellSize-15,cellSize-15);

            g.setColor(Color.BLACK);
            
            g.setFont(font);

            // Calculate text position to try and center it within the animal's drawn area
            String idText = String.valueOf(animal.getId());
            int textWidth = g.getFontMetrics().stringWidth(idText);
            int textHeight = g.getFontMetrics().getHeight();
            System.out.println("This is animal id: "+idText);
            int textX = animal.getX() * cellSize + (cellSize / 2) - (textWidth / 2);
            int textY = animal.getY() * cellSize + (cellSize / 2) + (textHeight / 4);

            g.drawString(idText, textX, textY);
        });

        int scoreY= world.size*cellSize+25;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial",Font.PLAIN,16));
        String statement= "Seconds elapsed: "+ world.getSecondsElapsed()+ " Num of Goats is:  "+world.findNumOfGoats()+" Num of Grass: "+world.findNumOfGrass();
        g.drawString(statement, cellSize, scoreY);

    }
}
