package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
}
