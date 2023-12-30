package com.cgvsu.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon { // класс полигона модели
    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;


    public Polygon() {
        this.vertexIndices = new ArrayList<>();
        this.textureVertexIndices = new ArrayList<>();
        this.normalIndices = new ArrayList<>();
    }

    public void addVertexIndex(final int vertexIndex) {
        this.vertexIndices.add(vertexIndex);
    }

    public void addTextureVertexIndex(final int textureVertexIndex) {
        this.textureVertexIndices.add(textureVertexIndex);
    }

    public void addNormalIndex(final int normalIndex) {
        this.normalIndices.add(normalIndex);
    }

    public void setVertexIndices(final List<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(final List<Integer> textureVertexIndices) {
        assert textureVertexIndices.size() >= 3;
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(final List<Integer> normalIndices) {
        assert normalIndices.size() >= 3;
        this.normalIndices = normalIndices;
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    public boolean equals(final Polygon other) {
        return (this.vertexIndices.equals(other.vertexIndices)) &&
                (this.textureVertexIndices.equals(other.textureVertexIndices)) &&
                (this.normalIndices.equals(other.normalIndices));
    }

    public static List<Polygon> changePolygonsNumeration(List<Polygon> polygonList, int amountOfVertices, int amountOfTextureVertices, int amountOfNormals) {
        List<Polygon> polygons = new ArrayList<>();

        for (Polygon oldPolygon : polygonList) {
            Polygon newPolygon = new Polygon();
            newPolygon.changePolygonNumeration(oldPolygon, amountOfVertices, amountOfTextureVertices, amountOfNormals);
            polygons.add(newPolygon);
        }

        return polygons;
    }

    private void changePolygonNumeration(Polygon oldPolygon, int amountOfVertices, int amountOfTextureVertices, int amountOfNormals) {
        this.vertexIndices = new ArrayList<>(oldPolygon.vertexIndices);
        this.textureVertexIndices = new ArrayList<>(oldPolygon.textureVertexIndices);
        this.normalIndices = new ArrayList<>(oldPolygon.normalIndices);

        this.vertexIndices.replaceAll(integer -> integer + amountOfVertices);
        this.textureVertexIndices.replaceAll(integer -> integer + amountOfTextureVertices);
        this.normalIndices.replaceAll(integer -> integer + amountOfNormals);
    }
}