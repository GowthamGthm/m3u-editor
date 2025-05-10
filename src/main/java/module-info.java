module com.gthm.m3ueditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.gthm.m3ueditor to javafx.fxml;
    exports com.gthm.m3ueditor;
    exports com.gthm.m3ueditor.controllers;
    opens com.gthm.m3ueditor.controllers to javafx.fxml;
}