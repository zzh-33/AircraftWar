package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NullShoot implements ShootStrategy {;
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power) {
        return new LinkedList<>();
    }
}
