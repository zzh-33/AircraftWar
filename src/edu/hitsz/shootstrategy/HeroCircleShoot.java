package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class HeroCircleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power) {

        int shootNum = 20;

        List<BaseBullet> bullets = new LinkedList<>();


        BaseBullet bullet;

        for(int i = 0; i < shootNum; i++){
            bullet = new HeroBullet(
                    x,
                    y,
                    (int)(speedX * Math.cos(Math.toRadians(360 / shootNum * i))),
                    (int)(speedY * Math.sin(Math.toRadians(360 / shootNum * i))),
                    power
            );
            bullets.add(bullet);
        }
        return bullets;
    }
}
