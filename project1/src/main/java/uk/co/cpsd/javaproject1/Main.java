package uk.co.cpsd.javaproject1;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Assigning the new object to a variable resolves the warning.
            SimulatorFrame frame = new SimulatorFrame();
        });
    }
}
