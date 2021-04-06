import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class WinScreen extends JPanel {

    private AudioInputStream winSoundStream;
    private Clip winSound;

    public WinScreen() {
        setLayout(null);
        setPreferredSize(new Dimension(900, 650));
        setBackground(Color.black);
        JLabel gameOverText = new JLabel("Y O U   W O N !");
        add(gameOverText);
        gameOverText.setForeground(Color.green);
        gameOverText.setBounds(325, 200, 400, 50);
        gameOverText.setFont(new Font("DialogInput", Font.BOLD, 30));
        JButton quitButton = new JButton("Quit");
        add(quitButton);
        quitButton.setFocusPainted(false);
        quitButton.setBorderPainted(false);
        quitButton.setBounds(375, 325, 150, 50);
        quitButton.setBackground(Color.white);
        quitButton.setForeground(Color.black);
        quitButton.setFont(new Font("DialogInput", Font.BOLD, 15));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(10);
            }
        });
        try {
            winSoundStream = AudioSystem.getAudioInputStream(new File("../HyperTyperMusic/YouWon.wav"));
            winSound = AudioSystem.getClip();
            winSound.open(winSoundStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Plays the sound file for winning the game
    // Void method, returns nothing
    public void playWinSound() {
        winSound.start();
    }
}
