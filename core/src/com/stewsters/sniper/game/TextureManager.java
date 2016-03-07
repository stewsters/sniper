package com.stewsters.sniper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by stewsters on 3/6/16.
 */
public class TextureManager {

    public static TextureAtlas atlas;

    public static TextureRegion player;
    public static TextureRegion soldier;
    public static TextureRegion dog;

    public static void init() {
        FileHandle fh = Gdx.files.internal("textureAtlas/tile.atlas");
        atlas = new TextureAtlas(fh);

        player = TextureManager.atlas.findRegion("player");
        soldier = TextureManager.atlas.findRegion("soldier");
        dog = TextureManager.atlas.findRegion("dog");
    }

}
