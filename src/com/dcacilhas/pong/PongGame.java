package com.dcacilhas.pong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dcacilhas on 12/16/13.
 */
public class PongGame implements ApplicationListener {
    private Rectangle field = new Rectangle();

    @Override
    public void create() {
        field.set(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        draw(dt);
    }

    private void update(float dt) {

    }

    private void draw(float dt) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.resizable = false;
        cfg.width = 1280;
        cfg.height = 720;
        cfg.title = "Pong";
        cfg.useGL20 = true;
        new LwjglApplication(new PongGame(), cfg);
    }
}
