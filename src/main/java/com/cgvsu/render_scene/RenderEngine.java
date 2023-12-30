package com.cgvsu.render_scene;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.ModelOnScene;

import static com.cgvsu.rasterization.Rasterization.*;
import static com.cgvsu.render_scene.GraphicConveyor.*;

import java.util.ArrayList;
import java.util.Comparator;

import com.cgvsu.model.ModelUtils;
import com.cgvsu.model.Polygon;
import com.cgvsu.rasterization.GraphicsUtils;
import com.cgvsu.rasterization.MyColor;
import com.cgvsu.rasterization.MyPoint2D;
import com.cgvsu.rasterization.Rasterization;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.vecmath.*;

public class RenderEngine { // исходный класс от Косенко, тут какие-то математические преобразования :)
    // тут один из человеков должен заменить исходные классы на СВОи, вроде как
    // но в целом готов (наверное) и работает

    class Triangle {
        Vector3f v1;
        Vector3f v2;
        Vector3f v3;
        Color color;

        Triangle(Vector3f v1, Vector3f v2, Vector3f v3, Color color) {
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.color = color;
        }
    }

    public static void render(final GraphicsContext graphicsContext,
                              final GraphicsUtils graphicsUtils,
                              final com.cgvsu.render_scene.Camera camera,
                              final ModelOnScene modelOnScene,
                              final int width,
                              final int height) {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        ArrayList<Polygon> sortedPols = sortPolygons(modelOnScene, camera);

        final int nPolygons = sortedPols.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = sortedPols.get(polygonInd).getVertexIndices().size();
            ArrayList<Integer> normalIndicesArr = (ArrayList<Integer>) sortedPols.get(polygonInd).getNormalIndices();
            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = modelOnScene.getVertices().get(sortedPols.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.getX(), vertex.getY(), vertex.getZ());

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            if (modelOnScene.isDrawTexture() && (modelOnScene.getTexture() != null)) {
                javafx.scene.image.Image texture = modelOnScene.getTexture();
                for (int i = 0; i < nVerticesInPolygon - 2; i++) {
                    MyPoint2D f = new MyPoint2D(resultPoints.get(i).x, resultPoints.get(i).y);
                    MyPoint2D s = new MyPoint2D(resultPoints.get(i + 1).x, resultPoints.get(i + 1).y);
                    MyPoint2D t = new MyPoint2D(resultPoints.get(resultPoints.size() - 1).x, resultPoints.get(resultPoints.size() - 1).y);
                    double ftx1 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(i)).getX();
                    double fty1 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(i)).getY();
                    double ftx2 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(i + 1)).getX();
                    double fty2 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(i + 1)).getY();
                    double ftx3 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(resultPoints.size() - 1)).getX();
                    double fty3 = modelOnScene.getTextureVertices().get(sortedPols.get(polygonInd).getTextureVertexIndices().get(resultPoints.size() - 1)).getY();
                    MyPoint2D ft1 = new MyPoint2D(ftx1, fty1);
                    MyPoint2D ft2 = new MyPoint2D(ftx2, fty2);
                    MyPoint2D ft3 = new MyPoint2D(ftx3, fty3);
                    //MyColor mColor2 = new MyColor(0.3, 0.3,0.3);
                    fillTexture(graphicsUtils, f, s, t, ft1, ft2, ft3, texture);
                }

            } else if (modelOnScene.isDrawColorFilling()) {
                for (int i = 0; i < nVerticesInPolygon - 2; i++) {
                    MyPoint2D f = new MyPoint2D(resultPoints.get(i).x, resultPoints.get(i).y);
                    MyPoint2D s = new MyPoint2D(resultPoints.get(i + 1).x, resultPoints.get(i + 1).y);
                    MyPoint2D t = new MyPoint2D(resultPoints.get(resultPoints.size() - 1).x, resultPoints.get(resultPoints.size() - 1).y);
                    Color mColor = modelOnScene.getColor();
                    //MyColor mColor2 = new MyColor(0.3, 0.3,0.3);
                    MyColor color = new MyColor(mColor.getRed(), mColor.getGreen(), mColor.getBlue());
                    fillTriangle(graphicsUtils, f, s, t, color, color, color);
                }
            }

            if (modelOnScene.isDrawLighting()) {
                /*
                for (int i = 0; i < nVerticesInPolygon - 2; i++) {
                    MyPoint2D f = new MyPoint2D(resultPoints.get(i).x, resultPoints.get(i).y);
                    MyPoint2D s = new MyPoint2D(resultPoints.get(i + 1).x, resultPoints.get(i + 1).y);
                    MyPoint2D t = new MyPoint2D(resultPoints.get(resultPoints.size() - 1).x, resultPoints.get(resultPoints.size() - 1).y);
                    final Vector3f n1 = normals.get(i);
                    final Vector3f n2 = normals.get(i+1);
                    final Vector3f n3 = normals.get(nVerticesInPolygon-1);

                    Vector3f v = new Vector3f(n1.getX()+n2.getX()+ n3.getX(), n1.getY()+n2.getY()+ n3.getY(),
                            n1.getZ()+n2.getZ()+ n3.getZ());
                    Vector3f pol = findVector(modelOnScene, camera, sortedPols.get(polygonInd));
                    double sh = findAngle(v, pol);
                    Color dColor = modelOnScene.getColor();
                    MyColor color = new MyColor(dColor.getRed() * sh, dColor.getGreen() * sh, dColor.getBlue() * sh);
                    fillTriangle(graphicsUtils, f, s, t, color, color, color);
                }
                */
            }


            if (modelOnScene.isDrawPolygonMesh()) {
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    graphicsContext.strokeLine(
                            resultPoints.get(vertexInPolygonInd - 1).x,
                            resultPoints.get(vertexInPolygonInd - 1).y,
                            resultPoints.get(vertexInPolygonInd).x,
                            resultPoints.get(vertexInPolygonInd).y);
                }


                if (nVerticesInPolygon > 0) {
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).x,
                            resultPoints.get(nVerticesInPolygon - 1).y,
                            resultPoints.get(0).x,
                            resultPoints.get(0).y);
                }
            }
        }
    }


    public static ArrayList<Polygon> sortPolygons(ModelOnScene modelOnScene, com.cgvsu.render_scene.Camera camera) {
        ArrayList<Polygon> sortedPols = new ArrayList<>(modelOnScene.polygons);
        Comparator<Polygon> c = new Comparator<Polygon>() {
            @Override
            public int compare(Polygon o1, Polygon o2) {
                Point3f center1 = new Point3f();
                Point3f center2 = new Point3f();
                center1.x = (modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getX() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getX() +
                        modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getX());
                center1.y = (modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getY() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getY() +
                        modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getY());
                center1.z = (modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getZ() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getZ() +
                        modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getZ());
                center2.x = (modelOnScene.getVertices().get(o2.getVertexIndices().get(0)).getX() + modelOnScene.getVertices().get(o2.getVertexIndices().get(1)).getX() +
                        modelOnScene.getVertices().get(o2.getVertexIndices().get(2)).getX());
                center2.y = (modelOnScene.getVertices().get(o2.getVertexIndices().get(0)).getY() + modelOnScene.getVertices().get(o2.getVertexIndices().get(1)).getY() +
                        modelOnScene.getVertices().get(o2.getVertexIndices().get(2)).getY());
                center2.z = (modelOnScene.getVertices().get(o2.getVertexIndices().get(0)).getZ() + modelOnScene.getVertices().get(o2.getVertexIndices().get(1)).getZ() +
                        modelOnScene.getVertices().get(o2.getVertexIndices().get(2)).getZ());


                int r1 = (int) Math.sqrt((camera.getPosition().x - center1.x) * (camera.getPosition().x - center1.x) +
                        (camera.getPosition().y - center1.y) * (camera.getPosition().y - center1.y) +
                        (camera.getPosition().z - center1.z) * (camera.getPosition().z - center1.z));
                int r2 = (int) Math.sqrt((camera.getPosition().x - center2.x) * (camera.getPosition().x - center2.x) +
                        (camera.getPosition().y - center2.y) * (camera.getPosition().y - center2.y) +
                        (camera.getPosition().z - center2.z) * (camera.getPosition().z - center2.z));
                return r2 - r1;
            }
        };
        sortedPols.sort(c);
        return sortedPols;
    }

    private static Vector3f findVector(ModelOnScene modelOnScene, com.cgvsu.render_scene.Camera camera, Polygon o1) {
        Vector3f center1 = new Vector3f(0, 0, 0);
        center1.setX((modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getX() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getX() +
                modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getX()));
        center1.setY(modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getY() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getY() +
                modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getY());
        center1.setZ(modelOnScene.getVertices().get(o1.getVertexIndices().get(0)).getZ() + modelOnScene.getVertices().get(o1.getVertexIndices().get(1)).getZ() +
                modelOnScene.getVertices().get(o1.getVertexIndices().get(2)).getZ());

        Vector3f vector = new Vector3f(0, 0, 0);
        vector.setX(camera.getPosition().x - center1.getX());
        vector.setY(camera.getPosition().y - center1.getY());
        vector.setZ(camera.getPosition().z - center1.getZ());

        return vector;
    }

    private static double findAngle(Vector3f v1, Vector3f v2) {
        double result;
        double up = v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
        double down = Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY() + v1.getZ() * v1.getZ()) *
                Math.sqrt(v2.getX() * v2.getX() + v2.getY() * v2.getY() + v2.getZ() * v2.getZ());
        result = up / down;
        return result;
    }
}

/*
                    MyPoint2D f = new MyPoint2D(resultPoints.get(i).x, resultPoints.get(i).y);
                    MyPoint2D s = new MyPoint2D(resultPoints.get(i + 1).x, resultPoints.get(i + 1).y);
                    MyPoint2D t = new MyPoint2D(resultPoints.get(resultPoints.size() - 1).x, resultPoints.get(resultPoints.size() - 1).y);
                    Color mColor = modelOnScene.getColor();

                    Vector3f v1 = ModelUtils.calculateNormalForVertexInModel( modelOnScene, sortedPols.get(nPolygons-1).getVertexIndices().get(i));
                    Vector3f v2 = ModelUtils.calculateNormalForVertexInModel( modelOnScene, sortedPols.get(nPolygons-1).getVertexIndices().get(i+1));
                    Vector3f v3 = ModelUtils.calculateNormalForVertexInModel( modelOnScene, sortedPols.get(nPolygons-1).getVertexIndices().get(nVerticesInPolygon - 1));

                    Vector3f pp1 = ModelUtils.calculateNormalForPolygon(sortedPols.get(nPolygons-1), modelOnScene);
                    Point3f vp1 = new Point3f(pp1.getX(), pp1.getY(), pp1.getZ());

                    Point3f cvector = findVector(modelOnScene, camera, sortedPols.get(polygonInd));
                    double angleCos1 = findAngle(cvector, vp1);
                    System.out.println(angleCos1);
                    MyColor color = new MyColor(mColor.getRed(), mColor.getGreen(), mColor.getBlue());
                    fillTriangle(graphicsUtils, f, s, t, getShade(color, angleCos1), getShade(color, angleCos1), getShade(color, angleCos1));
 */