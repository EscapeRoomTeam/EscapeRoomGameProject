package tech.makers.demo.screens;

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
import tech.makers.demo.assets.Sound;

import java.util.Objects;
import java.util.Random;

public class IntroScreen {

    private Scene scene;
    private Stage primaryStage;
    private EscapeRoomGame game;
    private ImageView characterView;
    private Image[] mouthImages;
    private int currentMouthImageIndex = 0;
    private Timeline mouthAnimationTimeline;
    private Timeline textTimeline;
    Sound sound = new Sound();
    private int stepCounter = 0;
    private Random random = new Random();
    private int lastSoundIndex = -1;
    private int[] voxSounds = {7, 8, 9, 10, 11, 12};

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

        // Load character images with different mouth positions
        mouthImages = new Image[]{
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tech/makers/demo/images/eddie.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tech/makers/demo/images/eddie2.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tech/makers/demo/images/eddie3.png")))
        };

        // Initialize character view with the first image
        characterView = new ImageView(mouthImages[0]);
        characterView.setFitWidth(150);
        characterView.setPreserveRatio(true);

        // Create speech bubble
        Label speechBubble = new Label();
        speechBubble.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10; -fx-background-radius: 10; -fx-border-radius: 10;");
        speechBubble.setWrapText(true);
        speechBubble.setMaxWidth(300);

        // Add elements to layout
        layout.getChildren().addAll(characterView, speechBubble);
        root.getChildren().add(layout);

        // Create scene
        scene = new Scene(root, 800, 600);

        // Start text animation
        String instructions = "Welcome to the Escape Room Game! Use the arrow keys to move, and press Enter to interact with objects.";
        animateText(speechBubble, instructions);

        // Add start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            stopAudio(); // Stop audio and animations before starting the game
            sound.setFile(17);
            sound.setVolume(-5.0f);
            sound.play();
            game.startGame(primaryStage);
        });
        layout.getChildren().add(startButton);
    }

    private void animateText(Label label, String text) {
        final int[] index = {0};
        textTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (index[0] < text.length()) {
                label.setText(label.getText() + text.charAt(index[0]));
                index[0]++;

                stepCounter++;
                if (stepCounter % 3 == 0) {
                    int soundIndex;
                    do {
                        soundIndex = random.nextInt(voxSounds.length);
                    } while (soundIndex == lastSoundIndex);
                    lastSoundIndex = soundIndex;
                    sound.setFile(voxSounds[soundIndex]);
                    sound.setVolume(-10.0f);
                    sound.play();
                }
            }
        }));
        textTimeline.setCycleCount(text.length());
        textTimeline.setOnFinished(event -> stopMouthAnimation());
        textTimeline.play();


        startMouthAnimation();
    }

    private void startMouthAnimation() {
        mouthAnimationTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            currentMouthImageIndex = (currentMouthImageIndex + 1) % mouthImages.length;
            characterView.setImage(mouthImages[currentMouthImageIndex]);
        }));
        mouthAnimationTimeline.setCycleCount(Timeline.INDEFINITE);
        mouthAnimationTimeline.play();
    }

    private void stopMouthAnimation() {
        if (mouthAnimationTimeline != null) {
            mouthAnimationTimeline.stop();
            characterView.setImage(mouthImages[0]); // Reset to the first image with mouth closed
            sound.stop();
        }
    }

    private void stopAudio() {
        if (textTimeline != null) {
            textTimeline.stop();
        }
        if (sound != null) {
            sound.stop();
        }
    }

    public void show() {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Add this method to get the scene
    public Scene getScene() {
        return scene;
    }


}