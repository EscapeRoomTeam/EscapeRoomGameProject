module tech.makers.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires junit;
    requires jcommander;
    requires org.mockito;

    opens tech.makers.demo to javafx.fxml;
    exports tech.makers.demo;
}