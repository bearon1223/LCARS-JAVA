package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class Readout {
    protected float x, y, w, h;
    protected int scene;
    protected boolean pMousePressed = false;
    private Texture circleButton;

    private Timer t = new Timer(2);
    private String[] s = new String[3];

    public Readout(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        scene = 0;
        circleButton = new Texture(Gdx.files.internal("Circle Button.png"));
        resetDefaultReadout();
    }

    public void render(MyShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.rect(x, y, w, h);
        pMousePressed = Gdx.input.isTouched();
    }

    protected void circleButton(SpriteBatch batch, Sound click, float x, float y, float w, float h, Runnable run1,
            Runnable run2, Runnable run3, Runnable run4, Runnable run5, Runnable run6, Runnable run7, Runnable run8) {
        batch.draw(circleButton, x, y, w, h);
        /*
         * Runnable Legend
         * 11 5 22
         * 11 5 22
         * 77 - 88
         * 33 6 44
         * 33 6 44
         */
        if (button(click, x, y, w * 0.40f, h * 0.40f) && run1 != null)
            run1.run();
        if (button(click, x + w * 0.60f, y, w * 0.40f, h * 0.40f) && run2 != null)
            run2.run();
        if (button(click, x, y + h * 0.60f, w * 0.40f, h * 0.40f) && run3 != null)
            run3.run();
        if (button(click, x + w * 0.60f, y + h * 0.60f, w * 0.40f, h * 0.40f) && run4 != null)
            run4.run();
        if (button(click, x + w * 0.43f, y, w * 0.14f, h * 0.40f) && run5 != null)
            run5.run();
        if (button(click, x + w * 0.43f, y + h * 0.60f, w * 0.14f, h * 0.40f) && run6 != null)
            run6.run();
        if (button(click, x, y + h * 0.43f, w * 0.40f, h * 0.14f) && run7 != null)
            run7.run();
        if (button(click, x + w * 0.60f, y + h * 0.43f, w * 0.40f, h * 0.14f) && run8 != null)
            run8.run();
    }

    protected boolean button(Sound clickSound, float x, float y, float w, float h) {
        x = this.x + x;
        y = this.y + y;
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        if (touchPos.x > x && touchPos.x < x + w && touchPos.y > y && touchPos.y < y + h) {
            clickSound.play(0.125f);
            return true;
        }
        return false;
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

    public void batchRenderer(SpriteBatch batch, BitmapFont font) {
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

    // rect(x + w/4, y, w - w/4, h, 2);
    // ellipse(x+w/4, y+h/2, w - w/2, h);

    public void dispose() {
        circleButton.dispose();
    }
}
