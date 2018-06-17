package BallGame.Game;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;

/**
 * Stałe parametry programu
 */
public final class Constants {
    /**
     * Szerokość okna głównego gry
     */
    public static int mainMenuFrameWidth;
    /**
     * Wysokość okna głównego gry
     */
    public static int mainMenuFrameHeight;
    /**
     * Nazwa gry
     */
    public static String gameTitle;
    /**
     * Tekst wyświetlany w przycisku nowej gry
     */
    public static String playButtonText;
    /**
     * Tekst wyświetlany w przycisku najlepszych wyników
     */
    public static String highScoresButtonText;
    /**
     * Tekst wyświetlany w przycisku wyjścia z gry
     */
    public static String exitButtonText;
    /**
     *Tekst przy liczbie dostępnych żyć
     */
    public static String lifeLabelText;
    /**
     * Początkowa liczba żyć
     */
    public static int initialLives;
    /**
     * Tekst przy liczbie punktów
     */
    public static String pointsLabel;
    /**
     * Początkowa liczba punktów
     */
    public static int initialPoints;
    /**
     * Tekst przy liczniku czasu
     */
    public static String timeLabel;
    /**
     * Początkowa wartość licznika czasu
     */
    public static int initialTime;
    /**
     * średnica kulki
     */
    public static float ballRadius;
    /**
     * Kolor kulki
     */
    public static Color ballColor;
    /**
     * Kolor przeszkody
     */
    public static Color obstacleColor;
    /**
     * Kolor portalu
     */
    public static Color portalColor;
    /**
     * Wymiary portalu
     */
    public static float portalWidth;
    /**
     * Wymiary gwiazdki
     */
    public static float starWidth;
    /**
     * Scieżka do pliku z poziomem
     */
    public static String filePath;
    /**
     * Ilość poziomów w grze
     */
    public static int numberOfLevels;
    /**
     * Scieżka do grafiki gwiazdy
     */
    public static String starImagePath;
    /**
     * Dodatkowe punkty otrzymane po zachowaniu życia i ukończeniu gry
     */
    public static int pointsForLive;
    /**
     * ścieżka pliku z tabelą najlepszych wynków
     */
    public static String highScoresFile;
    /**
     * Maksymalna prędkość kulki w poziomie
     */
    public static float maximumHorizontalVelocity;
    /**
     * Maksymalna prędkość kulki w pionie
     */
    public static float maximumVerticalVelocity;

    static{
        parseConstantsFile();
    }

    /**
     * Metoda wczytująca stałe z pliku konfiguracyjnego
     */
    private static void parseConstantsFile(){
        try {
            File xmlFile = new File("ConfigFiles\\constants.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            mainMenuFrameWidth=Integer.parseInt(doc.getElementsByTagName("mainMenuFrameWidth").item(0).getTextContent());
            mainMenuFrameHeight=Integer.parseInt(doc.getElementsByTagName("mainMenuFrameHeight").item(0).getTextContent());
            gameTitle=doc.getElementsByTagName("gameTitle").item(0).getTextContent();
            playButtonText=doc.getElementsByTagName("playButtonText").item(0).getTextContent();
            highScoresButtonText=doc.getElementsByTagName("highScoresButtonText").item(0).getTextContent();
            exitButtonText=doc.getElementsByTagName("exitButtonText").item(0).getTextContent();
            lifeLabelText=doc.getElementsByTagName("lifeLabelText").item(0).getTextContent();
            initialLives=Integer.parseInt(doc.getElementsByTagName("initialLives").item(0).getTextContent());
            pointsLabel=doc.getElementsByTagName("pointsLabel").item(0).getTextContent();
            initialPoints=Integer.parseInt(doc.getElementsByTagName("initialPoints").item(0).getTextContent());
            timeLabel=doc.getElementsByTagName("timeLabel").item(0).getTextContent();
            initialTime=Integer.parseInt(doc.getElementsByTagName("initialTime").item(0).getTextContent());
            ballRadius=Float.parseFloat(doc.getElementsByTagName("ballRadius").item(0).getTextContent());
            portalWidth=Float.parseFloat(doc.getElementsByTagName("portalWidth").item(0).getTextContent());
            starWidth=Float.parseFloat(doc.getElementsByTagName("starWidth").item(0).getTextContent());
            filePath=doc.getElementsByTagName("filePath").item(0).getTextContent();
            numberOfLevels=Integer.parseInt(doc.getElementsByTagName("numberOfLevels").item(0).getTextContent());
            starImagePath=doc.getElementsByTagName("starImagePath").item(0).getTextContent();
            String ballColorString=doc.getElementsByTagName("ballColor").item(0).getTextContent();
            pointsForLive=Integer.parseInt(doc.getElementsByTagName("pointsForLive").item(0).getTextContent());
            highScoresFile=doc.getElementsByTagName("highScoresFile").item(0).getTextContent();
            maximumHorizontalVelocity=Float.parseFloat(doc.getElementsByTagName("maximumHorizontalVelocity").item(0).getTextContent());
            maximumVerticalVelocity=Float.parseFloat(doc.getElementsByTagName("maximumVerticalVelocity").item(0).getTextContent());
            try {
                Field field = Class.forName("java.awt.Color").getField(ballColorString);
                ballColor = (Color)field.get(null);
            } catch (Exception e) {
                ballColor = null;
            }
            String obstacleColorString=doc.getElementsByTagName("obstacleColor").item(0).getTextContent();
            try {
                Field field = Class.forName("java.awt.Color").getField(obstacleColorString);
                obstacleColor = (Color)field.get(null);
            } catch (Exception e) {
                obstacleColor = null;
            }
            String portalColorString=doc.getElementsByTagName("portalColor").item(0).getTextContent();
            try {
                Field field = Class.forName("java.awt.Color").getField(portalColorString);
                portalColor = (Color)field.get(null);
            } catch (Exception e) {
                portalColor = null;
            }
        }
        //catch(ParserConfigurationException e){
            //e.printStackTrace();
        //}
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
