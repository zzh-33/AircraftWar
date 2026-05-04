package edu.hitsz.application;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioManager {
    private static volatile AudioManager audioManager;

    private final ExecutorService soundPool;

    private Thread bgmThread;
    private Thread bossBgmThread;

    private volatile boolean isGameRunning = false;
    private volatile boolean isBossActive = false;

    private static final String BGM_PATH = "src/videos/bgm.wav";
    private static final String BOSS_BGM_PATH = "src/videos/bgm_boss.wav";
    private static final String BOMB_EXPLOSION_SOUND_PATH = "src/videos/bomb_explosion.wav";
    private static final String BULLET_HIT_SOUND_PATH = "src/videos/bullet_hit.wav";
    private static final String GAME_OVER_SOUND_PATH = "src/videos/game_over.wav";
    private static final String GET_SUPPLY_SOUND_PATH = "src/videos/get_supply.wav";

    private AudioManager() {
        soundPool = Executors.newCachedThreadPool();
    }

    public static AudioManager getAudioManager() {
        if (audioManager == null) {
            synchronized (AudioManager.class) {
                if (audioManager == null) {
                    audioManager = new AudioManager();
                }
            }
        }
        return audioManager;
    }

    public void startBgm() {
        if (isGameRunning) return;
        isGameRunning = true;

        bgmThread = new Thread(() -> {
           while (isGameRunning && !Thread.currentThread().isInterrupted()) {
               playLoopAudio(BGM_PATH);
           }
        });

        bgmThread.setDaemon(true);
        bgmThread.start();
    }

    public void startBossBgm() {
        if (isBossActive) return;
        isBossActive = true;

        stopBgm();

        bossBgmThread = new Thread(() -> {
            while (isBossActive && !Thread.currentThread().isInterrupted()) {
                playLoopAudio(BOSS_BGM_PATH);
            }
        });

        bossBgmThread.setDaemon(true);
        bossBgmThread.start();
    }

    public void stopBgm() {
        isGameRunning = false;
        if (bgmThread != null && bgmThread.isAlive()) {
            bgmThread.interrupt();
        }
    }

    public void stopBossBgm() {
        isBossActive = false;
        if (bossBgmThread != null && bossBgmThread.isAlive()) {
            bossBgmThread.interrupt();
        }
        startBgm();
    }

    public void stopAllBgm() {
        stopBgm();
        stopBossBgm();
        soundPool.shutdown();
    }

    public void playBombExplosionSound() {
        playSound(BOMB_EXPLOSION_SOUND_PATH);
    }

    public void playBulletHitSound() {
        playSound(BULLET_HIT_SOUND_PATH);
    }

    public void playGameOverSound() {
        playSound(GAME_OVER_SOUND_PATH);
    }

    public void playGetSupplySound() {
        playSound(GET_SUPPLY_SOUND_PATH);
    }

    private void playSound(String path) {
        soundPool.submit(() -> {
            try {
                URL url = getClass().getResource(path);
                if (url == null) {
                    return;
                }

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioInputStream);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void playLoopAudio(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) {
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            while (isGameRunning && !Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
            }

            clip.stop();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
