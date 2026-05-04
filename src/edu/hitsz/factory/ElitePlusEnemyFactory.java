package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public class ElitePlusEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        return new ElitePlusEnemy(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
}
