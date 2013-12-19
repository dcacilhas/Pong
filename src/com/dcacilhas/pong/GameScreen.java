package com.dcacilhas.pong;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dcacilhas on 12/16/13.
 */
public class GameScreen implements Screen {
    private static final float BALL_MAX_SPEED = 800f;
    private static final float BALL_REFLECT_ANGLE = 70f;
    private static final float BALL_VELOCITY = 350f;
    private static final float BALL_VELOCITY_MODIFIER = 1.1f;
    private static final float PADDLE_VELOCITY = 400f;
    private Rectangle field = new Rectangle();
    private Ball ball = new Ball();
    private Paddle paddle1 = new Paddle(), paddle2 = new Paddle();
    private ShapeRenderer shapeRenderer;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;
    private SpriteBatch batch;
    private FPSLogger fps = new FPSLogger();
    private float fieldTop, fieldBottom, fieldRight, fieldLeft;
    private GameState currentState = GameState.NEW;
    private enum GameState { NEW, RESET, PLAY }
    private int score1 = 0, score2 = 0;
    private Game game;

    public GameScreen (Game g) {
        this.game = g;
        create();
    }


    public void create() {
        field.set(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fieldLeft = field.x;
        fieldRight = field.x + field.width;
        fieldBottom = field.y;
        fieldTop = field.y + field.height;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/arcade.ttf"));
        font = generator.generateFont(128);
        font.setColor(Color.WHITE);
        newGame();
        reset();
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
    public void render(float dt) {
        //fps.log();
        //System.out.println("Velocity.x: " + ball.getVelocityX() + " | Velocity.y: " + ball.getVelocityY());
        dt = Gdx.graphics.getRawDeltaTime();
        update(dt);
        draw(dt);
    }

    private void update(float dt) {
        switch (currentState) {
            case NEW:
                newGame();
                reset();
                currentState = GameState.PLAY;
                break;
            case RESET:
                reset();
                currentState = GameState.PLAY;
                break;
            case PLAY:
                handleInput();
                updateBall(dt);
                updatePaddle1(dt);
                updatePaddle2(dt);
                break;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            currentState = GameState.NEW;
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void newGame() {
        score1 = 0;
        score2 = 0;
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
            paddle1.setVelocity(0f, PADDLE_VELOCITY);
        } else if (moveDown) {
            paddle1.setVelocity(0f, -PADDLE_VELOCITY);
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
            paddle2.setVelocity(0f, PADDLE_VELOCITY);
        } else if (moveDown) {
            paddle2.setVelocity(0f, -PADDLE_VELOCITY);
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

        // Ball collision with field
        if (ball.left() < fieldLeft) {
//            ball.move(fieldLeft, ball.getY());
//            ball.reflect(true, false);
            score2++;
            currentState = GameState.RESET;
        }
        if (ball.right() > fieldRight) {
//            ball.move(fieldRight - ball.getWidth(), ball.getY());
//            ball.reflect(true, false);
            score1++;
            currentState = GameState.RESET;
        }
        if (ball.bottom() < fieldBottom) {
            ball.move(ball.getX(), fieldBottom);
            ball.reflect(false, true);
        }
        if (ball.top() > fieldTop) {
            ball.move(ball.getX(), fieldTop - ball.getHeight());
            ball.reflect(false, true);
        }

        // Ball collision with paddles
        if (ball.getBounds().overlaps(paddle1.getBounds())) {
            if (ball.left() < paddle1.right() && ball.right() > paddle1.right()){
                ball.move(paddle1.right(), ball.getY());
                ball.reflect(true, false);

                float ballCenterY = ball.getY() + (ball.getHeight() / 2);
                float paddleCenterY = paddle1.getY() + (paddle1.getHeight() / 2);
                float centerDiff = ballCenterY - paddleCenterY;
                float position = centerDiff / paddle1.getHeight();
                float angle = BALL_REFLECT_ANGLE * position;
                Vector2 velocity = ball.getVelocity();
                velocity.setAngle(angle);
                velocity.scl(BALL_VELOCITY_MODIFIER);

                if (velocity.x >= BALL_MAX_SPEED) {
                    ball.setVelocity(BALL_MAX_SPEED, ball.getVelocityY());
                } else {
                    ball.setVelocity(velocity);
                }
            }
        } else if (ball.getBounds().overlaps(paddle2.getBounds())) {
            if (ball.right() > paddle2.left() && ball.left() < paddle2.left()) {
                ball.move(paddle2.left() - ball.getWidth(), ball.getY());
                ball.reflect(true, false);

                float ballCenterY = ball.getY() + (ball.getHeight() / 2);
                float paddleCenterY = paddle2.getY() + (paddle2.getHeight() / 2);
                float centerDiff = ballCenterY - paddleCenterY;
                float position = centerDiff / paddle2.getHeight();
                float angle = BALL_REFLECT_ANGLE * position;
                Vector2 velocity = ball.getVelocity();
                velocity.setAngle(180f - angle);
                velocity.scl(BALL_VELOCITY_MODIFIER);
                if (velocity.x <= -BALL_MAX_SPEED ) {
                    ball.setVelocity(-BALL_MAX_SPEED, ball.getVelocityY());
                } else {
                    ball.setVelocity(velocity);
                }
            }
        }
    }

    private void draw(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(field.getWidth()/2, field.y, field.getWidth()/2, field.getHeight());
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBall(dt);
        drawPaddles(dt);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, Integer.toString(score1), field.x + field.getWidth() / 2 * 0.8f - font.getBounds(Integer.toString(score1)).width, field.y + field.getHeight() * 0.9f);
        font.draw(batch, Integer.toString(score2), field.x + field.getWidth() / 2 * 1.2f, field.y + field.getHeight() * 0.9f);
        batch.end();
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
        velocity.set(BALL_VELOCITY, 0);
        velocity.setAngle(-45);
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
        batch.dispose();
        font.dispose();
        generator.dispose();
    }
}
