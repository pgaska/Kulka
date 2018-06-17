package BallGame.Game;

import Gui.MenuWindow;

import javax.swing.*;

/**
 * Główna klasa inicjująca działanie programu
 */
public class Main {
    /**
     * Funkcja inicjująca program
     * @param args nieużywane
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuWindow();
            }
        });
    }
}
