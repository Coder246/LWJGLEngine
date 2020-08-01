package de.tim.renderengine.shaders;

import de.tim.renderengine.Entity.Camera;
import de.tim.renderengine.Entity.Light;
import de.tim.renderengine.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/de/tim/renderengine/shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/de/tim/renderengine/shaders/terrainFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;
    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocation() {
        location_transformationMatrix =  super.getUniformLocation("transformationMatrix");
        location_projectionMatrix =  super.getUniformLocation("projectionMatrix");
        location_viewMatrix =  super.getUniformLocation("viewMatrix");
        location_lightPosition =  super.getUniformLocation("lightPosition");
        location_lightColour =  super.getUniformLocation("lightColour");
        location_shineDamper =  super.getUniformLocation("shineDamper");
        location_reflectivity =  super.getUniformLocation("reflectivity");
        location_skyColor =  super.getUniformLocation("skyColor");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
        super.bindAttribute(2,"normal");

    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix,matrix);

    }
    public void loadLight(Light light) {
        super.loadVector(location_lightPosition,light.getPosition());
        super.loadVector(location_lightColour,light.getColor());


    }
    public void loadSkyColor(float r,float g, float b) {
        super.loadVector(location_skyColor,new Vector3f(r,g,b));
    }
    public void loadShineVariables(float damper,float reflectivity) {
        super.loadFloat(location_shineDamper,damper);
        super.loadFloat(location_reflectivity,reflectivity);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix,matrix);
    }
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);

        super.loadMatrix(location_viewMatrix,viewMatrix);
    }

}
