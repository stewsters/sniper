package com.stewsters.sniper.systems;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.sniper.screen.GameScreen;
import com.stewsters.util.math.Point3i;

/**
 * This is not a real system, it prints the visible map
 */
public class MapRenderSystem {

    private SpriteBatch spriteBatch;
    private WorldMap region;
    private GameScreen gameScreen;


    public MapRenderSystem(GameScreen gameScreen, SpriteBatch spriteBatch, WorldMap region) {
        this.gameScreen = gameScreen;
        this.spriteBatch = spriteBatch;
        this.region = region;
    }

    public void processSystem() {

        // if we can limit this to what the camera sees, we can speed it up slightly
        Point3i point = region.player.pos.current;
        int zLevel = point.z;

        //TODO: use getWidth and calculate how many tiles are visible

        int lowX = Math.max(point.x - 20, 0);
        int highX = Math.min(point.x + 20, region.getXSize());

        int lowY = Math.max(point.y - 20, 0);
        int highY = Math.min(point.y + 20, region.getYSize());


        for (int x = lowX; x < highX; x++) {
            for (int y = lowY; y < highY; y++) {


                int zDown = 0;
                for (int zD = 0; zD < 10; zD++) {

                    int tz = zLevel - zD;
                    if (tz < 0) {
                        break;
                    }

                    zDown = zD;
                    TileType tileType = region.getCellTypeAt(x, y, tz);
                    if (tileType.texture != null) {
                        float tint = 1f / (zD + 1f);
                        spriteBatch.setColor(tint, tint, tint, 1);
                        spriteBatch.draw(tileType.texture, x, y, 1, 1);
                        break;
                    }
                }

                // now draw sprite from bottom to top
                for (int zMod = zDown; zMod >= 0; zMod--) {
                    Pawn pawn = region.pawnAt(x, y, zLevel - zMod);
                    if (pawn != null) {
                        float tint = 1f / (zMod + 1f);
                        spriteBatch.setColor(tint, tint, tint, 1);
                        spriteBatch.draw(pawn.appearance.textureRegion, pawn.pos.getRenderedX(), pawn.pos.getRenderedY(), 1, 1);
                    }
                }

            }
        }

    }


}
