package com.libgdx.lcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LCARS extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Sound click;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		click = Gdx.audio.newSound(Gdx.files.internal("Click.wav"));
		font.getData().setScale(1);
		this.setScreen(new LoginScene(this));
	}

	@Override
	public void render() {
		super.render();
		// System.out.println(Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		click.dispose();
	}
}
