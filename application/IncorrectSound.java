package application;

import java.util.Random;

public class IncorrectSound extends Sound {
    private Random random = new Random();

    public void playInCorrectSound() {
        int result = random.nextInt(8) + 1;
        switch (result) {
            case 1:
                sound = "/Sound Effects/wrong1.mp3";
                playSoundEffect(sound);
                System.out.println(sound);
                break;
            case 2:
                sound = "/Sound Effects/wrong2.mp3";
                playSoundEffect(sound);
                System.out.println(sound);
                break;
            case 3:
                sound = "/Sound Effects/wrong3.mp3";
                playSoundEffect(sound);
                System.out.println(sound);
                break;
            case 4:
                sound = "/Sound Effects/wrong4.mp3";
                playSoundEffect(sound);
                System.out.println(sound);
                break;
            case 5:
                sound = "/Sound Effects/wrong5.mp3";
                playSoundEffect(sound);
                System.out.println(sound);
                break;
            default:
                System.out.println("null sai nha");
                break;
        }
    }
}