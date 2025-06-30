package uk.co.cpsd.javaproject1;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            @SuppressWarnings("unused")
            SimulatorFrame frame = new SimulatorFrame();
        });
    }
}
