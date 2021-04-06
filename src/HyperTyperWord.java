import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HyperTyperWord extends JLabel implements ActionListener {

    private AudioInputStream correctStream;
    private Clip correctSound;
    private AudioInputStream incorrectStream;
    private Clip incorrectSound;

    private Level level;

    private Timer tm;
    private int x = -100;
    private int y;

    private boolean visible = true;


    public HyperTyperWord(String text, int moveSpeed, Level level) {
        super(text);
        this.level = level;
        setBounds(x, y, 100, 50);
        tm = new Timer(moveSpeed, this);
        y = (int) (Math.random() * 451) + 75;
        setForeground(Color.white);
        try {
            correctStream = AudioSystem.getAudioInputStream(new File("../HyperTyperMusic/correct.wav"));
            correctSound = AudioSystem.getClip();
            correctSound.open(correctStream);
            incorrectStream = AudioSystem.getAudioInputStream(new File("../HyperTyperMusic/incorrect.wav"));
            incorrectSound = AudioSystem.getClip();
            incorrectSound.open(incorrectStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Starts timer
    // Void method, returns nothing
    public void start() {
        tm.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += 1;
        setBounds(x, y, 100, 50);
        if(x == 850 && visible) {
            level.loseLife();
            destroy();
        }
    }

    // Causes the word to blink green for 100ms, plays correct sound effect, then gets rid of the word from the screen
    // Void method, returns nothing
    public void remove() {
        level.decrementWordsLeft();
        visible = false;
        setForeground(Color.green);
        playCorrectSound();
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    // Same as remove(), but makes the word blink red
    // Void method, returns nothing
    private void destroy() {
        level.decrementWordsLeft();
        setForeground(Color.red);
        playIncorrectSound();
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
    }

    // Plays the sound file for an incorrect word
    // Void method, returns nothing
    private void playIncorrectSound() {
        incorrectSound.start();
    }

    // Plays the sound file for a correct word
    // Void method, returns nothing
    private void playCorrectSound() {
        correctSound.start();
    }

    // Changes global variable visibility to be equal to the visible variable in the parameter
    // Void method, returns nothing
    public void changeVisibility(boolean visible) {
        this.visible = visible;
    }
}
