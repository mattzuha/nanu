package application;

public class DiceSound extends Sound {

    public void playDiceSound() {
        sound = "/Sound Effects/dice.mp3";
        playSoundEffect(sound);
        System.out.println(sound);
    }
}