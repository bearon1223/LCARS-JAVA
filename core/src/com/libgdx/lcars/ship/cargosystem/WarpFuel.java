package com.libgdx.lcars.ship.cargosystem;

import com.badlogic.gdx.Gdx;

public class WarpFuel extends Item {
    public WarpFuel() {
        super(Gdx.files.internal("Dilithium Crystal.png"), "Dilithium", 2, 4, 100, 5);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }

    public WarpFuel(int startingAmount) {
        super(Gdx.files.internal("Dilithium Crystal.png"), "Dilithium", 2, 4, startingAmount, 5);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }
}
