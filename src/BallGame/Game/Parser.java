package BallGame.Game;

import java.util.*;
import java.io.*;

/**
 * Obiekt parsujący pliki konfiguracyjne z opisem kolejnych poziomów
 */
public class Parser {
    /**
     * Kulka z położeniem wczytanym z pliku
     */
    protected Ball ball = null;
    /**
     * Lista, do której wczytane będą przeszkody
     */
    protected ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    /**
     * Lista, do której wczytane będą poruszające się przeszkody
     */
    protected ArrayList<MovingObstacle> movingObstacles = new ArrayList<>();
    /**
     * Portal z położeniem wczytanym z pliku
     */
    private Portal portal = null;
    /**
     * Gwiazda
     */
    public Star star = null;
    /**
     * Czas potrzebny do uzyskania dodatkowych punktów za poziom
     */
    public double t = 0;
    /**
     * Stała grawitacji
     */
    private float g = 0f;
    /**
     * Konstruktor klasy Parser
     * @param path ścieżka do pliku
     */
    public Parser(String path){
        loadLevelData(path);
    }

    /**
     * Getter kulki
     * @return Kulka
     */
    public Ball getBall(){
        return ball;
    }

    /**
     * Getter przeszkód
     * @return Przeszkody
     */
    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    /**
     * Getter portalu
     * @return portal
     */
    public Portal getPortal(){
        return portal;
    }

    public ArrayList<MovingObstacle> getMovingObstacles(){ return movingObstacles; }
    /**
     * Wczytuje dane o poziomie z pliku
     * @param path ścieżka pliku
     * @return dane w obiekcie typu Properties
     */
    private Properties loadFile(String path){
        Properties data = new Properties();
        try {
            Reader reader = new FileReader(path);
            data.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Wczytuje dane o poziomie
     * @param path ścieżka pliku
     */
    private void loadLevelData(String path){
        Properties p = loadFile(path);
        for(String key : p.stringPropertyNames()){
            String values = p.getProperty(key);
            String[] splitValues = values.split(" ");
            if(key.equals("K")){
                ball = new Ball((int)(Float.parseFloat(splitValues[0])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[1])*Constants.mainMenuFrameHeight));
                ball.normalizedX = Float.parseFloat(splitValues[0]);
                ball.normalizedStartX = ball.normalizedX;
                ball.normalizedY = Float.parseFloat(splitValues[1]);
                ball.normalizedStartY = ball.normalizedY;
            } else if (key.substring(0,1).equals("P")) {
                Obstacle o = new Obstacle((int)(Float.parseFloat(splitValues[0])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[1])*Constants.mainMenuFrameHeight),
                        (int)(Float.parseFloat(splitValues[2])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[3])*Constants.mainMenuFrameHeight));
                o.normalizedX = Float.parseFloat(splitValues[0]);
                o.normalizedY = Float.parseFloat(splitValues[1]);
                o.normalizedWidth = Float.parseFloat(splitValues[2]);
                o.normalizedHeight = Float.parseFloat(splitValues[3]);
                obstacles.add(o);
            } else if (key.equals("W")){
                portal = new Portal((int)(Float.parseFloat(splitValues[0])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[1])*Constants.mainMenuFrameHeight));
                portal.normalizedX = Float.parseFloat(splitValues[0]);
                portal.normalizedY = Float.parseFloat(splitValues[1]);
            }else if(key.equals("G")){
                g = Float.parseFloat(splitValues[0]);
            }
            else if(key.substring(0,1).equals("S")){
                star = new Star((int)(Float.parseFloat(splitValues[0])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[1])*Constants.mainMenuFrameHeight));
                star.normalizedX=Float.parseFloat(splitValues[0]);
                star.normalizedY=Float.parseFloat(splitValues[1]);
            }
            else if(key.substring(0,1).equals("M")){
                MovingObstacle m = new MovingObstacle((int)(Float.parseFloat(splitValues[0])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[1])*Constants.mainMenuFrameHeight),
                        (int)(Float.parseFloat(splitValues[2])*Constants.mainMenuFrameWidth), (int)(Float.parseFloat(splitValues[3])*Constants.mainMenuFrameHeight),
                        Float.parseFloat(splitValues[4]), Float.parseFloat(splitValues[5]), Float.parseFloat(splitValues[6]));
                m.normalizedX = Float.parseFloat(splitValues[0]);
                m.normalizedY = Float.parseFloat(splitValues[1]);
                m.normalizedWidth = Float.parseFloat(splitValues[2]);
                m.normalizedHeight = Float.parseFloat(splitValues[3]);
                movingObstacles.add(m);
            }
            else if(key.equals("T")){
                t = Double.parseDouble(splitValues[0]);
            }
        }
        ball.g = g;
    }
}
