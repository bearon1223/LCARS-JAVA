package com.libgdx.lcars.ship.cargosystem;

import com.badlogic.gdx.Gdx;

public class WarpFuel extends Item {
    public WarpFuel() {
        this(200);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }

    public WarpFuel(int startingAmount) {
        super(Gdx.files.internal("Dilithium Crystal.png"), "Dilithium", 2, 4, startingAmount, 4);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }
}
