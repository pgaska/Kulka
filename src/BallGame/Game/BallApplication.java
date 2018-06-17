package BallGame.Game;

import javafx.util.Pair;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import java.util.ArrayList;

/**
 * Główna klasa zarządzająca działaniem aplikacji
 */
public class BallApplication {
    /**
     * Flaga określająca czy trwa rozgrywka
     */
    public boolean isPlaying;

    private ArrayList<Pair<Integer, String>> scores = new ArrayList<>();

    public BallApplication(String nick, int score){
        isPlaying = false;
        loadHighScores();
        isHighScore(nick, score);
    }

    /**
     * Wczytuje listę najlepszych wyników z pliku
     */
    private void loadHighScores(){
        Properties data = new Properties();
        try {
            Reader reader = new FileReader("ConfigFiles\\highScores.txt");
            data.load(reader);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String key : data.stringPropertyNames()){
            String values = data.getProperty(key);
            String[] splitValues = values.split(" ");
            String name = "";
            for(int i = 1; i<splitValues.length; i++)
                name += splitValues[i] + " ";
            scores.add(new Pair(Integer.parseInt(splitValues[0]), name));
        }
    }

    /**
     * Sortuje listę najlepszych wyników
     */
    private void sortHighScores(){
        Collections.sort(scores, (a, b) -> a.getKey() < b.getKey() ? 1 : a.getKey().equals(b.getKey()) ? 0 : -1);
    }

    /**
     * Sprawdza czy wynik jest odpowiednio wysoki by znaleźć się na liście
     * @param nick Nazwa podana przez gracza
     * @param score Wynik uzyskany przez gracza
     */
    private void isHighScore(String nick, int score){
        if(score > scores.get(scores.size() -1).getKey() || scores.size() < 15){
            saveHighScore(nick, score);
        }
    }

    /**
     * Zapisuje nowy wynik do tablicy
     * @param nick Nazwa podana przez gracza
     * @param score Wynik uzyskany przez gracza
     */
    private void saveHighScore(String nick ,int score){
        scores.remove(scores.size() -1);
        scores.add(new Pair<Integer, String>(score, nick));
        sortHighScores();

        File inFile = new File("ConfigFiles\\highScores.txt");
        File tempFile = new File("ConfigFiles\\temp.txt");

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            for(int i =0; i<scores.size(); i++){
                pw.println(i+" "+scores.get(i).getKey()+" "+scores.get(i).getValue());
            }
            pw.close();

            if (!inFile.delete()) {
                System.out.println("Could not delete file");
            }

            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
