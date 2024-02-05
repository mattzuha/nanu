package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            music();
            Parent root = FXMLLoader.load(getClass().getResource("StartGame.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Nanu?");
            stage.setResizable(false);
            stage.setFullScreen(true);
            stage.show();
            stage.setOnCloseRequest(e -> {
                e.consume();
                exitGame(stage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitGame(Stage stage) {

        Alert alertBox = new Alert(AlertType.CONFIRMATION);
        alertBox.setTitle("Log out");
        alertBox.setHeaderText("You are about to exit Nanu?");
        alertBox.setContentText("Thanks for playing our (not so great) game :')");

        if (alertBox.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    MediaPlayer mediaPlayer;

    public void music() {
        String musicFile = "/Sound Effects/background.mp3";
        Media media = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.15);
        mediaPlayer.play();
    }
}
