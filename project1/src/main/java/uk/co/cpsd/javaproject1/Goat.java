package uk.co.cpsd.javaproject1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.awt.Point;

public class Goat extends Animal {
    
    public final int ENERGY_DECREASE_INTERVAL=5;
    public final int HUNGER_TRESHHOLDS=80;
    public final double CHANCE_TO_EAT_IF_FULL=0.2;
    public final int MAX_GOAT_ENERGY_LEVEL=130;

    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }

    public Goat(int x,int y){
        super(x,y,10);
        this.gender=Math.random()<.5 ? Gender.MALE:Gender.FEMALE;
    }

    public Gender getGender(){
        return gender;
    }

    public void eatGrass(){
        this.energyLevel+=5;
        System.out.println("Goat " + animalId + " at (" + x + "," + y + ") ate grass. Energy: " + energyLevel);
    }
    @Override
    public void move(int worldSize){
        int dx= (int) (Math.random()*3)-1;
        int dy= (int) (Math.random()*3)-1;

        x = Math.max(0, Math.min(worldSize - 1, x + dx));
        y = Math.max(0, Math.min(worldSize - 1, y + dy));
    }

    

    @Override
    public Color getColor(){
        return Color.RED;
    }

    @Override
    public void act(World world){

        boolean shallIEat=Math.random()<CHANCE_TO_EAT_IF_FULL;
        boolean isGrass= world.hasGrass(x, y);
        if(isGrass&&(isHungry() && shallIEat)){
            eatGrass();
            world.removeGrass(x, y);
            
        }
        move(world.size);

    }

    @Override
    public boolean decreaseEnergy(int currentTick){

        if(currentTick-lastEnergyDecreaseTick>=ENERGY_DECREASE_INTERVAL){
            energyLevel-=2;
            lastEnergyDecreaseTick=currentTick;
            return energyLevel <= 0;
        }

        // System.out.println("Goat " + animalId + " energy decreased to " + energyLevel);
        return false;
    }

    @Override
    public Animal tryReproduceWith(Animal partner) {
        if (!(partner instanceof Goat)) return null;
        Goat other = (Goat) partner;

        boolean samePosition = this.x == other.x && this.y == other.y;
        boolean differentGender = this.gender != other.gender;
        boolean bothEnergetic = this.energyLevel >= 20 && other.energyLevel >= 20;
        boolean notSameAnimal = this.animalId != other.animalId;

        if (samePosition && differentGender && bothEnergetic && notSameAnimal) {
            this.decreaseEnergyBy(2);
            other.decreaseEnergyBy(2);
            Goat baby = new Goat(this.x, this.y);
            baby.energyLevel = 5;
            System.out.println("🐐 Baby goat born at (" + x + ", " + y + ")");
            return baby;
        }

        return null;
    }

    @Override
    public boolean isHungry(){
        return this.energyLevel<HUNGER_TRESHHOLDS;
    }

    @Override
    public boolean isSick(){
        return this.energyLevel>130;
    }

    @Override
    public Point animalDecisionMaking(World world){
        int x=this.getX();
        int y= this.getY();

        Map<Point,List<Object>> scanedNeighbourByGoat = world.scanNeighbour(x, y);
        if(isHungry()){
            for (Map.Entry<Point, List<Object>> posInfo : scanedNeighbourByGoat.entrySet()) {
                if (posInfo.getValue().contains("grass")) {
                    return posInfo.getKey();
                }
            }
            return findRandomSafePos(scanedNeighbourByGoat);
        }else{
            return findRandomPos(scanedNeighbourByGoat);
        }

        

    }

    public Point findRandomSafePos(Map<Point, List<Object>> neighbourHoodPos) {
        List<Point> safeTiles = neighbourHoodPos.entrySet()
                .stream()
                .filter(e -> !e.getValue().contains("lion"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    
        if (!safeTiles.isEmpty()) {
            return safeTiles.get(new Random().nextInt(safeTiles.size()));
        }
    
        // All tiles are dangerous stay in its place
        return new Point(this.getX(),this.getY());
    }

    public Point findRandomPos(Map<Point, List<Object>> neighbourHoodPos) {
        List<Point> allTiles = new ArrayList<>(neighbourHoodPos.keySet());
        if (!allTiles.isEmpty()) {
            return allTiles.get(new Random().nextInt(allTiles.size()));
        }
        return new Point(this.getX(),this.getY());
    }


}
