package BallGame.Game;

import java.awt.Color;

/**
 * Klasa opisująca portal - cel każdego poziomu gry
 */
public class Portal extends GameObject implements CollisionListener{
    /**
     * Kolor portalu
     */
    public static final Color color = Constants.portalColor;

    /**
     * Konstruktor portalu
     * @param x współrzędna x
     * @param y współrzędna y
     */
    public Portal(int x, int y){
        this.x = x;
        this.y = y;
        this.width = (int)(Constants.portalWidth*Constants.mainMenuFrameWidth);
        this.height = (int)(Constants.portalWidth*Constants.mainMenuFrameHeight);
        this.normalizedWidth = Constants.portalWidth;
        this.normalizedHeight = Constants.portalWidth;
    }

    /**
     * Metoda wywoływana po kolizji kulki z portalem
     * @param source główne okno gry
     * @param e przesyłane zdarzenie
     */
    public void collided(GameScreen source, CollisionEvent e){
        source.handleCollision(e);
    }
}
