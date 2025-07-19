package uk.co.cpsd.javaproject1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import uk.co.cpsd.javaproject1.DecisionInfo.DecisionType;

import java.awt.Point;

public class Goat extends Animal {

    public final int ENERGY_DECREASE_INTERVAL = 5;
    public final int HUNGER_TRESHHOLDS = 50;
    public final int MAX_GOAT_ENERGY_LEVEL = 130;
    private int lastReproductionTick = -5;

    public Goat(int x, int y) {
        super(x, y, 10);
    }

    public void eatGrass() {
        this.energyLevel += 5;
        System.out.println("Goat " + animalId + " at (" + x + "," + y + ") ate grass. Energy: " + energyLevel);
    }

    @Override
    public void move(int worldSize) {
        int dx = (int) (Math.random() * 3) - 1;
        int dy = (int) (Math.random() * 3) - 1;

        x = Math.max(0, Math.min(worldSize - 1, x + dx));
        y = Math.max(0, Math.min(worldSize - 1, y + dy));
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public boolean decreaseEnergy(int currentTick) {

        if (currentTick - lastEnergyDecreaseTick >= ENERGY_DECREASE_INTERVAL) {
            energyLevel -= 2;
            lastEnergyDecreaseTick = currentTick;
            return energyLevel <= 0;
        }

        // System.out.println("Goat " + animalId + " energy decreased to " +
        // energyLevel);
        return false;
    }

    @Override
    public boolean isHungry() {
        return this.energyLevel < HUNGER_TRESHHOLDS;
    }

    @Override
    public void act(World world, List<Animal> babyAnimalHolder) {
        DecisionInfo decisionInfo = animalDecisionMaking(world);

        switch (decisionInfo.getType()) {
            case EAT:
                if (world.hasGrass(decisionInfo.getNextPos().x, decisionInfo.getNextPos().y)) {
                    eatGrass();
                    world.removeGrass(decisionInfo.getNextPos().x, decisionInfo.getNextPos().y);
                    setPosition(decisionInfo.getNextPos());
                }
                break;
            case REPRODUCE:
                Point partnerlocation = decisionInfo.getNextPos();
                Animal partnerGoat = world.getAnimalAt(partnerlocation.x, partnerlocation.y);

                if (partnerGoat instanceof Goat otherGoat && this.canReproduceWith(otherGoat, world.getTotalTicks())) {
                    Animal babyGoat = this.reproduceWith(otherGoat, world.getTotalTicks());
                    babyAnimalHolder.add(babyGoat);
                }
                break;
            case FLEE:
                Point safeRandomPoint = decisionInfo.getNextPos();
                setPosition(safeRandomPoint);
            case WANDER:
                Point randomMove = decisionInfo.getNextPos();
                setPosition(randomMove);

        }
    }

    @Override
    public DecisionInfo animalDecisionMaking(World world) {

        Map<Point, List<Object>> scanedNeighbourHoodByGoat = world.scanNeighbour(getX(), getY());

        // 1. Priority: Eat if hungry
        if (isHungry()) {
            for (Map.Entry<Point, List<Object>> entry : scanedNeighbourHoodByGoat.entrySet()) {
                if (entry.getValue().contains("grass")) {
                    return new DecisionInfo(DecisionType.EAT, entry.getKey());
                }
            }
        }

        // 2. Priority: Reproduce (check nearby goats)
        for (Map.Entry<Point, List<Object>> entry : scanedNeighbourHoodByGoat.entrySet()) {
            for (Object obj : entry.getValue()) {
                if (obj instanceof Goat otherGoat && this.canReproduceWith(otherGoat, world.getTotalTicks())) {
                    return new DecisionInfo(DecisionType.REPRODUCE, entry.getKey());
                }
            }
        }

        // 3. Priority: Flee from danger
        Point safe = findRandomSafePos(scanedNeighbourHoodByGoat);
        if (!safe.equals(new Point(getX(), getY()))) {
            return new DecisionInfo(DecisionType.FLEE, safe);
        }

        // 4. Default: Random move
        Point randomMove = findRandomPos(scanedNeighbourHoodByGoat);
        return new DecisionInfo(DecisionType.WANDER, randomMove);
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
        return new Point(this.getX(), this.getY());
    }

    public Point findRandomPos(Map<Point, List<Object>> neighbourHoodPos) {
        List<Point> allTiles = new ArrayList<>(neighbourHoodPos.keySet());
        if (!allTiles.isEmpty()) {
            return allTiles.get(new Random().nextInt(allTiles.size()));
        }
        return new Point(this.getX(), this.getY());
    }

    public boolean canReproduceWith(Goat otherGoat, int currentTick) {
        if (otherGoat == this)
            return false;

        boolean oppositeGender = this.getGender() != otherGoat.getGender();
        boolean pairsHaveEenergy = this.energyLevel >= 10 && otherGoat.energyLevel >= 10;
        boolean sinceLastReproduce = currentTick - this.lastReproductionTick >= 5
                && currentTick - otherGoat.lastReproductionTick >= 5;
        boolean canReproduce = oppositeGender && pairsHaveEenergy && sinceLastReproduce;
        return canReproduce;

    }

    @Override
    public Animal reproduceWith(Animal partner, int currentTick) {
        Goat goatPartner = (Goat) partner;
        this.lastReproductionTick = currentTick;
        goatPartner.lastReproductionTick = currentTick;
        this.energyLevel -= 10;
        partner.energyLevel -= 10;

        Goat babyGoat = new Goat(getX(), getY());
        babyGoat.energyLevel = 5;

        return babyGoat;
    }

}
