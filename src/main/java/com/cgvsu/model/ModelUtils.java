package com.cgvsu.model;

import com.cgvsu.math.Vector3f;
import com.cgvsu.math.VectorsAction;

import java.util.ArrayList;

import static com.cgvsu.math.VectorsAction.calculateCrossProduct;
import static com.cgvsu.math.VectorsAction.createFromTwoPoints;

public class ModelUtils { // класс преобразования модели (операций над моделью)

    public static void recalculateNormals(Model model) {
        model.normals.clear();
        for (int i = 0; i < model.vertices.size(); i++) {
            model.normals.add(calculateNormalForVertexInModel(model, i));
            //System.out.println(model.normals.get(i).x + " " + model.normals.get(i).y + " " + model.normals.get(i).z + "   "
            //      + i + "/"+model.vertices.size());
        }
    }

    public static void triangulate(Model model){
        boolean flag = false;
        while (!flag) {
            for (int i = 0; i < model.polygons.size(); i++) {
                if (model.polygons.get(i).getVertexIndices().size() > 3) {
                    for (int j = 0; j < model.polygons.get(i).getVertexIndices().size() - 2; j++) {
                        Polygon triangle = new Polygon();
                        ArrayList<Integer> vertexes = new ArrayList<>();
                        Integer p1 = model.polygons.get(i).getVertexIndices().get(j);
                        Integer p2 = model.polygons.get(i).getVertexIndices().get(j + 1);
                        Integer p3 = model.polygons.get(i).getVertexIndices().get(model.polygons.get(i).getVertexIndices().size() - 1);
                        vertexes.add(p1);
                        vertexes.add(p2);
                        vertexes.add(p3);
                        triangle.setVertexIndices(vertexes);
                        model.polygons.add(triangle);
                    }
                    model.polygons.remove(i);
                }
            }
            flag = true;
            for (int i = 0; i < model.polygons.size(); i++) {
                if (model.polygons.get(i).getVertexIndices().size() > 3) flag = false;
            }
        }
    }

    public static Vector3f calculateNormalForPolygon(final Polygon polygon, final Model model){

        ArrayList<Integer> vertexIndices = (ArrayList<Integer>) polygon.getVertexIndices();
        int verticesCount = vertexIndices.size();

        Vector3f vector1 = createFromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(1)));
        Vector3f vector2 = createFromTwoPoints(model.vertices.get(vertexIndices.get(0)), model.vertices.get(vertexIndices.get(verticesCount - 1)));

        return calculateCrossProduct(vector1, vector2);
    }

    public static Vector3f calculateNormalForVertexInModel(final Model model, final int vertexIndex) {
        ArrayList<Vector3f> saved = new ArrayList<>();
        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().contains(vertexIndex)) {
                saved.add(calculateNormalForPolygon(polygon, model));
            }
        }
        Vector3f result = VectorsAction.sumVectors(saved);
        VectorsAction va = new VectorsAction(result.getX(), result.getY(), result.getZ());
        return va.divideScalar(saved.size());
    }
}