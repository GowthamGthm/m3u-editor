package com.gthm.m3ueditor.controllers;

import com.gthm.m3ueditor.M3UEditorApplication;
import com.gthm.m3ueditor.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.gthm.m3ueditor.constant.AppConstants.M3U_TABLE_VIEW_FXML;

public class RootController {

    @FXML
    private Label welcomeText;

    @FXML
    private Label labelFilePath;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onHelloButtonClicked() {
        welcomeText.setText("Welcome to JavaFX Application Clicked !!! ");
    }


    @FXML
    public void goToM3uTable(ActionEvent actionEvent) throws IOException {
        M3UEditorApplication.setRoot(M3U_TABLE_VIEW_FXML);
    }

    @FXML
    public void chooseFile(ActionEvent actionEvent) {
        labelFilePath.setText("");
        FileChooser fileChooser = new FileChooser();

        // Set extension filters
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "M3U Files (*.m3u)", "*.m3u");
        fileChooser.getExtensionFilters().add(extFilter);

        String downloadPath = FileUtils.getDownloadPath();
        // Set initial directory (optional)
        fileChooser.setInitialDirectory(new File(downloadPath));

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            // Handle the selected file
            String absolutePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + absolutePath);
            labelFilePath.setText(absolutePath);
            // Add your file processing logic here
        }

    }
}