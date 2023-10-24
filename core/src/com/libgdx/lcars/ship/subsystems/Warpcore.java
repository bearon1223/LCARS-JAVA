package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Readout.Readout;
import com.libgdx.lcars.Readout.Starchart;
import com.libgdx.lcars.SpaceTravel.Sector;
import com.libgdx.lcars.SpaceTravel.Starsystem;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargosystem.Cargo;
import com.libgdx.lcars.ship.cargosystem.WarpFuel;
import static com.badlogic.gdx.math.MathUtils.map;

import com.badlogic.gdx.graphics.Color;

// Define the Warpcore class, which extends the Subsystem class
public class Warpcore extends Subsystem {
    // Declare private instance variables
    private Cargo cargo;    // Represents the cargo system of the ship
    private WarpFuel fuel;   // Represents the warp fuel used by the warp core
    private Ship s;         // Represents the ship

    // Declare public instance variables
    public float travelDistance = 0;     // Represents the total distance to travel
    public float traveledDistance = 0;   // Represents the distance already traveled
    private int WCsegmantLight = 0;

    // Constructor for the Warpcore class
    public Warpcore(Ship ship, Cargo cargo, float startingPower, float startingHP) {
        // Call the constructor of the superclass (Subsystem)
        super(startingPower, startingHP, -0.1f, 0.15f);
        
        // Initialize instance variables
        this.s = ship;
        this.cargo = cargo;
        fuel = cargo.getWarpFuel();
    }

    // Overloaded constructor for the Warpcore class
    public Warpcore(Ship ship, Cargo cargo) {
        // Call the parameterized constructor with default values
        this(ship, cargo, 100, 100);
    }

    // Method to initiate space travel with the warp core
    public void travel(Starchart tD, Sector current, Sector destination, Starsystem currentS, Starsystem destinationS,
            boolean startTravel, float speed) {
        // Calculate the travel distance based on current and destination parameters
        if (Math.floor(current.distanceSector(destination.arrayID)) == 0)
            travelDistance = (map(0, (float) Math.hypot(228, 173), 0, 10, currentS.distanceSystem(destinationS.loc))
                    * 10) * 2;
        else
            travelDistance = (map(0, (float) Math.hypot(5, 4), 10, 50, current.distanceSector(destination.arrayID)) * 10
                    + map(0, (float) Math.hypot(228, 173), 0, 10, currentS.distanceSystem(destinationS.loc)) * 10) * 2;

        // If travel is ongoing, update the traveled distance and fuel consumption
        if (traveledDistance < travelDistance && startTravel && isEnabled) {
            s.isAttacking = false;
            cargo.removeItemsContinuous(fuel, map(0, 7, 0.5f, 1, speed));
            traveledDistance += speed / 6;
        }
        
        // When travel is complete, update ship's position and other properties
        if (traveledDistance >= travelDistance && s.isTravelingWarp) {
            s.isAttacking = false;
            traveledDistance = 0;
            s.sectorCoords = new Vector3(destination.id, destinationS.id, 1);
            s.coordinates.x = 1;
            tD.currentSector = destination.arrayID;
            s.isTravelingWarp = false;

            // Set the mining viewer for the destination starsystem's planet
            s.getMining().setMiningViewer(destinationS.getPlanet(1));

            // Potential code for handling planet ship interactions (commented out)
            // if(destinationS.getPlanet((int)s.coordinates.x).hasShip) destinationS.getPlanet((int) s.coordinates.x).ship = new Ship(false);
        }
    }

    // Render the Warpcore display
    public void render(MyShapeRenderer shape, Readout r){
        // Warpcore offset stuff
        int offset = 10;

        // Background display stuff
        r.rect(new Color(0.78f, 0.78f, 0.78f, 1), 0, offset + 112, 150, 340 - 120 * 2 + 18, 50);
        r.rect(new Color(0.39f, 0.39f, 0.39f, 1), 0, offset + 340 / 2 - 20, 150, 40, 3);

        // Color the Warpcore differently depending on if it is powered or not.
        Color c1 = new Color();
        if (isEnabled())
            c1 = Color.WHITE;
        else
            c1 = new Color(0.59f, 0.59f, 0.59f, 1);
        
        //I honetly aint got a clue
        r.rect(c1, 0, offset + 340 / 2 - 10, 150, 20);
        shape.setColor(100, 100, 100, 1);
        shape.ellipse(r.x + 150 / 2 - 25, offset + r.y + 340 / 2 - 25, 50, 50);

        //?????
        Color c2 = new Color();
        if (isEnabled())
            c2 = Color.WHITE;
        else
            c2 = new Color(0.59f, 0.59f, 0.59f, 1);
        shape.setColor(c2);
        shape.ellipse(r.x + 150 / 2 - 12.5f, offset + r.y + 340 / 2 - 12.5f, 25, 25);

        // Something I'm sure of it
        shape.setColor(new Color(0.39f, 0.39f, 0.39f, 1));
        shape.rect(r.x + 150 / 2 - 2.5f, offset + r.y + 340 / 2 - 15, 5, 30);

        // Render the many segments of the Warpcore at different colors depending on WCsegmentLight
        for (int i = 0; i < 6; i++) {
            Color c3;
            if (WCsegmantLight == i)
                c3 = Color.valueOf("#95D2FFFF");
            else
                c3 = Color.valueOf("#0075CBFF");
            r.rect(c3, 10, offset + 20 * (i), 150 - 20, 20, 10);
        }

        //Bottom section or maybe the top who knows :/
        for (int i = 0; i < 6; i++) {
            Color c3;
            if (WCsegmantLight == i)
                c3 = Color.valueOf("#95D2FFFF");
            else
                c3 = Color.valueOf("#0075CBFF");
            r.rect(c3, 10, offset + 20 * (5 - i) + (340 - 120), 150 - 20, 20, 10);
        }

        //Cycle through segment light
        if (isEnabled())
            WCsegmantLight = (int) Math.floor((System.currentTimeMillis() / 1000) % 6);
        else
            WCsegmantLight = -1;
    }

    // Method to update the Warpcore subsystem
    public void update() {
        if (isEnabled) {
            // Remove warp fuel and add waste continuously
            cargo.removeItemsContinuous(fuel, 0.1f);
            cargo.addItemsContinuous(cargo.getWaste(), 0.05f);
        }
        // Disable the subsystem if warp fuel is running low
        if (cargo.getWarpFuel().getTotalWeight() < 5) {
            isEnabled = false;
        }
        // Update the cargo system
        cargo.update();
    }
}
