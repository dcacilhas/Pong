package com.dcacilhas.pong;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by dcacilhas on 12/17/13.
 */
public class Ball extends GameObject {
    public Ball() {
        super(16, 16);
    }

    public void reflect(boolean x, boolean y) {
        Vector2 velocity = getVelocity();
        if (x) velocity.x *= -1;
        if (y) velocity.y *= -1;
        setVelocity(velocity);
    }
}
