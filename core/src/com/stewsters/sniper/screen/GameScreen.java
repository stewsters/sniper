package com.stewsters.sniper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.generator.CityGen;
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.sniper.systems.MapRenderSystem;

public class GameScreen implements Screen {

    public int zLevel = 0;

    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    MapChunk mapChunk;
    MapRenderSystem mapRenderSystem;

    @Override
    public void show() {

        camera = new OrthographicCamera();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(10, 10, 0);
        camera.zoom = 0.05f;

        spriteBatch = new SpriteBatch();


        mapChunk = CityGen.populate(CityGen.gen());


        //set up systems
        mapRenderSystem = new MapRenderSystem(this, spriteBatch, mapChunk);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0.6f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        mapRenderSystem.processSystem();

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
//        tileAtlas.dispose();
    }
}
