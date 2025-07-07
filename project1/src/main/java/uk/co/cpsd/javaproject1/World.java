package uk.co.cpsd.javaproject1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class World {
    public final int size = 10;
    private int[][] grassAge = new int[size][size];
    private List<Animal> animals;
    private List<Animal> deadAnimals= new ArrayList<>();
    private int nextAnimalId=0;
    private int totalTicks;

    public World(int numOfGoats){
        animals=new ArrayList<>();
        for(int i=0;i<numOfGoats;i++){
            animals.add(new Goat((int)(Math.random() * size),(int)(Math.random() * size),nextAnimalId++));
        }

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                grassAge[i][j]=0;
            }
        }
    }

    public int findNumOfGoats(){
        return (int) animals.stream().filter(animal-> animal instanceof Goat).count();
    }

    public int findNumOfGrass(){
        int numOfGrass=0;
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(grassAge[i][j]>0){
                    numOfGrass++;
                }
            }
        }
        return numOfGrass;
    }

    public int getGrassAgeAt(int x,int y){
        return grassAge[x][y];
    }
    
    public int getSecondsElapsed(){
        return totalTicks;
    }
    public void growGrass() {
        int x = (int)(Math.random() * size);
        int y = (int)(Math.random() * size);
        
        if(grassAge[x][y]==0){
            grassAge[x][y]=1;
        }
        System.out.println("Grass grew at (" + x + "," + y + ") at total tick: " + totalTicks);
    }

    public boolean hasGrass(int x, int y) {
        return grassAge[x][y]>0;
    }

    public void removeGrass(int x, int y) {
        grassAge[x][y] = 0;
    }

    public Stream<Animal> animals(){
        return animals.stream();
    }

    public void ageIncreaser(int worldSize){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(grassAge[i][j]>0){
                    grassAge[i][j]++;
                }
            }
        }
    }

    public void grassDies(int worldSize,int maxAge){
        for(int i=0;i<size;i++){
            for(int j=0; j<size; j++){
                if(grassAge[i][j]>maxAge){
                    removeGrass(i, j);
                    System.out.println("Grass at [" + i + " , " + j + "] died of old age after " + maxAge + " ticks (at total tick: " + totalTicks + ").");
                }
            }
        }
    }
    public void tick() {
        totalTicks++;
        ageIncreaser(size);

        growGrass();
        growGrass();
        growGrass();
        growGrass();

        final int MAX_GRASS_AGE=20;
        grassDies(size, MAX_GRASS_AGE);

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
                    Goat baby= new Goat(goat1.getX(), goat1.getY(), nextAnimalId++);
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
