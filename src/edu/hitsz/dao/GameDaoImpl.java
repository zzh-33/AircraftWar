package edu.hitsz.dao;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class GameDaoImpl implements GameDao {
    private List<GameInfoLog> gameInfoLogs = new ArrayList<>();

    public GameDaoImpl() {
        loadFromFile();
    }

    @Override
    public void saveGameInfoLog(GameInfoLog gameInfoLog) {
        gameInfoLogs.add(gameInfoLog); // 内存列表此时已包含历史数据，直接追加即可
        saveToFile();
    }

    @Override
    public String[][] getAllGameInfoLogs(String gameMode) {
        gameInfoLogs.sort((o1, o2) -> o2.getScore() - o1.getScore());

        String[][] gameInfoLogsStr = new String[gameInfoLogs.size()][5];

        int i = 1;
        for (GameInfoLog gameInfoLog : gameInfoLogs) {
            System.out.println("第" + i + "位" + ":" + gameInfoLog.toString());
            gameInfoLogsStr[i-1] = new String[]{"第" + i + "位", gameInfoLog.getUserName(), gameInfoLog.getGameMode(), gameInfoLog.getScore() + "分", gameInfoLog.getGameTime()};
            i++;
        }

        return gameInfoLogsStr;
    }

    @Override
    public void deleteGameInfoLog(int index) {
        gameInfoLogs.remove(gameInfoLogs.get(index));
        saveToFile();
    }

    private void loadFromFile() {
        File gameInfoLogFile = new File("gameInfoLog.txt");
        if (!gameInfoLogFile.exists()) return;

        try (FileInputStream fis = new FileInputStream(gameInfoLogFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            int size = ois.readInt(); // 读取数量
            for (int i = 0; i < size; i++) {
                gameInfoLogs.add((GameInfoLog) ois.readObject()); // 循环读取
            }

        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        File gameInfoLogFile = new File("gameInfoLog.txt");

        try (FileOutputStream fos = new FileOutputStream(gameInfoLogFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeInt(gameInfoLogs.size());
            for (GameInfoLog log : gameInfoLogs) {
                oos.writeObject(log);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
