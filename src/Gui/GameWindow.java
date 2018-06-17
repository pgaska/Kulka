package Gui;

import BallGame.Game.Constants;
import BallGame.Game.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Okno gry
 */
public class GameWindow extends JFrame{
    /**
     * Panel, na którym toczy się gra
     */
    public final GameScreen gameScreen;

    /**
     * Konstruktor okna gry.
     */
    public GameWindow() {
        this.setTitle(Constants.gameTitle);
        this.setPreferredSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        gameScreen = new GameScreen(this);
        gameScreen.setSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        PauseMenuBar pauseMenuBar = new PauseMenuBar(gameScreen);
        this.add(pauseMenuBar, BorderLayout.PAGE_START);
        this.add(gameScreen);
        this.addKeyListener(gameScreen);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent ce) {
                gameScreen.updateOffscreenSize(ce.getComponent().getWidth(), ce.getComponent().getHeight());
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowIconified(WindowEvent we) {
                gameScreen.kicker = null;
            }

            public void windowDeiconified(WindowEvent we) {
                (gameScreen.kicker = new Thread(gameScreen)).start();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
