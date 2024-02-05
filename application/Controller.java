package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Controller implements Initializable {
    Random random = new Random();
    static int totalScore = 0;

    @FXML
    private GridPane myGridPane;

    @FXML
    private GridPane myGridPane2;

    @FXML
    private ImageView diceImage;

    @FXML
    private ImageView avocado, ball, banana, beer, cat, coke, doll, dragon, dragonfruit, drum, egg, games, glasses,
            goat, hat, noodles, potato, sandwich, sausage, seal, shirt, shorts, tea, water;

    @FXML
    private Button rollButton;

    @FXML
    private Label scoreLabel1, scoreLabel2, scoreLabel3, scoreLabel4, firstPlace, secondPlace, thirdPlace;

    @FXML
    private Button submitButton;

    @FXML
    private ChoiceBox<String> choiceBox1;

    @FXML
    private ChoiceBox<String> choiceBox2;

    @FXML
    private Rectangle redRectangle, blueRectangle, greenRectangle, yellowRectangle, purpleRectangle;
    private Rectangle currentRectangle = null;

    private int diceValue;

    private Label currentLabel;

    private static int score1 = 0, score2 = 0, score3 = 0, score4 = 0;

    private String[] choiceBox1Strings = { "2 Players", "3 Players", "4 Players" };
    private String[] choiceBox2Strings = { "avocado", "ball", "banana", "beer", "cat", "coke", "doll", "dragon",
            "dragonfruit", "drum", "egg", "games", "glasses",
            "goat", "hat", "noodles", "potato", "sandwich", "sausage", "seal", "shirt", "shorts", "tea", "water" };

    int n = 0;
    private static int numberOfPlayer = 3;

    GridPaneOperator gridPaneOperator = new GridPaneOperator();
    SceneSwitcher sceneSwitcher = new SceneSwitcher();
    GameLogic gameLogic = new GameLogic();
    CorrectSound correctSoundEffect = new CorrectSound();
    IncorrectSound incorrectSoundEffect = new IncorrectSound();
    DiceSound diceSound = new DiceSound();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (choiceBox1 != null) {
            choiceBox1.getItems().addAll(choiceBox1Strings);
            choiceBox1.setOnAction(this::getNumberOfPlayers);
        }

        if (choiceBox2 != null)
            choiceBox2.getItems().addAll(choiceBox2Strings);

        if (firstPlace != null)
            // gameLogic.winner(score1, score2, score3, score4, firstPlace, secondPlace,
            // thirdPlace);
            gameLogic.winner(3, 5, 7, 2, firstPlace, secondPlace, thirdPlace);
    }

    public void getNumberOfPlayers(ActionEvent event) {
        String result = choiceBox1.getValue();
        if (result.equals("2 Players")) {
            numberOfPlayer = 2;
        } else if (result.equals("3 Players")) {
            numberOfPlayer = 3;
        } else
            numberOfPlayer = 4;
    }

    @FXML
    public void switchToMainScene(ActionEvent event) throws IOException {
        sceneSwitcher.switchToMainScene(event);
    }

    @FXML
    public void switchScene2(ActionEvent event) throws IOException {
        sceneSwitcher.switchScene2(event);
    }

    @FXML
    public void switchToEndScene(ActionEvent event) throws IOException {
        sceneSwitcher.switchToEndScene(event);
    }

    @FXML
    public void exitGame(ActionEvent event) throws IOException {
        sceneSwitcher.exitGame(event);
    }

    public void roll(ActionEvent e) {
        currentRectangle = null;
        diceSound.playDiceSound();
        rollButton.setDisable(true);

        Thread animationThread = new Thread(() -> {
            try {
                for (int i = 0; i < 24; i++) {
                    diceValue = random.nextInt(6) + 1;
                    Image image = new Image(getClass().getResourceAsStream("/roll/" + diceValue + ".png"));

                    Platform.runLater(() -> diceImage.setImage(image));
                    Thread.sleep(50);
                }

                switch (diceValue) {
                    case 1:
                        gridPaneOperator.startRotationAnimation(blueRectangle);
                        break;
                    case 2:
                        gridPaneOperator.startRotationAnimation(yellowRectangle);
                        break;
                    case 3:
                        gridPaneOperator.startRotationAnimation(greenRectangle);
                        break;
                    case 4:
                        gridPaneOperator.startRotationAnimation(purpleRectangle);
                        break;
                    case 5:
                        gridPaneOperator.startRotationAnimation(redRectangle);
                        break;
                    case 6:
                        currentRectangle = null; // Reset currentRectangle

                        // Set the event handler for moving the rectangle
                        myGridPane.getChildren().forEach(node -> {
                            if (node instanceof Rectangle) {
                                Rectangle rectangle = (Rectangle) node;
                                rectangle.setOnMouseClicked(mouseEvent -> {
                                    currentRectangle = rectangle;
                                    gridPaneOperator.startRotationAnimation(currentRectangle);
                                    System.out.println(currentRectangle);
                                });
                            }
                        });
                        break;
                }

                gridPaneOperator.enableDisableAnswerAndSubmitButton(choiceBox2, submitButton);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        animationThread.start();
    }

    public void returnPosImage(MouseEvent event) {
        // Get the source node of the MouseEvent
        Node source = (Node) event.getSource();

        // Fetch the row and column indices of the ImageView in the GridPane
        Integer row = GridPane.getRowIndex(source);
        Integer col = GridPane.getColumnIndex(source);

        // Check if a rectangle in myGridPane2 is clicked
        if (source instanceof Rectangle && ((Rectangle) source).getParent() == myGridPane2) {
            currentRectangle = (Rectangle) source;
        } else if (currentRectangle != null) {
            // If a rectangle is selected and a cell in myGridPane is clicked
            gridPaneOperator.moveOverlay(myGridPane, col, row, currentRectangle);
            if (!gridPaneOperator.moveUnSuccessfull(myGridPane, col, row, currentRectangle)) {
                currentRectangle = null; // Reset selected rectangle after moving
            }
        }
    }

    public void submit(ActionEvent event) {
        try {
            switch (diceValue) {
                case 1:
                    currentRectangle = blueRectangle;
                    break;
                case 2:
                    currentRectangle = yellowRectangle;
                    break;
                case 3:
                    currentRectangle = greenRectangle;
                    break;
                case 4:
                    currentRectangle = purpleRectangle;
                    break;
                case 5:
                    currentRectangle = redRectangle;
                    break;
            }
            handleUserChoice();

            if (totalScore == 20) {

                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(e -> {
                    gameLogic.endGame(myGridPane);
                });
                pause.play();
            } else {
                rollButton.setDisable(false);

            }

        } finally {
            gridPaneOperator.enableDisableAnswerAndSubmitButton(choiceBox2, submitButton);
        }
    }

    private void handleUserChoice() {
        nextPlayer(numberOfPlayer);

        if ((currentRectangle != null && gameLogic.check(myGridPane, currentRectangle, choiceBox2))) {

            totalScore++;
            System.out.println(totalScore);
            if (currentLabel.getId().equals("scoreLabel1")) {
                score1++;
                currentLabel.setText(": " + score1);

            } else if (currentLabel.getId().equals("scoreLabel2")) {
                score2++;
                currentLabel.setText(": " + score2);
            } else if (currentLabel.getId().equals("scoreLabel3")) {
                score3++;
                currentLabel.setText(": " + score3);
            } else if (currentLabel.getId().equals("scoreLabel4")) {
                score4++;
                currentLabel.setText(": " + score4);
            }

            correctSoundEffect.playCorrectSound();
            gridPaneOperator.ableImageView(myGridPane);
            myGridPane2.getChildren().remove(currentRectangle);

        } else {

            incorrectSoundEffect.playInCorrectSound();
            gridPaneOperator.disableImageView(myGridPane);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), currentRectangle);
            fadeOut.setFromValue(1.0); // Full opacity
            fadeOut.setToValue(0.5);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), currentRectangle);
            fadeIn.setFromValue(0.5); // Half opacity
            fadeIn.setToValue(1.0);

            // Set the onFinished event for fadeOut to start fadeIn after fadeOut finishes
            fadeOut.setOnFinished(e -> {
                fadeIn.play();
                currentRectangle = null; // Set to null after the fade-in animation
            });

            fadeOut.play();
        }
    }

    @FXML
    private void shuffle(ActionEvent event) {
        rollButton.setDisable(false);
        redRectangle.setDisable(false);
        blueRectangle.setDisable(false);
        yellowRectangle.setDisable(false);
        purpleRectangle.setDisable(false);
        greenRectangle.setDisable(false);

        List<ImageView> imageViews = new ArrayList<>();

        // Collect ImageView elements from the GridPane
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof ImageView) {
                node.setOpacity(1);
                imageViews.add((ImageView) node);
            }
        }

        // Shuffle the positions of ImageView elements
        gridPaneOperator.shuffleImageViews(myGridPane, imageViews);
    }

    public void nextPlayer(int numberOfPlayer) {
        int turn = n % numberOfPlayer;

        switch (turn) {
            case 0:
                currentLabel = scoreLabel1;
                break;
            case 1:
                currentLabel = scoreLabel2;
                break;
            case 2:
                currentLabel = scoreLabel3;
                break;
            case 3:
                currentLabel = scoreLabel4;
                break;
        }
        n++;
    }
}

