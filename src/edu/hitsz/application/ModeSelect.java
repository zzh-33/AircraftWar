package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelect {
    private JPanel MainPanel;
    private JLabel modeSelectLabel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel emptyLeft;
    private JPanel emptyRight;
    private JPanel buttonPanel;
    private JPanel buttonEmpty1;
    private JPanel buttonEmpty2;
    private JPanel buttonEmpty3;
    private JPanel buttonEmpty4;

    private Game game;

    public ModeSelect(Game game) {

        this.game = game;
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("easy");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("simple");
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame("hard");
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    private void startGame(String gameMode) {
        switch (gameMode) {
            case "easy":
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE;
                break;
            case "simple":
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_2;
                break;
            case "hard":
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_3;
                break;
            default:
                break;
        }
        Main.cardLayout.show(Main.cardPanel, "game");
        game.gameInfo.setGameMode(gameMode);
        game.audioManager.startBgm();
        game.action();
    }

}
