package BallGame.Game;

import java.util.EventListener;

/**
 * Interfejs klas nasłuchujących, czy wydarzyła się kolizja
 */
public interface CollisionListener extends EventListener {
    /**
     * Metoda wywoływana po nastąpieniu kolizji
     * @param source główne okno gry
     * @param e przesyłane zdarzenie
     */
    void collided(GameScreen source, CollisionEvent e);
}
