package com.libgdx.lcars.ship.cargosystem;

import com.badlogic.gdx.Gdx;

public class Alloys extends Item{

    public Alloys() {
        this(10);
        // Volume in Liters, Mass in Kilograms, Starting Amount, Price on Market
    }

    public Alloys(int startingAmount) {
        super(Gdx.files.internal("Alloys.png"), "Alloys", 4, 10, startingAmount, 2f);
        // Volume in Liters, Mass in Kilograms, Starting Amount, Price on Market
    }
    
}
