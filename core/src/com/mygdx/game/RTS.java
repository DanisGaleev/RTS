package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RTS extends ApplicationAdapter implements GestureDetector.GestureListener {
    private Unit[] units;
    private Stage stage;
    private OrthographicCamera camera;
    private float currentZoom = 1;
    private ShapeRenderer shape;

    @Override
    public void create() {
        shape = new ShapeRenderer();
        camera = new OrthographicCamera(1280, 720);
        stage = new Stage(new ScreenViewport(camera));
        camera.setToOrtho(false, 1280, 720);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(this));

        units = new Unit[10];
        for (int i = 0; i < 10; i++) {
            units[i] = new Unit(new Texture("unit.jpg"), 100, 20, 90 * i, 0);
            stage.addActor(units[i]);
        }
        stage.setDebugAll(true);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        camera.update();
        for (Unit unit : units) {
            if (unit.getTouched()) {
                if (Gdx.input.justTouched()) {
                    // if (stage.hit(Gdx.input.getX(), Gdx.input.getY(), true) == null) {
                    if (stage.hit(stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY())).x, stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY())).y, true) == null) {
                        Vector3 v = new Vector3();
                        camera.unproject(v.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                        //     units[i].setMoveTo(v.x, v.y);
                        unit.addAction(Actions.moveTo(v.x, v.y, v.sub(unit.getX(), unit.getY(), 0).len() / unit.getSpeed()));
                        unit.setTouched(false);
                    } else
                        unit.setTouched(false);
                    //  }
                }
            }
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setProjectionMatrix(camera.combined);
        shape.setColor(Color.GREEN);
        for (Unit unit : units) {
            if (unit.getTouched()) {
                double radius = Math.sqrt(unit.getWidth() * unit.getWidth() + unit.getHeight() * unit.getHeight()) / 2;
                shape.circle(unit.getX() + unit.getWidth() / 2, unit.getY() + unit.getHeight() / 2, (float) radius);
            }
        }
        shape.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        shape.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX * currentZoom, deltaY * currentZoom);
        camera.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        currentZoom = camera.zoom;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        camera.zoom = currentZoom * (initialDistance / distance);
        if (camera.zoom > 2)
            camera.zoom = 2;
        else if (camera.zoom < 0.5)
            camera.zoom = 0.5f;
        camera.update();
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
