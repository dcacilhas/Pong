package com.dcacilhas.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by dcacilhas on 12/18/13.
 */
public class MainMenu implements Screen {
    private SpriteBatch batch;
    private Texture texture;
    private Game game;
    private Table table;
    private FreeTypeFontGenerator generator;
    private BitmapFont title, message;
    private float w = Gdx.graphics.getWidth();
    private float h = Gdx.graphics.getHeight();
    private final String TITLE = "PONG", MESSAGE = "Press Any Key To Start";

    public MainMenu (Game g) {
        this.game = g;

        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/arcade.ttf"));
        title = generator.generateFont(400);
        title.setColor(Color.YELLOW);
        message = generator.generateFont(96);
        message.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f , 1f);

        batch.begin();
        title.draw(batch, TITLE, w / 2 - title.getBounds(TITLE).width / 2, h * 0.6f);
        message.draw(batch, MESSAGE, w / 2 - message.getBounds(MESSAGE).width / 2, h * 0.25f);
        batch.end();

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
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
        title.dispose();
        message.dispose();
    }
}
