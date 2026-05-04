package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public class MobEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        return new MobEnemy(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
}
