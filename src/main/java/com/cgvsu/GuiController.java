package com.cgvsu;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.ModelOnScene;
import com.cgvsu.rasterization.DrawUtilsJavaFX;
import com.cgvsu.rasterization.GraphicsUtils;
import com.cgvsu.render_scene.Camera;
import com.cgvsu.model.Model;
import com.cgvsu.render_scene.RenderEngine;

import static com.cgvsu.Scene.*;
import static com.cgvsu.objwriter.ObjWriter.*;

import static javafx.scene.paint.Color.*;

import java.io.IOException;
import java.io.File;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GuiController {
    @FXML
    AnchorPane anchorPane; // форма окна
    @FXML
    private Canvas canvas; // поле для отрисовки сцены (холст сцены)
    private Timeline timeline; // инструмент для периодического обновления сцены
    @FXML
    private ColorPicker sceneColorPicker; // выбор цвета заднего фона
    @FXML
    private ListView<String> CamerasListView; // список имён камер
    @FXML
    private ListView<String> ModelsListView; // список имён моделей
    @FXML
    private Spinner<Double> editModelTransitionXSpinner; // редактор перемещения модели по оси X
    @FXML
    private Spinner<Double> editModelTransitionYSpinner; // редактор перемещения модели по оси Y
    @FXML
    private Spinner<Double> editModelTransitionZSpinner; // редактор перемещения модели по оси Z
    @FXML
    private Spinner<Double> editModelRotationXSpinner; // редактор поворота модели по оси X
    @FXML
    private Spinner<Double> editModelRotationYSpinner; // редактор поворота модели по оси Y
    @FXML
    private Spinner<Double> editModelRotationZSpinner; // редактор поворота модели по оси Z
    @FXML
    private Spinner<Double> editModelScalingXSpinner; // редактор масштабирования модели по оси X
    @FXML
    private Spinner<Double> editModelScalingYSpinner; // редактор масштабирования модели по оси Y
    @FXML
    private Spinner<Double> editModelScalingZSpinner; // редактор масштабирования модели по оси Z
    private SpinnerValueFactory<Double> editModelTransitionXSpinnerValue; // значение перемещения модели по оси X
    private SpinnerValueFactory<Double> editModelTransitionYSpinnerValue; // значение перемещения модели по оси Y
    private SpinnerValueFactory<Double> editModelTransitionZSpinnerValue; // значение перемещения модели по оси Z
    private SpinnerValueFactory<Double> editModelRotationXSpinnerValue; // значение поворота модели по оси X
    private SpinnerValueFactory<Double> editModelRotationYSpinnerValue; // значение поворота модели по оси Y
    private SpinnerValueFactory<Double> editModelRotationZSpinnerValue; // значение поворота модели по оси Z
    private SpinnerValueFactory<Double> editModelScalingXSpinnerValue; // значение масштабирования модели по оси X
    private SpinnerValueFactory<Double> editModelScalingYSpinnerValue; // значение масштабирования модели по оси Y
    private SpinnerValueFactory<Double> editModelScalingZSpinnerValue; // значение масштабирования модели по оси Z
    @FXML
    private CheckBox drawModelCheckBox; // галочка "отрисовывать модель"
    @FXML
    private CheckBox drawPolygonMeshCheckBox; // галочка "отрисовывать полигональную сетку модели"
    @FXML
    private CheckBox drawColorFillingCheckBox; // галочка "отрисовывать заливку модели  цветом"
    @FXML
    private CheckBox drawLightingCheckBox; // галочка "отрисовывать освещение сцены"
    @FXML
    private CheckBox drawTextureCheckBox; // галочка "отрисовывать текстуру модели"
    @FXML
    private CheckBox saveWithChangesCheckBox; // галочка "сохранить модель с изменениями"
    @FXML
    private ChoiceBox<String> drawTextureChoiceBox; // выпадающий список текстур для модели
    @FXML
    private ColorPicker drawColorFillingColorPicker; // выбор цвета заливки модели
    @FXML
    private Spinner<Double> editCameraTransitionXSpinner; // редактор перемещения камеры по оси X
    @FXML
    private Spinner<Double> editCameraTransitionYSpinner; // редактор перемещения камеры по оси Y
    @FXML
    private Spinner<Double> editCameraTransitionZSpinner; // редактор перемещения камеры по оси Z
    @FXML
    private Spinner<Double> editCameraViewTransitionXSpinner; // редактор перемещения точки зрения камеры по оси X
    @FXML
    private Spinner<Double> editCameraViewTransitionYSpinner; // редактор перемещения точки зрения камеры по оси Y
    @FXML
    private Spinner<Double> editCameraViewTransitionZSpinner; // редактор перемещения точки зрения камеры по оси Z
    private SpinnerValueFactory<Double> editCameraTransitionXSpinnerValue; // значение перемещения камеры по оси X
    private SpinnerValueFactory<Double> editCameraTransitionYSpinnerValue; // значение перемещения камеры по оси Y
    private SpinnerValueFactory<Double> editCameraTransitionZSpinnerValue; // значение перемещения камеры по оси Z
    private SpinnerValueFactory<Double> editCameraViewTransitionXSpinnerValue; // значение перемещения точки зрения камеры по оси X
    private SpinnerValueFactory<Double> editCameraViewTransitionYSpinnerValue; // значение перемещения точки зрения камеры по оси Y
    private SpinnerValueFactory<Double> editCameraViewTransitionZSpinnerValue; // значение перемещения точки зрения камеры по оси Z
    @FXML
    private Spinner<Double> createCameraTransitionXSpinner; // редактор перемещения создаваемой камеры по оси X
    @FXML
    private Spinner<Double> createCameraTransitionYSpinner; // редактор перемещения создаваемой камеры по оси Y
    @FXML
    private Spinner<Double> createCameraTransitionZSpinner; // редактор перемещения создаваемой камеры по оси Z
    @FXML
    private Spinner<Double> createCameraViewTransitionXSpinner; // редактор перемещения точки зрения создаваемой камеры по оси X
    @FXML
    private Spinner<Double> createCameraViewTransitionYSpinner; // редактор перемещения точки зрения создаваемой камеры по оси Y
    @FXML
    private Spinner<Double> createCameraViewTransitionZSpinner; // редактор перемещения точки зрения создаваемой камеры по оси Z
    private SpinnerValueFactory<Double> createCameraTransitionXSpinnerValue; // значение перемещения создаваемой камеры по оси X
    private SpinnerValueFactory<Double> createCameraTransitionYSpinnerValue; // значение перемещения создаваемой камеры по оси Y
    private SpinnerValueFactory<Double> createCameraTransitionZSpinnerValue; // значение перемещения создаваемой камеры по оси Z
    private SpinnerValueFactory<Double> createCameraViewTransitionXSpinnerValue; // значение перемещения точки зрения создаваемой камеры по оси X
    private SpinnerValueFactory<Double> createCameraViewTransitionYSpinnerValue; // значение перемещения точки зрения создаваемой камеры по оси Y
    private SpinnerValueFactory<Double> createCameraViewTransitionZSpinnerValue; // значение перемещения точки зрения создаваемой камеры по оси Z
    @FXML
    private TextField cameraNameTextField; // поле для ввода имени создаваемой камеры

    @FXML
    private void initialize() {
        initializeScene(); // инициализация сцены
        initializeModelTitledPane(); // инициализация выпалающей формы редактирования модели
        initializeCameraTitledPane(); // инициализация выпалающей формы редактирования камеры
        initializeCreateCameraTitledPane(); // инициализация выпалающей формы создания камеры

        CamerasListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // можно выбрать только 1 камеру за раз
        ModelsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);// можно выбрать сколько угодно моделей за раз

        // создание текущей камеры
        Camera camera = new Camera(new javax.vecmath.Vector3f(0, 0, 100), new javax.vecmath.Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100);
        CamerasList.add(camera); // добавление камеры в список камер
        CamerasListView.getItems().add("camera"); // добавление имени камеры в список имён камер
        currentCamera = CamerasList.get(0); // выбор текущей камеры
        setActualEditCameraTitledPane(new Camera()); // установка актуальных значений выбранной камеры в форме редактирования камеры

        // создание слушателя -> при выборе имени камеры из списка имён камер будет выбираться соответствующая камера из списка камер
        CamerasListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentCamera = getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex());
                if (CamerasListView.getSelectionModel().getSelectedIndex() != -1) { // если выбрана камера (а не ничего)
                    setActualEditCameraTitledPane(currentCamera); // установка актуальных значений выбранной камеры в форме редактирования камеры
                } else {
                    setActualEditCameraTitledPane(new Camera()); // установка пустых значений в форме редактирования камеры
                }
            }
        });

        // создание слушателя -> при выборе имени (имён) модели из списка имён моделей будет выбираться соответствующая модель (модели) из списка камер
        ModelsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                List<ModelOnScene> selectedModelsOnScene = getSelectedModelsOnScene(ModelsListView.getSelectionModel().getSelectedIndices());

                if (selectedModelsOnScene.size() == 0) { // если выбрано ничего (а не модель/модели)
                    setActualEditModelTitledPane(new ModelOnScene()); // установка пустых значений в форме редактирования модели
                } else if (selectedModelsOnScene.size() == 1) { // если выбрана 1 модель
                    ModelOnScene modelOnScene = selectedModelsOnScene.get(0);
                    setActualEditModelTitledPane(modelOnScene); // установка актуальных значений выбранной модели в форме редактирования модели
                } else { // если выбрано несколько моделей
                    setActualEditModelTitledPane(new ModelOnScene()); // установка пустых значений в форме редактирования модели
                    boolean isDrawModel = true, isDrawPolygonMesh = true, isDrawColorFilling = true,
                            isDrawLighting = true, isSaveWithChanges = true;

                    // если не у всех моделей выбрана соответствующая галочка - она будет не выбрана для всех моделей
                    for (ModelOnScene selectedModelOnScene : selectedModelsOnScene) {
                        isDrawModel = isDrawModel && selectedModelOnScene.isDrawModel();
                        isDrawPolygonMesh = isDrawPolygonMesh && selectedModelOnScene.isDrawPolygonMesh();
                        isDrawColorFilling = isDrawColorFilling && selectedModelOnScene.isDrawColorFilling();
                        isDrawLighting = isDrawLighting && selectedModelOnScene.isDrawLighting();
                        isSaveWithChanges = isSaveWithChanges && selectedModelOnScene.isSaveWithChanges();
                    }
                    drawModelCheckBox.setSelected(isDrawModel);
                    drawPolygonMeshCheckBox.setSelected(isDrawPolygonMesh);
                    drawColorFillingCheckBox.setSelected(isDrawColorFilling);
                    drawLightingCheckBox.setSelected(isDrawLighting);
                    saveWithChangesCheckBox.setSelected(isSaveWithChanges);

                    if (doSelectedModelsHaveSameColor(selectedModelsOnScene)) { // если у всех моделей выбран одинаковый цвет для заливки
                        drawColorFillingColorPicker.setValue(selectedModelsOnScene.get(0).getColor());
                    }
                }
            }
        });

        // создание слушателей -> при изменении размера окна будет изменяться и размер холста сцены
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        // инструмент обновления холста сцены будет работать бесконечно
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        // каждыет 16 миллисекунд будет обновляться холст сцены
        KeyFrame frame = new KeyFrame(Duration.millis(16), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height); // очистка холста сцены
            currentCamera.setAspectRatio((float) (width / height));

            // отрисовка всех моделей сцены на холсте сцены
            GraphicsUtils graphicsUtils = new DrawUtilsJavaFX(canvas);
            for (ModelOnScene modelOnScene : ModelsOnSceneList) {
                if (modelOnScene.isDrawModel()) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), graphicsUtils, currentCamera, modelOnScene, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play(); // запуск инструмента обновления холста сцены
    }

    @FXML
    private void loadModelFromFile() { // загрузка модели (.obj) из файла
        // диалоговое окно с выбором файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) { // если выбран файл (а не ничего)
            try {
                tryLoadModelFromFile(file, ModelsListView); // загрузка модели из файла
            } catch (Exception exception) {
                throwErrorMessageBox("Error", exception.getMessage() + "\n" + "Please, choose correct .obj file");
            }
        }
    }

    @FXML
    private void loadTextureFromFile() { // загрузка текстуры (.jpg) из файла
        // диалоговое окно с выбором файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texture (*.jpg)", "*.jpg"));
        fileChooser.setTitle("Load Texture");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) { // если выбран файл (а не ничего)
            try {
                tryLoadTextureFromFile(file, drawTextureChoiceBox); // загрузка текстуры из файла
            } catch (Exception exception) {
                throwErrorMessageBox("Error", exception.getMessage() + "\n" + "Please, choose correct .jpg file");
            }
        }
    }

    @FXML
    private void saveModelToFile() throws IOException { // сохранение модели (моделей) в файл (.obj)
        // диалоговое окно с выбором файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) { // если выбрано ничего (а не файл)
            return;
        }
        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // индексы выбранных моделей
        if (selectedModelsIndicesList.size() == 0) { // если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to save");
        } else {
            Model model = mergeSelectedModelsOnScene(selectedModelsIndicesList); // создание объединённой модели из моделей
            writeToFile(model, file); // сохранение созданной модели в файл
        }
    }

    @FXML
    private void changeBackgroundColor() { // смена цвета заднего фона
        Color color = sceneColorPicker.getValue();
        anchorPane.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @FXML
    private void handleCameraForward() { // перемещение камеры -Z
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, 0, -TRANSLATION));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraBackward() { // перемещение камеры Z
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, 0, TRANSLATION));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraLeft() { // перемещение камеры X
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(TRANSLATION, 0, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraRight() { // перемещение камеры -X
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(-TRANSLATION, 0, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraUp() { // перемещение камеры Y
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, TRANSLATION, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraDown() { // перемещение камеры -Y
        try {
            handleCamera(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, -TRANSLATION, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewForward() { // перемещение точки зрения камеры -Z
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, 0, -TRANSLATION));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewBackward() { // перемещение точки зрения камеры Z
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, 0, TRANSLATION));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewLeft() { // перемещение точки зрения камеры X
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(TRANSLATION, 0, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewRight() { // перемещение точки зрения камеры -X
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(-TRANSLATION, 0, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewUp() { // перемещение точки зрения камеры Y
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, TRANSLATION, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleCameraViewDown() { // перемещение точки зрения камеры -Y
        try {
            handleCameraView(CamerasListView.getSelectionModel().getSelectedIndex(), new javax.vecmath.Vector3f(0, -TRANSLATION, 0));
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to manipulate");
        }
    }

    @FXML
    private void handleModelForward() { // перемещение модели -Z
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(0, 0, -TRANSLATION));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void handleModelBackward() { // перемещение модели Z
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(0, 0, TRANSLATION));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void handleModelLeft() { // перемещение модели X
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(TRANSLATION, 0, 0));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void handleModelRight() { // перемещение модели -X
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(-TRANSLATION, 0, 0));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void handleModelUp() { // перемещение модели Y
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(0, TRANSLATION, 0));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void handleModelDown() { // перемещение модели -Y
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ЕСЛИ ТЫ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo тогда измени эту функцию по шаблону из функций поворота и масштабирования
        // todo да, сейчас эта функция выглядит не по-шаблону, а всё потому, что я вынес всю логику из неё в другую функцию
        // todo ибо Косенко сказал, так правильнее и опрятнее
        // todo но ты не вздумай делать так же, у тебя и так мало времени + в процессе разделения этой функции ты можешь накосячить
        // todo и на зачёте всплывёт неприятный неожиданный баг

        // todo !!! ЕСЛИ ТЫ НЕ СДЕЛАЛ ПЕРЕМЕЩЕНИЕ !!!
        // todo ты лох

        try {
            ModelOnScene modelOnScene = handleModel(ModelsListView.getSelectionModel().getSelectedIndices(), new Vector3f(0, -TRANSLATION, 0));
            if (modelOnScene != null) setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate");
        }
    }

    @FXML
    private void rotateModelForward() { // поворот модели вокруг оси Z по часовой стрелке
        // старые сообщения моему коллеге (он так и не сделал перемещение, пришлось самому)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ Z ПО ЧАСОВОЙ СТРЕЛКЕ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void rotateModelBackward() { // поворот модели вокруг оси Z против часовой стрелки
        // старые сообщения моему коллеге (он так и не сделал поворот)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ Z ПРОТИВ ЧАСОВОЙ СТРЕЛКИ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void rotateModelLeft() { // поворот модели вокруг оси X против часовой стрелки
        // старые сообщения моему коллеге (он так и не сделал поворот)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ X ПРОТИВ ЧАСОВОЙ СТРЕЛКИ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void rotateModelRight() { // поворот модели вокруг оси X по часовой стрелке
        // старые сообщения моему коллеге (он так и не сделал поворот)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ X ПО ЧАСОВОЙ СТРЕЛКЕ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void rotateModelUp() { // поворот модели вокруг оси Y по часовой стрелке
        // старые сообщения моему коллеге (он так и не сделал поворот)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ Y ПО ЧАСОВОЙ СТРЕЛКЕ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void rotateModelDown() { // поворот модели вокруг оси Y против часовой стрелки
        // старые сообщения моему коллеге (он так и не сделал поворот)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! ПОВОРОТ МОДЕЛИ ВОКРУГ ОСИ Y ПРОТИВ ЧАСОВОЙ СТРЕЛКИ !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ ПОВОРОТА (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelForward() { // увеличение модели вдоль оси Z
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ Z (УВЕЛИЧЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelBackward() { // уменьшение модели вдоль оси Z
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ Z (УМЕНЬШЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelLeft() { // уменьшение модели вдоль оси X
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ X (УМЕНЬШЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelRight() { // увеличение модели вдоль оси X
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ X (УВЕЛИЧЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelUp() { // увеличение модели вдоль оси Y
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ Y (УВЕЛИЧЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void scaleModelDown() { // уменьшение модели вдоль оси Y
        // старые сообщения моему коллеге (он так и не сделал масштабирование)
        // todo !!! ВАЖНО !!!
        // todo я уже привязал эту функцию к клавишам и форме в интерфейсе
        // todo поэтому от тебя требуется только следовать моим инструкциям ниже

        List<Integer> selectedModelsIndicesList = ModelsListView.getSelectionModel().getSelectedIndices(); // todo список индексов выбранных моделей

        if (selectedModelsIndicesList.size() == 0) { // todo если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to manipulate"); // todo окно с ошибкой
            setActualEditModelTitledPane(new ModelOnScene()); // todo обнуление информации в форме

        } else { // todo если выбрана хотя бы одна модель
            List<ModelOnScene> selectedModelsOnSceneList = getSelectedModelsOnScene(selectedModelsIndicesList); // todo список выбранных моделей

            for (ModelOnScene modelOnScene : selectedModelsOnSceneList) { // todo для каждой модели из списка выбранных моделей
                // todo  !!! МАСШТАБИРОВАНИЕ МОДЕЛИ ВДОЛЬ ОСИ Y (УМЕНЬШЕНИЕ) !!!
                // todo  !!! ЖЕЛАТЕЛЬНО ПЕРЕДАВАТЬ НЕБОЛЬШОЕ ЗНАЧЕНИЕ МАСШТАБИРОВАНИЯ (ПЕРЕМЕННАЯ TRANSITION ИЗ КЛАССА SCENE НАПРИМЕР) !!!
            }

            if (selectedModelsIndicesList.size() == 1) { // todo если была выбрана одна модель
                setActualEditModelTitledPane(selectedModelsOnSceneList.get(0)); // todo вывод актуальной информации о выбранной модели в форму
            }
        }
    }

    @FXML
    private void resetCamera() { // сброс настроек камеры до стартовых
        try {
            tryResetCamera(CamerasListView.getSelectionModel().getSelectedIndex());
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose one camera to reset");
        }
    }

    @FXML
    private void resetModel() {  // сброс настроек модели (моделей) до стартовых
        try {
            ModelOnScene modelOnScene = tryResetModel(ModelsListView.getSelectionModel().getSelectedIndices());
            setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to reset");
        }
    }

    @FXML
    private void deleteCamera() { // удаление камеры
        try {
            tryDeleteCamera(CamerasListView);
            setActualEditCameraTitledPane(getSelectedCamera(CamerasListView.getSelectionModel().getSelectedIndex()));
        } catch (Exception exception) {
            throwErrorMessageBox("Error", exception.getMessage());
        }
    }

    @FXML
    private void deleteModel() { // удаление модели
        try {
            ModelOnScene modelOnScene = tryDeleteModel(ModelsListView);
            setActualEditModelTitledPane(modelOnScene);
        } catch (Exception exception) {
            throwErrorMessageBox("Error", "Please, choose at least one model to delete");
        }
    }

    @FXML
    private void applyCameraChanges() { // применение настроек камеры
        int selectedCameraIndex = CamerasListView.getSelectionModel().getSelectedIndex();
        if (selectedCameraIndex == -1) {
            throwErrorMessageBox("Error", "Please, choose one camera to apply changes");
            setActualEditCameraTitledPane(new Camera());
        } else {
            Camera camera = getSelectedCamera(selectedCameraIndex);
            double X = (-1) * editCameraTransitionXSpinner.getValue();
            double Y = editCameraTransitionYSpinner.getValue();
            double Z = (-1) * editCameraTransitionZSpinner.getValue();
            camera.setPosition(new javax.vecmath.Vector3f((float) X, (float) Y, (float) Z));
            X = (-1) * editCameraViewTransitionXSpinner.getValue();
            Y = editCameraViewTransitionYSpinner.getValue();
            Z = (-1) * editCameraViewTransitionZSpinner.getValue();
            camera.setTarget(new javax.vecmath.Vector3f((float) X, (float) Y, (float) Z));
        }
    }

    @FXML
    private void createCamera() { // применение настроек камеры
        Camera camera = new Camera(new javax.vecmath.Vector3f(0, 0, 0), new javax.vecmath.Vector3f(0, 0, 0), 1.0F, 1, 0.01F, 100);

        double X = (-1) * createCameraTransitionXSpinner.getValue();
        double Y = createCameraTransitionYSpinner.getValue();
        double Z = (-1) * createCameraTransitionZSpinner.getValue();
        camera.setPosition(new javax.vecmath.Vector3f((float) X, (float) Y, (float) Z));
        X = (-1) * createCameraViewTransitionXSpinner.getValue();
        Y = createCameraViewTransitionYSpinner.getValue();
        Z = (-1) * createCameraViewTransitionZSpinner.getValue();
        camera.setTarget(new javax.vecmath.Vector3f((float) X, (float) Y, (float) Z));

        String cameraName;
        if (cameraNameTextField.getText().trim().isEmpty()) {
            cameraName = "camera";
        } else {
            cameraName = cameraNameTextField.getText().trim();
        }
        cameraName = chooseNameForCamera(cameraName, CamerasListView);

        CamerasList.add(camera);
        CamerasListView.getItems().add(cameraName);

        createCameraTransitionXSpinnerValue.setValue(0.0);
        createCameraTransitionYSpinnerValue.setValue(0.0);
        createCameraTransitionZSpinnerValue.setValue(0.0);
        createCameraViewTransitionXSpinnerValue.setValue(0.0);
        createCameraViewTransitionYSpinnerValue.setValue(0.0);
        createCameraViewTransitionZSpinnerValue.setValue(0.0);
        cameraNameTextField.setText("");
    }

    @FXML
    private void applyModelChanges() { // применение настроек иодели (моделей)
        List<ModelOnScene> selectedModelsOnScene = getSelectedModelsOnScene(ModelsListView.getSelectionModel().getSelectedIndices());

        if (selectedModelsOnScene.size() == 0) { // если не выбрана ни одна модель
            throwErrorMessageBox("Error", "Please, choose at least one model to apply changes");
            setActualEditModelTitledPane(new ModelOnScene());
        } else if (selectedModelsOnScene.size() == 1) { // если выбрана 1 модель
            ModelOnScene selectedModelOnScene = selectedModelsOnScene.get(0);

            double X = (-1) * editModelTransitionXSpinner.getValue();
            double Y = editModelTransitionYSpinner.getValue();
            double Z = (-1) * editModelTransitionZSpinner.getValue();
            Vector3f transition = new Vector3f((float) X, (float) Y, (float) Z);
            selectedModelOnScene.applyMovePosition(transition);

            selectedModelOnScene.setDrawModel(drawModelCheckBox.isSelected());
            selectedModelOnScene.setDrawPolygonMesh(drawPolygonMeshCheckBox.isSelected());
            selectedModelOnScene.setDrawColorFilling(drawColorFillingCheckBox.isSelected());
            selectedModelOnScene.setDrawLighting(drawLightingCheckBox.isSelected());
            selectedModelOnScene.setDrawTexture(drawTextureCheckBox.isSelected());
            selectedModelOnScene.setSaveWithChanges(saveWithChangesCheckBox.isSelected());

            int indexOfSelectedTexture = drawTextureChoiceBox.getSelectionModel().getSelectedIndex();
            if (indexOfSelectedTexture > 0) {
                selectedModelOnScene.setTexture(TexturesList.get(indexOfSelectedTexture - 1));
            } else {
                selectedModelOnScene.setTexture(null);
            }
            selectedModelOnScene.setColor(drawColorFillingColorPicker.getValue());

        } else { // если выбрано несколько моделей
            boolean isDrawModel = isDrawModelForSelectedModels(selectedModelsOnScene);
            boolean isDrawPolygonMesh = isDrawPolygonMeshForSelectedModels(selectedModelsOnScene);
            boolean isDrawColorFilling = isDrawColorFillingForSelectedModels(selectedModelsOnScene);
            boolean isDrawLighting = isDrawLightingForSelectedModels(selectedModelsOnScene);
            boolean isSaveWithChanges = isSaveWithChangesForSelectedModels(selectedModelsOnScene);

            for (ModelOnScene selectedModelOnScene : selectedModelsOnScene) {
                if (drawModelCheckBox.isSelected() || ((!drawModelCheckBox.isSelected()) && isDrawModel)) {
                    selectedModelOnScene.setDrawModel(drawModelCheckBox.isSelected());
                }
                if (drawPolygonMeshCheckBox.isSelected() || ((!drawPolygonMeshCheckBox.isSelected()) && isDrawPolygonMesh)) {
                    selectedModelOnScene.setDrawPolygonMesh(drawPolygonMeshCheckBox.isSelected());
                }
                if (drawColorFillingCheckBox.isSelected() || ((!drawColorFillingCheckBox.isSelected()) && isDrawColorFilling)) {
                    selectedModelOnScene.setDrawColorFilling(drawColorFillingCheckBox.isSelected());
                }
                if (drawLightingCheckBox.isSelected() || ((!drawLightingCheckBox.isSelected()) && isDrawLighting)) {
                    selectedModelOnScene.setDrawLighting(drawLightingCheckBox.isSelected());
                }
                if (saveWithChangesCheckBox.isSelected() || ((!saveWithChangesCheckBox.isSelected()) && isSaveWithChanges)) {
                    selectedModelOnScene.setSaveWithChanges(saveWithChangesCheckBox.isSelected());
                }
            }

            if (drawTextureCheckBox.isSelected()) {
                drawTextureCheckBox.setSelected(false);
                throwErrorMessageBox("Error", "Can't draw texture for few models");
            }
            if (!(editModelTransitionXSpinnerValue.getValue().equals(0.0) && editModelTransitionYSpinnerValue.getValue().equals(0.0) &&
                    editModelTransitionZSpinnerValue.getValue().equals(0.0) && editModelRotationXSpinnerValue.getValue().equals(0.0) &&
                    editModelRotationYSpinnerValue.getValue().equals(0.0) && editModelRotationZSpinnerValue.getValue().equals(0.0) &&
                    editModelScalingXSpinnerValue.getValue().equals(1.0) && editModelScalingYSpinnerValue.getValue().equals(1.0) &&
                    editModelScalingZSpinnerValue.getValue().equals(1.0))) {
                throwErrorMessageBox("Error", "Can't show and edit values for few models");

                editModelTransitionXSpinnerValue.setValue(0.0);
                editModelTransitionYSpinnerValue.setValue(0.0);
                editModelTransitionZSpinnerValue.setValue(0.0);
                editModelRotationXSpinnerValue.setValue(0.0);
                editModelRotationYSpinnerValue.setValue(0.0);
                editModelRotationZSpinnerValue.setValue(0.0);
                editModelScalingXSpinnerValue.setValue(1.0);
                editModelScalingYSpinnerValue.setValue(1.0);
                editModelScalingZSpinnerValue.setValue(1.0);
            }
            if (!drawTextureChoiceBox.getValue().equals("none")) {
                throwErrorMessageBox("Error", "Can't set texture values for few models");
                drawTextureChoiceBox.setValue("none");
            }
            for (ModelOnScene selectedModelOnScene : selectedModelsOnScene) {
                selectedModelOnScene.setColor(drawColorFillingColorPicker.getValue());
            }
        }
    }

    private void throwErrorMessageBox(final String headerText, final String errorText) { // создание окна с ошибкой
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(AlertType.ERROR, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText(errorText);
        alert.getDialogPane().setHeaderText(headerText);
        alert.showAndWait();
    }

    private void setActualEditCameraTitledPane(Camera camera) { // установка актуальных настроек камеры в окне
        double X = (-1) * camera.getPosition().x;
        if (X == -0) {
            X = 0;
        }
        double Y = camera.getPosition().y;
        double Z = (-1) * camera.getPosition().z;
        if (Z == -0) {
            Z = 0;
        }
        editCameraTransitionXSpinnerValue.setValue(X);
        editCameraTransitionYSpinnerValue.setValue(Y);
        editCameraTransitionZSpinnerValue.setValue(Z);

        X = (-1) * camera.getTarget().x;
        if (X == -0) {
            X = 0;
        }
        Y = camera.getTarget().y;
        Z = (-1) * camera.getTarget().z;
        if (Z == -0) {
            Z = 0;
        }
        editCameraViewTransitionXSpinnerValue.setValue(X);
        editCameraViewTransitionYSpinnerValue.setValue(Y);
        editCameraViewTransitionZSpinnerValue.setValue(Z);
    }

    private void setActualEditModelTitledPane(ModelOnScene modelOnScene) { // установка актуальных настроек модели (моделей) в окне
        double X = (-1) * modelOnScene.getTransition().getX();
        if (X == -0) {
            X = 0;
        }
        double Y = modelOnScene.getTransition().getY();
        double Z = (-1) * modelOnScene.getTransition().getZ();
        if (Z == -0) {
            Z = 0;
        }

        editModelTransitionXSpinnerValue.setValue(X);
        editModelTransitionYSpinnerValue.setValue(Y);
        editModelTransitionZSpinnerValue.setValue(Z);

        editModelRotationXSpinnerValue.setValue(0.0);
        editModelRotationYSpinnerValue.setValue(0.0);
        editModelRotationZSpinnerValue.setValue(0.0);

        editModelScalingXSpinnerValue.setValue(1.0);
        editModelScalingYSpinnerValue.setValue(1.0);
        editModelScalingZSpinnerValue.setValue(1.0);

        if (modelOnScene.equalsEmptyModel()) {
            drawModelCheckBox.setSelected(false);
            drawPolygonMeshCheckBox.setSelected(false);
            drawColorFillingCheckBox.setSelected(false);
            drawLightingCheckBox.setSelected(false);
            drawTextureCheckBox.setSelected(false);
            saveWithChangesCheckBox.setSelected(false);
        } else {
            drawModelCheckBox.setSelected(modelOnScene.isDrawModel());
            drawPolygonMeshCheckBox.setSelected(modelOnScene.isDrawPolygonMesh());
            drawColorFillingCheckBox.setSelected(modelOnScene.isDrawColorFilling());
            drawLightingCheckBox.setSelected(modelOnScene.isDrawLighting());
            drawTextureCheckBox.setSelected(modelOnScene.isDrawTexture());
            saveWithChangesCheckBox.setSelected(modelOnScene.isSaveWithChanges());
        }

        if (modelOnScene.getTexture() == null) {
            drawTextureChoiceBox.setValue("none");
        } else {
            int indexOfTexture = TexturesList.indexOf(modelOnScene.getTexture());
            String value = drawTextureChoiceBox.getItems().get(indexOfTexture + 1);
            drawTextureChoiceBox.setValue(value);
        }
        if (modelOnScene.getColor() == null) {
            drawColorFillingColorPicker.setValue(WHITE);
        } else {
            drawColorFillingColorPicker.setValue(modelOnScene.getColor());
        }
    }

    private void initializeCameraTitledPane() { // инициализация формы настроек камеры
        editCameraTransitionXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraTransitionXSpinnerValue.setValue(0.0);
        editCameraTransitionXSpinner.setValueFactory(editCameraTransitionXSpinnerValue);
        editCameraTransitionYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraTransitionYSpinnerValue.setValue(0.0);
        editCameraTransitionYSpinner.setValueFactory(editCameraTransitionYSpinnerValue);
        editCameraTransitionZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraTransitionZSpinnerValue.setValue(0.0);
        editCameraTransitionZSpinner.setValueFactory(editCameraTransitionZSpinnerValue);

        editCameraViewTransitionXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraViewTransitionXSpinnerValue.setValue(0.0);
        editCameraViewTransitionXSpinner.setValueFactory(editCameraViewTransitionXSpinnerValue);
        editCameraViewTransitionYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraViewTransitionYSpinnerValue.setValue(0.0);
        editCameraViewTransitionYSpinner.setValueFactory(editCameraViewTransitionYSpinnerValue);
        editCameraViewTransitionZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        editCameraViewTransitionZSpinnerValue.setValue(0.0);
        editCameraViewTransitionZSpinner.setValueFactory(editCameraViewTransitionZSpinnerValue);
    }

    private void initializeCreateCameraTitledPane() { // инициализация формы создания камеры
        createCameraTransitionXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraTransitionXSpinnerValue.setValue(0.0);
        createCameraTransitionXSpinner.setValueFactory(createCameraTransitionXSpinnerValue);
        createCameraTransitionYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraTransitionYSpinnerValue.setValue(0.0);
        createCameraTransitionYSpinner.setValueFactory(createCameraTransitionYSpinnerValue);
        createCameraTransitionZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraTransitionZSpinnerValue.setValue(0.0);
        createCameraTransitionZSpinner.setValueFactory(createCameraTransitionZSpinnerValue);

        createCameraViewTransitionXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraViewTransitionXSpinnerValue.setValue(0.0);
        createCameraViewTransitionXSpinner.setValueFactory(createCameraViewTransitionXSpinnerValue);
        createCameraViewTransitionYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraViewTransitionYSpinnerValue.setValue(0.0);
        createCameraViewTransitionYSpinner.setValueFactory(createCameraViewTransitionYSpinnerValue);
        createCameraViewTransitionZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-5000, 5000);
        createCameraViewTransitionZSpinnerValue.setValue(0.0);
        createCameraViewTransitionZSpinner.setValueFactory(createCameraViewTransitionZSpinnerValue);
    }

    private void initializeModelTitledPane() { // инициализация формы настроек модели (моделей)
        editModelTransitionXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-2500, 2500);
        editModelTransitionXSpinnerValue.setValue(0.0);
        editModelTransitionXSpinner.setValueFactory(editModelTransitionXSpinnerValue);
        editModelTransitionYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-2500, 2500);
        editModelTransitionYSpinnerValue.setValue(0.0);
        editModelTransitionYSpinner.setValueFactory(editModelTransitionYSpinnerValue);
        editModelTransitionZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-2500, 2500);
        editModelTransitionZSpinnerValue.setValue(0.0);
        editModelTransitionZSpinner.setValueFactory(editModelTransitionZSpinnerValue);

        editModelRotationXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1800, 1800);
        editModelRotationXSpinnerValue.setValue(0.0);
        editModelRotationXSpinner.setValueFactory(editModelRotationXSpinnerValue);
        editModelRotationYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1800, 1800);
        editModelRotationYSpinnerValue.setValue(0.0);
        editModelRotationYSpinner.setValueFactory(editModelRotationYSpinnerValue);
        editModelRotationZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(-1800, 1800);
        editModelRotationZSpinnerValue.setValue(0.0);
        editModelRotationZSpinner.setValueFactory(editModelRotationZSpinnerValue);

        editModelScalingXSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.04, 25);
        editModelScalingXSpinnerValue.setValue(1.0);
        editModelScalingXSpinner.setValueFactory(editModelScalingXSpinnerValue);
        editModelScalingYSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.04, 25);
        editModelScalingYSpinnerValue.setValue(1.0);
        editModelScalingYSpinner.setValueFactory(editModelScalingYSpinnerValue);
        editModelScalingZSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.04, 25);
        editModelScalingZSpinnerValue.setValue(1.0);
        editModelScalingZSpinner.setValueFactory(editModelScalingZSpinnerValue);

        drawModelCheckBox.setSelected(false);
        drawPolygonMeshCheckBox.setSelected(false);
        drawColorFillingCheckBox.setSelected(false);
        drawLightingCheckBox.setSelected(false);
        drawTextureCheckBox.setSelected(false);
        saveWithChangesCheckBox.setSelected(false);
        drawTextureChoiceBox.getItems().add("none");
        drawTextureChoiceBox.setValue("none");
        drawColorFillingColorPicker.setValue(WHITE);
    }
}