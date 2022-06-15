package com.libgdx.lcars.ship.subsystems;

public class Subsystem {
    float power, hp;
    boolean isEnabled = true;
    Subsystem(float startingPower, float startingHP){
        this.power = startingPower;
        this.hp = startingHP;
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
