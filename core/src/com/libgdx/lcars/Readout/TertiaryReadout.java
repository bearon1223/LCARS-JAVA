package com.libgdx.lcars.Readout;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.libgdx.lcars.Timer;

public class TertiaryReadout extends Readout {
    private Timer t = new Timer(2);
    private String[] s = new String[3];

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

    private void resetDefaultReadout() {
        generateStrings();
        t.resetTimer();
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        font.setColor(1, 1, 1, 1);
        if (t.countTimer())
            resetDefaultReadout();
        for (int i = 0; i < s.length; i++) {
            float offset = (2.5f * w / h * 12);
            if (Math.floor(t.getTime(false)) == i)
                font.setColor(1, MathUtils.map(0, 255, 0, 1, 124), MathUtils.map(0, 255, 0, 1, 16), 1);
            else
                font.setColor(MathUtils.map(0, 255, 0, 1, 106), MathUtils.map(0, 255, 0, 1, 88), 1, 1);
            font.draw(batch, String.valueOf(s[i]), x, y + h - i * offset - 2, w, -1, true);
        }
    }
}
