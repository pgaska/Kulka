package BallGame.Game;
/**
 * Abstrakcyjna klasa opisująca parametry obiektów w grze
 */
public abstract class GameObject {
    /**
     * Współrzędna x obiektu
     */
    public int x;
    /**
     * Współrzędna y obiektu
     */
    public int y;
    /**
     * Szerokość obiektu
     */
    public int width;
    /**
     * wysokość obiektu
     */
    public int height;
    /**
     * Znormalizowana współrzędna x
     */
    public float normalizedX;
    /**
     * Znormalizowana współrzędna y
     */
    public float normalizedY;
    /**
     * Znormalizowana szerokość
     */
    public float normalizedWidth;
    /**
     * Znormalizowana wysokość
     */
    public float normalizedHeight;
    /**
     * Metoda dostosowująca położenie i rozmiar obiektu po zmianie wielkości ekranu
     * @param screenWidth szerokość ekranu
     * @param screenHeight wysokość ekranu
     */
    public void resize(int screenWidth, int screenHeight){
        x = (int)(normalizedX*screenWidth);
        y = (int)(normalizedY*screenHeight);
        width = (int)(normalizedWidth*screenWidth);
        height = (int)(normalizedHeight*screenHeight);
    }
}
