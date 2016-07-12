package com.zackwantsfriends.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionComponent extends AbstractComponent {
    private Rectangle AABB;
    private Vector2 collisionDirection;

    public CollisionComponent() {
        AABB = new Rectangle(getGameObject().getX(), getGameObject().getY(),
                getGameObject().getWidth(), getGameObject().getHeight());
    }

    @Override
    public void update(float deltaTime) {

    }
}
