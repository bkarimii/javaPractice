package uk.co.cpsd.javaproject1;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SimulatorFrame extends JFrame {
    private World world;
    private WorldPanel worldPanel;
    private final Timer timer;

    public SimulatorFrame() {
        world = new World(7);

        worldPanel = new WorldPanel(world);
        add(worldPanel);

        setTitle("Ecosystem Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Set up simulation timer (1 tick per 500ms)
        timer = new Timer(500, (ActionEvent e) -> {
            world.tick();
            worldPanel.repaint();
        });

        // Start the simulation
        timer.start();
    }
}
