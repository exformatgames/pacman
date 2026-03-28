package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.api.ConnectedI;
import com.github.exformatgames.pacman.api.DisconnectedI;
import com.github.exformatgames.pacman.app.AppContext;

public class WaitLayout extends Table implements Layout, Localizable, ConnectedI, DisconnectedI {

	public static final String NAME = "WaitLayout";

	private final AppContext context;

	private Skin skin;

	private Label titleLabel;
	private Image image;

	public WaitLayout (AppContext context) {
		this.context = context;
		skin = context.getAssets().getSkin();

		createElements();
		initElements();
		initLayout();
	}

	@Override
	public void show () {
		Action action = Actions.moveTo(0, 0, 0.1f);
		addAction(action);
	}

	@Override
	public void hide () {
		float positionX = getStage().getViewport().getWorldWidth();
		Action action = Actions.moveTo(positionX, 0);
		addAction(action);
	}

	private void createElements () {
		titleLabel = new Label("", skin, "Title");
		image = new Image(skin, "Loading");
	}

	private void initElements () {
		Action action = Actions.forever(Actions.rotateBy(360, 0.5f));
		image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
		image.addAction(action);
	}

	private void initLayout () {
		setFillParent(true);
		add(titleLabel).maxWidth(360).padTop(50).padBottom(50).growY();
		row();
		add(image).padBottom(50);
		pack();
	}

	@Override
	public void online () {
		context.getLayoutManager().show(MainMenuLayout.NAME);
	}

	@Override
	public void offline () {
		context.getLayoutManager().show(MainMenuLayout.NAME);
	}


	@Override
	public void applyLocale (I18NBundle bundle) {
		titleLabel.setText(bundle.get("WaitTitle"));
	}
}
