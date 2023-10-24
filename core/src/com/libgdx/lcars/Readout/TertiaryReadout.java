package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.libgdx.lcars.Timer;

public class TertiaryReadout extends Readout {
    private Timer t = new Timer(2);
    private String[] s = new String[3];
    private boolean debug = false;

    public TertiaryReadout(float x, float y, float w, float h) {
        super(x, y, w, h);
        resetDefaultReadout();
    }

    private void generateStrings() {
        for (int i = 0; i < s.length; i++) {
            String[] number = new String[36];
            for (int j = 0; j < 36; j++) {
                if (j != 6 && j != 24) {
                    number[j] = String.valueOf(Math.round(MathUtils.random(1, 9)));
                } else {
                    number[j] = "-";
                }
            }
            String output = String.join("", number);
            s[i] = output;
        }
    }

    public void toggleDebug(){
        // System.out.printf("Frame Rate Test: %d FPS", Gdx.graphics.getFramesPerSecond());
        debug = !debug;
    }

    private void resetDefaultReadout() {
        generateStrings();
        t.resetTimer();
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        if(debug){
            displayText("FPS: "+Gdx.graphics.getFramesPerSecond(), 0, 10);
        }
        else{
            font.setColor(1, 1, 1, 1);
            Color c = new Color();
            if (t.countTimer())
                resetDefaultReadout();
            for (int i = 0; i < s.length; i++) {
                float offset = (2.5f * w / h * 12);
                if (Math.floor(t.getTime(false)) == i)
                    c = new Color(1, 124f/255f, 16f/255f, 1);
                else
                    c = new Color(106f/255f, 88f/255f, 1, 1);
                // font.draw(batch, String.valueOf(s[i]), x, y + h - i * offset - 2, w, -1, true);
                displayText(c, String.valueOf(s[i]), 0, h-i * offset - 2, w, -1, 1);
            }
        }
        super.batchRenderer(batch, font, pMousePressed);
    }
}
