import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JPanel {

    public GameOver() {
        setLayout(null);
        setPreferredSize(new Dimension(900, 650));
        setBackground(Color.black);
        JLabel gameOverText = new JLabel("G A M E   O V E R");
        add(gameOverText);
        gameOverText.setForeground(Color.red);
        gameOverText.setBounds(300, 200, 400, 50);
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
    }


}
