package uk.co.cpsd.javaproject1;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        boolean justSimulate=true;
        if(justSimulate){
            SimulatorRunner.noGUISimulation(10000, 20);
        }else{
            SwingUtilities.invokeLater(() -> {
                @SuppressWarnings("unused")
                SimulatorFrame frame = new SimulatorFrame();
            });
        }
        
    }
}
