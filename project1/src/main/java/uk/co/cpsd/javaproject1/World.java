package uk.co.cpsd.javaproject1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class World {
    public final int size = 10;
    private List<Animal> animals;
    private int totalTicks;
    private final int MAX_GRASS_AGE=25;
    private int[][] grassDeathTime= new int[size][size]; 
    public World(int numOfGoats){
        animals=new ArrayList<>();
        for(int i=0;i<numOfGoats;i++){
            animals.add(new Goat((int)(Math.random() * size),(int)(Math.random() * size)));
        }
    }

    public boolean hasGrass(int x , int y){
        return grassDeathTime[x][y]>totalTicks;
    }
    public int findNumOfGoats(){
        return (int) animals.stream().filter(animal-> animal instanceof Goat).count();
    }

    public int findNumOfGrass(){
        int numOfGrass=0;
        for(int x=0;x<size;x++){
            for(int y=0;y<size;y++){
                if(hasGrass(x, y)){
                    numOfGrass++;
                }
            }
        }
        return numOfGrass;
    }

    public int getSecondsElapsed(){
        return totalTicks;
    }
    public void growGrass(){
        int x = (int)(Math.random() * size);
        int y = (int)(Math.random() * size);
        if(!hasGrass(x, y)){
            grassDeathTime[x][y]=totalTicks+MAX_GRASS_AGE;
        }
    }

    public void removeGrass(int x, int y){
        grassDeathTime[x][y]=0;
    }

    public Stream<Animal> animals(){
        return animals.stream();
    }


    public void tick() {
        totalTicks++;

        growGrass();
        growGrass();
        growGrass();
        growGrass();
        
        List<Animal> deadAnimals=new ArrayList<>();
        for(Animal animal:animals){
            boolean isDead= animal.decreaseEnergy(totalTicks);
            if(isDead){
                deadAnimals.add(animal);
            }
        }
        makeBabyGoat();
        animals.removeAll(deadAnimals);
    }

    

    public void moveAnimals(){
        animals.forEach(animal-> animal.act(this));

    }

    public void makeBabyGoat(){
        Set<Integer> isAlreadyProduced=new HashSet<>();
        List<Animal> newBabies=new ArrayList<>();

        for(Animal a1:animals){
            if(!(a1 instanceof Goat) || isAlreadyProduced.contains(a1.getId())) continue;
            Goat goat1=(Goat) a1;
            for(Animal a2:animals){
                if((a1==a2) || !(a2 instanceof Goat) || isAlreadyProduced.contains(a2.getId())) continue;
                Goat goat2= (Goat) a2;
                boolean isSameLocation= (goat1.getX()==goat2.getX() && goat1.getY()==goat2.getY());
                boolean isDifferentGender= goat1.getGender()!=goat2.getGender();
                if(isSameLocation && isDifferentGender && goat1.getEnergy()>=15 && goat2.getEnergy()>=15){
                    Goat baby= new Goat(goat1.getX(), goat1.getY());
                    baby.energyLevel=5;
                    newBabies.add(baby);
                    System.out.println("================New baby was born =========");

                    isAlreadyProduced.add(goat1.getId());
                    isAlreadyProduced.add(goat2.getId());
                    goat1.decreaseEnergyBy(2);
                    goat2.decreaseEnergyBy(2);
                }
            }

        }

        animals.addAll(newBabies);

        
    }
}
