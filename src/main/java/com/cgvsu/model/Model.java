package com.cgvsu.model;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    protected List<Vector3f> vertices;
    protected List<Vector2f> textureVertices;
    protected List<Vector3f> normals;
    public List<Polygon> polygons;

    public Model() {
        this.vertices = new ArrayList<>();
        this.textureVertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.polygons = new ArrayList<>();
    }

    public Model(final Model model) {
        this.vertices = new ArrayList<>(model.vertices);
        this.textureVertices = new ArrayList<>(model.textureVertices);
        this.normals = new ArrayList<>(model.normals);
        this.polygons = new ArrayList<>(model.polygons);
    }

    public void addVertex(final Vector3f vertex) {
        this.vertices.add(vertex);
    }

    public void addVertices(final List<Vector3f> vertices) {
        this.vertices.addAll(vertices);
    }

    public void addTextureVertex(final Vector2f textureVertex) {
        this.textureVertices.add(textureVertex);
    }

    public void addTextureVertices(final List<Vector2f> textureVertices) {
        this.textureVertices.addAll(textureVertices);
    }

    public void addNormal(final Vector3f normal) {
        this.normals.add(normal);
    }

    public void addNormals(final List<Vector3f> normals) {
        this.normals.addAll(normals);
    }

    public void addPolygon(final Polygon polygon) {
        this.polygons.add(polygon);
    }

    public void addPolygons(final List<Polygon> polygons) {
        this.polygons.addAll(polygons);
    }

    public void setVertices(final ArrayList<Vector3f> vertices) {
        assert vertices.size() >= 3;
        this.vertices = vertices;
    }

    public void setTextureVertices(final ArrayList<Vector2f> textureVertices) {
        assert textureVertices.size() >= 3;
        this.textureVertices = textureVertices;
    }

    public void setNormals(final ArrayList<Vector3f> normals) {
        assert normals.size() >= 3;
        this.normals = normals;
    }

    public void setPolygons(final ArrayList<Polygon> polygons) {
        assert polygons.size() >= 3;
        this.polygons = polygons;
    }

    public List<Vector3f> getVertices() {
        return this.vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return this.textureVertices;
    }

    public List<Vector3f> getNormals() {
        return this.normals;
    }

    public List<Polygon> getPolygons() {
        return this.polygons;
    }

    public boolean equals(final Model other) {
        return (this.vertices.equals(other.vertices)) &&
                (this.textureVertices.equals(other.textureVertices)) &&
                (this.normals.equals(other.normals)) &&
                (this.polygons.equals(other.polygons));
    }

    public boolean equalsEmptyModel() {
        return (this.vertices.size() == 0) && (this.textureVertices.size() == 0) && (this.normals.size() == 0) && (this.polygons.size() == 0);
    }
}