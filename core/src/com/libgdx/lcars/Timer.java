package com.libgdx.lcars;

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
            return Math.round(endingTime - time / 60);
        return (float)Math.floor(time / 60);
    }

    public boolean countTimer() {
        time++;
        return Math.floor(time / 60) >= endingTime + 1;
    }

}
