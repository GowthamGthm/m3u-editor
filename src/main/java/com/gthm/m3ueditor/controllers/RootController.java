package com.gthm.m3ueditor.controllers;

import com.gthm.m3ueditor.M3UEditorApplication;
import com.gthm.m3ueditor.m3u.M3UProcessor;
import com.gthm.m3ueditor.m3u.model.M3uOrganiser;
import com.gthm.m3ueditor.m3u.model.M3uType;
import com.gthm.m3ueditor.utils.FileUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.gthm.m3ueditor.constant.AppConstants.M3U_TABLE_VIEW_FXML;

public class RootController {

    @FXML
    private Label welcomeText;

    @FXML
    private Label labelFilePath;

    private Stage primaryStage;

    @FXML
    Tab tab_livetv, tab_movies, tab_series;

    @FXML
    private TabPane tabs_m3u_types;

    @FXML
    Accordion accordian_m3u;

    @FXML
    ProgressIndicator progress_bar;

    @FXML
    ScrollPane scrollPane;

    List<M3uOrganiser> m3uOrganiserList;


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
            m3uOrganiserList = M3UProcessor.processFile(filePath);
        }
    }

    @FXML
    public void initialize() {
        // Set individual tab actions
        progress_bar.setVisible(false);

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
//        label_for_tabs.setText(M3uType.LIVE_TV.getDisplayName());
    }

    public void handleTabs(M3uType m3uType) {
        progress_bar.setVisible(true);
        accordian_m3u.getPanes().clear();

        accordian_m3u.prefWidthProperty().bind(scrollPane.widthProperty());

        List<M3uOrganiser> typeList = m3uOrganiserList.stream().filter(ele -> ele.getType() == m3uType).toList();

        typeList.forEach(ele -> {
            addNewSection(ele.getGroupName() , ele);
        });
        progress_bar.setVisible(false);
    }

    public void addNewSection(String groupName, M3uOrganiser typeList) {
        TableView<String> tableView = createTable(groupName, typeList);
    }

    private TableView<String> createTable(String groupName, M3uOrganiser typeList) {
        TableView<String> tableView = new TableView<>();
//        tableView.setPrefHeight(200);
        tableView.setMaxHeight(Double.MAX_VALUE);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<String, String> col = new TableColumn<>("Item");
        col.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue()));
        tableView.getColumns().add(col);

        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Name,Group");

        typeList.getM3uList().stream().forEach(ele -> {
            String name = ele.getName();
            String group = ele.getGroupTitle();
            items.add(name + "," + group);
        });

        tableView.setItems(items);

        VBox contentBox = new VBox(tableView);
        contentBox.setPadding(new Insets(10));
        contentBox.setFillWidth(true);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TitledPane pane = new TitledPane(groupName, contentBox);
        pane.setExpanded(true); // Ensure it starts open

        accordian_m3u.getPanes().add(pane);
        enableRowDragAndDrop(tableView);
        return tableView;
    }

    private void enableRowDragAndDrop(TableView<String> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(row.getItem());
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasString()) {
                    int draggedIndex = tableView.getItems().indexOf(db.getString());
                    int dropIndex = row.getIndex();

                    if (draggedIndex >= 0 && dropIndex >= 0) {
                        ObservableList<String> items = tableView.getItems();
                        String draggedItem = items.remove(draggedIndex);
                        items.add(dropIndex, draggedItem);
                        success = true;
                    }
                }

                event.setDropCompleted(success);
                event.consume();
            });

            return row;
        });
    }

}