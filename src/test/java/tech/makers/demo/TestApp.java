package tech.makers.demo;

//This file is just for testing JavaFX without needing to run the game
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // No need to show any UI
    }

    public static void launchApp() {
        Thread thread = new Thread(() -> Application.launch(TestApp.class));
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(500); // Wait for JavaFX to initialize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

