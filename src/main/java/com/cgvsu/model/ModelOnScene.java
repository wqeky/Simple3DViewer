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
    public void scaleModel(final Vector3f scaleFactor) {
        for (Vector3f vertex : this.vertices) {
            vertex.setX(vertex.getX() * scaleFactor.getX());
            vertex.setY(vertex.getY() * scaleFactor.getY());
            vertex.setZ(vertex.getZ() * scaleFactor.getZ());
        }

        for (Vector3f normal : this.normals) {
            normal.setX(normal.getX() * scaleFactor.getX());
            normal.setY(normal.getY() * scaleFactor.getY());
            normal.setZ(normal.getZ() * scaleFactor.getZ());
        }

    }
    public void rotateModel(final Vector3f rotationAngles) {
        for (Vector3f vertex : this.vertices) {
            double x = vertex.getX();
            double y = vertex.getY();
            double z = vertex.getZ();

            // Apply rotation around X-axis
            double tempY = y * Math.cos(rotationAngles.getX()) - z * Math.sin(rotationAngles.getX());
            double tempZ = y * Math.sin(rotationAngles.getX()) + z * Math.cos(rotationAngles.getX());
            y = tempY;
            z = tempZ;

            // Apply rotation around Y-axis
            double tempX = x * Math.cos(rotationAngles.getY()) + z * Math.sin(rotationAngles.getY());
            z = -x * Math.sin(rotationAngles.getY()) + z * Math.cos(rotationAngles.getY());
            x = tempX;

            // Apply rotation around Z-axis
            tempX = x * Math.cos(rotationAngles.getZ()) - y * Math.sin(rotationAngles.getZ());
            tempY = x * Math.sin(rotationAngles.getZ()) + y * Math.cos(rotationAngles.getZ());
            x = tempX;
            y = tempY;

            vertex.setX(x);
            vertex.setY(y);
            vertex.setZ(z);
        }

        for (Vector3f normal : this.normals) {
            double nx = normal.getX();
            double ny = normal.getY();
            double nz = normal.getZ();

            normal.setX(nx);
            normal.setY(ny);
            normal.setZ(nz);
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
