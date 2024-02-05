package application;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import javafx.util.Duration;

public class GridPaneOperator {

    private final Random random = new Random();
    RotateTransition rotateTransition;

    public void shuffleImageViews(GridPane myGridPane, List<ImageView> imageViews) {
        Collections.shuffle(imageViews);
        myGridPane.getChildren().clear();

        for (ImageView imageView : imageViews) {
            int randomRow, randomCol;
            do {
                randomRow = random.nextInt(5);
                randomCol = random.nextInt(5);
            } while (isImgViewOccupied(myGridPane, randomRow, randomCol) || (randomCol == 2 && randomRow == 2));

            myGridPane.add(imageView, randomCol, randomRow);
        }
    }

    public boolean isImgViewOccupied(GridPane myGridPane, int row, int col) {
        for (Node node : myGridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return true;
            }
        }
        return false;
    }

    public boolean isRecOccupied(GridPane myGridPane, int row, int col) {
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveOverlay(GridPane myGridPane, int newColumn, int newRow, Rectangle rectangle) {
        if (isRecOccupied(myGridPane, newRow, newColumn) != true) {
            myGridPane.getChildren().remove(rectangle);
            GridPane.setColumnIndex(rectangle, newColumn);
            GridPane.setRowIndex(rectangle, newRow);
            myGridPane.getChildren().add(rectangle);
        }
    }

    public boolean moveUnSuccessfull(GridPane myGridPane, int newColumn, int newRow, Rectangle rectangle) {
        boolean result = true;
        if (isRecOccupied(myGridPane, newRow, newColumn) != true) {
            result = false;
        }
        return result;
    }

    public void disableImageView(GridPane myGridPane) {
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof ImageView) {
                node.setDisable(true);
            }
        }
    }

    public void ableImageView(GridPane myGridPane) {
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof ImageView) {
                node.setDisable(false);
            }
        }
    }

    public void enableDisableAnswerAndSubmitButton(ChoiceBox<String> choiceBox, Button submitButton) {
        Platform.runLater(() -> {
            if (choiceBox.isDisable()) {
                choiceBox.setDisable(false);
                submitButton.setDisable(false);
            } else {
                choiceBox.setDisable(true);
                submitButton.setDisable(true);
            }
        });

    }

    public void startRotationAnimation(Rectangle rectangle) {
        if (rectangle != null) {
            rotateTransition = new RotateTransition(Duration.seconds(2.5), rectangle);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(2);
            rotateTransition.play();
        }
    }
}
