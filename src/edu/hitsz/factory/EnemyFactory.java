package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.shootstrategy.ShootStrategy;

public interface EnemyFactory {
    public abstract AbstractEnemy createEnemy(
            int locationX,
            int locationY,
            int speedX,
            int speedY,
            int hp,
            ShootStrategy shootStrategy
    );
}
