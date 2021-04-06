import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    private AudioInputStream audioStream;
    private Clip backgroundMusic;
    private Level nextLevel;


    public MainMenu(Level nextLevel) {
        this.nextLevel = nextLevel;
        setLayout(null);
        setPreferredSize(new Dimension(900, 650));
        setBackground(Color.black);
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("DialogInput", Font.BOLD, 20));
        startButton.setBounds(390, 300, 120, 40);
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("DialogInput", Font.BOLD, 20));
        quitButton.setBounds(400, 370, 100, 40);
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFocusPainted(false);
        quitButton.setBorderPainted(false);
        add(startButton);
        add(quitButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopBackgroundMusic();
                HyperTyper.cardLayout.show(HyperTyper.cardPanel, nextLevel.getLevelName());
                nextLevel.setUp();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JLabel gameTitle = new JLabel("Hyper Typer");
        gameTitle.setForeground(Color.white);
        gameTitle.setBounds(350, 150, 200, 50);
        gameTitle.setFont(new Font("DialogInput", Font.BOLD, 30));
        add(gameTitle);
        try {
            audioStream = AudioSystem.getAudioInputStream(
                    new File("HyperTyperMusic/mainmenu.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playBackgroundMusic();
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
}
