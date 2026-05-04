package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.GameDaoImpl;
import edu.hitsz.dao.GameInfoLog;
import edu.hitsz.factory.*;
import edu.hitsz.props.*;
import edu.hitsz.shootstrategy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    //敌机工厂
    private EnemyFactory enemyFactory;

    private final HeroAircraft heroAircraft;

    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProps> props;

    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 7;

    //敌机生成周期
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;

    //英雄机和敌机射击周期
    protected double bossShoutCycle = 80;
    protected double enemyShootCycle = 30;
    protected double heroShootCycle = 20;
    protected double heroShootCycleCircle = 40;
    private int heroShootCycleCounter = 0;
    private int bossShootCounter = 0;
    private int heroShootCounter = 0;
    private int enemyShootCounter = 0;

    //当前玩家分数
    private int score = 0;

    //游戏结束标志
    private boolean gameOverFlag = false;

    //游戏信息
    public GameInfoLog gameInfo;
    public GameDaoImpl gameDao;

    //背景音乐
    public final AudioManager audioManager;

    public Game() {
        gameInfo = new GameInfoLog();
        gameDao = new GameDaoImpl();

        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        audioManager = AudioManager.getAudioManager();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //生成敌机
                enemyProduction();
                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                // 道具移动
                propsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(task,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void enemyProduction() {

        enemySpawnCounter++;

        if (enemySpawnCounter >=enemySpawnCycle) {

            enemySpawnCounter = 0;
            // 产生普通敌机
            if (enemyAircrafts.size() < enemyMaxNumber) {

                selectEnemy();

            }
        }
    }

    private void selectEnemy() {
        if (score > 1000 && Math.random() < 0.1 && enemyAircrafts.stream().noneMatch(x->x instanceof BossEnemy)) {
            enemyFactory = new BossEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    1,
                    0,
                    5000,
                    new CircleShoot()
            ));
        } else if (Math.random() < 0.1) {
            enemyFactory = new EliteProEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITEPRO_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    Math.random() > 0.5 ? 2 : -2,
                    5,
                    90,
                    new ScatterShoot()
            ));
        } else if (Math.random() < 0.2) {
            enemyFactory = new ElitePlusEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITEPLUS_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    Math.random() > 0.5 ? 1 : -1,
                    8,
                    60,
                    new DoubleShoot()
            ));
        }else if (Math.random() < 0.3) {
            enemyFactory = new EliteEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    10,
                    30,
                    new DirectShoot()
            ));
        } else {
            enemyFactory = new MobEnemyFactory();
            enemyAircrafts.add(enemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    10,
                    30,
                    new NullShoot()
            ));
        }
    }

    private void shootAction() {
        heroShootCounter++;
        if (heroShootCounter >= heroShootCycle) {
            heroShootCounter = 0;
            //英雄机射击
            if(heroAircraft.shootStrategy instanceof HeroDirectShoot || heroAircraft.shootStrategy instanceof HeroScatterShoot) {
                heroBullets.addAll(heroAircraft.executeShootStrategy());
            }
        }

        heroShootCycleCounter++;
        if (heroShootCycleCounter >= heroShootCycleCircle) {
            heroShootCycleCounter = 0;
            //英雄机射击
            if(heroAircraft.shootStrategy instanceof HeroCircleShoot) {
                heroBullets.addAll(heroAircraft.executeShootStrategy());
            }
        }

        enemyShootCounter++;
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            //敌机射击
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if(enemyAircraft instanceof BossEnemy) {
                    continue;
                }
                enemyBullets.addAll(enemyAircraft.executeShootStrategy());
            }
        }

        bossShootCounter++;
        if (bossShootCounter >= bossShoutCycle) {
            bossShootCounter = 0;
            //BossEnemy射击
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if(enemyAircraft instanceof BossEnemy){
                    enemyBullets.addAll(enemyAircraft.executeShootStrategy());
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProps prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (bullet.crash(heroAircraft)) {
                bullet.vanish();
                heroAircraft.decreaseHp(bullet.getPower());
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        getScoreAndProps(enemyAircraft);
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProps prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (prop.crash(heroAircraft)) {

                prop.vanish();

                propEffect(prop);
            }
        }
    }

    private void propEffect(AbstractProps prop) {

        if (prop instanceof BulletProps) {

            heroAircraft.setShootStrategy(new HeroScatterShoot());

        } else if (prop instanceof BulletPlusProps) {

            heroAircraft.setShootStrategy(new HeroCircleShoot());

        } else if (prop instanceof BloodProps) {

            heroAircraft.setHp(Math.min(heroAircraft.getHp() + 30, 100));

        } else if (prop instanceof BombProps) {

            for (AbstractAircraft enemyAircraft : enemyAircrafts) {

                if (enemyAircraft instanceof BossEnemy) {
                    continue;
                } else if (enemyAircraft instanceof EliteProEnemy) {

                    enemyAircraft.decreaseHp(60);

                } else {

                    enemyAircraft.vanish();

                }
            }

        } else if (prop instanceof FreezeProps) {

            for (AbstractAircraft enemyAircraft : enemyAircrafts) {

                if (enemyAircraft instanceof BossEnemy) {
                    continue;
                } else if (enemyAircraft instanceof EliteProEnemy) {

                    enemyAircraft.setSpeed(0, 2);

                } else if (enemyAircraft instanceof ElitePlusEnemy || enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof MobEnemy) {

                    enemyAircraft.setSpeed(0, 0);

                }
            }
        }
    }

    private void getScoreAndProps(AbstractAircraft enemyAircraft) {
        if (enemyAircraft instanceof BossEnemy) {

            score += 1000;

            for (int i = 0; i < 3; i++) {
                if (Math.random() < 0.2) {

                    props.add(PropsSimpleFactory.createProps(
                            "blood",
                            enemyAircraft.getLocationX() + 32 * (i *2 - 2),
                            enemyAircraft.getLocationY()
                    ));
                } else if (Math.random() < 0.4) {

                    props.add(PropsSimpleFactory.createProps(
                            "bomb",
                            enemyAircraft.getLocationX() + 32 * (i *2 - 2),
                            enemyAircraft.getLocationY()
                    ));
                } else if (Math.random() < 0.6) {

                    props.add(PropsSimpleFactory.createProps(
                            "bullet",
                            enemyAircraft.getLocationX() + 32 * (i *2 - 2),
                            enemyAircraft.getLocationY()
                    ));
                } else if (Math.random() < 0.8) {

                    props.add(PropsSimpleFactory.createProps(
                            "bulletPlus",
                            enemyAircraft.getLocationX() + 32 * (i *2 - 2),
                            enemyAircraft.getLocationY()
                    ));
                } else {

                    props.add(PropsSimpleFactory.createProps(
                            "freeze",
                            enemyAircraft.getLocationX() + 32 * (i *2 - 2),
                            enemyAircraft.getLocationY()
                    ));
                }
            }

        } else if (enemyAircraft instanceof EliteProEnemy) {

            score += 50;

            if (Math.random() < 0.2) {

                props.add(PropsSimpleFactory.createProps(
                        "blood",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.4) {

                props.add(PropsSimpleFactory.createProps(
                        "bomb",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.6) {

                props.add(PropsSimpleFactory.createProps(
                        "bullet",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.8) {

                props.add(PropsSimpleFactory.createProps(
                        "bulletPlus",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else {

                props.add(PropsSimpleFactory.createProps(
                        "freeze",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            }

        } else if (enemyAircraft instanceof ElitePlusEnemy) {

            score += 30;

            if (Math.random() < 0.15) {

                props.add(PropsSimpleFactory.createProps(
                        "bulletPlus",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.3) {

                props.add(PropsSimpleFactory.createProps(
                        "bomb",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.45) {

                props.add(PropsSimpleFactory.createProps(
                        "bullet",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.6) {

                props.add(PropsSimpleFactory.createProps(
                        "blood",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            }

        } else if (enemyAircraft instanceof EliteEnemy) {

            score += 20;

            if (Math.random() < 0.1) {

                props.add(PropsSimpleFactory.createProps(
                        "bullet",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.2) {

                props.add(PropsSimpleFactory.createProps(
                        "blood",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            } else if (Math.random() < 0.3) {

                props.add(PropsSimpleFactory.createProps(
                        "bulletPlus",
                        enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY()
                ));
            }

        } else {
            score += 10;
        }
    }



    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        // 游戏结束检查英雄机是否存活
        if (heroAircraft.getHp() <= 0) {
            timer.cancel(); // 取消定时器并终止所有调度任务
            gameOverFlag = true;
            System.out.println("Game Over!");

            gameInfo.setGameTime();
            gameInfo.setScore(score);

            String userName = JOptionPane.showInputDialog("本次游戏得分为:" + gameInfo.getScore() + "分\n请输入用户名:");
            gameInfo.setUserName(userName);
            gameDao.saveGameInfoLog(gameInfo);
            gameDao.getAllGameInfoLogs(gameInfo.getGameMode());

            LeaderBorad leaderBorad = new LeaderBorad(this);
            Main.cardPanel.add(leaderBorad.getMainPanel(), "leaderBorad");
            Main.cardLayout.show(Main.cardPanel, "leaderBorad");
        }
    };

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.SECLECTED_BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.SECLECTED_BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}
