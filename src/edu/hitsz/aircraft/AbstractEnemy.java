package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.shootstrategy.ShootStrategy;

public abstract class AbstractEnemy extends AbstractAircraft{

    protected int power = 10;
    protected int direction = 1;

    public AbstractEnemy(
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
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

}
