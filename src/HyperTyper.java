// Langstaff Secondary School
// June 14th, 2019
// Jordan Na and Jeremy Goldberg
// This program is a game in which words fly across the screen from the left to the right. The user must type the words
// that are flying across the screen before it hits the right side. The user has a number of lives each level and once
// there are no more lives the game is over. One life is deducted every time a word hits the right side of the screen.
// The speed, complexity, number of lives, all vary depending on the level.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HyperTyper extends JFrame {

    static CardLayout cardLayout = new CardLayout();
    static JPanel cardPanel = new JPanel(cardLayout);

    public HyperTyper() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        WinScreen winScreen = new WinScreen();
        Level levelFour = new Level("Impossible", "WordFiles/LevelFourWords/", "HyperTyperMusic/impossible.wav",
                15, 10, 1100, 3, winScreen);
        Level levelThree = new Level("Level 3", "WordFiles/LevelThreeWords/", "HyperTyperMusic/hard.wav",
                15, 10, 1100, 3, levelFour);
        Level levelTwo = new Level("Level 2", "WordFiles/LevelTwoWords/", "HyperTyperMusic/medium.wav",
                15, 12, 1300, 4, levelThree);
        Level levelOne = new Level("Level 1", "WordFiles/LevelOneWords/", "HyperTyperMusic/easy.wav",
                10, 15, 1500, 5, levelTwo);
        MainMenu mainMenu = new MainMenu(levelOne);
        GameOver gameOver = new GameOver();
        cardPanel.add(mainMenu, "MainMenu");
        cardPanel.add(levelOne, "Level 1");
        cardPanel.add(levelTwo, "Level 2");
        cardPanel.add(levelThree, "Level 3");
        cardPanel.add(levelFour, "Impossible");
        cardPanel.add(winScreen, "WinScreen");
        cardPanel.add(gameOver, "GameOver");
        add(cardPanel);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new HyperTyper();
    }
}