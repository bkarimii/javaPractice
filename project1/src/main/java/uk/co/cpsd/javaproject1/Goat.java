package uk.co.cpsd.javaproject1;

public class Goat extends Animal {
    
    public Goat(int x,int y){
        super(x,y,10);
    }

    @Override
    public void move(int worldSize){
        int dx= (int) (Math.random()*3)-1;
        int dy= (int) (Math.random()*3)-1;

        x = Math.max(0, Math.min(worldSize - 1, x + dx));
        y = Math.max(0, Math.min(worldSize - 1, y + dy));
    }
}
