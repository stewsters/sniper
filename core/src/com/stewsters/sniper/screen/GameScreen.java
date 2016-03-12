package com.stewsters.sniper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.SniperGame;
import com.stewsters.sniper.game.TextureManager;
import com.stewsters.sniper.generator.CityGen;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.sniper.systems.HudRenderSystem;
import com.stewsters.sniper.systems.MapRenderSystem;
import com.stewsters.sniper.systems.TurnProcessSystem;

public class GameScreen implements Screen {

    private final SniperGame sniperGame;

    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    OrthographicCamera hudCamera;
    WorldMap worldMap;

    MapRenderSystem mapRenderSystem;
    TurnProcessSystem turnProcessSystem;
    HudRenderSystem hudRenderSystem;

    public GameScreen(SniperGame sniperGame) {
        this.sniperGame = sniperGame;
    }

    @Override
    public void show() {


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w, h);
        hudCamera = new OrthographicCamera(w, h);

        camera.position.set(10, 10, 0);
        camera.zoom = 0.05f;

        spriteBatch = new SpriteBatch();


        worldMap = CityGen.populate(CityGen.gen());

        //set up systems
        mapRenderSystem = new MapRenderSystem(this, spriteBatch, worldMap);
        turnProcessSystem = new TurnProcessSystem(worldMap);

        hudRenderSystem = new HudRenderSystem(worldMap, hudCamera, spriteBatch, TextureManager.bitmapFont);

    }

    @Override
    public void render(float delta) {

        if (worldMap.player.health.getHP()<=0) {
            Gdx.app.log("Game Screen", "Switch to Lose Screen");
            sniperGame.setScreen(new LoseScreen(sniperGame));
        }

        turnProcessSystem.process();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(
                worldMap.player.pos.getRenderedX(),
                worldMap.player.pos.getRenderedY(),
                0
        );
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        mapRenderSystem.processSystem();

        //Hud stuff
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hudRenderSystem.processSystem();

        spriteBatch.end();


    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        hudCamera.setToOrtho(false, width, height);
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
//        FontFactory.dispose();
        spriteBatch.dispose();
        TextureManager.atlas.dispose();
    }
}
