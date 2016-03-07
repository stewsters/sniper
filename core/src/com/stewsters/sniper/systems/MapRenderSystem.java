package com.stewsters.sniper.systems;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.sniper.screen.GameScreen;

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

        // Don't use transparency... yet
//        spriteBatch.disableBlending();

        // if we can limit this to what the camera sees, we can speed it up slightly

        for (int x = 0; x < region.getXSize(); x++) {
            for (int y = 0; y < region.getYSize(); y++) {

                TileType tileType = region.tiles[x][y][gameScreen.zLevel];

                if (tileType.texture != null) {

                    //TODO: depth needs to be fixed.  wont show people falling

                    spriteBatch.setColor(1, 1, 1, 1);
                    spriteBatch.draw(tileType.texture, x, y, 1, 1);

                    Pawn pawn = region.pawnAt(x, y, gameScreen.zLevel);
                    if (pawn != null) {
                        spriteBatch.draw(pawn.appearance.textureRegion, pawn.pos.getRenderedX(),  pawn.pos.getRenderedY(), 1, 1);
                    }

//                    spriteBatch.setColor(1, 1, 1, 1);
//                    spriteBatch.draw(appearance.textureRegion, position.x, position.y, 1, 1);

                } else if (gameScreen.zLevel - 1 >= 0 && region.tiles[x][y][gameScreen.zLevel - 1].texture != null) {

                    spriteBatch.setColor(0.5f, 0.5f, 0.5f, 1);

                    spriteBatch.draw(region.tiles[x][y][gameScreen.zLevel - 1].texture, x, y, 1, 1);

                    Pawn pawn = region.pawnAt(x, y, gameScreen.zLevel - 1);
                    if (pawn != null) {
                        spriteBatch.draw(pawn.appearance.textureRegion, x, y, 1, 1);
                    }

                } else if (gameScreen.zLevel - 2 >= 0 && region.tiles[x][y][gameScreen.zLevel - 2].texture != null) {

                    spriteBatch.setColor(0.25f, 0.25f, 0.25f, 1);
                    spriteBatch.draw(region.tiles[x][y][gameScreen.zLevel - 2].texture, x, y, 1, 1);
                    Pawn pawn = region.pawnAt(x, y, gameScreen.zLevel - 1);
                    if (pawn != null) {
                        spriteBatch.draw(pawn.appearance.textureRegion, x, y, 1, 1);
                    }

                }

            }
        }
//        spriteBatch.enableBlending();

    }


}
