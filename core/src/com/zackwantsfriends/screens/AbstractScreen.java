package com.zackwantsfriends.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zackwantsfriends.TheGame;
import com.zackwantsfriends.components.collision.CollisionComponent;
import com.zackwantsfriends.gameobjects.AbstractGameObject;
import com.zackwantsfriends.manager.CollisionManager;

public abstract class AbstractScreen extends Stage implements Screen {
    private CollisionManager collisionManager;

    protected AbstractScreen() {
        super(new FitViewport(TheGame.RESOLUTION_WIDTH, TheGame.RESOLUTION_HEIGHT, new OrthographicCamera()));
        Gdx.input.setInputProcessor(new InputMultiplexer(this));

        collisionManager = new CollisionManager();
    }

    // Load actors in this method
    public abstract void buildStage();

    public void addActor(AbstractGameObject gameObject) {
        getActors().add(gameObject);

        if (gameObject.getComponent(CollisionComponent.class) != null) {
            collisionManager.addCollider(gameObject.getComponent(CollisionComponent.class));
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calling to Stage methods
        //super.act(delta);
        update(delta);
        draw();
    }

    void update(float delta) {
        for (int i = 0, n = getActors().size; i < n; i++) {
            AbstractGameObject gameObject = (AbstractGameObject) getActors().get(i);
            collisionManager.update(gameObject);
            gameObject.act(delta);
            getRoot().act(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
