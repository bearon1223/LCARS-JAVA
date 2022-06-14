package com.libgdx.lcars.ship.cargo;

public class Cargo {
    private WarpFuel warpFuel;
    private ImpulseFuel impulseFuel;
    private float maxStorage;
    private float currentStorage;

    public Cargo(float maxStorage, boolean isPlayer) {
        this.maxStorage = maxStorage;
        warpFuel = new WarpFuel();
        impulseFuel = new ImpulseFuel();
        if(!isPlayer){
            warpFuel.addItems(9999);
            impulseFuel.addItems(9999);
        }
    }

    public WarpFuel getWarpFuel() {
        return warpFuel;
    }

    public ImpulseFuel getImpulseFuel() {
        return impulseFuel;
    }

    public float getCurrentStorage() {
        return currentStorage;
    }

    public float getMaxStorage() {
        return maxStorage;
    }

    public void update() {
        currentStorage = warpFuel.getTotalVolume() + impulseFuel.getTotalVolume();
    }

    public void addItem(Item item, float amount){
        if(currentStorage + amount < maxStorage) item.addItems(amount);
        else System.out.println("Error: Not enough Storage");
    }

    public void removeItem(Item item, float amount) {
        if(item.itemCount - amount >= 0) item.removeItems(amount);
        else System.out.println("Error: Not enough Items");
    }
}
