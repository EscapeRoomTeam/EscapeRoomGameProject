module tech.makers.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jcommander;


    opens tech.makers.demo to javafx.fxml;
    exports tech.makers.demo;
    exports tech.makers.demo.puzzles;
    opens tech.makers.demo.puzzles to javafx.fxml;
    exports tech.makers.demo.objects;
    opens tech.makers.demo.objects to javafx.fxml;
    exports tech.makers.demo.player;
    opens tech.makers.demo.player to javafx.fxml;
}