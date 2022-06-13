package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Readout {
    protected float x, y, w, h;
    protected int scene;
    protected boolean pMousePressed = false;

    private Texture circleButton;
    private Texture test;
    private Array<RectHolder> rectCoordinates;
    private Array<TextureHolder> textureHolder;

    public Readout(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        scene = 0;
        circleButton = new Texture(Gdx.files.internal("Circle Button.png"));
        test = new Texture(Gdx.files.internal("Tactical Display Template.png"));
        rectCoordinates = new Array<RectHolder>();
        textureHolder = new Array<TextureHolder>();
    }

    protected void rect(float x, float y, float w, float h, float r) {
        rect(Color.WHITE, x, y, w, h, r);
    }

    protected void rect(float x, float y, float w, float h) {
        rect(x, y, w, h, 0);
    }

    protected void rect(Color c, float x, float y, float w, float h) {
        rect(c, x, y, w, h, 0);
    }

    protected void rect(Color c, float x, float y, float w, float h, float r) {
        rectCoordinates.add(new RectHolder(c, x, y, w, h, r));
    }

    protected void image(Texture texture, float x, float y, float w, float h) {
        textureHolder.add(new TextureHolder(texture, x, y, w, h));
    }

    protected int circleButton(SpriteBatch batch, Sound click, float x, float y, float w, float h,
            boolean pMousePressed) {
        batch.draw(circleButton, this.x + x, this.y + y, w, h);
        /*
         * Integer Legend
         * 11 5 22
         * 11 5 22
         * 77 - 88
         * 33 6 44
         * 33 6 44
         */
        if (button(click, x, y, w * 0.40f, h * 0.40f, pMousePressed))
            return 1;
        if (button(click, x + w * 0.60f, y, w * 0.40f, h * 0.40f, pMousePressed))
            return 2;
        if (button(click, x, (y + h * 0.60f), w * 0.40f, h * 0.40f, pMousePressed))
            return 3;
        if (button(click, x + w * 0.60f, (y + h * 0.60f), w * 0.40f, h * 0.40f, pMousePressed))
            return 4;
        if (button(click, x + w * 0.43f, y, w * 0.14f, h * 0.40f, pMousePressed))
            return 5;
        if (button(click, x + w * 0.43f, (y + h * 0.60f), w * 0.14f, h * 0.40f, pMousePressed))
            return 6;
        if (button(click, x, y + h * 0.43f, w * 0.40f, h * 0.14f, pMousePressed))
            return 7;
        if (button(click, x + w * 0.60f, (y + h * 0.43f), w * 0.40f, h * 0.14f, pMousePressed))
            return 8;
        return 0;
    }

    protected boolean button(Sound clickSound, float x, float y, float w, float h, boolean pMousePressed) {
        x = this.x + x;
        y = this.y + y;
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), 600-Gdx.input.getY(), 0);
        if (touchPos.x > x && touchPos.x < x + w && touchPos.y > y && touchPos.y < y + h
                && pMousePressed != Gdx.input.isTouched() && Gdx.input.isTouched()) {
            clickSound.play(0.125f);
            return true;
        }
        return false;
    }

    public void shapeRenderer(MyShapeRenderer renderer) {
        for (RectHolder coordinates : rectCoordinates) {
            renderer.setColor(coordinates.c);
            renderer.roundedRect(coordinates.x, coordinates.y, coordinates.z, coordinates.w, coordinates.h);
        }
        rectCoordinates = new Array<RectHolder>();
    }

    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        for (TextureHolder tex : textureHolder) {
            batch.draw(tex.texture, tex.x, tex.y, tex.w, tex.h);
        }
        textureHolder = new Array<TextureHolder>();
    }

    // rect(x + w/4, y, w - w/4, h, 2);
    // ellipse(x+w/4, y+h/2, w - w/2, h);

    public void dispose() {
        circleButton.dispose();
    }
}