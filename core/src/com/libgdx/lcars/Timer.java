package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;

public class Timer {
    private int time = 0, endingTime;

    public Timer(int time) {
        endingTime = time;
    }

    public void resetTime(int time) {
        this.time = 0;
        endingTime = time;
    }

    public void resetTimer() {
        time = 0;
    }

    public float getTime(boolean countDown) {
        if (countDown)
            return Math.round(endingTime - time*Gdx.graphics.getDeltaTime());
        return (float)Math.floor(time*Gdx.graphics.getDeltaTime());
    }

    public boolean countTimer() {
        time++;
        return Math.floor(time*Gdx.graphics.getDeltaTime()) >= endingTime + 1;
    }

}
