package uk.co.cpsd.javaproject1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class World {
    public final int size = 10;
    private List<Animal> animals;
    private int totalTicks;
    private final int MAX_GRASS_AGE=100;
    private int[][] grassDeathTime= new int[size][size]; 
    private List<Integer> goatHistory = new ArrayList<>();
    private List<Integer> grassHistory = new ArrayList<>();
    public World(int numOfGoats){
        animals=new ArrayList<>();
        for(int i=0;i<numOfGoats;i++){
            animals.add(new Goat((int)(Math.random() * size),(int)(Math.random() * size)));
        }
    }

    public List<Integer> getGoatHistory() {
        return goatHistory;
    }

    public List<Integer> getGrassHistory() {
        return grassHistory;
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

    //=============================

    public void writeToCSV(List<Integer> goatHistory, List<Integer> grassHistory){
        
        try{

            FileWriter csvData=new FileWriter("data.csv");
            csvData.write("Tick, NumOfGoat, NumOfGrass \n");
            for(int i=0;i<goatHistory.size();i++){
                csvData.write(i+", "+goatHistory.get(i)+", "+grassHistory.get(i));
                csvData.write("\n");
            }
            csvData.close();

        }catch(IOException e){
            System.out.println(e);
        }
    }    


    public void tick() {
        totalTicks++;
        growGrass();
        growGrass();
        growGrass();
        growGrass();
        growGrass();
        growGrass();
        int goatCount = findNumOfGoats();
        goatHistory.add(goatCount);
        grassHistory.add(findNumOfGrass());
        handleReproduction();
        List<Animal> deadAnimals=new ArrayList<>();
        for(Animal animal:animals){
            boolean isDead= animal.decreaseEnergy(totalTicks);
            if(animal.isSick()){
                isDead=true;
                System.out.println("☠️ Goat " + animal.getId() + " died of sickness (energy: " + animal.getEnergy() + ")");
            }
            if(isDead){
                deadAnimals.add(animal);
            }
        }
        animals.removeAll(deadAnimals);
    }

    

    public void moveAnimals(){
        animals.forEach(animal-> animal.act(this));
    }

    public void handleReproduction(){

        Set<Integer> alreadyUsed=new HashSet<>();
        List<Animal> newBabies=new ArrayList<>();
        for(Animal animal1:animals){
            if (alreadyUsed.contains(animal1.getId())) continue;
            for(Animal animal2:animals){
                if(animal1==animal2 || alreadyUsed.contains(animal2.getId())) continue;

                Animal baby= animal1.tryReproduceWith(animal2);
                if(baby!=null){
                    newBabies.add(baby);
                    System.out.println("=================Bbay was born==================");
                    alreadyUsed.add(animal1.getId());
                    alreadyUsed.add(animal2.getId());
                    break;
                }
            }
        }

        animals.addAll(newBabies);
    }

}
