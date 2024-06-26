package tech.makers.demo.levelManagement.levels.LevelThree.interactions;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import tech.makers.demo.levelManagement.Interaction;

public class ReceptionInteraction extends Interaction {
    private final double width;
    private final double height;

    public ReceptionInteraction(double x, double y, String imagePath) {
        super(x, y, imagePath);
        this.width = 125;
        this.height = 80;
    }

    @Override
    public void interact(){
        showAlert();
    }
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height); // Draw the image at (x, y) with size 250x250
    }
    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Welcome to Deloitte! Good luck delivering your presentation.");
        alert.showAndWait();
    }
    public boolean collidesWith(double playerX, double playerY, double playerWidth, double playerHeight) {
        return playerX < x + width - 15 && playerX + playerWidth > x &&
                playerY < y + height - 20 && playerY + playerHeight > y;
    }

}
