package com.libgdx.lcars.ship.subsystems;

public class Subsystem {
    float power, hp;
    boolean isEnabled = true;
    Subsystem(float startingPower, float startingHP){
        this.power = startingPower;
        this.hp = startingHP;
    }
}
