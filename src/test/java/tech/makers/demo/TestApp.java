package tech.makers.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestApp extends Application {

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TestApp.primaryStage = primaryStage;
        // Initialize your application
    }

    public static void launchApp() {
        if (initialized.compareAndSet(false, true)) {
            Thread thread = new Thread(() -> Application.launch(TestApp.class));
            thread.setDaemon(true);
            thread.start();
            try {
                Thread.sleep(500); // Wait for JavaFX to initialize (optional)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopApp() {
        if (initialized.compareAndSet(true, false)) {
            Platform.runLater(() -> {
                if (primaryStage != null) {
                    primaryStage.close();
                    primaryStage = null;
                }
                Platform.exit();
            });
        }
    }
}




