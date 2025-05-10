package com.gthm.m3ueditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onHelloButtonClicked() {
        welcomeText.setText("Welcome to JavaFX Application Clicked !!! ");
    }


    public void goToM3uTable(ActionEvent actionEvent) throws IOException {
        HelloApplication.setRoot("m3u-list-view");
    }

}