package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.List;

import static com.cgvsu.model.Polygon.changePolygonsNumeration;

public final class ModelOnScene extends Model {
    private Vector3f translation;
    private Vector3f scale;

    private Vector3f rotation;
    private boolean drawModel;
    private boolean drawPolygonMesh;
    private boolean drawColorFilling;
    private Color color;
    private boolean drawLighting;
    private boolean drawTexture;
    private Image texture;
    private boolean saveWithChanges;

    public ModelOnScene() {
        super();

        this.translation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(-1,-1,-1);
        this.rotation = new Vector3f(0,0,0);

        this.drawModel = true;
        this.drawPolygonMesh = true;
        this.drawColorFilling = false;
        this.drawLighting = false;
        this.drawTexture = false;
        this.saveWithChanges = true;
        this.color = null;
        this.texture = null;
    }

    public ModelOnScene(final Model model) {
        super(model);

        this.translation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(-1,-1,-1);
        this.rotation = new Vector3f(0, 0, 0);
        this.drawModel = true;
        this.drawPolygonMesh = true;
        this.drawColorFilling = false;
        this.drawLighting = false;
        this.drawTexture = false;
        this.saveWithChanges = true;
        this.color = null;
        this.texture = null;
    }

    public boolean isDrawModel() {
        return drawModel;
    }

    public boolean isDrawPolygonMesh() {
        return drawPolygonMesh;
    }

    public boolean isDrawColorFilling() {
        return drawColorFilling;
    }

    public boolean isDrawLighting() {
        return drawLighting;
    }

    public boolean isDrawTexture() {
        return drawTexture;
    }

    public boolean isSaveWithChanges() {
        return saveWithChanges;
    }

    public Vector3f getTranslation() {
        return this.translation;
    }
    public Vector3f getScale(){return this.scale;}
    public Vector3f getRotation(){return this.rotation;}

    public Color getColor() {
        return this.color;
    }

    public Image getTexture() {
        return this.texture;
    }

    public void setDrawModel(boolean drawModel) {
        this.drawModel = drawModel;
    }

    public void setDrawPolygonMesh(boolean drawPolygonMesh) {
        this.drawPolygonMesh = drawPolygonMesh;
    }

    public void setDrawColorFilling(boolean drawColorFilling) {
        this.drawColorFilling = drawColorFilling;
    }

    public void setDrawLighting(boolean drawLighting) {
        this.drawLighting = drawLighting;
    }

    public void setDrawTexture(boolean drawTexture) {
        this.drawTexture = drawTexture;
    }

    public void setSaveWithChanges(boolean saveWithChanges) {
        this.saveWithChanges = saveWithChanges;
    }

    public void setTranslation(final Vector3f translation) {
        this.translation = translation;
    }
    public void setScale(final Vector3f scale){this.scale = scale;}
    public void setRotation(final Vector3f scale){this.rotation = rotation;}

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public ModelOnScene sumVec(Vector3f vector3f) {
        ModelOnScene modelOnScene = new ModelOnScene();

        for (Vector3f other : this.vertices) {
            Vector3f vertex = new Vector3f(other.getX(), other.getY(), other.getZ());
            vertex.sumVec(vector3f);
            modelOnScene.addVertex(vertex);
        }
        modelOnScene.addTextureVertices(this.textureVertices);
        modelOnScene.addNormals(this.normals);
        modelOnScene.addPolygons(this.polygons);

        return modelOnScene;
    }

    public ModelOnScene subtractVec(Vector3f vector3f) {
        ModelOnScene modelOnScene = new ModelOnScene();

        for (Vector3f other : this.vertices) { //надо проверить
            Vector3f vertex = new Vector3f(other.getX(), other.getY(), other.getZ());
            vertex.subtractVec(vector3f);
            modelOnScene.addVertex(vertex);
        }
        modelOnScene.addTextureVertices(this.textureVertices);
        modelOnScene.addNormals(this.normals);
        modelOnScene.addPolygons(this.polygons);

        return modelOnScene;
    }

    public void movePosition(final Vector3f transition) {
        for (Vector3f vector : this.vertices) {
            vector.subtractVec(this.translation);
        }
        this.translation.sumVec(transition);
        for (Vector3f vector : this.vertices) {
            vector.sumVec(this.translation);
        }
    }

    public void applyMovePosition(final Vector3f transition) {
        for (Vector3f vector : this.vertices) {
            vector.subtractVec(this.translation);
        }
        this.translation = transition;
        for (Vector3f vector : this.vertices) {
            vector.sumVec(this.translation);
        }
    }


    public static ModelOnScene createMetaModel(final List<ModelOnScene> ModelList) {
        ModelOnScene metaModelOnScene = new ModelOnScene();
        int amountOfVertices = 0, amountOfTextureVertices = 0, amountOfNormals = 0;

        for (ModelOnScene modelOnScene : ModelList) {
            metaModelOnScene.addVertices(modelOnScene.vertices);
            metaModelOnScene.addTextureVertices(modelOnScene.textureVertices);
            metaModelOnScene.addNormals(modelOnScene.normals);
            metaModelOnScene.addPolygons(changePolygonsNumeration(modelOnScene.polygons, amountOfVertices, amountOfTextureVertices, amountOfNormals));

            amountOfVertices += modelOnScene.getVertices().size();
            amountOfTextureVertices += modelOnScene.getTextureVertices().size();
            amountOfNormals += modelOnScene.getNormals().size();
        }

        return metaModelOnScene;
    }
}
