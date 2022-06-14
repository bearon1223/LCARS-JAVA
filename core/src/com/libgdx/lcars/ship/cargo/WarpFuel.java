package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.Gdx;

public class WarpFuel extends Item {
    public WarpFuel() {
        super(Gdx.files.internal("Dilithium Crystal.png"), "Dilithium", 2, 4, 300);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }
}
