package de.tim.renderengine.engine;

import de.tim.renderengine.Entity.Entity;
import de.tim.renderengine.models.RawModel;
import de.tim.renderengine.models.TexturedModel;
import de.tim.renderengine.shaders.StaticShader;
import de.tim.renderengine.textures.ModelTexture;
import de.tim.renderengine.toolbox.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

public class EntityRenderer {



    private StaticShader staticShader;

    public EntityRenderer(StaticShader staticShader,Matrix4f projectionMatrix) {
        this.staticShader = staticShader;


        staticShader.start();
        staticShader.loadProjectionMatrix(projectionMatrix);
        staticShader.stop();
    }






    public void render(Map<TexturedModel, List<Entity>> entities) {

        for(TexturedModel model:entities.keySet()) {
            prepareTexturedModels(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity:batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES,model.getRawModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);

            }
            unbindTexturedModel();
        }


    }

    private void prepareTexturedModels(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();

        if(texture.isHasTransparency()) {
            MasterRenderer.disableCulling();
        }
        staticShader.loadFakeLightningVariable(texture.isUseFakeLighting());

        staticShader.loadShineVariables(texture.getShineDamper(),texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,model.getTexture().getID());
    }

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f trasformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotx(),entity.getRoty(),entity.getRotz(),entity.getScale());
        staticShader.loadTransformationMatrix(trasformationMatrix);
    }




}
