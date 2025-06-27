package uk.co.cpsd.javaproject1;

import javax.swing.*;
import java.awt.event.*;

public class SimulatorFrame extends JFrame {
    private World world;
    private WorldPanel worldPanel;
    private Timer timer;

    public SimulatorFrame() {
        world = new World();

        worldPanel = new WorldPanel(world);
        add(worldPanel);

        setTitle("Ecosystem Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Set up simulation timer (1 tick per 500ms)
        timer = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.tick();
                worldPanel.repaint();
            }
        });

        // Start the simulation
        timer.start();
    }
}
