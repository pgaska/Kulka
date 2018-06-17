package BallGame.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Klasa opisująca gwiazdę
 */
public class Star extends GameObject implements CollisionListener{
    /**
     * Obraz przedstawiający gwiazdę
     */
    public static Image starImage;
    /**
     * Czy została zebrana
     */
    public boolean isCollected;

    /**
     * Konstruktor gwiazdy
     * @param x współrzędna x
     * @param y współrzędna y
     */
    public Star(int x, int y){
        this.x=x;
        this.y=y;
        this.normalizedWidth=Constants.starWidth;
        this.normalizedHeight=Constants.starWidth;
        this.width=(int)(this.normalizedWidth*Constants.mainMenuFrameWidth);
        this.height=(int)(this.normalizedHeight*Constants.mainMenuFrameHeight);
        try {
            starImage = ImageIO.read(new File(Constants.starImagePath));
        }catch(IOException e){
            e.printStackTrace();
        }
        isCollected=false;
    }
    /**
     * Metoda wywoływana po nastąpieniu kolizji
     * @param source główne okno gry
     * @param e przesyłane zdarzenie
     */
    @Override
    public void collided(GameScreen source, CollisionEvent e){
        source.handleCollision(e);
    }
}
