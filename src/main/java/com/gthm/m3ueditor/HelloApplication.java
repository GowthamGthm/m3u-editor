package com.gthm.m3ueditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static final String FXML_PATH = "layouts/";
    private static Scene mainScene;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(FXML_PATH + "hello-view.fxml"));
        mainScene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("M3U Editor!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(FXML_PATH + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

}