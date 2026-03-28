package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.api.ConnectedI;
import com.github.exformatgames.pacman.api.DisconnectedI;
import com.github.exformatgames.pacman.app.AppContext;

public class AuthLayout extends Table implements Layout, Localizable, ConnectedI, DisconnectedI {

	public static final String NAME = "AuthLayout";
	
	private final AppContext context;
	
	private final Skin skin;

	private Label titleLabel;
	private Label serverLabel;
	private Label usernameLabel;
	private Label passwordLabel;
	private CheckBox localhostCheckBox;
	private TextField serverTextField;
	private TextField usernameTextField;
	private TextField passwordTextField;
	private TextButton authBtn;
	private TextButton registrationBtn;

	public AuthLayout (AppContext context) {
		this.context = context;

		skin = context.getAssets().getSkin();
		
		createElements();
		initElements();
		initLayout();
		createListeners();
	}

	public void show() {
		//context.getAudioManager().playSound("ShowAuthLayout");
		Action action = Actions.moveTo(0, 0, 0.1f);
		addAction(action);
	}

	public void hide () {
		float positionX = getStage().getViewport().getWorldWidth();
		Action action = Actions.moveTo(positionX, 0);
		addAction(action);
	}

	private void createElements () {
		titleLabel = new Label("", skin, "Title");
		serverLabel = new Label("", skin, "Settings");
		usernameLabel = new Label("", skin, "Settings");
		passwordLabel = new Label("", skin, "Settings");

		serverTextField = new TextField("http://", skin);
		usernameTextField = new TextField("", skin);
		passwordTextField = new TextField("", skin);

		localhostCheckBox = new CheckBox("", skin, "switch");
		
		authBtn = new TextButton("", skin, "emphasis");
		registrationBtn = new TextButton("", skin, "emphasis");
	}

	private void initElements () {
		passwordTextField.setPasswordMode(true);
		passwordTextField.setPasswordCharacter('*');
	}

	private void initLayout () {
		Table inputServerTable = new Table();
		Table inputAuthTable = new Table();
		Table buttonsTable = new Table();

		inputServerTable.add(serverLabel).colspan(2).padBottom(20);
		inputServerTable.row();
		inputServerTable.add(serverTextField).padLeft(10).padRight(20);
		inputServerTable.add(localhostCheckBox).padRight(10);
		inputServerTable.pack();
		
		inputAuthTable.defaults().padLeft(10).padBottom(20);
		inputAuthTable.add(usernameLabel);
		inputAuthTable.row();
		inputAuthTable.add(usernameTextField);
		inputAuthTable.row();
		inputAuthTable.add(passwordLabel);
		inputAuthTable.row();
		inputAuthTable.add(passwordTextField);
		inputAuthTable.pack();
		
		buttonsTable.defaults().pad(20);
		buttonsTable.add(registrationBtn);
		buttonsTable.add(authBtn);
		buttonsTable.pack();
		
		setFillParent(true);
		add(titleLabel).padTop(20).padBottom(20);
		row();
		add(inputServerTable).padBottom(20);
		row();
		add(inputAuthTable);
		row();
		add(buttonsTable).padBottom(50).growY();
		row();
		
		pack();
	}

	private void createListeners () {
		authBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					//play sound
					//change layout
				}
			});

		registrationBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					//play sound
					//change screen
				}
			});

		localhostCheckBox.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeListener.ChangeEvent event, Actor actor) {
					if (localhostCheckBox.isChecked()) {
						serverTextField.setDisabled(true);
						//serverTextField.setText(context.getNetManager().LOCALHOST_ADR);
					}
					else {
						serverTextField.setDisabled(false);
						serverTextField.setText("http://");
					}
					//context.getAudioManager().playSound("CheckBox");
				}
			});
			
		addListener(new InputListener() {
				public boolean keyDown(InputEvent event, int keycode) {
					if (keycode == Input.Keys.BACK && context.getLayoutManager().thisIsActiveLayout(AuthLayout.this)) {
						//context.getAudioManager().playSound("ShowMenu");
						context.getLayoutManager().show(MainMenuLayout.NAME);
					}
					return true;
				}
			});
	}


	@Override
	public void online () {
		context.getLayoutManager().show(MainMenuLayout.NAME);
	}

	@Override
	public void offline () {}

	@Override
	public void applyLocale (I18NBundle bundle) {
		titleLabel.setText(bundle.get("AuthTitleLabel"));
		serverLabel.setText(bundle.get("ServerLabel"));
		usernameLabel.setText(bundle.get("UsernameLabel"));
		passwordLabel.setText(bundle.get("PasswordLabel"));
		registrationBtn.setText(bundle.get("RegistrationBtn"));
		authBtn.setText(bundle.get("AuthBtn"));
		localhostCheckBox.setText(bundle.get("LocalhostCB"));
	}
}
