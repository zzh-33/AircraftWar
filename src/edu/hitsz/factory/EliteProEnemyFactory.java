package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public class EliteProEnemyFactory implements EnemyFactory {
    @Override
    public AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    ) {
        return new EliteProEnemy(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
}
