package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shootstrategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class EliteProEnemy extends AbstractEnemy{

    public EliteProEnemy(
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
        return shootStrategy.shoot(
                locationX,
                locationY + direction * 2,
                speedX,
                speedY + direction * 5,
                power
        );
    }
}
