package uk.co.cpsd.javaproject1;

public class World {
    public final int size = 10;
    private final boolean[][] grass = new boolean[size][size];

    public void growGrass() {
        int x = (int)(Math.random() * size);
        int y = (int)(Math.random() * size);
        grass[x][y] = true;
    }

    public boolean hasGrass(int x, int y) {
        return grass[x][y];
    }

    public void removeGrass(int x, int y) {
        grass[x][y] = false;
    }

    public void tick() {
        growGrass();
    }
}
