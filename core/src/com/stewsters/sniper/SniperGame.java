package com.stewsters.sniper;

import com.badlogic.gdx.Game;
import com.stewsters.sniper.game.TextureManager;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.screen.GameScreen;

public class SniperGame extends Game {

    private GameScreen gameScreen;


    @Override
    public void create() {
        TextureManager.init();
        TileType.setupTextures();

        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

}
