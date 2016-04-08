package com.stewsters.sniper;

import com.badlogic.gdx.Game;
import com.stewsters.sniper.assets.TextureManager;
import com.stewsters.sniper.map.TileType;
import com.stewsters.sniper.screen.GameScreen;

public class SniperGame extends Game {

    private GameScreen gameScreen;


    @Override
    public void create() {
        TextureManager.init();
        TileType.setupTextures();

        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

}
