package uk.co.cpsd.javaproject1;

import java.awt.Color;

public abstract class Animal {
    
    protected int x;
    protected int y;
    protected int energyLevel;

    public Animal(int x,int y, int energyLevel){
        this.x=x;
        this.y=y;
        this.energyLevel=energyLevel;
    }

    public abstract void move(int worldSize);

    public Color getColor(){
        return Color.RED;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
}
