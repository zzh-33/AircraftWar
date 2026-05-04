package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public class BossEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
}
