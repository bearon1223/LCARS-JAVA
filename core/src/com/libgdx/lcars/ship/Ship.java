package com.libgdx.lcars.ship;

import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.ship.cargo.Cargo;
import com.libgdx.lcars.ship.subsystems.Impulse;
import com.libgdx.lcars.ship.subsystems.Power;
import com.libgdx.lcars.ship.subsystems.Shields;
import com.libgdx.lcars.ship.subsystems.Warpcore;

public class Ship {
    protected Power power;
    protected Warpcore wc;
    protected Shields shields;
    protected Impulse impulse;
    protected Cargo cargo;

    // SectorX, SectorY, SystemID, PlanetID
    public Vector3 sectorCoords = new Vector3(0, 0, 1);
    // Planet, X, Y
    public Vector3 coordinates = new Vector3(1, 50, 50);

    private boolean isPlayer = false;
    public boolean isTravelingWarp = false;
    public boolean isTravelingImpulse = false;
    public boolean isAttacking = false;

    public Ship(boolean isPlayer) {
        this.isPlayer = isPlayer;
        cargo = new Cargo(5000, isPlayer);
        // 5 KiloLiters max storage
        wc = new Warpcore(this, cargo);
        impulse = new Impulse(this, cargo);
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void update() {
        if (isPlayer) {
            wc.update();
            impulse.update();
            cargo.update();
        }
    }

    public Warpcore getWarpCore() {
        return wc;
    }

    public Shields Sields() {
        return shields;
    }

    public Impulse getImpulse() {
        return impulse;
    }

    public void dispose() {
        cargo.dispose();
    }
}
