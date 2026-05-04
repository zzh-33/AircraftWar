package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class DoubleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power) {

        List<BaseBullet> bullets = new LinkedList<>();

        int shootNum = 2;

        for(int i = 0; i < shootNum; i++){
            bullets.add(new EnemyBullet(
                    x + 8 * (i*2 - shootNum + 1),
                    y,
                    speedX,
                    speedY,
                    power
            ));
        }
        return bullets;
    }
}
