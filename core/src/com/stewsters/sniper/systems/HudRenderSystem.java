package com.stewsters.sniper.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.generator.CityGen;
import com.stewsters.sniper.map.WorldMap;

public class HudRenderSystem {

    private WorldMap gameMap;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private final boolean debug = false;

    public HudRenderSystem(WorldMap gameMap, OrthographicCamera camera, SpriteBatch batch, BitmapFont bitmapFont) {
        this.gameMap = gameMap;
        this.batch = batch;
        this.camera = camera;
        this.font = bitmapFont;
    }

    public void processSystem() {
        float height = font.getLineHeight();
        batch.setColor(1, 1, 1, 1);

        font.draw(batch, "Stealth: " + gameMap.player.stealth, 20, camera.viewportHeight - height * 2);
        font.draw(batch, "Kills: " + gameMap.player.kills, 20, camera.viewportHeight - height * 3);
        font.draw(batch, "Floor: " + (gameMap.player.pos.current.z - CityGen.groundHeight), 20, camera.viewportHeight - height * 4);


//        font.draw(batch, "Stealth: " + (d(10) / 10f), 20, 20);
//        font.draw(batch, "Kills: " + gameMap.player.kills, 20, 20);

//        if (debug) {
//            font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, camera.viewportHeight - height * 4);
//            font.draw(batch, "Active entities: " + world.getEntityManager().getActiveEntityCount(), 20, camera.viewportHeight - height * 5);
//            font.draw(batch, "Total created: " + world.getEntityManager().getTotalCreated(), 20, camera.viewportHeight - height * 6);
//            font.draw(batch, "Total deleted: " + world.getEntityManager().getTotalDeleted(), 20, camera.viewportHeight - height * 7);
//        }
    }


}