package com.stewsters.sniper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stewsters.sniper.SniperGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        ImagePacker.run();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new SniperGame(), config);
    }
}
