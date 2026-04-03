package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.client.ConnectionStatusListener;
import com.github.exformatgames.pacman.client.service.TestService;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.screens.MenuScreen;
import com.badlogic.gdx.math.MathUtils;

public class TestNetLayout extends Table implements Layout, LocalizationManager.Localizable, ConnectionStatusListener, TestService.ResponsePongListener {

	public static final String NAME = "ExitLayout";

	private final GameContext context;
	private final Skin skin;

	private Label titleLabel;
	private CheckBox connectionStatusCB;
	private TextButton connectBtn;
	private TextButton disconnectBtn;
	private TextButton sendPingBtn;
	private Image pongIndicatorImg;

	public TestNetLayout (GameContext context) {
		this.context = context;
		this.skin = context.getAssets().getSkin();

		createElements();
		initLayout();
		createListeners();
		
		context.getClient().addConnectionStatusListener(this);
	}

	@Override
	public void show () {
		getStage().setKeyboardFocus(this);
	}

	@Override
	public void hide () {}

	private void createElements () {
		titleLabel = new Label("test", skin, "Title");

		connectionStatusCB = new CheckBox("connection", skin, "switch");
		connectBtn = new TextButton("connect", skin);
		disconnectBtn = new TextButton("disconnect", skin);

		sendPingBtn = new TextButton("ping", skin);
		pongIndicatorImg = new Image(skin, "white");
	}

	private void initLayout () {
		setFillParent(true);
		top();

		connectionStatusCB.setTouchable(Touchable.disabled);
		pongIndicatorImg.setColor(Color.BLACK);
		pongIndicatorImg.setSize(20, 20);

		Table connectionTable = new Table();
		connectionTable.add(connectBtn).padRight(20);
		connectionTable.add(disconnectBtn).padRight(20);
		connectionTable.add().growX();
		connectionTable.add(connectionStatusCB);

		Table pingTable = new Table();
		pingTable.add(sendPingBtn).padRight(20);
		pingTable.add(pongIndicatorImg).size(20);

		add(titleLabel).padTop(30).padBottom(30);
		row();

		add(connectionTable).padBottom(20);
		row();

		add(pingTable);
		add().expandY();
	}

	private void createListeners () {
		connectBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					context.getClient().getConnectionService().connect(context.getHost(), context.getPort());
				}
			});

		disconnectBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					context.getClient().getConnectionService().disconnect();
				}
			});

		sendPingBtn.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					context.getClient().getTestService().requestPing();
				}
			});

		addListener(new InputListener() {
				public boolean keyDown (InputEvent event, int keycode) {
					if (keycode == Input.Keys.BACK) {
						context.getGame().showScreen(MenuScreen.NAME);
						return true;
					}
					return false;
				}
			});
	}

	@Override
	public void onConnected () {
		connectionStatusCB.setChecked(true);
	}

	@Override
	public void onDisconnected () {
		connectionStatusCB.setChecked(false);
	}

	@Override
	public void pong () {
		pongIndicatorImg.setColor(
			MathUtils.random(0.3f, 1f),
			MathUtils.random(0.3f, 1f),
			MathUtils.random(0.3f, 1f),
			1f
		);
	}

	
	
	
	@Override
	public void applyLocale (I18NBundle bundle) {}
}
