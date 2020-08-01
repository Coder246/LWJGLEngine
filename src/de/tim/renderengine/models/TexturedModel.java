package de.tim.renderengine.models;

import de.tim.renderengine.textures.ModelTexture;

public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model,ModelTexture texture) {
        this.rawModel = model;
        this.texture = texture;

    }


    public ModelTexture getTexture() {
        return texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }


    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }
}
