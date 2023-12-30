package com.cgvsu;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml"))); // загрузка fxml формы окна

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1600); // ширина
        stage.setMinHeight(900); // высота
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("3DViewer"); // имя окна
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(); // запуск окна
    }
}