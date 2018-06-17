package Gui;

import BallGame.Game.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.Vector;

/**
 * Klasa opisująca okno najlepszych wynikow
 */
public class ListWindow extends JFrame {

    /**
     * Etykieta tabeli najlepszych wynikow
     */
    private JLabel listTitle;
//    /**
//     * Pole tekstowe zawierajace najlepsze wyniki
//     */
//    private JTextArea jTextArea;
    /**
     * Tablica zawierająca nagłówek tabeli wyników
     */
    private Vector<Object> columnNames = new Vector<>();
    /**
     * Wiersze tabeli
     */
    private Vector<Vector<Object>> rows = new Vector<>();
    /**
     * Tabela najlepszych wyników
     */
    private JTable highScoreTable;
    /**
     * Konstruktor klasy ListWindow
     */
    public ListWindow() {

        super("Lista wynikow");
        this.setSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        parseHighScores();
        columnNames.add("Pozycja");
        columnNames.add("Imie");
        columnNames.add("Wynik");
        this.highScoreTable = new JTable(rows, columnNames);
        startListGui();
    }

    /**
     * Inicializator interfejsfu graficznego okna najlepszych wynikow
     */
    private void startListGui() {
        this.setLayout(new BorderLayout());
        this.listTitle = new JLabel("Lista najlepszych wynikow:", 0);
        //this.jTextArea = new JTextArea();
        //this.jTextArea.setEditable(false);
        this.listTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        this.add(listTitle, BorderLayout.NORTH);
        this.add(highScoreTable.getTableHeader(), BorderLayout.PAGE_START);
        this.add(highScoreTable, BorderLayout.CENTER);
        setTableSize();
        //this.add(jTextArea, BorderLayout.CENTER);
    }

    private void parseHighScores(){
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
            Vector<Object> column = new Vector<>();
            column.add(key);
            column.add(name);
            column.add(splitValues[0]);
            rows.add(column);
        }
    }

    private void setTableSize(){
        highScoreTable.setShowHorizontalLines(false);
        highScoreTable.setShowVerticalLines(false);
        highScoreTable.getColumnModel().getColumn(0).setPreferredWidth(10);

    }
}
