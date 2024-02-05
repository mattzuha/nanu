package application;

import java.util.*;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameLogic {

    CorrectSound correctSound = new CorrectSound();
    IncorrectSound incorrectSound = new IncorrectSound();
    GridPaneOperator gridPaneOperator = new GridPaneOperator();

    public boolean check(GridPane myGridPane, Rectangle rectangle, ChoiceBox<String> choiceBox2) {

        String answer = choiceBox2.getValue();
        int targetRow = GridPane.getRowIndex(rectangle);
        int targetColumn = GridPane.getColumnIndex(rectangle);
        boolean result = false;

        System.out.println(answer);

        for (Node node : myGridPane.getChildren()) {
            if (node instanceof ImageView) {
                if (GridPane.getColumnIndex(node) == targetColumn
                        && GridPane.getRowIndex(node) == targetRow
                        && answer.equals(node.getId())) {

                    result = true;
                    node.setVisible(false);
                    node.setDisable(true);
                }
            }
        }
        return result;
    }

    public void endGame(GridPane myGridPane) {
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof Rectangle) {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), node);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0);
                fadeOut.play();
            }
        }
    }

    public void winner(int score1, int score2, int score3, int score4, Label first, Label second, Label third) {
        HashMap<Integer, String> winner = new HashMap<>();
        winner.put(score1, "Oggy");
        winner.put(score2, "Jack");
        winner.put(score3, "Olivia");
        winner.put(score4, "Bob");

        // Create a TreeSet with a custom comparator to sort by keys
        TreeSet<Map.Entry<Integer, String>> sortedEntries = new TreeSet<>(
                Comparator.<Map.Entry<Integer, String>>comparingInt(Map.Entry::getKey).reversed()
        );

        // Add all entries from the HashMap to the TreeSet
        sortedEntries.addAll(winner.entrySet());

        // Retrieve the entries based on their position in the set
        Iterator<Map.Entry<Integer, String>> iterator = sortedEntries.iterator();
        Map.Entry<Integer, String> firstPlace = iterator.next();
        Map.Entry<Integer, String> secondPlace = iterator.next();
        Map.Entry<Integer, String> thirdPlace = iterator.next();

        // Set the text of the labels
        first.setText(firstPlace.getValue());
        second.setText(secondPlace.getValue());

        if(thirdPlace.getKey() != 0) {
            third.setText(thirdPlace.getValue());
        }
        else {
            third.setText(null);
        }
    }
}
