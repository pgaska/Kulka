package BallGame.Game;

import java.awt.*;

/**
 * Klasa opisująca przeszkody
 */
public class Obstacle extends GameObject implements CollisionListener{
    /**
     * Kolor przeszkody
     */
    public static final Color color = Constants.obstacleColor;

    /**
     * Konstruktor domyślny klasy Obstacle
     */
    public Obstacle(){

    }
    /**
     *Konstruktor przeszkody
     * @param x Współrzędna x
     * @param y Współrzędna y
     * @param width Szerokość
     * @param height Wysokość
     */
    public Obstacle(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
