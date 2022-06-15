package com.libgdx.lcars.ship.cargosystem;

import com.badlogic.gdx.Gdx;

public class Alloys extends Item{

    public Alloys() {
        super(Gdx.files.internal("Alloys.png"), "Alloys", 4, 10, 10, 4f);
        // Volume in Liters, Mass in Kilograms, Starting Amount, Price on Market
    }

    public Alloys(int startingAmount) {
        super(Gdx.files.internal("Alloys.png"), "Alloys", 4, 10, startingAmount, 4f);
        // Volume in Liters, Mass in Kilograms, Starting Amount, Price on Market
    }
    
}
