package com.zackwantsfriends.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent extends AbstractComponent {
    private InputHandlerMovementComponent inputHandlerMovementComponent;

    private TextureRegion[][] textureRegions;
    private float timePerFrame;
    private int currentFrame;
    private float timeOnFrame;

    private boolean animate;
    private boolean flip;

    /**
     * @param path         path to the texture image
     * @param regionWidth  width in px of each region of the texture
     * @param regionHeight height in px of each region of the texture
     */
    public AnimationComponent(String path, int regionWidth, int regionHeight, boolean animate, float timePerFrame) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textureRegions = TextureRegion.split(texture, regionWidth, regionHeight);
        this.animate = animate;
        this.timePerFrame = timePerFrame;

        // set current frame to 0
        currentFrame = 0;
    }

    @Override
    public void initialize() {
        inputHandlerMovementComponent = getGameObject().getComponent(InputHandlerMovementComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        if (inputHandlerMovementComponent.getMovementVector() == 0) {
            animate = false;
        } else if (inputHandlerMovementComponent.getMovementVector() < 0) {
            animate = true;
            flip = true;
        } else if (inputHandlerMovementComponent.getMovementVector() > 0) {
            animate = true;
            flip = false;
        }

        if (animate) {
            if (timeOnFrame > timePerFrame) {   // check if it's time to go to the next frame yet
                currentFrame = ++currentFrame % textureRegions[0].length;
                timeOnFrame = 0;
            } else {                            // add to the time counter
                timeOnFrame += deltaTime;
            }
        }
    }

    @Override
    public void render(Batch batch) {
        if (textureRegions[0][currentFrame] == null) return;
        // draw the texture region corresponding to the current frame
        batch.draw(textureRegions[0][currentFrame], getGameObject().getX(), getGameObject().getY(),
                getGameObject().getOriginX(), getGameObject().getOriginY(),
                textureRegions[0][currentFrame].getRegionWidth(), textureRegions[0][currentFrame].getRegionHeight(),
                getGameObject().getScaleX() * (flip ? -1 : 1), getGameObject().getScaleY(), getGameObject().getRotation());
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }
}