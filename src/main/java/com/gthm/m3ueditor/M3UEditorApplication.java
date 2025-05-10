package com.gthm.m3ueditor;

import com.gthm.m3ueditor.controllers.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.gthm.m3ueditor.constant.AppConstants.FXML_PATH;
import static com.gthm.m3ueditor.constant.AppConstants.ROOT_VIEW_FXML;

public class M3UEditorApplication extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(M3UEditorApplication.class.getResource(FXML_PATH + ROOT_VIEW_FXML));

        // First load the FXML
        Parent root = fxmlLoader.load();

        // Then get the controller
        RootController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        mainScene = new Scene(root, 800, 800);  // Use the already loaded root
        stage.setTitle("M3U Editor!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(M3UEditorApplication.class.getResource(FXML_PATH + fxml ));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

}