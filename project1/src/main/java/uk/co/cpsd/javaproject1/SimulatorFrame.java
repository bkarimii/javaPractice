package uk.co.cpsd.javaproject1;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SimulatorFrame extends JFrame {
    private World world;
    private WorldPanel worldPanel;
    private Timer timer;
    private int tickLimitSimulation=10;

    public SimulatorFrame() {
        world = new World(20);

        worldPanel = new WorldPanel(world);
        add(worldPanel);

        setTitle("Ecosystem Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // Set up simulation timer (1 tick per 500ms)
        timer = new Timer(1000, (ActionEvent e) -> {
            world.tick();               // Tick: grow grass, energy, reproduction
            worldPanel.repaint();       // Show updates on the screen
        
            if (world.getSecondsElapsed() >= tickLimitSimulation) {
                world.writeToCSV(world.getGoatHistory(), world.getGrassHistory());
                timer.stop();
        
                SwingUtilities.invokeLater(() -> {
                    GoatChart chart = new GoatChart(world.getGoatHistory(),world.getGrassHistory());
                    chart.setVisible(true);
                });
            }
        });

        // Start the simulation
        timer.start();
        
    }
}
