package BallGame.Game;

/**
 * Zdarzenie związane z kolizją
 */
public class CollisionEvent{
    /**
     * Zmienna wyliczeniowa opisująca typ obiektu, z którym zderzyła się kulka
     */
    public enum CollisionType{
        OBSTACLE, PORTAL, STAR
    }
    /**
     * Zmienna opisująca typ obiektu, z którym zderzyła się kulka
     */
    private CollisionType type;

    /**
     * Konstruktor zdarzenia opisującego kolizję
     * @param type typ kolizji
     */
    public CollisionEvent(CollisionType type){
        this.type = type;
    }

    /**
     * Metoda zwracająca typ kolizji
     * @return typ kolizji
     */
    public CollisionType getCollisionType(){
        return type;
    }
}
