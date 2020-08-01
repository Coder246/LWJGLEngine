package de.tim.renderengine.engineTester;

import de.tim.renderengine.Entity.Camera;
import de.tim.renderengine.Entity.Entity;
import de.tim.renderengine.Entity.Light;
import de.tim.renderengine.OBJLoader.ModelData;
import de.tim.renderengine.OBJLoader.OBJFileLoader;
import de.tim.renderengine.engine.*;
import de.tim.renderengine.models.RawModel;
import de.tim.renderengine.models.TexturedModel;
import de.tim.renderengine.terrains.Terrain;
import de.tim.renderengine.textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EngineTester {
    public static void main(String[] args) {
        WindowManager.createDisplay();

        Loader loader = new Loader();


        ModelData model1 = OBJFileLoader.loadOBJ("tree");
        ModelData model_fern1 = OBJFileLoader.loadOBJ("fern");
        ModelData model_grass1 = OBJFileLoader.loadOBJ("grassModel");


        RawModel model = loader.loadToVAO(model1.getVertices(),model1.getTextureCoords(),model1.getNormals(),model1.getIndices());
        RawModel model_fern = loader.loadToVAO(model_fern1.getVertices(),model_fern1.getTextureCoords(),model_fern1.getNormals(),model_fern1.getIndices());
        RawModel model_grass = loader.loadToVAO(model_grass1.getVertices(),model_grass1.getTextureCoords(),model_grass1.getNormals(),model_grass1.getIndices());
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        TexturedModel staticModel_fern = new TexturedModel(model_fern,new ModelTexture(loader.loadTexture("fern")));
        staticModel_fern.getTexture().setHasTransparency(true);
        TexturedModel staticModel_grass = new TexturedModel(model_grass,new ModelTexture(loader.loadTexture("grassTexture")));
        staticModel_grass.getTexture().setHasTransparency(true);
        staticModel_grass.getTexture().setUseFakeLighting(true);


        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));

        }

        for(int i=0;i<800;i++) {
            entities.add(new Entity(staticModel_fern, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
            entities.add(new Entity(staticModel_grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
        }



        Light light = new Light(new Vector3f(100,100,100),new Vector3f(0.8f,0.8f,0.8f));


        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass")));
        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {
            // game logic

            camera.move();


            renderer.renderTerrain(terrain);
            renderer.renderTerrain(terrain2);
            for(Entity entity:entities){
                renderer.renderEntity(entity);
            }

            renderer.render(light,camera);


            WindowManager.updateDisplay();
        }

   renderer.dispose();
        loader.dispose();
        WindowManager.destroyDisplay();



    }
}
