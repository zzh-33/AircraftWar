package edu.hitsz.shootstrategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy  {
    List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int power);
}
