package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoginScene implements Screen {
    final LCARS app;
    private Texture logo;
    private MyShapeRenderer renderer;
    // Rectangle federationLogo;

    private OrthographicCamera camera;

    public LoginScene(final LCARS app) {
        this.app = app;
        logo = new Texture(Gdx.files.internal("Federation Logo.jpg"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);

        renderer = new MyShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        app.batch.setProjectionMatrix(camera.combined);
        app.batch.begin();
        app.batch.draw(logo, 250, 300 - 394 / 2, 500, 394);
        app.batch.end();

        renderer.setProjectionMatrix(app.batch.getProjectionMatrix());
        renderer.begin(ShapeType.Filled);
        renderer.setColor(100, 150, 255, 255);
        renderer.roundedRect(20, 20, 960, 20, 10);
        renderer.roundedRect(20, 560, 960, 20, 10);
        renderer.end();

        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            app.setScreen(new MainMenu(app));
            app.click.play(0.125f);
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logo.dispose();
        renderer.dispose();
    }

}
