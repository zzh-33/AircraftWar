package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy {

    public MobEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        super(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }

    @Override
    public List<BaseBullet> executeShootStrategy() {
        return shootStrategy.shoot(locationX, locationY, speedX, speedY, power);
    }

}
