package com.github.exformatgames.pacman.Assets.utils;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;

//ChatGPT

public class JsonLoader<T> extends AsynchronousAssetLoader<T, JsonLoader.JsonParameter<T>> {

    private T data;
    private Class<T> type;
    private Json json;

    public JsonLoader(FileHandleResolver resolver) {
        super(resolver);
        json = new Json();
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file,
                          JsonParameter<T> parameter) {

        this.type = parameter.type;
        data = json.fromJson(type, file);
    }

    @Override
    public T loadSync(AssetManager manager, String fileName, FileHandle file,
                      JsonParameter<T> parameter) {

        return data;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
												  JsonParameter<T> parameter) {
        return null;
    }

    public static class JsonParameter<T> extends AssetLoaderParameters<T> {
        public Class<T> type;

        public JsonParameter(Class<T> type) {
            this.type = type;
        }
    }
}
