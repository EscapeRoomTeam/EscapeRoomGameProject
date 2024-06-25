module tech.makers.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires junit;
    requires jcommander;
    requires java.desktop;
    requires org.mockito;
    requires org.testfx.junit5;

    opens tech.makers.demo to javafx.fxml, org.testfx.junit5;  // Open to TestFX
    exports tech.makers.demo;
    exports tech.makers.demo.controllers;
    opens tech.makers.demo.controllers to javafx.fxml, org.testfx.junit5;  // Open to TestFX
    exports tech.makers.demo.models.assets;
    opens tech.makers.demo.models.assets to javafx.fxml, org.testfx.junit5;  // Open to TestFX
    exports tech.makers.demo.models;
    opens tech.makers.demo.models to javafx.fxml, org.testfx.junit5;  // Open to TestFX

    exports tech.makers.demo.views;
    opens tech.makers.demo.views to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.models.levels;
    opens tech.makers.demo.models.levels to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.models.sound;
    opens tech.makers.demo.models.sound to javafx.fxml, org.testfx.junit5;  // Open to TestFX
}