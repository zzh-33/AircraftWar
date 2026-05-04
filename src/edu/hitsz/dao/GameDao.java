package edu.hitsz.dao;

import java.util.List;

public interface GameDao {
    void saveGameInfoLog (GameInfoLog gameInfoLog);
    void deleteGameInfoLog (int index);
    String[][] getAllGameInfoLogs (String gameMode);
}
