package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.lcars.Readout.AUXReadout;
import com.libgdx.lcars.Readout.MainReadout;
import com.libgdx.lcars.Readout.SecondaryReadout;
import com.libgdx.lcars.Readout.TertiaryReadout;

public class MainScreen implements Screen {
        final LCARS app;
        private OrthographicCamera camera;
        private MyShapeRenderer renderer;

        private Panel mainSideMenu = new Panel(170, 5, 110, 595, new Vector2(1, 7))
                        .addNames(TextArrays.mainSideMenuNames);
        private Panel mainViewSideMenuU = new Panel(170 + 110, 600 - 226.871f, 110, 226.871f, new Vector2(1, 2));
        private Panel mainViewSideMenuB = new Panel(170 + 110, 5, 110, 600 - 231.871f, 1, 8);
        private Panel midMenu = new Panel(170 + 110 + 110, 600 - 201 - 25, 1000 - (170 + 110 + 110), 25, 7, 1);
        private Panel smallSettingsPanel = new Panel(7, 600 - 420 - 151, 160, 151, 2, 4)
                        .addNames(TextArrays.smallSettingsPanelNames);
        private Panel statusButtonL = new Panel(7, 600 - 141.650671785f - 151, 80, 151, 1, 4);
        private Panel statusButtonR = new Panel(87, 600 - 141.650671785f - 151, 80, 151, 1, 4);
        private Panel upperButtons = new Panel(1000 - 180, 600 - 50 - 113.25f, 180, 113.25f, 2, 3)
                        .addNames(TextArrays.upperButtonsNames);

        private TertiaryReadout tReadout;
        private AUXReadout aReadout;
        private MainReadout mReadout;
        private SecondaryReadout sReadout;

        private boolean pMousePressed = false;

        public MainScreen(final LCARS app) {
                this.app = app;
                camera = new OrthographicCamera();
                camera.setToOrtho(false, 1000, 600);
                renderer = new MyShapeRenderer();

                tReadout = new TertiaryReadout(7, 600 - (141.65f + 151) - 125, 155, 120);
                sReadout = new SecondaryReadout(390, 600 - 5 - 195, 1000 - 395 - 180, 195);
                aReadout = new AUXReadout(5, 600 - 141.652f + 5, mainSideMenu.x - 10, 141.651f - 10);
                mReadout = new MainReadout(app.playerShip, midMenu.x, 5, 1000 - midMenu.x - 5,
                                600 - (201 + midMenu.size.y) - 5);

        }

        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
                ScreenUtils.clear(0, 0, 0, 1);
                camera.update();
                // try {
                renderer.setProjectionMatrix(camera.combined);
                renderer.begin(ShapeType.Filled);
                mainSideMenu.render(renderer, 0, false);
                midMenu.render(renderer, 0, false);
                smallSettingsPanel.render(renderer, 1, true);
                mainViewSideMenuU.render(renderer, 0, false);
                mainViewSideMenuB.render(renderer, 0, false);
                statusButtonR.render(renderer, 0, false);
                statusButtonL.render(renderer, 0, false);
                upperButtons.render(renderer, 2, false);

                mReadout.shapeRenderer(renderer, app.click, pMousePressed);
                renderer.end();

                app.batch.setProjectionMatrix(camera.combined);
                app.batch.begin();
                app.font.setColor(0, 0, 0, 1);
                mainSideMenu.textRenderer(app.batch, app.font);
                midMenu.textRenderer(app.batch, app.font);
                smallSettingsPanel.textRenderer(app.batch, app.font);
                mainViewSideMenuU.textRenderer(app.batch, app.font);
                mainViewSideMenuB.textRenderer(app.batch, app.font);
                statusButtonR.textRenderer(app.batch, app.font);
                statusButtonL.textRenderer(app.batch, app.font);
                upperButtons.textRenderer(app.batch, app.font);

                mReadout.batchRenderer(app.batch, app.font, pMousePressed);
                tReadout.batchRenderer(app.batch, app.font, pMousePressed);
                sReadout.render(app.batch);
                aReadout.render(app.batch);

                // app.playerShip.getCargo().getWarpFuel().render(app.batch, 10, 10, 10, 10);
                app.batch.end();

                // app.playerShip.update();
                // System.out.println(app.playerShip.getCargo().getWarpFuel().getItemCount());

                // mReadout.seperateRender(app.batch, app.font, pMousePressed, renderer,
                // app.click);

                if (upperButtons.Button(app.click, new Vector2(0, 2), pMousePressed)) {
                        mReadout.scene = 0;
                }
                if (smallSettingsPanel.Button(app.click, new Vector2(0, 2), pMousePressed)) {
                        app.setScreen(new LoginScene(app));
                        dispose();
                }
                if (smallSettingsPanel.Button(app.click, new Vector2(0, 0), pMousePressed)) {
                        System.exit(0);
                        dispose();
                }

                mainSideMenu.clickArray(app.click, mReadout, TextArrays.mainSideMenuClickID, pMousePressed);
                Vector2 selected = mReadout.getStarchart().selectedSector;

                app.playerShip.getWarpCore().travel(mReadout.getStarchart(), app.playerShip.getCurrentSector(),
                                app.playerShip.getSector((int) selected.x), app.playerShip.getSystem(),
                                app.playerShip.getSystem((int) selected.x, (int) selected.y),
                                app.playerShip.isTravelingWarp, mReadout.selectedSpeed);

                app.playerShip.getImpulse().travel(mReadout.getStarchart(), app.playerShip.getPlanet(),
                                app.playerShip.getPlanet((int) mReadout.getStarchart().selected.z),
                                app.playerShip.isTravelingImpulse, mReadout.selectedSpeed);

                pMousePressed = Gdx.input.isTouched();
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
                renderer.dispose();
                mReadout.dispose();
                tReadout.dispose();
                sReadout.dispose();
                aReadout.dispose();
        }
}
