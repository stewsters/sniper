package com.stewsters.sniper.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.extra.Bresenham3d;
import com.stewsters.sniper.extra.ClearShotEvaluator;
import com.stewsters.sniper.generator.CityGen;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.sniper.systems.MapRenderSystem;
import com.stewsters.sniper.systems.TurnProcessSystem;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.pathfinder.ChebyshevHeuristic3d;

public class GameScreen implements Screen {

    public int zLevel;

    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    WorldMap worldMap;

    MapRenderSystem mapRenderSystem;
    TurnProcessSystem turnProcessSystem;

    ClearShotEvaluator clearShotEvaluator;
    ChebyshevHeuristic3d chebyshevHeuristic3d;

    @Override
    public void show() {

        camera = new OrthographicCamera();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(10, 10, 0);
        camera.zoom = 0.05f;

        spriteBatch = new SpriteBatch();


        worldMap = CityGen.populate(CityGen.gen());
        zLevel = worldMap.player.pos.current.z;

        clearShotEvaluator = new ClearShotEvaluator(worldMap);

        //set up systems
        mapRenderSystem = new MapRenderSystem(this, spriteBatch, worldMap);
        turnProcessSystem = new TurnProcessSystem(worldMap);
    }

    @Override
    public void render(float delta) {
        turnProcessSystem.process();

        int numTargets = 0;
        for (Pawn pawn : worldMap.pawnQueue) {
            if (pawn != worldMap.player) {

                Point3i p1 = worldMap.player.pos.current;
                Point3i p2 = pawn.pos.current;

                int dx = Math.abs(p1.x - p2.x);
                int dy = Math.abs(p1.y - p2.y);
                int dz = Math.abs(p1.z - p2.z);

                if (Math.max(dz, Math.max(dx, dy)) < 50) {
                    if (Bresenham3d.open(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z, clearShotEvaluator)) {
//                    Gdx.app.log("shot", "player to " + (p2.x - p1.x) + " " + (p2.y - p1.y) + " " + (p2.z - p1.z));
                        numTargets++;
                    }
                }

            }
        }
        Gdx.app.log("shot", "" + numTargets);

        Gdx.gl.glClearColor(1f, 0.6f, 0, 1);
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
