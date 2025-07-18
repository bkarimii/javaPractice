package uk.co.cpsd.javaproject1;

public class SimulatorRunner {
    public static void noGUISimulation(int ticks,int numOfGoats){
        World world=new World(numOfGoats);

        for(int i=1;i<=ticks;i++){
            // world.moveAnimals();
            world.tick();
        }

        world.writeToCSV(world.getGoatHistory(), world.getGrassHistory());
        GoatChart chart=new GoatChart(world.getGoatHistory(), world.getGrassHistory());
        chart.setVisible(true);
    }


}
