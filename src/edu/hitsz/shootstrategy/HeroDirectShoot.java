package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class HeroDirectShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power) {

        List<BaseBullet> bullets = new LinkedList<>();

        int shootNum = 1;

        for(int i = 0; i < shootNum; i++){
            bullets.add(new HeroBullet(
                    x,
                    y,
                    speedX,
                    speedY,
                    power
            ));
        }
        return bullets;
    }
}
