package edu.hitsz.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameInfoLog implements Serializable {
    private String userName;
    private String gameMode;
    private String gameTime;
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime() {
        this.gameTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return userName + "," + gameMode + "," + score + "," + gameTime;
    }
}
