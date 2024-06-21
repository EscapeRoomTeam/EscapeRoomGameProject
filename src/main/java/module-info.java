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
    exports tech.makers.demo.levelManagement;
    opens tech.makers.demo.levelManagement to javafx.fxml, org.testfx.junit5;  // Open to TestFX
    exports tech.makers.demo.assets;
    opens tech.makers.demo.assets to javafx.fxml, org.testfx.junit5;  // Open to TestFX
    exports tech.makers.demo.player;
    opens tech.makers.demo.player to javafx.fxml, org.testfx.junit5;  // Open to TestFX

    exports tech.makers.demo.gui;
    opens tech.makers.demo.gui to javafx.fxml, org.testfx.junit5;  // Open to TestFX
}