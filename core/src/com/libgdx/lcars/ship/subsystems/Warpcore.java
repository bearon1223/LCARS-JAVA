package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.Readout.Starchart;
import com.libgdx.lcars.SpaceTravel.Sector;
import com.libgdx.lcars.SpaceTravel.Starsystem;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargo.Cargo;
import com.libgdx.lcars.ship.cargo.WarpFuel;

import static com.badlogic.gdx.math.MathUtils.map;

public class Warpcore extends Subsystem {
    private Cargo cargo;
    private WarpFuel fuel;
    private Ship s;

    public float travelDistance = 0;
    public float traveledDistance = 0;

    public Warpcore(Ship ship, Cargo cargo, float startingPower, float startingHP) {
        super(startingPower, startingHP);
        this.s = ship;
        this.cargo = cargo;
        fuel = cargo.getWarpFuel();
    }

    public Warpcore(Ship ship, Cargo cargo) {
        this(ship, cargo, 100, 100);
    }

    public void travel(Starchart tD, Sector current, Sector destination, Starsystem currentS, Starsystem destinationS,
            boolean startTravel, float speed) {
        if (Math.floor(current.distanceSector(destination.arrayID)) == 0)
            travelDistance = (map(0, (float) Math.hypot(228, 173), 0, 10, currentS.distanceSystem(destinationS.loc))
                    * 10) * 2;
        else
            travelDistance = (map(0, (float) Math.hypot(5, 4), 10, 50, current.distanceSector(destination.arrayID)) * 10
                    + map(0, (float) Math.hypot(228, 173), 0, 10, currentS.distanceSystem(destinationS.loc)) * 10) * 2;

        if (traveledDistance < travelDistance && startTravel && isEnabled) {
            s.isAttacking = false;
            cargo.removeItem(fuel, map(0, 7, 0.5f, 1, speed));
            traveledDistance += speed / 6;
        }
        if (traveledDistance >= travelDistance && s.isTravelingWarp) {
            s.isAttacking = false;
            traveledDistance = 0;
            s.sectorCoords = new Vector3(destination.id, destinationS.id, 1);
            s.coordinates.x = 1;
            tD.currentSector = destination.arrayID;
            s.isTravelingWarp = false;
        }
    }

    public void update() {
        if (isEnabled) {
            cargo.removeItem(fuel, 0.2f);
        }
        if(cargo.getWarpFuel().getTotalWeight() < 5){
            isEnabled = false;
        }
        cargo.update();
    }
}