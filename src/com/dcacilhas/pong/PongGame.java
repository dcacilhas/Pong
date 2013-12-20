package com.dcacilhas.pong;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Created by dcacilhas on 12/18/13.
 */
public class PongGame extends Game {
    MainMenu mainMenuScreen;
    GameScreen gameScreen;
    PauseScreen pauseScreen;
    private FPSLogger fps = new FPSLogger();

    @Override
    public void create() {
        mainMenuScreen = new MainMenu(this);
        gameScreen = new GameScreen(this);
        pauseScreen = new PauseScreen(this);
        setScreen(mainMenuScreen);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.render();
        //fps.log();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
        getScreen().dispose();
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.resizable = false;
        cfg.width = 1280;
        cfg.height = 720;
        cfg.title = "Pong";
        cfg.useGL20 = true;
        cfg.addIcon("assets/icon.png", Files.FileType.Internal);
        new LwjglApplication(new PongGame(), cfg);
    }
}
