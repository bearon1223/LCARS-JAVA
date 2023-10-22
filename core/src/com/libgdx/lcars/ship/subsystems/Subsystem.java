package com.libgdx.lcars.ship.subsystems;

public class Subsystem {
    float power, hp;
    boolean isEnabled = true;
    float drawRate = -0.1f;
    float powerRate = 0.15f;

    Subsystem(float startingPower, float startingHP, float drawRate, float powerRate){
        this.power = startingPower;
        this.hp = startingHP;
        this.drawRate = drawRate;
        this.powerRate = powerRate;
    }

    public float getDrawRate() {
        return drawRate;
    }

    public float getPowerRate() {
        return powerRate;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void disable(){
        isEnabled = false;
    }

    public void enable() {
        isEnabled = true;
    }
}
