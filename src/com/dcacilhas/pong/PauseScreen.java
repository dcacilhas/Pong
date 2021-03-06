package com.dcacilhas.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by dcacilhas on 12/18/13.
 */
public class PauseScreen implements Screen {
    private PongGame game;
    private SpriteBatch batch;
    private FreeTypeFontGenerator generator;
    private BitmapFont paused;
    private BitmapFont message;
    private String msg = "N - New Game\nQ - Quit\nR - Resume";


    public PauseScreen(PongGame g) {
        this.game = g;

        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/arcade.ttf"));
        paused = generator.generateFont(200);
        paused.setColor(Color.YELLOW);
        message = generator.generateFont(60);
        message.setColor(Color.RED);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.begin();
        paused.draw(batch, "PAUSED", Gdx.graphics.getWidth() / 2 - paused.getBounds("PAUSED").width / 2, Gdx.graphics.getHeight() * 0.6f);
        message.drawMultiLine(batch, msg, Gdx.graphics.getWidth() / 2 - message.getMultiLineBounds(msg).width / 2, Gdx.graphics.getHeight() * 0.4f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyPressed(Input.Keys.R) && !Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            GameScreen.currentState = GameScreen.GameState.PLAY;
            game.setScreen(game.gameScreen);
        } else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            GameScreen.currentState = GameScreen.GameState.NEW;
            game.setScreen(game.gameScreen);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        paused.dispose();
        message.dispose();
        generator.dispose();
    }
}
