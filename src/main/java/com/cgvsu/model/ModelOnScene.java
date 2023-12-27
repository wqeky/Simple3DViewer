package com.cgvsu.model;

import com.cgvsu.math.Vector3f;

import static com.cgvsu.model.Polygon.changePolygonsNumeration;

import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public final class ModelOnScene extends Model {
    private Vector3f translation;

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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public ModelOnScene add(Vector3f vector3f) {
        ModelOnScene modelOnScene = new ModelOnScene();
