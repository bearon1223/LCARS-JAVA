package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.libgdx.lcars.Readout.Readout;
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
            if(s.isTravelingWarp)
                power += s.getWarpCore().getInUsePowerRate() * Gdx.graphics.getDeltaTime(); 
            else
                power += s.getWarpCore().getDrawRate() * Gdx.graphics.getDeltaTime();
            power += s.getWarpCore().getPowerRate() * Gdx.graphics.getDeltaTime();
        }

        if(s.getImpulse().isEnabled()){
            if(s.isTravelingImpulse)
                power += s.getImpulse().getInUsePowerRate() * Gdx.graphics.getDeltaTime(); 
            else
                power += s.getImpulse().getDrawRate() * Gdx.graphics.getDeltaTime();
            power += s.getImpulse().getPowerRate() * Gdx.graphics.getDeltaTime();
        } else {
            power += s.getImpulse().getPowerRate() * Gdx.graphics.getDeltaTime();
        }

        if(power < 0){
            s.powerDown();
        }
    }

    public void textRender(Readout r, float x, float y){
        r.displayText(Color.WHITE, "Power Supply", x, y+220);
    }

    public void render(Readout r, float x, float y){
        float powerPercent = power/100f;
        Color empty = new Color(0.4f, 0.4f, 1f, 1f);
        Color powered = new Color(0.7f, 0.7f, 1f, 1f);

        r.rect(empty, x, y, 105, 200);
        r.rect(powered, x, y, 105, 200*powerPercent);
    }
}
