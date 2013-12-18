package com.dcacilhas.pong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dcacilhas on 12/17/13.
 */
public abstract class GameObject {
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Rectangle bounds = new Rectangle();

    protected GameObject(int width, int height) {
        bounds.setWidth(width);
        bounds.setHeight(height);
    }

    public Rectangle getBounds() {
        bounds.setX(position.x);
        bounds.setY(position.y);
        return bounds;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void move(float x, float y) {
        position.set(x, y);
    }

    public void translate(float x, float y) {
        position.add(x, y);
    }

    public void integrate(float dt) {
        position.add(velocity.x * dt, velocity.y * dt);
    }
}
