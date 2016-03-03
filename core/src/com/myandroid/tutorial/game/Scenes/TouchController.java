package com.myandroid.tutorial.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;

/**
 * Created by vgainous on 3/3/2016.
 */
public class TouchController {
    public Stage stage;
    private Viewport viewport;
    public Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchPadSkin;
    private Drawable touchBackground;
    private Drawable touchPadKnob;

    public TouchController(SpriteBatch spriteBatch){
        touchPadSkin = new Skin();
        touchPadSkin.add("touchPadBackground", new Texture("touchBackground.png"));
        touchPadSkin.add("touchPadKnob", new Texture("touchPadKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchPadSkin.getDrawable("touchPadBackground");
        touchPadKnob = touchPadSkin.getDrawable("touchPadKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchPadKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15,15,200,200);

        viewport = new FitViewport(MyAndroidTutorialGame.V_WIDTH, MyAndroidTutorialGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        stage.addActor(touchpad);
    }

}
