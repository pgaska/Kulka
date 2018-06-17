package BallGame.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa opisująca kulkę sterowaną przez gracza
 */
public class Ball extends GameObject {
    /**
     * Kolor kulki
     */
    public static final Color color = Constants.ballColor;
    /**
     * prędkość kulki w kierunku pionowym
     */
    private float verticalVelocity;
    /**
     * Maksymalna prędkość w kierunku pionowym, której nie może przekroczyć kulka
     */
    private final float maximumVerticalVelocity = Constants.maximumVerticalVelocity;
    /**
     * Prędkość kulki w kierunku poziomym
     */
    private float horizontalVelocity;
    /**
     * Maksymalna prędkość w kierunku poziomym, której nie może przekroczyć kulka
     */
    private final float maximumHorizontalVelocity = Constants.maximumHorizontalVelocity;
    /**
     * Zmiana pozycji w płaszczyźnie x
     */
    private float dx;
    /**
     * Zmiana pozycji w płaszczyźnie y
     */
    private float dy;
    /**
     * Startowa współrzędna x
     */
    public float normalizedStartX;
    /**
     * Startowa współrzędna y
     */
    public float normalizedStartY;
    /**
     * Stała grawitacji
     */
    public float g;
    /**
     * Czy kulka spowalnia pionowo
     */
    public boolean isSlowingVertical;
    /**
     * Czy kulka przyspiesza pionowo
     */
    public boolean isAcceleratingHorizontal;
    /**
     * Czy kulka porusza się w prawo
     */
    public boolean isMovingRight;
    /**
     * Czy kulka przyspiesza poziomo
     */
    public boolean isAcceleratingVertical;
    /**
     * Czy kulka spowalnia poziomo
     */
    public boolean isSlowingHorizontal;
    /**
     * Konstruktor kulki
     * @param x Współrzędna x
     * @param y Współrzędna y
     */
    public Ball(int x, int y){
        this.x = x;
        this.y = y;
        this.normalizedWidth = Constants.ballRadius;
        this.normalizedHeight = Constants.ballRadius;
        this.width = (int)(normalizedWidth*Constants.mainMenuFrameWidth);
        this.height = (int)(normalizedHeight*Constants.mainMenuFrameHeight);
        this.verticalVelocity = 0;
        this.horizontalVelocity = 0;
        this.dx = 0;
        this.dy = 0;
        isAcceleratingVertical=true;
    }

    /**
     * Metoda symulująca działanie grawitacji na kulkę. Na podstawie stałej grawitacji oblicza o ile przesunie się kulka.
     * Stała grawitacji określa o ile dalej przesunie się kulka w następnej klatce ruchu.
     * Maksymalna prędkość kulki jest ograniczona przez wartość stałą zapisaną w pliku konfiguracyjnym.
     */
    public void fall(){
        if(verticalVelocity<maximumVerticalVelocity)
            verticalVelocity+=g;
        dy += verticalVelocity;
    }

    /**
     * Metoda nadająca przyspieszenie kulce w górę. Oblicza o ile przesunie się kulka w górę
     */
    public void moveUp(){
        if(verticalVelocity>-maximumVerticalVelocity)
            verticalVelocity-=g*2;
        dy += verticalVelocity;
    }

    /**
     * Metoda nadająca kulce przyspieszenie w prawo. Oblicza o ile przesunie się kulka w prawo
     */
    public void moveRight(){
        if(horizontalVelocity<maximumHorizontalVelocity)
            horizontalVelocity+=g;
        dx += horizontalVelocity;
    }

    /**
     * Metoda nadająca kulce przyspieszenie w lewo. Oblicza o ile przesunie się kulka w lewo
     */
    public void moveLeft(){
        if(horizontalVelocity>-maximumHorizontalVelocity)
            horizontalVelocity-=g;
        dx += horizontalVelocity;
    }

    /**
     * Metoda aktualizująca położenie kulki na planszy
     * @param screenWidth szerokość ekranu
     * @param screenHeight wysokość ekranu
     */
    public void updatePosition(int screenWidth, int screenHeight){
        if(isAcceleratingVertical)
            fall();
        if(isSlowingVertical)
            moveUp();
        if(isAcceleratingHorizontal && isMovingRight)
            moveRight();
        if(isAcceleratingHorizontal && !isMovingRight)
            moveLeft();
        if(isSlowingHorizontal){
            if(isMovingRight && horizontalVelocity > 0)
                horizontalVelocity-=0.0004f;
            if(!isMovingRight && horizontalVelocity < 0)
                horizontalVelocity+=0.0004f;
        }
        normalizedX += dx;
        normalizedY += dy;
        x = (int)(normalizedX*screenWidth);
        y = (int)(normalizedY*screenHeight);
    }

    /**
     * Funkcja resetująca położenie kulki
     */
    public void reset(){
        isAcceleratingVertical = true;
        isSlowingVertical = false;
        isAcceleratingHorizontal = false;
        isSlowingHorizontal = false;
        dx=0;
        dy=0;
        verticalVelocity=0;
        horizontalVelocity=0;
        normalizedX=normalizedStartX;
        normalizedY=normalizedStartY;
    }
}
