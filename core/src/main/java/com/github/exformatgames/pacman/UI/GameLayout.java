package com.github.exformatgames.pacman.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.screens.MenuScreen;
import data.InputData;

public class GameLayout extends Table implements Layout, LocalizationManager.Localizable {

    private final GameContext context;
    private final Skin skin;

    private Label titleLabel;
    private Button toLeftBtn;
    private Button toRightBtn;
    private Button toUpBtn;
    private Button toDownBtn;
    private TextButton exitBtn;


    public GameLayout(GameContext context) {
        this.context = context;
        skin = context.getAssets().getSkin();

        createElements();
        initElements();
        initLayout();
        createListeners();
    }

    @Override
    public void show() {
		getStage().setKeyboardFocus(this);
	}

    @Override
    public void hide() {}

    public void createElements() {
        titleLabel = new Label("PACMAN", skin, "Title");
        toLeftBtn = new Button(skin);
        toRightBtn = new Button(skin);
        toUpBtn = new Button(skin);
        toDownBtn = new Button(skin);
        exitBtn = new TextButton("", skin);
    }

    public void initElements() {}

    public void initLayout() {
        setFillParent(true);
        top();
        add(titleLabel).pad(10).expandX().left();
        add(exitBtn).pad(10).expandX().right();

        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            Table controlButtonsTable = new Table();

            controlButtonsTable.add();
            controlButtonsTable.add(toUpBtn);
            controlButtonsTable.add();
            controlButtonsTable.row();

            controlButtonsTable.add(toLeftBtn);
            controlButtonsTable.add();
            controlButtonsTable.add(toRightBtn);
            controlButtonsTable.row();

            controlButtonsTable.add();
            controlButtonsTable.add(toDownBtn);
            controlButtonsTable.add();
            controlButtonsTable.row();

            controlButtonsTable.pack();

			add().colspan(2).grow();
			row();
			add().growX();
            add(controlButtonsTable).padBottom(50);
			add().growX();
        }
    }

    public void createListeners() {
        exitBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                context.getAudioManager().stopMusic();
                context.getClient().getGameSessionService().exitGame();
                context.getGame().showScreen(MenuScreen.NAME);
            }
        });

		addListener(new InputListener() {
				public boolean keyDown (InputEvent event, int keycode) {
					if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
						context.getAudioManager().stopMusic();
						context.getClient().getGameSessionService().exitGame();
						context.getGame().showScreen(MenuScreen.NAME);
						return true;
					}

					return false;
				}
			});

        initKeyDownListeners();
        initKeyUpListeners();



        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            toLeftBtn.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_LEFT);
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_LEFT);
                }
            });

            toRightBtn.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_RIGHT);
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_RIGHT);
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            toDownBtn.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_DOWN);
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_DOWN);
                    super.touchUp(event, x, y, pointer, button);
                }
            });

            toUpBtn.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_UP);
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_UP);
                    super.touchUp(event, x, y, pointer, button);
                }
            });
        }
    }

    @Override
    public void applyLocale(I18NBundle bundle) {
        exitBtn.setText(bundle.get("ExitButton"));
    }

    private void initKeyDownListeners() {
        addListener(new InputListener() {
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_LEFT);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_RIGHT);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_UP);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
                    context.getClient().getButtonEventService().onButtonPressed(InputData.MOVE_DOWN);
                    return true;
                }
                return false;
            }
        });
    }

    private void initKeyUpListeners() {
        addListener(new InputListener() {
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_LEFT);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_RIGHT);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_UP);
                    return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean keyUp (InputEvent event, int keycode) {
                if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
                    context.getClient().getButtonEventService().onButtonReleased(InputData.MOVE_DOWN);
                    return true;
                }
                return false;
            }
        });
    }
}
