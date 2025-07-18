package uk.co.cpsd.javaproject1;

import java.awt.Color;
import java.util.List;
import java.awt.Point;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal {
    
    protected int x;
    protected int y;
    protected int energyLevel;
    protected int animalId;
    private static final AtomicInteger idCounter=new AtomicInteger(0);
    protected int lastEnergyDecreaseTick=0;

    private Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }


    public Animal(int x,int y, int energyLevel){
        this.x=x;
        this.y=y;
        this.energyLevel=energyLevel;
        this.gender=Math.random()<.5 ? Gender.MALE:Gender.FEMALE;
        this.animalId=idCounter.getAndIncrement();
    }

    public Gender getGender(){
        return gender;
    }

    public abstract void move(int worldSize);

    public boolean decreaseEnergy(int currentTime){
        return energyLevel<=0 ? true :false;
    }

    public void decreaseEnergyBy(int amount){
        this.energyLevel-=amount;
    }
    
    public abstract Color getColor();

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getId(){
        return animalId;
    }

    public abstract void act(World world,List<Animal> babyAnimalHolder);

    public int getEnergy(){
        return energyLevel;
    }

    // public abstract Animal tryReproduceWith(Animal partner);

    public abstract boolean isHungry();

    public abstract DecisionInfo animalDecisionMaking(World world);

    public abstract Animal reproduceWith(Animal partner,int currentTick);

    public void setPosition(Point point){
        this.x=point.x;
        this.y=point.y;
    }
}
