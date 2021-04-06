import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class Level extends JPanel implements KeyListener {

    private String levelName;
    private String wordFileName;
    private String audioFileName;
    private ArrayList<HyperTyperWord> words = new ArrayList<>();
    private int numWords;
    private int wordIndex = 0;
    private int spawnRate;
    private int moveSpeed;
    private int lives;

    private AudioInputStream backgroundStream;
    private Clip backgroundMusic;
    private AudioInputStream incorrectStream;
    private Clip incorrectSound;

    private JTextField textField;
    private JLabel numLives;

    private Level nextLevel;
    boolean isThereNextLevel;

    private WinScreen winScreen;

    public Level(String levelName, String wordFileName, String audioFileName, int numWords,
                 int moveSpeed, int spawnRate, int lives, WinScreen winScreen) {
        this.levelName = levelName;
        this.wordFileName = wordFileName;
        this.audioFileName = audioFileName;
        this.numWords = numWords;
        this.spawnRate = spawnRate;
        this.moveSpeed = moveSpeed;
        this.lives = lives;
        this.nextLevel = nextLevel;
        this.winScreen = winScreen;
        this.isThereNextLevel = false;
        setLayout(null);
        setFocusable(true);
        setPreferredSize(new Dimension(900, 650));
        setBackground(Color.black);
        JLabel levelLabel = new JLabel(levelName);
        add(levelLabel);
        levelLabel.setBounds(385, 10, 200, 50);
        levelLabel.setForeground(Color.white);
        levelLabel.setFont(new Font("DialogInput", Font.BOLD, 30));
        textField = new JTextField();
        textField.setBackground(Color.black);
        textField.setBounds(400, 560, 100, 25);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setCaretColor(Color.white);
        textField.setForeground(Color.white);
        textField.addKeyListener(this);
        add(textField);
        JLabel livesText = new JLabel("Lives: ");
        livesText.setFont(new Font("Arial", Font.BOLD, 20));
        livesText.setBounds(700, 15, 100, 50);
        livesText.setForeground(Color.white);
        add(livesText);
        numLives = new JLabel(Integer.toString(lives));
        numLives.setFont(new Font("Arial", Font.BOLD, 20));
        numLives.setForeground(Color.red);
        numLives.setBounds(800, 15, 100, 50);
        add(numLives);
        try {
            backgroundStream = AudioSystem.getAudioInputStream(new File(audioFileName));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(backgroundStream);
            incorrectStream = AudioSystem.getAudioInputStream(new File("HyperTyperMusic/incorrect.wav"));
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

    public Level(String levelName, String wordFileName, String audioFileName, int numWords,
                 int moveSpeed, int spawnRate, int lives, Level nextLevel) {
        this.levelName = levelName;
        this.wordFileName = wordFileName;
        this.audioFileName = audioFileName;
        this.numWords = numWords;
        this.spawnRate = spawnRate;
        this.moveSpeed = moveSpeed;
        this.lives = lives;
        this.nextLevel = nextLevel;
        this.isThereNextLevel = true;
        setLayout(null);
        setFocusable(true);
        setPreferredSize(new Dimension(900, 650));
        setBackground(Color.black);
        JLabel levelLabel = new JLabel(levelName);
        add(levelLabel);
        levelLabel.setBounds(385, 10, 200, 50);
        levelLabel.setForeground(Color.white);
        levelLabel.setFont(new Font("DialogInput", Font.BOLD, 30));
        textField = new JTextField();
        textField.setBackground(Color.black);
        textField.setBounds(400, 560, 100, 25);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setCaretColor(Color.white);
        textField.setForeground(Color.white);
        textField.addKeyListener(this);
        add(textField);
        JLabel livesText = new JLabel("Lives: ");
        livesText.setFont(new Font("Arial", Font.BOLD, 20));
        livesText.setBounds(700, 15, 100, 50);
        livesText.setForeground(Color.white);
        add(livesText);
        numLives = new JLabel(Integer.toString(lives));
        numLives.setFont(new Font("Arial", Font.BOLD, 20));
        numLives.setForeground(Color.red);
        numLives.setBounds(800, 15, 100, 50);
        add(numLives);
        try {
            backgroundStream = AudioSystem.getAudioInputStream(new File(audioFileName));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(backgroundStream);
            incorrectStream = AudioSystem.getAudioInputStream(new File("HyperTyperMusic/incorrect.wav"));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawLine(400, 587, 500, 587);
    }

    // Called once the level is shown on the JPanel
    // Starts the background music, reads a random file from the
    // corresponding folder for the level, adds a new word
    // to the HyperTyper array list, and spawns words every spawnRate seconds
    // Void method, returns nothing
    public void setUp() {
        playBackgroundMusic();
        try {
            int randFileIndex = (int) ((Math.random() * 10)) + 1;
            BufferedReader br = new BufferedReader(new FileReader(wordFileName + "Words" + randFileIndex + ".txt"));
            for(int i = 0; i < numWords; i++) {
                String word = br.readLine();
                HyperTyperWord hyperTyperWord = new HyperTyperWord(word, moveSpeed, this);
                words.add(hyperTyperWord);
                add(hyperTyperWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(wordIndex < words.size()) {
                    words.get(wordIndex).start();
                    wordIndex++;
                } else {
                    t.cancel();
                    t.purge();
                }
            }
        }, 0, spawnRate);
    }

    // Plays the background music
    // Void method, returns nothing
    private void playBackgroundMusic() {
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Stops the background music
    // Void method, returns nothing
    private void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    // Plays the sound file for an incorrect word
    // Void method, returns nothing
    private void playIncorrectSound() {
        incorrectSound.setFramePosition(0);
        incorrectSound.start();
    }

    // Deducts a life from lives
    // Void method, returns nothing
    public void loseLife() {
        lives--;
        if(lives >= 0) {
            numLives.setText(Integer.toString(lives));
            if(lives == 0) {
                gameOver();
            }
        }
    }

    // Stops background music, changes all HyperTyper words visibility to false, and transitions to gameover screen
    // Void method, returns nothing
    private void gameOver() {
        stopBackgroundMusic();
        for(HyperTyperWord word : words) {
            word.changeVisibility(false);
        }
        HyperTyper.cardLayout.show(HyperTyper.cardPanel, "GameOver");
    }

    // Decrements the number of words left, if there are no more words left and there is a next leve, nextLevel() is called.
    // If there are no more levels left, showWinScreen() is called.
    // Void methods=, returns nothing.
    public void decrementWordsLeft() {
        numWords--;
        if(numWords == 0) {
            if(isThereNextLevel) {
                nextLevel();
            } else {
                showWinScreen();
            }
        }
    }

    // Stops background music, shows the next level, and sets up the next level.
    // Void method, returns nothing.
    private void nextLevel() {
        stopBackgroundMusic();
        HyperTyper.cardLayout.show(HyperTyper.cardPanel, nextLevel.getLevelName());
        nextLevel.setUp();
    }

    // Stops background music, shows the win screen, and plays the winning sound.
    private void showWinScreen() {
        stopBackgroundMusic();
        HyperTyper.cardLayout.show(HyperTyper.cardPanel, "WinScreen");
        winScreen.playWinSound();
    }

    // Returns the level name
    public String getLevelName() {
        return this.levelName;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            String enteredText = textField.getText();
            textField.setText("");
            HyperTyperWord word = lookForWord(enteredText);
            if(word != null) {
                word.remove();
            } else {
                playIncorrectSound();
            }
        }
    }

    // String word represents the word that it will look for
    // Goes through each HyperTyper word in the HyperTyper word array list and returns the HyperTyper word that has the same string word value as the parameter
    // If it can't find a HyperTyper word with a matching string word value, then it returns null
    private HyperTyperWord lookForWord(String word) {
        for(HyperTyperWord w : words) {
            if(w.getText().equals(word)) {
                return w;
            }
        }
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
