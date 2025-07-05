package uk.co.cpsd.javaproject1;

import java.awt.Color;

public abstract class Animal {
    
    protected int x;
    protected int y;
    protected int energyLevel;
    protected int id;
    protected int lastEnergyDecreaseTick=0;

    public Animal(int x,int y, int energyLevel,int animalId){
        this.x=x;
        this.y=y;
        this.energyLevel=energyLevel;
        this.id=animalId;
    }

    public abstract void move(int worldSize);

    public boolean decreaseEnergy(int currentTime){
        return energyLevel<=0 ? true :false;
    }
    
    public abstract Color getColor();

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getId(){
        return id;
    }
    
}
