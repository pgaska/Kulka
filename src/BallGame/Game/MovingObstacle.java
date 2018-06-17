package BallGame.Game;

import java.util.Random;

/**
 * Klasa opisująca poruszającą się przeszkodę
 */
public class MovingObstacle extends Obstacle {
    /**
     * Znormalizowana minimalna wartość współrzędnej x
     */
    private float normalizedMinX;
    /**
     * Znormalizowana maksymalna wartość współrzędnej x
     */
    private float normalizedMaxX;
    /**
     * Prędkość przeszkody
     */
    private float velocity;
    /**
     * Flaga określająca czy obiekt porusza się w lewo czy w prawo
     */
    private boolean isMovingLeft;

    /**
     * Konstruktor poruszającej sie przeszkody
     * @param x współrzędna x
     * @param y współrzędna y
     * @param width szerokość
     * @param height wysokość
     * @param normalizedMinX Znormalizowana minimalna wartość współrzędnej x
     * @param normalizedMaxX Znormalizowana maksymalna wartość współrzędnej x
     * @param velocity prędkość
     */
    public MovingObstacle(int x, int y, int width, int height, float normalizedMinX, float normalizedMaxX, float velocity){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalizedMaxX = normalizedMaxX;
        this.normalizedMinX = normalizedMinX;
        this.velocity = velocity;
        isMovingLeft = false;
    }

    /**
     * Metoda aktualizująca pozycję przeszkody
     * @param screenWidth szerokość ekranu
     */
    public void updatePosition(int screenWidth){
        if(isMovingLeft){
            normalizedX-=velocity;
            if(normalizedX<=normalizedMinX)
                isMovingLeft=false;
        }
        else{
            normalizedX+=velocity;
            if(normalizedX>=normalizedMaxX)
                isMovingLeft=true;
        }
        x=(int)(normalizedX*screenWidth);
    }
}
