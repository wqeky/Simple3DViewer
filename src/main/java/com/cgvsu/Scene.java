package com.cgvsu;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.ModelOnScene;
import com.cgvsu.objreader.ObjReader;

import static com.cgvsu.Something.*;
import static com.cgvsu.Something.SAVE_WITH_CHANGES;
import static com.cgvsu.model.ModelOnScene.createMetaModel;
import static com.cgvsu.model.ModelUtils.recalculateNormals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cgvsu.render_scene.Camera;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

enum Something {
    MODEL,
    POLYGON_MESH,
    COLOR_FILLING,
    LIGHTING,
    SAVE_WITH_CHANGES
}

public class Scene {
    final public static float TRANSLATION = 0.5F; // единица перемещения (0.5)
    public static List<Camera> CamerasList; // список камер
    public static Camera currentCamera; // текущая камера
    public static List<ModelOnScene> ModelsOnSceneList; // список моделей
    public static List<Image> TexturesList; // список текстур моделей


    public static void initializeScene() { // инициализация сцены
        CamerasList = new ArrayList<>(); // инициализация списка камер
        currentCamera = new Camera(); // ициниализация текущей камеры
        ModelsOnSceneList = new ArrayList<>(); // инициализация списка моделей
        TexturesList = new ArrayList<>(); // инициализация списка текстур моделей
    }

    public static Camera getSelectedCamera(final int selectedCameraIndex) { // получение камеры по индексу
        Camera currentCamera;

        if (selectedCameraIndex == -1) {
            currentCamera = CamerasList.get(0);
        } else {
            currentCamera = CamerasList.get(selectedCameraIndex);
        }

        return currentCamera;
    }

    public static List<ModelOnScene> getSelectedModelsOnScene(final List<Integer> selectedModelsIndicesList) { // получение моделей по индексам
        List<ModelOnScene> selectedModelsList = new ArrayList<>();
        for (int selectedModelIndex : selectedModelsIndicesList) {
            selectedModelsList.add(ModelsOnSceneList.get(selectedModelIndex));
        }

        return selectedModelsList;
    }

    public static Model readModelFromFile(final Path fileName) throws IOException { // чтение модели из файла
        String fileContent;
        Pattern pattern = Pattern.compile("[а-яё]");
        Matcher matcher = pattern.matcher(fileName.toString().toLowerCase());

        if (matcher.find()) {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(fileName);
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            fileContent = stringBuilder.toString();
            scanner.close();
        } else {
            fileContent = Files.readString(fileName);
        }

        return ObjReader.readModel(fileContent);
    }

    public static String chooseNameForCamera(final String cameraName, final ListView<String> CamerasListView) { // выбор имени для камеры
        String actualCameraName = cameraName;

        int i = 0;
        while (true) {
            if (CamerasListView.getItems().contains(actualCameraName)) {
                actualCameraName = cameraName + " (" + i + ")";
            } else break;
            i++;
        }

        return actualCameraName;
    }

    public static String chooseNameForModel(final Path fileName, final ListView<String> ModelsListView) { // выбор имени для модели
        String modelName = fileName.toString();
        modelName = modelName.substring(modelName.lastIndexOf("\\") + 1);
        String actualModelName = modelName;

        int i = 0;
        while (true) {
            if (ModelsListView.getItems().contains(actualModelName)) {
                actualModelName = modelName + " (" + i + ")";
            } else break;
            i++;
        }

        return actualModelName;
    }

    public static String chooseNameForTexture(final String fileName, final ChoiceBox<String> texturesChoiceBox) { // выбор имени для текстуры
        String textureName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        String actualTextureName = textureName;

        int i = 0;
        while (true) {
            if (texturesChoiceBox.getItems().contains(actualTextureName)) {
                actualTextureName = textureName + " (" + i + ")";
            } else break;
            i++;
        }

        return actualTextureName;
    }

    public static ModelOnScene mergeSelectedModelsOnScene(final List<Integer> selectedModelsNamesList) { // объединение моделей в одну
        List<ModelOnScene> selectedModelsList = new ArrayList<>();

        for (int selectedModelIndex : selectedModelsNamesList) {
            ModelOnScene modelOnScene = ModelsOnSceneList.get(selectedModelIndex);

            if (!modelOnScene.isSaveWithChanges()) {
                // todo сохранение ИСХОДНОЙ МОДЕЛИ

                modelOnScene = modelOnScene.subtract(modelOnScene.getTransition());
            }

            selectedModelsList.add(modelOnScene);
        }

        return createMetaModel(selectedModelsList);
    }

    public static boolean doSelectedModelsHaveSameColor(List<ModelOnScene> selectedModelsOnScene) { // имеют ли выбранные модели одинаковый цвет заливки
        boolean result = true;
        Color color = selectedModelsOnScene.get(0).getColor();

        for (ModelOnScene selectedModelOnScene : selectedModelsOnScene) {
            result = result && (selectedModelOnScene.getColor().equals(color));
        }

        return result;
    }

    public static boolean isDrawModelForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, MODEL);
    }

    public static boolean isDrawPolygonMeshForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, POLYGON_MESH);
    }

    public static boolean isDrawColorFillingForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, COLOR_FILLING);
    }

    public static boolean isDrawLightingForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, LIGHTING);
    }

    public static boolean isSaveWithChangesForSelectedModels(List<ModelOnScene> selectedModelsOnScene) {
        return isSomethingForSelectedModels(selectedModelsOnScene, SAVE_WITH_CHANGES);
    }

    private static boolean isSomethingForSelectedModels(List<ModelOnScene> selectedModelsOnScene, Something something) { // получение Нужной (действительно тонко) информации о моделях
        boolean isDrawModel = true, isDrawPolygonMesh = true, isDrawColorFilling = true,
                isDrawLighting = true, isSaveWithChanges = true;

        for (ModelOnScene selectedModelOnScene : selectedModelsOnScene) {
            isDrawModel = isDrawModel && selectedModelOnScene.isDrawModel();
            isDrawPolygonMesh = isDrawPolygonMesh && selectedModelOnScene.isDrawPolygonMesh();
            isDrawColorFilling = isDrawColorFilling && selectedModelOnScene.isDrawColorFilling();
            isDrawLighting = isDrawLighting && selectedModelOnScene.isDrawLighting();
            isSaveWithChanges = isSaveWithChanges && selectedModelOnScene.isSaveWithChanges();
        }

        switch (something) {
            case MODEL -> {
                return isDrawModel;
            }
            case POLYGON_MESH -> {
                return isDrawPolygonMesh;
            }
            case COLOR_FILLING -> {
                return isDrawColorFilling;
            }
            case LIGHTING -> {
                return isDrawLighting;
            }
            case SAVE_WITH_CHANGES -> {
                return isSaveWithChanges;
            }
            default -> {
                return false;
            }
        }
    }

    public static void tryLoadModelFromFile(File file, ListView<String> ModelsListView) throws IOException { // попытка чтения модели из файла
        Path fileName = Path.of(file.getAbsolutePath());
        ModelOnScene modelOnScene = new ModelOnScene(readModelFromFile(fileName));
        recalculateNormals(modelOnScene);
        String modelName = chooseNameForModel(fileName, ModelsListView);
        ModelsOnSceneList.add(modelOnScene);
        ModelsListView.getItems().add(modelName);
    }

    public static void tryLoadTextureFromFile(File file, ChoiceBox<String> drawTextureChoiceBox) { // попытка чтения текстуры модели из файла
        Path fileName = Path.of(file.getAbsolutePath());
        Image texture = new Image(file.getAbsolutePath());
        TexturesList.add(texture);
        String textureName = chooseNameForTexture(fileName.toString(), drawTextureChoiceBox);
        drawTextureChoiceBox.getItems().add(textureName);
    }

    public static void handleCamera(int selectedCameraIndex, javax.vecmath.Vector3f vector3f) throws Exception { // перемещение камеры на передаваемый вектор
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.movePosition(vector3f);
        }
    }

    public static void handleCameraView(int selectedCameraIndex, javax.vecmath.Vector3f vector3f) throws Exception { // перемещение точки зрения камеры на передаваемый вектор
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.moveTarget(vector3f);
        }
    }

    public static ModelOnScene handleModel(final List<Integer> selectedModelsIndicesList, Vector3f vector3f) throws Exception { // перемещение моделей на передаваемый вектор
        if (selectedModelsIndicesList.size() == 0) {
            throw new Exception();
        } else {
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList);
            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) {
                modelOnScene.movePosition(vector3f);
            }
            if (selectedModelsIndicesList.size() == 1) {
                return selectedModelsOnSceneList.get(0);
            }
            return null;
        }
    }

    public static void tryResetCamera(int selectedCameraIndex) throws Exception { // попытка сброса настроек камеры до стартовых
        if (selectedCameraIndex == -1) {
            throw new Exception();
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            camera.setPosition(new javax.vecmath.Vector3f(0, 0, 100));
            camera.setTarget(new javax.vecmath.Vector3f(0, 0, 0));
        }
    }

    public static ModelOnScene tryResetModel(final List<Integer> selectedModelsIndicesList) throws Exception { // попытка сброса настроек модели до стартовых
        if (selectedModelsIndicesList.size() == 0) {
            throw new Exception();
        } else {
            for (ModelOnScene modelOnScene : getSelectedModelsOnScene(selectedModelsIndicesList)) {
                modelOnScene.applyMovePosition(new Vector3f(0, 0, 0));
                modelOnScene.setTransition(new Vector3f(0, 0, 0));
            }

            ModelOnScene modelOnScene = new ModelOnScene();
            if (selectedModelsIndicesList.size() > 1) {
                modelOnScene.setDrawModel(false);
                modelOnScene.setDrawPolygonMesh(false);
                modelOnScene.setSaveWithChanges(false);
            } else {
                modelOnScene = getSelectedModelsOnScene(selectedModelsIndicesList).get(0);
            }
            return modelOnScene;
        }
    }

    public static void tryDeleteCamera(ListView<String> CamerasListView) throws Exception { // попытка удаления камеры
        int selectedCameraIndex = CamerasListView.getSelectionModel().getSelectedIndex();
        if (selectedCameraIndex == -1) { // если не выбрана камера
            throw new Exception("Please, choose one camera to delete");
        } else if (CamerasListView.getItems().size() == 1) { // если в сцене всего 1 камера
            throw new Exception("Can't delete the only camera");
        } else {
            CamerasListView.getItems().remove(selectedCameraIndex);
            CamerasList.remove(selectedCameraIndex);
            selectedCameraIndex = CamerasListView.getSelectionModel().getSelectedIndex();
            Camera camera = getSelectedCamera(selectedCameraIndex);
            currentCamera = camera;
        }
    }

    public static ModelOnScene tryDeleteModel(ListView<String> ModelsListView) throws Exception { // попытка удаления модели
        List<Integer> selectedModelsIndicesList = new ArrayList<>(ModelsListView.getSelectionModel().getSelectedIndices());
        if (selectedModelsIndicesList.size() == 0) { // если не выбрана ни одна модель
            throw new Exception();
        } else {
            for (int i = selectedModelsIndicesList.size() - 1; i >= 0; i--) {
                int selectedModelsIndex = selectedModelsIndicesList.get(i);
                ModelsListView.getItems().remove(selectedModelsIndex);
                ModelsOnSceneList.remove(selectedModelsIndex);
            }

            selectedModelsIndicesList = new ArrayList<>(ModelsListView.getSelectionModel().getSelectedIndices());
            ModelOnScene modelOnScene = new ModelOnScene();
            if (selectedModelsIndicesList.size() == 0) {
                modelOnScene.setDrawModel(false);
                modelOnScene.setDrawPolygonMesh(false);
                modelOnScene.setSaveWithChanges(false);
            } else {
                modelOnScene = getSelectedModelsOnScene(selectedModelsIndicesList).get(0);
            }
            return modelOnScene;
        }
    }
}