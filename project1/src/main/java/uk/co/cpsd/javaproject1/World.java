package uk.co.cpsd.javaproject1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class World {
    public final int size = 10;
    private List<Animal> animals;
    private int totalTicks;
    private final int MAX_GRASS_AGE=25;
    List<GrassExpiry> grassDeathTimeList=new ArrayList<>();

    public World(int numOfGoats){
        animals=new ArrayList<>();
        for(int i=0;i<numOfGoats;i++){
            animals.add(new Goat((int)(Math.random() * size),(int)(Math.random() * size)));
        }
    }

    // new implementation
    public void growGrassTwo() {
        int x = (int)(Math.random() * size);
        int y = (int)(Math.random() * size);
        int deathTick=totalTicks+MAX_GRASS_AGE;
        // if(hasGrass(x, y)) return;
        GrassExpiry newGrass=new GrassExpiry(x, y, deathTick);
        grassDeathTimeList.add(newGrass);
        System.out.println("Grass grew at (" + x + "," + y + ") at total tick: " + totalTicks);
    }

    public boolean hasGrass(int x, int y){
        return grassDeathTimeList.stream().anyMatch(g-> g.x==x && g.y==y);
    }

    public void removeDeadGrass(){
        Iterator<GrassExpiry> iterateGrass= grassDeathTimeList.iterator();

        while(iterateGrass.hasNext()){
            GrassExpiry grass=iterateGrass.next();
            if(grass.deathTick==totalTicks){
                iterateGrass.remove();
            }
        }

    }
    public void removeGrass(int x, int y){
        grassDeathTimeList.removeIf(g->g.x==x&&g.y==y);
    }

    public Stream<Animal> animals(){
        return animals.stream();
    }


    public void tick() {

        growGrassTwo();
    }

    public void moveAnimals(){
        animals.forEach(animal-> animal.act(this));

    }
}
