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

    exports tech.makers.demo.screens;
    opens tech.makers.demo.screens to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelThree;
    opens tech.makers.demo.levelManagement.levels.LevelThree to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelTwo;
    opens tech.makers.demo.levelManagement.levels.LevelTwo to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelOne;
    opens tech.makers.demo.levelManagement.levels.LevelOne to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelOne.interactions;
    opens tech.makers.demo.levelManagement.levels.LevelOne.interactions to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelTwo.interactions;
    opens tech.makers.demo.levelManagement.levels.LevelTwo.interactions to javafx.fxml, org.testfx.junit5;
    exports tech.makers.demo.levelManagement.levels.LevelThree.interactions;
    opens tech.makers.demo.levelManagement.levels.LevelThree.interactions to javafx.fxml, org.testfx.junit5;  // Open to TestFX
}