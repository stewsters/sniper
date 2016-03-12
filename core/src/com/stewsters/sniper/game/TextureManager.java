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
    public static BitmapFont bitmapFont;

    public static void init() {
        FileHandle fh = Gdx.files.internal("textureAtlas/tile.atlas");
        atlas = new TextureAtlas(fh);

        player = TextureManager.atlas.findRegion("player");
        soldier = TextureManager.atlas.findRegion("soldier");
        dog = TextureManager.atlas.findRegion("dog");

        bitmapFont = getFont(Gdx.files.internal("fonts/square.ttf"));

    }

    public static BitmapFont getFont(FileHandle fileHandle) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fileHandle);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        return font12;

    }

}
