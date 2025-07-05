package uk.co.cpsd.javaproject1;

import java.awt.Color;

public class Goat extends Animal {
    
    public final int ENERGY_DECREASE_INTERVAL=5;

    public Goat(int x,int y,int id){
        super(x,y,10,id);
    }

    public void eatGrass(){
        this.energyLevel+=5;
        System.out.println("Goat " + id + " at (" + x + "," + y + ") ate grass. Energy: " + energyLevel);
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
    public boolean decreaseEnergy(int currentTick){

        if(currentTick-lastEnergyDecreaseTick>=ENERGY_DECREASE_INTERVAL){
            energyLevel-=2;
            lastEnergyDecreaseTick=currentTick;
            return energyLevel <= 0;
        }

        System.out.println("Goat " + id + " energy decreased to " + energyLevel);
        return false;
    }
}
