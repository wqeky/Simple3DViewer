//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class objwriter {

    public static void createObjFile(String absoluteFilePath, String fileName) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        absoluteFilePath = absoluteFilePath + fileSeparator + fileName + ".obj";
        new File(absoluteFilePath);
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

    private static String writeVertexes(List<Vector3f> v) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < v.size(); ++i) {
            String vx = String.format("%.4f", ((Vector3f)v.get(i)).getX()).replace(',', '.');
            String vy = String.format("%.4f", ((Vector3f)v.get(i)).getY()).replace(',', '.');
            String vz = String.format("%.4f", ((Vector3f)v.get(i)).getZ()).replace(',', '.');
            stringBuilder.append("v  ").append(vx).append(" ").append(vy).append(" ").append(vz).append("\n");
        }

        stringBuilder.append("# ").append(v.size()).append(" vertices").append("\n").append("\n");
        return stringBuilder.toString();
    }

    private static String writeTextureVertexes(List<Vector2f> vt) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < vt.size(); ++i) {
            String vtx = String.format("%.4f", ((Vector2f)vt.get(i)).getX()).replace(',', '.');
            String vty = String.format("%.4f", ((Vector2f)vt.get(i)).getY()).replace(',', '.');
            stringBuilder.append("vt ").append(vtx).append(" ").append(vty).append(" ").append("0.0000").append("\n");
        }

        stringBuilder.append("# ").append(vt.size()).append(" texture coords").append("\n").append("\n");
        return stringBuilder.toString();
    }

    private static String writeNormals(List<Vector3f> vn) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < vn.size(); ++i) {
            String vx = String.format("%.4f", ((Vector3f)vn.get(i)).getX()).replace(',', '.');
            String vy = String.format("%.4f", ((Vector3f)vn.get(i)).getY()).replace(',', '.');
            String vz = String.format("%.4f", ((Vector3f)vn.get(i)).getZ()).replace(',', '.');
            stringBuilder.append("vn  ").append(vx).append(" ").append(vy).append(" ").append(vz).append("\n");
        }

        stringBuilder.append("# ").append(vn.size()).append(" normals").append("\n").append("\n");
        return stringBuilder.toString();
    }

    private static String writePolygons(List<Polygon> p) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < p.size(); ++i) {
            stringBuilder.append("f ");
            Polygon pol = (Polygon)p.get(i);

            for(int j = 0; j < pol.getVertexIndices().size(); ++j) {
                if (!pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((Integer)pol.getVertexIndices().get(j) + 1).append("/").append((Integer)pol.getTextureVertexIndices().get(j) + 1).append(" ");
                }

                if (pol.getTextureVertexIndices().isEmpty() && pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((Integer)pol.getVertexIndices().get(j) + 1).append(" ");
                }

                if (!pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((Integer)pol.getVertexIndices().get(j) + 1).append("/").append((Integer)pol.getTextureVertexIndices().get(j) + 1).append("/").append((Integer)pol.getNormalIndices().get(j) + 1).append(" ");
                }

                if (pol.getTextureVertexIndices().isEmpty() && !pol.getNormalIndices().isEmpty()) {
                    stringBuilder.append((Integer)pol.getVertexIndices().get(j) + 1).append("//").append((Integer)pol.getNormalIndices().get(j) + 1).append(" ");
                }
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("# ").append(p.size()).append(" polygons");
        return stringBuilder.toString();
    }
}
