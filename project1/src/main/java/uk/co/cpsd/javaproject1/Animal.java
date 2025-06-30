package uk.co.cpsd.javaproject1;

import java.awt.Color;

public class Animal {
    
    private int x;
    private int y;
    @SuppressWarnings("unused")
    private int energyLevel;

    public Animal(int x,int y, int energyLevel){
        this.x=x;
        this.y=y;
        this.energyLevel=energyLevel;
    }

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
