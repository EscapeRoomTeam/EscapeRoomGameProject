package tech.makers.demo.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tech.makers.demo.EscapeRoomGame;



import static jdk.internal.misc.OSEnvironment.initialize;

public class OptionsScreen {
    private Scene scene;
    private Stage primaryStage;
    private EscapeRoomGame game;
    private Slider musicVolumeSlider;
    private Slider seVolumeSlider;


    public OptionsScreen(Stage primaryStage, EscapeRoomGame game) {
        this.primaryStage = primaryStage;
        this.game = game;
        initialize();
    }
    private void initialize() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: lightgrey;");

        // Music volume slider
        Label musicVolumeLabel = new Label("Music Volume");
        musicVolumeSlider = new Slider(0, 1, game.getMusicVolume());
        musicVolumeSlider.setMajorTickUnit(0.1);
        musicVolumeSlider.setShowTickMarks(true);
        musicVolumeSlider.setShowTickLabels(true);
        musicVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            game.setMusicVolume(newValue.doubleValue());
        });

        // Sound effects volume slider
        Label seVolumeLabel = new Label("Sound Effects Volume");
        seVolumeSlider = new Slider(0, 1, game.getSEVolume());
        seVolumeSlider.setShowTickLabels(true);
        seVolumeSlider.setShowTickMarks(true);
        seVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            game.setSEVolume(newValue.doubleValue());
        });

        Button resumeButton = new Button("Resume Game");
        resumeButton.setOnAction(e -> game.resumeGame());

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> primaryStage.close());

        layout.getChildren().addAll(musicVolumeLabel, musicVolumeSlider, resumeButton, exitButton);

        scene = new Scene(layout, 768, 576);
    }
    public Scene getScene() {
        return scene;
    }

}

