package Gui;

import BallGame.Game.Constants;
import BallGame.Game.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseScreen extends JPanel implements ActionListener{
    public JButton resumeButton;
    public JButton exitButton;
    private GameScreen gameScreen;

    public PauseScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        resumeButton = new JButton("Wznowienie");
        resumeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resumeButton.setBackground(Color.WHITE);
        resumeButton.addActionListener(this);
        exitButton = new JButton("Wyjscie");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBackground(Color.WHITE);
        exitButton.addActionListener(this);
        this.add(resumeButton);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == resumeButton || e.getSource() == exitButton){
            gameScreen.actionPerformed(e);
        }
    }
}
