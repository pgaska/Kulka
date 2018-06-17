package Gui;

import BallGame.Game.GameScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Klasa opisująca pasek menu z opcją pauzy
 */
public class PauseMenuBar extends JMenuBar implements ActionListener{
    private GameScreen gameScreen;
    private JMenu menu;
    private JMenuItem menuItem;

    public PauseMenuBar(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        menu = new JMenu("Pauza");
        this.add(menu);
        menu.setMnemonic(KeyEvent.VK_P);
        menuItem = new JMenuItem("Pauza");
        menu.add(menuItem);
        menuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == menuItem){
            gameScreen.actionPerformed(e);
        }
    }
}
