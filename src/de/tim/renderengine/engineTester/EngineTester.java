package de.tim.renderengine.engineTester;

import de.tim.renderengine.Entity.Camera;
import de.tim.renderengine.Entity.Entity;
import de.tim.renderengine.Entity.Light;
import de.tim.renderengine.engine.Loader;
import de.tim.renderengine.engine.OBJLoader;
import de.tim.renderengine.models.RawModel;
import de.tim.renderengine.engine.Renderer;
import de.tim.renderengine.engine.WindowManager;
import de.tim.renderengine.models.TexturedModel;
import de.tim.renderengine.shaders.StaticShader;
import de.tim.renderengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.Map.Entry;

public class EngineTester {
    public static void main(String[] args) {
        WindowManager.createDisplay();

        Loader loader = new Loader();
        StaticShader staticShader = new StaticShader();
        Renderer renderer = new Renderer(staticShader);

        RawModel model = OBJLoader.loadObjModel("stall",loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));


        TexturedModel staticModel = new TexturedModel(model,texture);
       staticModel.getTexture().setReflectivity(10);
       staticModel.getTexture().setShineDamper(1);

        Entity entity = new Entity(staticModel,new Vector3f(0,0,-25),0,0,0,1);
        Light light = new Light(new Vector3f(0,-10,-15),new Vector3f(1,1,1));
        Camera camera = new Camera();

        while(!Display.isCloseRequested()) {
            // game logic
            entity.increaseRotation(0,1,0);
            camera.move();

            renderer.prepare();
            staticShader.start();
            staticShader.loadLight(light);
           staticShader.loadViewMatrix(camera);
            renderer.render(entity,staticShader);
            staticShader.stop();
            WindowManager.updateDisplay();
        }

        staticShader.dispose();
        loader.dispose();
        WindowManager.destroyDisplay();



    }
}
