package BallGame.Game;

import Gui.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.awt.event.*;

/**
 * Klasa opisująca panel prezentjący aktualną rozgrywkę
 */
public class GameScreen extends JPanel implements Runnable, KeyListener,CollisionListener, ActionListener{
    /**
     * Główne okno
     */
    private GameWindow gameWindow;
    /**
     * Kulka, którą sterujemy w grze
     */
    public Ball ball;
    /**
     * Tablica przeszkód
     */
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    /**
     * Tablica poruszających się przeszkód
     */
    private ArrayList<MovingObstacle> movingObstacles = new ArrayList<>();
    /**
     * Gwiazda
     */
    private Star star;
    /**
     * Wyjście z poziomu
     */
    private Portal portal;
    /**
     * Aktualna ilość żyć
     */
    private int currentLives;
    /**
     * Aktualna liczba punktów
     */
    private int currentPoints;
    /**
     * Aktualny poziom
     */
    private int currentLevel;
    /**
     * Panel z informacjami
     */
    private JPanel textGUI = new JPanel();
    /**
     * Licznik
     */
    private JLabel timeLabel = new JLabel (Constants.timeLabel + Constants.initialTime);
    /**
     * Pole wyświetlające ilość zdobytych punktów
     */
    private JLabel pointsLabel = new JLabel (Constants.pointsLabel + Constants.initialPoints);
    /**
     * Pole wyświetlające pozostałe życia
     */
    private JLabel lifeLabel = new JLabel (Constants.lifeLabelText + Constants.initialLives);
    /**
     * Czas pomiędzy rysowaniem klatek animacji
     */
    private final int timeBetweenFrames = 20;
    /**
     * Bufor do przechowywania zawartości ekranu
     */
    private Image offScreen = null;
    /**
     * Główny wątek programu
     */
    public Thread kicker = null;
    /**
     * Ilość zebranych gwiaxdek
     */
    private int collectedStars;
    /**
     * Czy gra jest zatrzymana
     */
    public boolean isPaused;
    /**
     * Zmienna zliczająca czas
     */
    private double timer;
    /**
     * Czas potrzebny do uzyskania dodatkowych punktów za poziom
     */
    private double timeForBonus;
    /**
     * Obraz wyświetlany w trakcie pauzy
     */
    private Image pauseImage;

    /**
     * Konstruktor głównego panelu gry. Przypisuje wartości zmiennym i dodaje GUI.
     * @param gameWindow główne okno gry
     */
    public GameScreen(GameWindow gameWindow) {
        this.setPreferredSize(new Dimension(Constants.mainMenuFrameWidth,Constants.mainMenuFrameHeight));
        this.gameWindow = gameWindow;
        isPaused=false;
        try {
            pauseImage = ImageIO.read(new File("Images\\pauza.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setBackground(Color.white);
        timer=Constants.initialTime;
        collectedStars=0;
        currentLevel=1;
        currentLives = Constants.initialLives;
        currentPoints = Constants.initialPoints;
        drawFirstLevel();
    }

    /**
     * Rysuje makietę
     * @param g kontekst graficzny
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(ball != null){
            g.setColor(Ball.color);
            g.fillOval(ball.x, ball.y, ball.width, ball.height);
        }
        if(!obstacles.isEmpty()){
            g.setColor(Obstacle.color);
            for(Obstacle o : obstacles){
                g.fillRect(o.x, o.y, o.width, o.height);
            }
        }
        if(portal != null){
            g.setColor(Portal.color);
            g.fillRect(portal.x, portal.y, portal.width, portal.height);
        }
        if(star != null && !star.isCollected){
            g.drawImage(Star.starImage, star.x, star.y, star.width, star.height, this);
        }
        if(!movingObstacles.isEmpty()){
            g.setColor(MovingObstacle.color);
            for(MovingObstacle m : movingObstacles){
                g.fillRect(m.x, m.y, m.width, m.height);
            }
        }
        if(isPaused){
            g.drawImage(pauseImage, (int)((Constants.mainMenuFrameWidth/2)-(Constants.mainMenuFrameWidth/4)),
                    (int)((Constants.mainMenuFrameHeight/2)-(Constants.mainMenuFrameHeight/8)), (int)(Constants.mainMenuFrameHeight/2),
                    (int)(Constants.mainMenuFrameHeight/4), this);
        }

    }

    /**
     * Metoda rysująca pierwszy poziom gry
     */
    private void drawFirstLevel(){
        Parser parser = new Parser(Constants.filePath+1+".txt");
        if(parser.getBall() != null){
            ball = parser.getBall();
        }
        if(!parser.getObstacles().isEmpty()){
            obstacles = parser.getObstacles();
        }
        if(parser.getPortal() != null){
            portal = parser.getPortal();
        }
        if(parser.star!=null){
            star = parser.star;
        }
        if(!parser.getMovingObstacles().isEmpty()){
            movingObstacles = parser.getMovingObstacles();
        }
        timeForBonus = parser.t;
        textGUI.setLayout(new BoxLayout(textGUI, BoxLayout.PAGE_AXIS));
        textGUI.add(timeLabel);
        textGUI.add(lifeLabel);
        textGUI.add(pointsLabel);
        this.add(textGUI);
        this.setBorder(BorderFactory.createEmptyBorder(10,0,0,-500));
        (kicker = new Thread(this)).start();
    }

    /**
     * Metoda rysująca kolejną klatkę do bufora po zmianie rozmiaru okna
     * @param w szerokość ekranu
     * @param h wysokość ekranu
     */
    public void updateOffscreenSize(final int w, final int h) {
//        if (kicker != null) {
//            Thread k = kicker;
//            kicker = null;
//            k.interrupt();
//        }
        kicker = null;
        ball.resize(w, h);
        portal.resize(w, h);
        for(Obstacle o : obstacles){
            o.resize(w, h);
        }
        star.resize(w, h);
        for(MovingObstacle o :movingObstacles){
            o.resize(w, h);
        }
        (kicker = new Thread(this)).start();
    }

    /**
     * Metoda opisująca główny wątek programu
     */
    public void run() {
        while (kicker == Thread.currentThread()) {
            ball.updatePosition(this.getWidth(), this.getHeight());
            for(MovingObstacle m : movingObstacles){
                m.updatePosition(this.getWidth());
            }
            repaint();
            try {
                Thread.sleep(timeBetweenFrames);
            } catch (InterruptedException ie) {

            }
            isCollidingWithObstacle();
            isCollidingWithStar();
            isCollidingWithPortal();
            timer+=timeBetweenFrames*0.001f;
            BigDecimal bd = new BigDecimal(timer);
            timeLabel.setText(Constants.timeLabel + bd.setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }
    }

    /**
     * Metoda sprawdzająca czy kulka uderza w przeszkodę
     */
    private void isCollidingWithObstacle(){
        if(ball.x<=0 || ball.y <=0 || ball.x>=getWidth()-ball.width || ball.y>=getHeight()-ball.height){
            collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));
        }
        for(Obstacle o : obstacles){
            if(ball.x+ball.width/2>=o.x && ball.x+ball.width/2<=o.x+o.width && ball.y+ball.height>=o.y && ball.y+ball.height<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x+ball.width/2>=o.x && ball.x+ball.width/2<=o.x+o.width && ball.y>=o.y && ball.y<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x+ball.width>=o.x && ball.x+ball.width<=o.x+o.width && ball.y+ball.height/2>=o.y && ball.y+ball.height/2<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x>=o.x && ball.x<=o.x+o.width && ball.y+ball.height/2>=o.y && ball.y+ball.height/2<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
        }
        for(Obstacle o : movingObstacles){
            if(ball.x+ball.width/2>=o.x && ball.x+ball.width/2<=o.x+o.width && ball.y+ball.height>=o.y && ball.y+ball.height<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x+ball.width/2>=o.x && ball.x+ball.width/2<=o.x+o.width && ball.y>=o.y && ball.y<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x+ball.width>=o.x && ball.x+ball.width<=o.x+o.width && ball.y+ball.height/2>=o.y && ball.y+ball.height/2<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
            if(ball.x>=o.x && ball.x<=o.x+o.width && ball.y+ball.height/2>=o.y && ball.y+ball.height/2<=o.y+o.height){
                o.collided(this, new CollisionEvent(CollisionEvent.CollisionType.OBSTACLE));            }
        }
    }

    /**
     * Metoda sprawdzająca czy kulka uderza w gwiazdę
     */
    private void isCollidingWithStar(){
        if(star!=null && !star.isCollected){
            if(ball.x+ball.width/2>=star.x && ball.x+ball.width/2<=star.x+star.width && ball.y+ball.height>=star.y && ball.y+ball.height<=star.y+star.height){
                star.collided(this, new CollisionEvent(CollisionEvent.CollisionType.STAR));            }
            if(ball.x+ball.width/2>=star.x && ball.x+ball.width/2<=star.x+star.width && ball.y>=star.y && ball.y<=star.y+star.height){
                star.collided(this, new CollisionEvent(CollisionEvent.CollisionType.STAR));            }
            if(ball.x+ball.width>=star.x && ball.x+ball.width<=star.x+star.width && ball.y+ball.height/2>=star.y && ball.y+ball.height/2<=star.y+star.height){
                star.collided(this, new CollisionEvent(CollisionEvent.CollisionType.STAR));            }
            if(ball.x>=star.x && ball.x<=star.x+star.width && ball.y+ball.height/2>=star.y && ball.y+ball.height/2<=star.y+star.height){
                star.collided(this, new CollisionEvent(CollisionEvent.CollisionType.STAR));            }
        }
    }

    /**
     *Metoda sprawdzajaca czy kulka uderza w portal
     */
    private void isCollidingWithPortal(){
        if(ball.x+ball.width/2>=portal.x && ball.x+ball.width/2<=portal.x+portal.width && ball.y+ball.height>=portal.y && ball.y+ball.height<=portal.y+portal.height){
            portal.collided(this, new CollisionEvent(CollisionEvent.CollisionType.PORTAL));
        }
        if(ball.x+ball.width/2>=portal.x && ball.x+ball.width/2<=portal.x+portal.width && ball.y>=portal.y && ball.y<=portal.y+portal.height){
            portal.collided(this, new CollisionEvent(CollisionEvent.CollisionType.PORTAL));
        }
        if(ball.x+ball.width>=portal.x && ball.x+ball.width<=portal.x+portal.width && ball.y+ball.height/2>=portal.y && ball.y+ball.height/2<=portal.y+portal.height){
            portal.collided(this, new CollisionEvent(CollisionEvent.CollisionType.PORTAL));
        }
        if(ball.x>=portal.x && ball.x<=portal.x+portal.width && ball.y+ball.height/2>=portal.y && ball.y+ball.height/2<=portal.y+portal.height){
            portal.collided(this, new CollisionEvent(CollisionEvent.CollisionType.PORTAL));
        }
    }

    /**
     * Metoda wywoływana po zakończeniu poziomu
     */
    private void finishLevel(){
        currentPoints += 100;
        if(timer<timeForBonus){
            currentPoints += (int)((timeForBonus-timer)*10);
        }
        timer = 0;
        pointsLabel.setText(Constants.pointsLabel + currentPoints);
        if(currentLevel==Constants.numberOfLevels){
            gameOver();
        }
        else {
            loadNextLevel();
        }
    }

    /**
     * Metoda wczytująca kolejny poziom
     */
    private void loadNextLevel(){
        currentLevel++;
        cleanLevel();
        if(currentLevel<=Constants.numberOfLevels){
            Parser parser = new Parser(Constants.filePath+currentLevel+".txt");
            if(parser.getBall() != null){
                ball = parser.getBall();
                ball.normalizedStartX=ball.normalizedX;
                ball.normalizedStartY=ball.normalizedY;
            }
            if(!parser.getObstacles().isEmpty()){
                obstacles = parser.getObstacles();
            }
            if(parser.getPortal() != null){
                portal = parser.getPortal();
            }
            if(parser.star!=null){
                star = parser.star;
            }
            if(!parser.getMovingObstacles().isEmpty()){
                movingObstacles = parser.getMovingObstacles();
            }
        }
    }

    /**
     * Usuwa poziom przed wczytaniem kolejnego
     */
    private void cleanLevel(){
        ball = null;
        obstacles.clear();
        portal = null;
        star = null;
        movingObstacles.clear();
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
            ball.isAcceleratingVertical = false;
            ball.isSlowingVertical = true;
            ball.moveUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            ball.isAcceleratingHorizontal = true;
            ball.isSlowingHorizontal = false;
            ball.isMovingRight = true;
            ball.moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            ball.isAcceleratingHorizontal = true;
            ball.isSlowingHorizontal = false;
            ball.isMovingRight = false;
            ball.moveLeft();
        }
    }
    /**
     * Metoda opisująca zdarzenie po zwolnieniu przycisku
     * @param e Zdarzenie związane ze zwolnieniem przycisku
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            ball.isAcceleratingVertical = true;
            ball.isSlowingVertical = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ball.isAcceleratingHorizontal = false;
            ball.isSlowingHorizontal = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            ball.isAcceleratingHorizontal = false;
            ball.isSlowingHorizontal = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
            if(!isPaused){
                pauseGame();
            }else if(isPaused){
                unPauseGame();
            }
        }
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

    /**
     * Metoda opisująca zachowanie po wystąpieniu kolizji
     * @param collisionEvent zdarzenie związane z kolizją
     */
    public void handleCollision(CollisionEvent collisionEvent){
        if(collisionEvent.getCollisionType() == CollisionEvent.CollisionType.OBSTACLE){
            looseLife();
        }
        if(collisionEvent.getCollisionType() == CollisionEvent.CollisionType.STAR){
            collectStar();
        }
        if(collisionEvent.getCollisionType() == CollisionEvent.CollisionType.PORTAL){
            finishLevel();
        }
    }

    /**
     * Metoda reagująca na zdarzenia związane z wciśnięciem przycisków
     * @param e Obiekt zdarzenia
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() instanceof JMenuItem){
            if(!isPaused){
                pauseGame();
            }
            else {
                unPauseGame();
            }
        }
    }

    /**
     * Metoda wywoływana po zebraniu gwiazdy.
     */
    private void collectStar(){
        star.isCollected=true;
        collectedStars++;
        if(collectedStars==3){
            collectedStars=0;
            currentLives++;
            lifeLabel.setText(Constants.lifeLabelText + currentLives);
        }
    }

    /**
     * Metoda wywoływana po straceniu jednego życia
     */
    private void looseLife(){
        currentLives--;
        lifeLabel.setText(Constants.lifeLabelText + currentLives);
        if(currentLives>=1){
            ball.reset();
        }
        else{
            gameOver();
        }
    }

    /**
     * Metoda wywoływaa po przegraniu gry. Dodaje punkty za zachowanie żyć i wyświetla okno dialogowe proszące o podanie nicku
     */
    private void gameOver(){
        kicker = null;
        isPaused = true;
        if(currentLives == 1)
            currentLives--;
        currentPoints+=currentLives*Constants.pointsForLive;
        new GameOverDialog(currentPoints);
        new MenuWindow();
        gameWindow.dispose();
    }

    /**
     * Metoda zatrzymująca grę. Wyświetla panel pauzy
     */
    private void pauseGame(){
        isPaused = true;
        kicker = null;
        repaint();
    }

    /**
     * Metoda kończąca pauzę i wznawiająca grę.
     */
    private void unPauseGame(){
        isPaused = false;
        (kicker = new Thread(this)).start();
    }
}