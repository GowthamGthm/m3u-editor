package com.gthm.m3ueditor.controllers;

import com.gthm.m3ueditor.M3UEditorApplication;
import com.gthm.m3ueditor.m3u.M3UProcessor;
import com.gthm.m3ueditor.m3u.model.M3uType;
import com.gthm.m3ueditor.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.gthm.m3ueditor.constant.AppConstants.M3U_TABLE_VIEW_FXML;

public class RootController {

    @FXML
    private Label welcomeText , label_for_tabs;

    @FXML
    private Label labelFilePath;

    private Stage primaryStage;

    @FXML
    Tab tab_livetv, tab_movies, tab_series;

    @FXML
    private TabPane tabs_m3u_types;


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


    @FXML
    public void processFile(ActionEvent actionEvent) {
        String filePath = labelFilePath.getText();
        boolean validPath = FileUtils.isValidPath(filePath);
        if (validPath) {
//            M3UAnalyzer.processM3UFile(filePath);
            M3UProcessor.processFile(filePath);
        }
    }

    @FXML
    public void initialize() {
        // Set individual tab actions

        tab_livetv.setOnSelectionChanged(event -> {
            handleTabs(M3uType.LIVE_TV);
        });

        tab_movies.setOnSelectionChanged(event -> {
            handleTabs(M3uType.MOVIES);
        });

        tab_series.setOnSelectionChanged(event -> {
            handleTabs(M3uType.SERIES);
        });

        tabs_m3u_types.getSelectionModel().select(tab_livetv);
        label_for_tabs.setText(M3uType.LIVE_TV.getDisplayName());
    }

    public void handleTabs(M3uType m3uType) {

        label_for_tabs.setText(m3uType.getDisplayName());

    }

}