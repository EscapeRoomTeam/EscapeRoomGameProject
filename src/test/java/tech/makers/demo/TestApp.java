package tech.makers.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp extends Application {

    private static boolean initialized = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // No need to show any UI
    }

    public static void launchApp() {
        if (!initialized) {
            Thread thread = new Thread(() -> Application.launch(TestApp.class));
            thread.setDaemon(true);
            thread.start();
            try {
                Thread.sleep(500); // Wait for JavaFX to initialize (optional)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public static void reset() {
        initialized = false;
    }
}


