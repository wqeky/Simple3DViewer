package com.cgvsu.objwriter;

import com.cgvsu.math.*;
import com.cgvsu.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ObjWriter {


    public static void createObjFile(String absoluteFilePath, String fileName) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        absoluteFilePath += fileSeparator + fileName + ".obj";
        File file = new File(absoluteFilePath);
    }

    public static void writeToFile(Model model, File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(writeVertexes(model.getVertices()));
        stringBuilder.append(writeTextureVertexes(model.getTextureVertices()));
        stringBuilder.append(writeNormals(model.getNormals()));
        stringBuilder.append(writePolygons(model.getPolygons()));

        toFile(stringBuilder.toString(), file.getAbsolutePath());
    }

    private static void toFile(String line, String filePath) throws IOException {
        Path path = Path.of(filePath);
        Files.writeString(path, line);
    }

    private static String writeVertexes(final List<Vector3f> v) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < v.size(); i++) {
            final String vx = String.format("%.4f", v.get(i).getX()).replace(',', '.');
            final String vy = String.format("%.4f", v.get(i).getY()).replace(',', '.');
            final String vz = String.format("%.4f", v.get(i).getZ()).replace(',', '.');
            stringBuilder.append("v  ").append(vx).append(" ").append(vy).append(" ").append(vz).append("\n");
        }
        stringBuilder.append("# ").append(v.size()).append(" vertices").append("\n").append("\n");

        return stringBuilder.toString();
    }

    private static String writeTextureVertexes(final List<Vector2f> vt) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < vt.size(); i++) {
            final String vtx = String.format("%.4f", vt.get(i).getX()).replace(',', '.');
            final String vty = String.format("%.4f", vt.get(i).getY()).replace(',', '.');
            stringBuilder.append("vt ").append(vtx).append(" ").append(vty).append(" ").append("0.0000").append("\n");
        }
        stringBuilder.append("# ").append(vt.size()).append(" texture coords").append("\n").append("\n");

        return stringBuilder.toString();
    }

    private static String writeNormals(final List<Vector3f> vn) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < vn.size(); i++) {
            final String vx = String.format("%.4f", vn.get(i).getX()).replace(',', '.');
            final String vy = String.format("%.4f", vn.get(i).getY()).replace(',', '.');
            final String vz = String.format("%.4f", vn.get(i).getZ()).replace(',', '.');
            stringBuilder.append("vn  ").append(vx).append(" ").append(vy).append(" ").append(vz).append("\n");
        }
        stringBuilder.append("# ").append(vn.size()).append(" normals").append("\n").append("\n");

        return stringBuilder.toString();
    }

    private static String writePolygons(final List<Polygon> p) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < p.size(); i++) {
            stringBuilder.append("f ");
            final Polygon pol = p.get(i);

            for (int j = 0; j < pol.getVertexIndices().size(); j++) {
                if (!pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((pol.getVertexIndices().get(j) + 1)).append("/").append((pol.getTextureVertexIndices().get(j) + 1)).append(" ");
                }
                if (pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((pol.getVertexIndices().get(j) + 1)).append(" ");
                }
                if (!pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((pol.getVertexIndices().get(j) + 1)).append("/").append((pol.getTextureVertexIndices().get(j) + 1)).append("/").append((pol.getNormalIndices().get(j) + 1)).append(" ");
                }
                if (pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((pol.getVertexIndices().get(j) + 1)).append("//").append((pol.getNormalIndices().get(j) + 1)).append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("# ").append(p.size()).append(" polygons");

        return stringBuilder.toString();
    }
}