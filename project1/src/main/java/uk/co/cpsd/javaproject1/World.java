package uk.co.cpsd.javaproject1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class World {
     public final int size = 10;
    private boolean[][] grass = new boolean[size][size];
    private List<Animal> animals;
    private int nextAnimalId=0;

    public World(int numOfGoats){
        animals=new ArrayList<Animal>();
        for(int i=0;i<numOfGoats;i++){
            animals.add(new Goat((int)(Math.random() * size),(int)(Math.random() * size),nextAnimalId++));
        }
    }


    public void growGrass() {
        int x = (int)(Math.random() * size);
        int y = (int)(Math.random() * size);
        grass[x][y] = true;
    }

    public boolean hasGrass(int x, int y) {
        return grass[x][y];
    }

    public void removeGrass(int x, int y) {
        grass[x][y] = false;
    }

    public Stream<Animal> animals(){
        return animals.stream();
    }
    public void tick() {
        growGrass();
    }

    public void moveAnimals(){
        animals.forEach(animal-> {
            animal.move(size);

            if(animal instanceof Goat){
                Goat currentGoat=(Goat) animal;
                if(hasGrass(currentGoat.getX(), currentGoat.getY())){
                    currentGoat.eatGrass();
                    removeGrass(currentGoat.getX(), currentGoat.getY());
                }
            }
        });

    }
}
