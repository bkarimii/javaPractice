package uk.co.cpsd.javaproject1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class World {
    public final int size = 10;
    private List<Animal> animals;
    private int totalTicks;
    private final int MAX_GRASS_AGE=25;
    List<GrassExpiry> grassDeathTimeList=new ArrayList<>();
    HashMap<Integer, List<GrassExpiry>> grassTickTimeToDie= new HashMap<>();

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
        grassTickTimeToDie.computeIfAbsent(deathTick, k-> new ArrayList<>()).add(newGrass);
        System.out.println("Grass grew at (" + x + "," + y + ") at total tick: " + totalTicks);
    }

    public boolean hasGrass(int x, int y){
        return grassTickTimeToDie.values().stream().flatMap(List::stream).anyMatch(g-> g.x==x && g.y==y);
    }

    public void removeDeadGrass(){
        List<GrassExpiry> toRemove=  grassTickTimeToDie.get(totalTicks);

        if(toRemove!=null){
            for(GrassExpiry grass:toRemove){
                removeGrass(grass.x,grass.y);

            }
            grassTickTimeToDie.remove(totalTicks);
        }
    }
    public void removeGrass(int x, int y){
        for (Iterator<HashMap.Entry<Integer, List<GrassExpiry>>> mapIterator = grassTickTimeToDie.entrySet().iterator(); mapIterator.hasNext(); ) {
            HashMap.Entry<Integer, List<GrassExpiry>> entry = mapIterator.next();
            List<GrassExpiry> grassList = entry.getValue();
        
            for (Iterator<GrassExpiry> listIterator = grassList.iterator(); listIterator.hasNext(); ) {
                GrassExpiry g = listIterator.next();
                if (g.x == x && g.y == y) {
                    listIterator.remove();
        
                    if (grassList.isEmpty()) {
                        mapIterator.remove();
                    }
        
                    return; 
                }
            }
        }
    }

    public void removeGrassTest(int x , int y){

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
