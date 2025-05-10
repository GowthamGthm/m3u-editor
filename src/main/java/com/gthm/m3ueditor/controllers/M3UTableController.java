package com.gthm.m3ueditor.controllers;

import com.gthm.m3ueditor.M3UEditorApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

public class M3UTableController {
    @FXML
    private TableView<?> m3uTable; // Use proper generic type

    @FXML
    private Button backButton;

    @FXML
    private void goBackToHome() throws IOException {
        M3UEditorApplication.setRoot("hello-view");
    }

}