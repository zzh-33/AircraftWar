package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootstrategy.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    //子弹威力
    private int power = 30;

    //子弹射击方向 (向上发射：-1，向下发射：1)
    private int direction = -1;

    private volatile static HeroAircraft heroAircraft;

    private long propEffectStartTime = 0;
    private long propEffectDuration = 0;
    private static final long PROP_EFFECT_DURATION = 5000;

    private HeroAircraft(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        super(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }

    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft == null){
            synchronized (HeroAircraft.class){
                if(heroAircraft == null){
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0,
                            0,
                            100,
                            new HeroDirectShoot()
                    );
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void setShootStrategy(ShootStrategy shootStrategy){
        this.shootStrategy = shootStrategy;

        this.propEffectStartTime = System.currentTimeMillis();
        this.propEffectDuration = PROP_EFFECT_DURATION;
    }

    public void updatePropEffect(){
        if(propEffectStartTime == 0){
            return;
        }

        if(System.currentTimeMillis() - propEffectStartTime > propEffectDuration){
            this.shootStrategy = new HeroDirectShoot();

            propEffectStartTime = 0;
            propEffectDuration = 0;
        }
    }

    public boolean isPropEffect(){
        return propEffectStartTime != 0;
    }

    public long getRemainPropEffectTime(){
        if(propEffectStartTime == 0){
            return 0;
        }
        return propEffectDuration - (System.currentTimeMillis() - propEffectStartTime);
    }


    @Override
    public List<BaseBullet> executeShootStrategy() {
        if (shootStrategy instanceof HeroCircleShoot) {
            return shootStrategy.shoot(
                    locationX,
                    locationY + direction * 2,
                    8,
                    8,
                    power
            );
        } else {
            return shootStrategy.shoot(
                    locationX,
                    locationY + direction * 2,
                    0,
                    speedY + direction * 5,
                    power
            );
        }
    }

    public void setHp(int hp){
        this.hp = hp;
    }

}
