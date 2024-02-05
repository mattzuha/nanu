package application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    MediaPlayer mediaPlayer;
    String sound;

    public void playSoundEffect(String path) {
        Media media = new Media(getClass().getResource(sound).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }
}
