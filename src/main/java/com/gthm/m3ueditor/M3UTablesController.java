package com.gthm.m3ueditor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

public class M3UTablesController {
    @FXML
    private TableView<?> m3uTable; // Use proper generic type

    @FXML
    private Button backButton;

    @FXML
    private void goBackToHome() throws IOException {
        HelloApplication.setRoot("hello-view");
    }

}