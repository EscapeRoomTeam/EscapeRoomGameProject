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
    requires org.testfx.junit5;  // Ensure TestFX is required

    opens tech.makers.demo to javafx.fxml;
    exports tech.makers.demo;
    exports tech.makers.demo.levels;
    opens tech.makers.demo.levels to javafx.fxml;
    exports tech.makers.demo.assets;
    opens tech.makers.demo.assets to javafx.fxml;
    exports tech.makers.demo.player;
    opens tech.makers.demo.player to javafx.fxml;

    exports tech.makers.demo.gui;  // Ensure the gui package is exported
    opens tech.makers.demo.gui to javafx.fxml;
}