package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power) {

        int shootNum = 3;

        List<BaseBullet> bullets = new LinkedList<>();

        BaseBullet bullet;

        for(int i = 0; i < shootNum; i++){
            bullet = new EnemyBullet(
                    x + (i*2 - shootNum + 1),
                    y,
                    speedX + (i*2 - shootNum + 1),
                    speedY - Math.abs(i*2 - shootNum + 1),
                    power
            );
            bullets.add(bullet);
        }
        return bullets;
    }
}
