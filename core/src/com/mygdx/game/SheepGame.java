package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class SheepGame extends Game {
	private AssetManager assetManager = new AssetManager();

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}
}
