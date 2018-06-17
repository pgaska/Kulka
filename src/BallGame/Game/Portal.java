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

    public Portal(int x, int y){
        this.x = x;
        this.y = y;
        this.width = (int)(Constants.portalWidth*Constants.mainMenuFrameWidth);
        this.height = (int)(Constants.portalWidth*Constants.mainMenuFrameHeight);
        this.normalizedWidth = Constants.portalWidth;
        this.normalizedHeight = Constants.portalWidth;
    }

    public void collided(GameScreen source, CollisionEvent e){
        source.handleCollision(e);
    }
}
