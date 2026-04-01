package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.client.ConnectionStatusListener;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.data.AppData;
import com.github.exformatgames.pacman.screens.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MenuLayout extends Table implements Layout, LocalizationManager.Localizable, ConnectionStatusListener{

	public static final String NAME = "MainMenuLayout";

	private final GameContext context;

	private final Skin skin;

	private Label titleLabel;
	private Label soundLabel;
    private Label connectionStatusLabel;
    private Label musicLabel;
    private Label volumeLabel;
    private CheckBox soundCheckBox;
    private CheckBox musicCheckBox;
	private CheckBox connectionIndicatorCheckBox;
    private TextButton startGameBtn;
    private Slider volumeSlider;
    private SelectBox<String> langSelectBox;

	public MenuLayout(GameContext context) {
		skin = context.getAssets().getSkin();
		this.context = context;

		createElements();
		initElements();
		initLayout();
		createListeners();
	}

	@Override
	public void show () {
		getStage().setKeyboardFocus(this);
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
		titleLabel = new Label("PACMAN", skin, "Title");
		soundLabel = new Label("", skin, "Settings");
		musicLabel = new Label("", skin, "Settings");
		volumeLabel = new Label("", skin, "Settings");
		connectionStatusLabel = new Label("", skin, "Settings");

		soundCheckBox = new CheckBox("", skin, "switch");
		musicCheckBox = new CheckBox("", skin, "switch");
		connectionIndicatorCheckBox = new CheckBox("", skin, "switch");

		volumeSlider = new Slider(0, 1, 0.01f, false, skin);

		langSelectBox = new SelectBox<>(skin);

		startGameBtn = new TextButton("", skin, "big");

		langSelectBox.setItems("english", "русский");
	}

	private void initElements () {
		AppData data = context.getDataManager().getData();

		startGameBtn.setDisabled(true);
        startGameBtn.setTouchable(Touchable.disabled);
		soundCheckBox.setChecked(data.soundOn);
		musicCheckBox.setChecked(data.musicOn);
		connectionIndicatorCheckBox.setTouchable(Touchable.disabled);
		volumeSlider.setValue(data.volumeValue);
		langSelectBox.setSelected(data.lang);
	}

	private void initLayout () {
		Table buttonsTable = new Table();
		Table settingsTable = new Table();
		Table soundTable = new Table();
		Table musicTable = new Table();
		Table volumeTable = new Table();
		Table connectStatusTable = new Table();

		connectStatusTable.add(connectionStatusLabel);
		connectStatusTable.add(connectionIndicatorCheckBox);
		connectStatusTable.pack();

		buttonsTable.add(startGameBtn);
		buttonsTable.pack();

		settingsTable.defaults().padBottom(10);
		settingsTable.add(soundTable);
		settingsTable.row();
		settingsTable.add(musicTable);
		settingsTable.row();
		settingsTable.add(volumeTable);
		settingsTable.row();
		settingsTable.add(langSelectBox);
		settingsTable.pack();

		soundTable.add(soundLabel);
		soundTable.add(soundCheckBox);
		soundTable.pack();

		musicTable.add(musicLabel);
		musicTable.add(musicCheckBox);
		musicTable.pack();

		volumeTable.add(volumeLabel);
		volumeTable.row();
		volumeTable.add(volumeSlider).growX();
		volumeTable.pack();

		setFillParent(true);
        add(connectStatusTable).padTop(5);
        row();
        add(titleLabel).padTop(50).padBottom(50).growY();
		row();
		add(buttonsTable).padBottom(50);
		row();
		add(settingsTable).padBottom(20).growX();
		row();
		add(connectStatusTable);
		pack();
	}

	private void createListeners () {

		startGameBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					context.getGame().showScreen(GameScreen.NAME);
                    context.getAudioManager().playButtonSound();
				}
			});

		soundCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeListener.ChangeEvent event, Actor actor) {
					context.getDataManager().getData().soundOn = soundCheckBox.isChecked();
					context.getDataManager().saveData();
                    context.getAudioManager().playCheckBoxSound();
				}
			});

		musicCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeListener.ChangeEvent event, Actor actor) {
					context.getDataManager().getData().musicOn = musicCheckBox.isChecked();
					context.getDataManager().saveData();
					context.getAudioManager().playCheckBoxSound();
                    if ( ! musicCheckBox.isChecked()) {
                        context.getAudioManager().stopMusic();
                    }
                    else {
                        context.getAudioManager().playMenuMusic();
                    }
				}
			});

		volumeSlider.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeListener.ChangeEvent event, Actor actor) {
					context.getAudioManager().changeVolume(volumeSlider.getValue());
				}
			});

		volumeSlider.addListener(new InputListener() {
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
					context.getDataManager().getData().volumeValue = volumeSlider.getValue();
					context.getDataManager().saveData();
				}
			});

		langSelectBox.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                    context.getAudioManager().playCheckBoxSound();
					context.getDataManager().getData().lang = langSelectBox.getSelected();
					context.getDataManager().saveData();
					context.getLocalizationManager().change(langSelectBox.getSelected());
				}
			});

		addListener(new InputListener() {
				public boolean keyDown (InputEvent event, int keycode) {
					if ((keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) && context.getLayoutManager().thisIsActiveLayout(MenuLayout.this)) {
						context.getLayoutManager().show(ExitLayout.NAME);
						return true;
					}

					return false;
				}
			});
	}

    @Override
    public void onConnected() {
        startGameBtn.setDisabled(false);
        startGameBtn.setTouchable(Touchable.enabled);
        connectionIndicatorCheckBox.setChecked(false);
    }

    @Override
    public void onDisconnected() {
        startGameBtn.setDisabled(true);
        startGameBtn.setTouchable(Touchable.disabled);
        connectionIndicatorCheckBox.setChecked(false);
    }

	@Override
	public void applyLocale (I18NBundle bundle) {
        connectionStatusLabel.setText(bundle.get("ConnectionStatusLabel"));
		soundLabel.setText(bundle.get("SoundLabel"));
		musicLabel.setText(bundle.get("MusicLabel"));
		volumeLabel.setText(bundle.get("VolumeLabel"));
		startGameBtn.setText(bundle.get("StartGameBtn"));
	}
}
