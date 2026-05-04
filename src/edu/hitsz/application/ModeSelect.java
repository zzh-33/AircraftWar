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
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE;
                Main.cardLayout.show(Main.cardPanel, "game");
                game.gameInfo.setGameMode("easy");
                game.action();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_2;
                Main.cardLayout.show(Main.cardPanel, "game");
                game.gameInfo.setGameMode("simple");
                game.action();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageManager.SECLECTED_BACKGROUND_IMAGE = ImageManager.BACKGROUND_IMAGE_3;
                Main.cardLayout.show(Main.cardPanel, "game");
                game.gameInfo.setGameMode("hard");
                game.action();
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

}
