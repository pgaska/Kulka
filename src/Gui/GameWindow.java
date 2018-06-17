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

    public final GameScreen gameScreen;

    public JLayeredPane lp;
    /**
     * Ekran wyświetlany w trakcie pauzy
     */
    public PauseScreen pauseScreen;

    /**
     * Konstruktor okna gry.
     */
    public GameWindow() {
        lp = new JLayeredPane();
        this.setTitle(Constants.gameTitle);
        this.setPreferredSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        gameScreen = new GameScreen(this);
        gameScreen.setSize(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight);
        PauseMenuBar pauseMenuBar = new PauseMenuBar(gameScreen);
        this.add(pauseMenuBar, BorderLayout.PAGE_START);
        this.add(gameScreen);
        this.addKeyListener(gameScreen);
        this.add(lp);
        lp.add(gameScreen, Integer.valueOf(1));
        lp.setPreferredSize(new Dimension(Constants.mainMenuFrameWidth,Constants.mainMenuFrameHeight));
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
        (gameScreen.kicker = new Thread(gameScreen)).start();
    }

}
