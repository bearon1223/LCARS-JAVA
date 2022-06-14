package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.Gdx;

public class ImpulseFuel extends Item {
    public ImpulseFuel() {
        super(Gdx.files.internal("deuterium.png"), "Deuterium", 1, 1, 300);
        // Volume in Liters, Mass in Kilograms, Starting Amount
    }
}
