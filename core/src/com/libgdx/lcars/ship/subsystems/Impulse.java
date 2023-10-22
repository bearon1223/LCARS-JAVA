package com.libgdx.lcars.ship.subsystems;

import com.libgdx.lcars.Readout.Starchart;
import com.libgdx.lcars.SpaceTravel.Planet;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargosystem.Cargo;
import com.libgdx.lcars.ship.cargosystem.ImpulseFuel;

import static com.badlogic.gdx.math.MathUtils.map;

public class Impulse extends Subsystem {

    private Cargo cargo;
    private ImpulseFuel fuel;
    private Ship s;

    public float travelDistance = 0;
    public float traveledDistance = 0;

    public Impulse(Ship ship, Cargo cargo, float startingPower, float startingHP) {
        super(startingPower, startingHP);
        this.s = ship;
        this.cargo = cargo;
        fuel = cargo.getImpulseFuel();
    }

    public Impulse(Ship ship, Cargo cargo) {
        this(ship, cargo, 100, 100);
    }

    public void update() {
        if (isEnabled) {
            cargo.removeItemsContinuous(fuel, 0.05f);
            cargo.addItemsContinuous(cargo.getWaste(), 0.025f);
        }
        if(cargo.getImpulseFuel().getTotalWeight() < 5){
            isEnabled = false;
        }
    }

    public void travel(Starchart tD, Planet current, Planet destination, boolean startTravel, float speed) {
        travelDistance = map(0, 3, 0, 10, Math.abs(current.getID() - destination.getID()));
        if (traveledDistance < travelDistance && startTravel && isEnabled) {
            s.isAttacking = false;
            cargo.removeItem(fuel, 0.2f);
            traveledDistance += speed / 12;
        }
        if (traveledDistance >= travelDistance && s.isTravelingImpulse) {
            s.isAttacking = false;
            traveledDistance = 0;
            s.sectorCoords.z = destination.getID();
            s.coordinates.x = destination.getID();
            s.isTravelingImpulse = false;
            tD.selected.z = destination.getID();

            s.getMining().setMiningViewer(destination);
            // if(destination.hasShip) destination.ship = new Ship(false);
        }
    }

}
