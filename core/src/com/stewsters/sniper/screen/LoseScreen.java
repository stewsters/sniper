package com.stewsters.sniper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.SniperGame;
import com.stewsters.sniper.game.TextureManager;

public class LoseScreen implements Screen {

    SniperGame sniperGame;
    SpriteBatch spriteBatch;
    OrthographicCamera hudCamera;

    public LoseScreen(SniperGame sniperGame) {
        this.sniperGame = sniperGame;
    }


    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        hudCamera = new OrthographicCamera(w, h);
        spriteBatch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.app.log("Lose Screen", "Switch to GameScreen");
            sniperGame.setScreen(new GameScreen(sniperGame));
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(hudCamera.combined);

        spriteBatch.setColor(1, 1, 1, 1);

        spriteBatch.begin();

        TextureManager.bitmapFont.draw(spriteBatch, "You are dead. Press space to restart.", hudCamera.viewportWidth / 4f, hudCamera.viewportHeight / 2f);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        spriteBatch.dispose();
    }
}
