package tech.makers.demo.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tech.makers.demo.EscapeRoomGame;

import java.util.Objects;

public class IntroScreen {

    private Scene scene;
    private Stage primaryStage;
    private EscapeRoomGame game;
    private Button startButton;

    public IntroScreen(Stage primaryStage, EscapeRoomGame game) {
        this.primaryStage = primaryStage;
        this.game = game;
        initialize();
    }

    private void initialize() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: white;");

        VBox layout = new VBox(20);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Load character image
        Image characterImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tech/makers/demo/images/eddie.png")));
        ImageView characterView = new ImageView(characterImage);
        characterView.setFitWidth(150);
        characterView.setPreserveRatio(true);

        // Create speech bubble
        Label speechBubble = new Label();
        speechBubble.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10; -fx-background-radius: 10; -fx-border-radius: 10;");
        speechBubble.setWrapText(true);
        speechBubble.setMaxWidth(300);

        // Create start button and make it initially invisible
        startButton = new Button("Start Game");
        startButton.setVisible(false);
        startButton.setOnAction(e -> game.startGame(primaryStage));

        // Add elements to layout
        layout.getChildren().addAll(characterView, speechBubble, startButton);
        root.getChildren().add(layout);

        // Create scene
        scene = new Scene(root, 800, 600);

        // Start text animation
        String instructions = "Welcome to the Escape Room Game! Use the arrow keys to move, and press Enter to interact with objects.";
        animateText(speechBubble, instructions);
    }

    private void animateText(Label label, String text) {
        final int[] index = {0};
        final Timeline[] timeline = {new Timeline()};
        timeline[0] = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (index[0] < text.length()) {
                label.setText(label.getText() + text.charAt(index[0]));
                index[0]++;
            } else {
                timeline[0].stop();
                startButton.setVisible(true); // Show the start button when the animation finishes
            }
        }));
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();
    }

    public void show() {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}