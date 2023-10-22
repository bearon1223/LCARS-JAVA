package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.Gdx;
import com.libgdx.lcars.ship.Ship;

public class Power extends Subsystem {

    public Power(float startingHP) {
        super(50, startingHP, 0, 0);
    }

    public float getPower() {
        return power;
    }

    public void update(Ship s) {
        //TODO: Take power from this power subsystem to power the other systems and add power to this subsystem from the WC.
        // If the warpcore is enabled, take power from the power subsystem.
        if(s.getWarpCore().isEnabled()){
            power += s.getWarpCore().getDrawRate() * Gdx.graphics.getDeltaTime();
            power += s.getWarpCore().getPowerRate() * Gdx.graphics.getDeltaTime();
        }

        if(s.getImpulse().isEnabled()){
            power += s.getImpulse().getDrawRate() * Gdx.graphics.getDeltaTime();
            power += s.getImpulse().getPowerRate() * Gdx.graphics.getDeltaTime();
        } else {
            power += s.getImpulse().getPowerRate() * Gdx.graphics.getDeltaTime();
        }

        if(power < 0){
            s.powerDown();
        }
    }
}
