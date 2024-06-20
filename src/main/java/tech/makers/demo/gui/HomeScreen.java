package tech.makers.demo.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.assets.Sound;

import java.util.Objects;

public class HomeScreen {
    private Scene scene;
    private Stage primaryStage;
    private EscapeRoomGame game;
    Sound sound = new Sound();

    public HomeScreen(Stage primaryStage, EscapeRoomGame game) {
        this.primaryStage = primaryStage;
        this.game = game;
        initialize();
    }

    private void initialize() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: white;");

        // Load and add the logo image
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tech/makers/demo/images/logo.png")));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(300);
        logoView.setPreserveRatio(true);
        layout.getChildren().add(logoView); // Add the image at the top

        // Create and add buttons
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> showIntroScreen());

        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> showHelp());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(startButton, helpButton, exitButton);

        scene = new Scene(layout, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    private void showIntroScreen() {
        IntroScreen introScreen = new IntroScreen(primaryStage, game);
        introScreen.show();
    }

    private void startGame() {
        game.startGame(primaryStage);
    }


    private void showHelp() {
        // Display help information (e.g., a new scene or dialog with instructions)
    }
}