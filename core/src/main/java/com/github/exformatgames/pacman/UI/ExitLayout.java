package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.app.AppContext;

public class ExitLayout extends Table implements Layout, Localizable {

	public static final String NAME = "ExitLayout";

	private final AppContext context;

	private Skin skin;

	private Label titleLabel;
	private TextButton exitBtn;
	private TextButton cancelBtn;

	public ExitLayout (AppContext context) {
		this.context = context;

		skin = context.getAssets().getSkin();

		createElements();
		initLayout();
		createListeners();
	}


	@Override
	public void show () {
		getStage().setKeyboardFocus(this);
		//context.getAudioManager().playSound("ShowExitLayout");
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
		exitBtn = new TextButton("", skin, "big");
		cancelBtn = new TextButton("", skin, "big");
	}

	private void initLayout () {
		Table buttonsTable = new Table();

		buttonsTable.add(exitBtn).padRight(20);
		buttonsTable.add(cancelBtn);
		buttonsTable.pack();

		setFillParent(true);

		add(titleLabel).padTop(50).padBottom(50).growY();
		row();
		add(buttonsTable);
		pack();
	}


	private void createListeners () {
		exitBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					Gdx.app.exit();
				}
			});

		cancelBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					context.getLayoutManager().show(MainMenuLayout.NAME);
				}
			});

		addListener(new InputListener() {
				public boolean keyDown (InputEvent event, int keycode) {
					if (keycode == Input.Keys.BACK && context.getLayoutManager().thisIsActiveLayout(ExitLayout.this)) {
						context.getLayoutManager().show(MainMenuLayout.NAME);
						return true;
					}
					return false;
				}
			});
	}

	@Override
	public void applyLocale (I18NBundle bundle) {
		titleLabel.setText(bundle.get("ExitTitle"));
		exitBtn.setText(bundle.get("ExitButton"));
		cancelBtn.setText(bundle.get("CancelExitButton"));
	}
}
