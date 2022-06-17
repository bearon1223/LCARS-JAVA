package com.libgdx.lcars.ship.cargosystem;

import com.badlogic.gdx.Gdx;

public class ImpulseFuel extends Item {
    public ImpulseFuel() {
        this(150);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }

    public ImpulseFuel(int startingAmount) {
        super(Gdx.files.internal("deuterium.png"), "Deuterium", 1, 1, startingAmount, 3);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }
}
