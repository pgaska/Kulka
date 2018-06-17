package BallGame.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa opisująca kulkę sterowaną przez gracza
 */
public class Ball extends GameObject implements KeyListener{
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
    private final float maximumVerticalVelocity = 0.0005f;
    /**
     * Prędkość kulki w kierunku poziomym
     */
    private float horizontalVelocity;
    /**
     * Maksymalna prędkość w kierunku poziomym, której nie może przekroczyć kulka
     */
    private final float maximumHorizontalVelocity = 0.0005f;
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
    private boolean isSlowingVertical;
    /**
     * Czy kulka przyspiesza pionowo
     */
    private boolean isAcceleratingHorizontal;
    /**
     * Czy kulka porusza się w prawo
     */
    private boolean isMovingRight;
    /**
     * Czy kulka przyspiesza poziomo
     */
    private boolean isAcceleratingVertical;
    /**
     * Czy kulka spowalnia poziomo
     */
    private boolean isSlowingHorizontal;
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
     * Metoda symulująca działanie grawitacji na kulkę. Korzysta ze wzoru na drogę w ruchu jednostajnie przyspieszonym
     */
    public void fall(){
        if(verticalVelocity<maximumVerticalVelocity)
            verticalVelocity+=g;
        dy += verticalVelocity;
    }

    /**
     * Metoda nadająca przyspieszenie kulce w górę
     */
    private void moveUp(){
        if(verticalVelocity>-maximumVerticalVelocity)
            verticalVelocity-=g*2;
        dy += verticalVelocity;
    }

    /**
     * Metoda nadająca kulce przyspieszenie w prawo
     */
    private void moveRight(){
        if(horizontalVelocity<maximumHorizontalVelocity)
            horizontalVelocity+=g;
        dx += horizontalVelocity;
    }

    /**
     * Metoda nadająca kulce przyspieszenie w lewo
     */
    private void moveLeft(){
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
     * Metoda opisująca zdarzenie po wciśnięciu przycisku
     * @param e Zdarzenie związane z wciśnięciem przycisku
     */
    @Override
    public void keyTyped(KeyEvent e){
    }

    /**
     * Metoda opisująca zdarzenie po wciśnięciu przycisku
     * @param e Zdarzenie związane z wciśnięciem przycisku
     */
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
            isAcceleratingVertical = false;
            isSlowingVertical = true;
            moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            isAcceleratingHorizontal = true;
            isSlowingHorizontal = false;
            isMovingRight = true;
            moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            isAcceleratingHorizontal = true;
            isSlowingHorizontal = false;
            isMovingRight = false;
            moveLeft();
        }
    }

    /**
     * Metoda opisująca zdarzenie po zwolnieniu przycisku
     * @param e Zdarzenie związane ze zwolnieniem przycisku
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            isAcceleratingVertical = true;
            isSlowingVertical = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            isAcceleratingHorizontal = false;
            isSlowingHorizontal = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            isAcceleratingHorizontal = false;
            isSlowingHorizontal = true;
        }
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
