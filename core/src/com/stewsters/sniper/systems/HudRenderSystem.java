package com.stewsters.sniper.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.generator.CityGen;
import com.stewsters.sniper.map.WorldMap;

public class HudRenderSystem {

    private WorldMap worldMap;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private final boolean debug = false;

    public HudRenderSystem(WorldMap gameMap, OrthographicCamera camera, SpriteBatch batch, BitmapFont bitmapFont) {
        this.worldMap = gameMap;
        this.batch = batch;
        this.camera = camera;
        this.font = bitmapFont;
    }

    public void processSystem() {
        float height = font.getLineHeight();
        batch.setColor(1, 1, 1, 1);

        font.draw(batch, "Stealth: " + worldMap.player.stealth, 20, camera.viewportHeight - height * 2);
        font.draw(batch, "Kills: " + worldMap.player.kills, 20, camera.viewportHeight - height * 3);
        font.draw(batch, "Floor: " + (worldMap.player.pos.current.z - CityGen.groundHeight), 20, camera.viewportHeight - height * 4);

        for (int i = 0; i < Math.min(worldMap.player.playerControl.validTargets.size(), 8); i++) {
            char button = (char) ((int) '1' + i);

            Pawn target = worldMap.player.playerControl.validTargets.get(i);
            font.draw(batch, button + " " + target.snipe.percentageToKill, 20, camera.viewportHeight - height * (5 + i));
        }

    }


}