package Gui;

import BallGame.Game.Constants;
import BallGame.Game.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa opisujaca Menu Głowne gry
 */
public class MenuWindow extends JFrame implements ActionListener {
    /**
     * Panel zawierajacy wszystkie przyciski
     */
    private JPanel menuPanel;
    /**
     * Przycisk rozpoczecia nowej gry
     */
    private JButton playButton;
    /**
     * Przycisk wejscia do tablicy najlepszych wynikow
     */
    private JButton highScoresButton;
    /**
     * Przycisk wyjscia z gry
     */
    private JButton exitButton;

    /**
     * Konstruktor klasy MenuWindow
     */
    public MenuWindow() {
        super(Constants.gameTitle);
        this.setSize(new Dimension(Constants.mainMenuFrameWidth, Constants.mainMenuFrameHeight));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.startMenuGui();
    }

    /**
     * Inicializator interfejsfu graficznego menu glownego
     */
    private void startMenuGui() {
        this.menuPanel = new JPanel();
        this.menuPanel.setBackground(Color.BLACK);
        this.menuPanel.setLayout(new GridLayout(3, 1, 30, 40));
        this.playButton = new JButton(Constants.playButtonText);
        this.highScoresButton = new JButton(Constants.highScoresButtonText);
        this.exitButton = new JButton(Constants.exitButtonText);
        this.playButton.setBorderPainted(false);
        this.playButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.playButton.setBackground(Color.white);
        this.playButton.addActionListener(this);
        this.highScoresButton.setBorderPainted(false);
        this.highScoresButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.highScoresButton.setBackground(Color.white);
        this.highScoresButton.addActionListener(this);
        this.exitButton.setBorderPainted(false);
        this.exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        this.exitButton.setBackground(Color.white);
        this.exitButton.addActionListener(this);
        this.menuPanel.add(playButton);
        this.menuPanel.add(highScoresButton);
        this.menuPanel.add(exitButton);
        this.add(menuPanel);

    }

    /**
     * Metoda obsługjąca zdarzenia wciśnięcia przycisków w menu
     * @param e obiekt zdarzenia akcji zwiazanych z wcisnieciem przycisku
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            //GameScreen.createAndShowGUI();
            new GameWindow();
            this.dispose();

        } else if (e.getSource() == highScoresButton) {
            new ListWindow();


        } else {
            int confirmed = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjsc z programu?", "Ostrzezenie",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                this.dispose();
                System.exit(0);
            }

        }

    }
}

