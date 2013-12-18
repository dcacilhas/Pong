package com.dcacilhas.pong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dcacilhas on 12/16/13.
 */
public class PongGame implements ApplicationListener {
    private Rectangle field = new Rectangle();
    private Ball ball = new Ball();
    private Paddle paddle1 = new Paddle(), paddle2 = new Paddle();
    private ShapeRenderer shapeRenderer;
    private FPSLogger fps = new FPSLogger();
    private float fieldTop, fieldBottom, fieldRight, fieldLeft;

    @Override
    public void create() {
        field.set(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fieldLeft = field.x;
        fieldRight = field.x + field.width;
        fieldBottom = field.y;
        fieldTop = field.y + field.height;
        shapeRenderer = new ShapeRenderer();
        reset();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        fps.log();
        float dt = Gdx.graphics.getRawDeltaTime();
        update(dt);
        draw(dt);
    }

    private void update(float dt) {
        updateBall(dt);
        updatePaddle1(dt);
        updatePaddle2(dt);
    }

    private void updatePaddle1(float dt) {
        boolean moveDown = false, moveUp = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp = true;
            moveDown = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown = true;
            moveUp = false;
        }

        if (moveUp) {
            paddle1.setVelocity(0f, 300f);
        } else if (moveDown) {
            paddle1.setVelocity(0f, -300f);
        } else {
            paddle1.setVelocity(0f, 0f);
        }

        paddle1.integrate(dt);
        paddle1.updateBounds();

        if (paddle1.top() > fieldTop) {
            paddle1.move(paddle1.getX(), fieldTop - paddle1.getHeight());
            paddle1.setVelocity(0f, 0f);
        }

        if (paddle1.bottom() < fieldBottom) {
            paddle1.move(paddle1.getX(), fieldBottom);
            paddle1.setVelocity(0f, 0f);
        }
    }

    private void updatePaddle2(float dt) {
        boolean moveDown = false, moveUp = false;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveUp = true;
            moveDown = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveDown = true;
            moveUp = false;
        }

        if (moveUp) {
            paddle2.setVelocity(0f, 300f);
        } else if (moveDown) {
            paddle2.setVelocity(0f, -300f);
        } else {
            paddle2.setVelocity(0f, 0f);
        }

        paddle2.integrate(dt);
        paddle2.updateBounds();

        if (paddle2.top() > fieldTop) {
            paddle2.move(paddle2.getX(), fieldTop - paddle2.getHeight());
            paddle2.setVelocity(0f, 0f);
        }

        if (paddle2.bottom() < fieldBottom) {
            paddle2.move(paddle2.getX(), fieldBottom);
            paddle2.setVelocity(0f, 0f);
        }
    }

    private void updateBall(float dt) {
        ball.integrate(dt);
        ball.updateBounds();

        if (ball.left() < fieldLeft) {
            ball.move(fieldLeft, ball.getY());
            ball.reflect(true, false);
        }
        if (ball.right() > fieldRight) {
            ball.move(fieldRight - ball.getWidth(), ball.getY());
            ball.reflect(true, false);
        }
        if (ball.bottom() < fieldBottom) {
            ball.move(ball.getX(), fieldBottom);
            ball.reflect(false, true);
        }
        if (ball.top() > fieldTop) {
            ball.move(ball.getX(), fieldTop - ball.getHeight());
            ball.reflect(false, true);
        }
    }

    private void draw(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBall(dt);
        drawPaddles(dt);
        shapeRenderer.end();
    }

    private void drawPaddles(float dt) {
        shapeRenderer.rect(paddle1.getX(), paddle1.getY(), paddle1.getWidth(), paddle1.getHeight());
        shapeRenderer.rect(paddle2.getX(), paddle2.getY(), paddle2.getWidth(), paddle2.getHeight());
    }

    private void drawBall(float dt) {
        shapeRenderer.rect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    }

    public void reset() {
        // Reset ball
        ball.move(field.x + (field.width - ball.getWidth()) /2, field.y + (field.height - ball.getHeight()) /2);
        Vector2 velocity = ball.getVelocity();
        velocity.set(300, 0);
        velocity.setAngle(360 - 45);
        ball.setVelocity(velocity);

        // Reset paddles
        paddle1.move(field.x + (field.width * 0.1f), field.y + (field.height - paddle1.getHeight()) / 2);
        paddle2.move(field.x + field.width - (field.width * 0.1f) - paddle2.getWidth(), field.y + (field.height - paddle2.getHeight()) / 2);
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
