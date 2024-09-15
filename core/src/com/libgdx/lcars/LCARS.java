package com.libgdx.lcars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.libgdx.lcars.ship.Ship;

public class LCARS extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Sound click;
	public Ship playerShip;

	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("impact.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		click = Gdx.audio.newSound(Gdx.files.internal("Click.wav"));
		font.getData().setScale(1);
		this.setScreen(new LoginScene(this));
		playerShip = new Ship(true);
	
		generator.dispose();
	}

	@Override
	public void render() {
		super.render();
		playerShip.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		click.dispose();
		playerShip.dispose();
	}
}
