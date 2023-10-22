package com.libgdx.lcars.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.longVector;
import com.libgdx.lcars.SpaceTravel.Planet;
import com.libgdx.lcars.SpaceTravel.Sector;
import com.libgdx.lcars.SpaceTravel.Starsystem;
import com.libgdx.lcars.ship.cargosystem.Cargo;
import com.libgdx.lcars.ship.subsystems.Impulse;
import com.libgdx.lcars.ship.subsystems.MiningSystem;
import com.libgdx.lcars.ship.subsystems.Power;
import com.libgdx.lcars.ship.subsystems.Shields;
import com.libgdx.lcars.ship.subsystems.Warpcore;

import static com.libgdx.lcars.Useful.convertIndexToVector;
import static com.libgdx.lcars.Useful.within;

public class Ship {
    protected Power power;
    protected Warpcore wc;
    protected Shields shields;
    protected Impulse impulse;
    protected Cargo cargo;
    protected MiningSystem mining;
    private longVector limits;

    private Sector[][] s;

    // Sector, SystemID, PlanetID
    public Vector3 sectorCoords = new Vector3(0, 0, 1);
    // Planet, X, Y
    public Vector3 coordinates = new Vector3(1, 50, 50);

    // only for AI ships
    private Vector3 randomCoordinates = new Vector3(1, 1, 1);

    private boolean isPlayer = false;
    public boolean isTravelingWarp = false;
    public boolean isTravelingImpulse = false;
    public boolean isAttacking = false;

    // private float hullHP = 100;

    public Ship(boolean isPlayer) {
        limits = new longVector(30, 228 - 30, 30, 173 - 40);
        this.isPlayer = isPlayer;
        cargo = new Cargo(5000, isPlayer);
        // 5 KiloLiters max storage
        wc = new Warpcore(this, cargo);
        impulse = new Impulse(this, cargo);
        mining = new MiningSystem(this, cargo);

        power = new Power(100);

        s = new Sector[5][4];
        int index = 0;
        if (isPlayer) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 5; i++) {
                    s[i][j] = new Sector(this, new Vector2(170 + 110 + 110 + 10, 600 - (201 + 25) - 5 - 310),
                            new Vector2(i, j),
                            index, randomSysCoords(7));
                    index++;
                }
            }
            mining.setMiningViewer(getPlanet());
        }
    }

    public Sector[][] getSector(){
        return s;
    }

    public Sector getSector(int sectorID){
        Vector3 i = convertIndexToVector(sectorID);
        return s[(int)i.x][(int)i.y];
    }

    public Sector getCurrentSector(){
        Vector3 i = convertIndexToVector(sectorCoords.x);
        return s[(int)i.x][(int)i.y];
    }

    public Planet getPlanet(){
        Vector3 i = convertIndexToVector(sectorCoords.x);
        return s[(int)i.x][(int)i.y].getSystem((int)sectorCoords.y).getPlanet((int)sectorCoords.z);
    }

    public Planet getPlanet(int sectorID, int SystemID, int PlanetID){
        Vector3 i = convertIndexToVector(sectorID);
        return s[(int)i.x][(int)i.y].getSystem(SystemID).getPlanet(PlanetID);
    }

    public Planet getPlanet(int PlanetID){
        return getSystem().getPlanet(PlanetID);
    }

    public Starsystem getSystem(){
        Vector3 i = convertIndexToVector(sectorCoords.x);
        return s[(int)i.x][(int)i.y].getSystem((int)sectorCoords.y);
    }

    public Starsystem getSystem(int sectorID, int SystemID){
        Vector3 i = convertIndexToVector(sectorID);
        return s[(int)i.x][(int)i.y].getSystem(SystemID);
    }

    public Starsystem getSystem(Sector s, int SystemID){
        return s.getSystem(SystemID);
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void update() {
        if (isPlayer) {
            wc.update();
            impulse.update();
            cargo.update();
            power.update(this);

            // Mining subsystem
            if (mining.getActive()) {
                mining.minePlanet(
                        s[(int) convertIndexToVector(sectorCoords.x).x][(int) convertIndexToVector(sectorCoords.x).y]
                                .getSystem((int) sectorCoords.y).getPlanet((int) coordinates.x));
            }
            s[(int) convertIndexToVector(sectorCoords.x).x][(int) convertIndexToVector(sectorCoords.x).y]
                    .getSystem((int) sectorCoords.y).getPlanet((int) coordinates.x).update();
        } else {
            ai();
        }
    }

    public void powerDown() {
        wc.disable();
        impulse.disable();
    }

    public Warpcore getWarpCore() {
        return wc;
    }

    public MiningSystem getMining() {
        return mining;
    }

    public Shields getShields() {
        return shields;
    }

    public Impulse getImpulse() {
        return impulse;
    }

    public void dispose() {
        cargo.dispose();
    }

    private Vector2[] randomSysCoords(int amount) {
        Vector2[] r = new Vector2[amount];
        for (int i = 0; i < amount; i++) {
            r[i] = new Vector2(MathUtils.random(limits.x, limits.y), MathUtils.random(limits.z, limits.w));
        }
        return r;
    }

    private boolean isAtCoords(Vector3 coords) {
        return within(new Vector2(coordinates.y, coordinates.z), coords.y, coords.z, 10);
    }

    // public boolean isPlayerHere() {
    // return (playerLocation.x == coordinates.x);
    // }

    // public void setPlayerLoc(Vector3 playerLoc) {
    // playerLocation = new Vector3(playerLoc);
    // }

    public void ai() {
        float speed = 5;
        if (!isAtCoords(randomCoordinates)) {
            if (coordinates.y < randomCoordinates.y)
                coordinates.y += speed * Gdx.graphics.getDeltaTime();
            if (coordinates.y > randomCoordinates.y)
                coordinates.y -= speed * Gdx.graphics.getDeltaTime();
            if (coordinates.z < randomCoordinates.z)
                coordinates.z += speed * Gdx.graphics.getDeltaTime();
            if (coordinates.z > randomCoordinates.z)
                coordinates.z -= speed * Gdx.graphics.getDeltaTime();
        } else {
            if (!isAttacking)
                randomCoordinates = new Vector3(1, MathUtils.random(0, 100), MathUtils.random(0, 100));
        }
    }
}
