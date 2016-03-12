package com.stewsters.sniper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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

    public static BitmapFont getFont() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/square.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        return font12;
    }

}
