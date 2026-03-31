package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class StartLoadingLayout extends Table {

    public StartLoadingLayout (Skin skin) {
        setFillParent(true);

        Stack stack = new Stack();
        stack.setFillParent(true);

        Image image = new Image(skin, "splash");
        image.setScaling(Scaling.fit);
        stack.add(image);

        Label label = new Label("EXFORMAT GAMES", skin, "TitleLabel");
        label.setAlignment(Align.bottom);

        Container<Label> container = new Container<>(label);
        container.align(Align.bottom);
        container.padBottom(20f);
        container.fillX();

        stack.add(container);

        add(stack).expand().fill();
    }
}
