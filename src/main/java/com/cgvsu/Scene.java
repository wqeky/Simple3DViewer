//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.model.ModelOnScene;
import com.cgvsu.model.ModelUtils;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_scene.Camera;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javax.vecmath.Vector3f;

public class Scene {
    public static final float TRANSLATION = 0.5F;
    public static List<Camera> CamerasList;
    public static Camera currentCamera;
    public static List<ModelOnScene> ModelsOnSceneList;
    public static List<Image> TexturesList;

    public Scene() {
    }

    public static void initializeScene() {
        CamerasList = new ArrayList();
        currentCamera = new Camera();
        ModelsOnSceneList = new ArrayList();
        TexturesList = new ArrayList();
    }

    public static Camera getSelectedCamera(int selectedCameraIndex) {
        Camera currentCamera;
        if (selectedCameraIndex == -1) {
            currentCamera = (Camera)CamerasList.get(0);
        } else {
            currentCamera = (Camera)CamerasList.get(selectedCameraIndex);
        }

        return currentCamera;
    }

    public static List<ModelOnScene> getSelectedModelsOnScene(List<Integer> selectedModelsIndicesList) {
        List<ModelOnScene> selectedModelsList = new ArrayList();
        Iterator var2 = selectedModelsIndicesList.iterator();

        while(var2.hasNext()) {
            int selectedModelIndex = (Integer)var2.next();
            selectedModelsList.add((ModelOnScene)ModelsOnSceneList.get(selectedModelIndex));
        }

        return selectedModelsList;
    }

    public static Model readModelFromFile(Path fileName) throws IOException {
        Pattern pattern = Pattern.compile("[а-яё]");
        Matcher matcher = pattern.matcher(fileName.toString().toLowerCase());
        String fileContent;
        if (matcher.find()) {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(fileName);

            while(scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }

            fileContent = stringBuilder.toString();
            scanner.close();
        } else {
            fileContent = Files.readString(fileName);
        }

        return ObjReader.readModel(fileContent);
    }

    public static String chooseNameForCamera(String cameraName, ListView<String> CamerasListView) {
        String actualCameraName = cameraName;

        for(int i = 0; CamerasListView.getItems().contains(actualCameraName); ++i) {
            actualCameraName = cameraName + " (" + i + ")";
        }

        return actualCameraName;
    }

    public static String chooseNameForModel(Path fileName, ListView<String> ModelsListView) {
        String modelName = fileName.toString();
        modelName = modelName.substring(modelName.lastIndexOf("\\") + 1);
        String actualModelName = modelName;

        for(int i = 0; ModelsListView.getItems().contains(actualModelName); ++i) {
            actualModelName = modelName + " (" + i + ")";
        }

        return actualModelName;
    }

    public static String chooseNameForTexture(String fileName, ChoiceBox<String> texturesChoiceBox) {
        String textureName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        String actualTextureName = textureName;

        for(int i = 0; texturesChoiceBox.getItems().contains(actualTextureName); ++i) {
            actualTextureName = textureName + " (" + i + ")";
        }

        return actualTextureName;
    }

    public static ModelOnScene mergeSelectedModelsOnScene(List<Integer> selectedModelsNamesList) {
        List<ModelOnScene> selectedModelsList = new ArrayList();

        ModelOnScene modelOnScene;
        for(Iterator var2 = selectedModelsNamesList.iterator(); var2.hasNext(); selectedModelsList.add(modelOnScene)) {
            int selectedModelIndex = (Integer)var2.next();
            modelOnScene = (ModelOnScene)ModelsOnSceneList.get(selectedModelIndex);
            if (!modelOnScene.isSaveWithChanges()) {
                modelOnScene = modelOnScene.subtract(modelOnScene.getTransition());
            }
        }

        return ModelOnScene.createMetaModel(selectedModelsList);
    }

    public static boolean doSelectedModelsHaveSameColor(List<ModelOnScene> selectedModelsOnScene) {
        boolean result = true;
        Color color = ((ModelOnScene)selectedModelsOnScene.get(0)).getColor();

        ModelOnScene selectedModelOnScene;
        for(Iterator var3 = selectedModelsOnScene.iterator(); var3.hasNext(); result = result && selectedModelOnScene.getColor().equals(color)) {
            selectedModelOnScene = (ModelOnScene)var3.next();
        }

        return result;
    }

    public static boolean isDrawModelForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, Something.MODEL);
    }

    public static boolean isDrawPolygonMeshForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, Something.POLYGON_MESH);
    }

    public static boolean isDrawColorFillingForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, Something.COLOR_FILLING);
    }

    public static boolean isDrawLightingForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, Something.LIGHTING);
    }

    public static boolean isSaveWithChangesForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, Something.SAVE_WITH_CHANGES);
    }

    private static boolean isSomethingForSelectedModels(List<ModelOnScene> selectedModelsOnScene, Something something) {
        boolean isDrawModel = true;
        boolean isDrawPolygonMesh = true;
        boolean isDrawColorFilling = true;
        boolean isDrawLighting = true;
        boolean isSaveWithChanges = true;

        ModelOnScene selectedModelOnScene;
        for(Iterator var7 = selectedModelsOnScene.iterator(); var7.hasNext(); isSaveWithChanges = isSaveWithChanges && selectedModelOnScene.isSaveWithChanges()) {
            selectedModelOnScene = (ModelOnScene)var7.next();
            isDrawModel = isDrawModel && selectedModelOnScene.isDrawModel();
            isDrawPolygonMesh = isDrawPolygonMesh && selectedModelOnScene.isDrawPolygonMesh();
            isDrawColorFilling = isDrawColorFilling && selectedModelOnScene.isDrawColorFilling();
            isDrawLighting = isDrawLighting && selectedModelOnScene.isDrawLighting();
        }

        switch (something) {
            case MODEL:
                return isDrawModel;
            case POLYGON_MESH:
                return isDrawPolygonMesh;
            case COLOR_FILLING:
                return isDrawColorFilling;
            case LIGHTING:
                return isDrawLighting;
            case SAVE_WITH_CHANGES:
                return isSaveWithChanges;
            default:
                return false;
        }
    }

    public static void tryLoadModelFromFile(File file, ListView<String> ModelsListView) throws IOException {
        Path fileName = Path.of(file.getAbsolutePath());
        ModelOnScene modelOnScene = new ModelOnScene(readModelFromFile(fileName));
        ModelUtils.recalculateNormals(modelOnScene);
        String modelName = chooseNameForModel(fileName, ModelsListView);
        ModelsOnSceneList.add(modelOnScene);
        ModelsListView.getItems().add(modelName);
    }

    public static void tryLoadTextureFromFile(File file, ChoiceBox<String> drawTextureChoiceBox) {
        Path fileName = Path.of(file.getAbsolutePath());
        Image texture = new Image(file.getAbsolutePath());
        TexturesList.add(texture);
        String textureName = chooseNameForTexture(fileName.toString(), drawTextureChoiceBox);
        drawTextureChoiceBox.getItems().add(textureName);
    }

    public static void handleCamera(int selectedCameraIndex, Vector3f vector3f) throws Exception {
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.movePosition(vector3f);
        }
    }

    public static void handleCameraView(int selectedCameraIndex, Vector3f vector3f) throws Exception {
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.moveTarget(vector3f);
        }
    }

    public static ModelOnScene handleModel(List<Integer> selectedModelsIndicesList, com.cgvsu.math.Vector3f vector3f) throws Exception {
        if (selectedModelsIndicesList.size() == 0) {
            throw new Exception();
        } else {
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList);
            Iterator var3 = selectedModelsOnSceneList.iterator();

            while(var3.hasNext()) {
                ModelOnScene modelOnScene = (ModelOnScene)var3.next();
                modelOnScene.movePosition(vector3f);
            }

            return selectedModelsIndicesList.size() == 1 ? (ModelOnScene)selectedModelsOnSceneList.get(0) : null;
        }
    }

    public static void tryResetCamera(int selectedCameraIndex) throws Exception {
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.setPosition(new Vector3f(0.0F, 0.0F, 100.0F));
            camera.setTarget(new Vector3f(0.0F, 0.0F, 0.0F));
        }
    }

    public static ModelOnScene tryResetModel(List<Integer> selectedModelsIndicesList) throws Exception {
        if (selectedModelsIndicesList.size() == 0) {
            throw new Exception();
        } else {
            Iterator var1 = getSelectedModelsOnScene(selectedModelsIndicesList).iterator();

            while(var1.hasNext()) {
                ModelOnScene modelOnScene = (ModelOnScene)var1.next();
                modelOnScene.applyMovePosition(new com.cgvsu.math.Vector3f(0.0F, 0.0F, 0.0F));
                modelOnScene.setTransition(new com.cgvsu.math.Vector3f(0.0F, 0.0F, 0.0F));
            }

            ModelOnScene modelOnScene = new ModelOnScene();
            if (selectedModelsIndicesList.size() > 1) {
                modelOnScene.setDrawModel(false);
                modelOnScene.setDrawPolygonMesh(false);
                modelOnScene.setSaveWithChanges(false);
            } else {
                modelOnScene = (ModelOnScene)getSelectedModelsOnScene(selectedModelsIndicesList).get(0);
            }

            return modelOnScene;
        }
    }

    public static void tryDeleteCamera(ListView<String> CamerasListView) throws Exception {
        int selectedCameraIndex = CamerasListView.getSelectionModel().getSelectedIndex();
        if (selectedCameraIndex == -1) {
            throw new Exception("Please, choose one camera to delete");
        } else if (CamerasListView.getItems().size() == 1) {
            throw new Exception("Can't delete the only camera");
        } else {
            CamerasListView.getItems().remove(selectedCameraIndex);
            CamerasList.remove(selectedCameraIndex);
            selectedCameraIndex = CamerasListView.getSelectionModel().getSelectedIndex();
            Camera camera = getSelectedCamera(selectedCameraIndex);
            currentCamera = camera;
        }
    }

    public static ModelOnScene tryDeleteModel(ListView<String> ModelsListView) throws Exception {
        List<Integer> selectedModelsIndicesList = new ArrayList(ModelsListView.getSelectionModel().getSelectedIndices());
        if (selectedModelsIndicesList.size() == 0) {
            throw new Exception();
        } else {
            for(int i = selectedModelsIndicesList.size() - 1; i >= 0; --i) {
                int selectedModelsIndex = (Integer)selectedModelsIndicesList.get(i);
                ModelsListView.getItems().remove(selectedModelsIndex);
                ModelsOnSceneList.remove(selectedModelsIndex);
            }

            selectedModelsIndicesList = new ArrayList(ModelsListView.getSelectionModel().getSelectedIndices());
            ModelOnScene modelOnScene = new ModelOnScene();
            if (selectedModelsIndicesList.size() == 0) {
                modelOnScene.setDrawModel(false);
                modelOnScene.setDrawPolygonMesh(false);
                modelOnScene.setSaveWithChanges(false);
            } else {
                modelOnScene = (ModelOnScene)getSelectedModelsOnScene(selectedModelsIndicesList).get(0);
            }

            return modelOnScene;
        }
    }
}
