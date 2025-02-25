package edu.farmingdale.maze_csc311_assignment3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private PixelReader pixelReader;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Maze Demo - Final Version");
        stage.show();

        HelloController controller = loader.getController();

        ImageView maze1View = controller.getMaze1View();

        ImageView robotView = controller.getRobotView();

        pixelReader = maze1View.getImage().getPixelReader();


        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.UP
                    || e.getCode() == KeyCode.DOWN
                    || e.getCode() == KeyCode.LEFT
                    || e.getCode() == KeyCode.RIGHT) {
                e.consume();

                double step = 5;
                double x = robotView.getLayoutX();
                double y = robotView.getLayoutY();
                double newX = x, newY = y;

                switch (e.getCode()) {
                    case UP -> newY -= step;
                    case DOWN -> newY += step;
                    case LEFT -> newX -= step;
                    case RIGHT -> newX += step;
                    default -> {}
                }

                double centerX = newX + 12;
                double centerY = newY + 12;

                if (isPath(centerX, centerY)) {
                    robotView.setLayoutX(newX);
                    robotView.setLayoutY(newY);
                } else {
                    System.out.println("Blocked at " + centerX + "," + centerY);
                }
            }
        });

        Platform.runLater(() -> root.requestFocus());
    }


    private boolean isPath(double x, double y) {

        if (x < 0 || y < 0 || x >= 600 || y >= 400) {
            return false;
        }
        Color c = pixelReader.getColor((int)x, (int)y);
        return c.getRed() > 0.9 && c.getGreen() > 0.9 && c.getBlue() > 0.9;
    }

    public static void main(String[] args) {
        launch();
    }
}
