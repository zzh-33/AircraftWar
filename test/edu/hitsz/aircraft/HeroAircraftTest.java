package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shootstrategy.HeroCircleShoot;
import edu.hitsz.shootstrategy.HeroDirectShoot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private static HeroAircraft heroAircraft;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Test Start");
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterAll
    static void afterAll() {
        heroAircraft = null;
        System.out.println("Test End");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Execute before each test method");
        heroAircraft.setHp(100);
        heroAircraft.setShootStrategy(new HeroDirectShoot());
    }

    @Test
    void getHeroAircraft_Singleton() {
        System.out.println("Test getHeroAircraft");
        HeroAircraft instance1 = HeroAircraft.getHeroAircraft();
        HeroAircraft instance2 = HeroAircraft.getHeroAircraft();
        assertSame(instance1, instance2, "getHeroAircraft should return the same instance");
    }
    
    @Test
    void forward() {
        System.out.println("Test forward");
        int initialX = heroAircraft.getLocationX();
        int initialY = heroAircraft.getLocationY();
        heroAircraft.forward();
        assertEquals(initialX, heroAircraft.getLocationX(), "forward should not change X location");
        assertEquals(initialY, heroAircraft.getLocationY(), "forward should not change Y location");
    }

    @Test
    void executeShootStrategy() {
        System.out.println("Test executeShootStrategy");
        List<BaseBullet> bullets = heroAircraft.executeShootStrategy();
        assertNotNull(bullets);
        assertEquals(1, bullets.size(), "HeroDirectShoot should produce 1 bullet");
        assertTrue(bullets.get(0) instanceof HeroBullet, "Bullet should be HeroBullet");

        heroAircraft.setShootStrategy(new HeroCircleShoot());
        List<BaseBullet> circleBullets = heroAircraft.executeShootStrategy();
        assertNotNull(circleBullets);
        assertEquals(20, circleBullets.size(), "HeroCircleShoot should produce 20 bullets");
        for (BaseBullet bullet : circleBullets) {
            assertTrue(bullet instanceof HeroBullet, "All bullets should be HeroBullet");
        }
    }

    @Test
    void setHp() {
        System.out.println("Test setHp");
        heroAircraft.setHp(50);
        assertEquals(50, heroAircraft.getHp(), "HP should be set to 50");

        heroAircraft.setHp(100);
        assertEquals(100, heroAircraft.getHp(), "HP should be set to 100");

        heroAircraft.setHp(0);
        assertEquals(0, heroAircraft.getHp(), "HP should be set to 0");
    }

    @Test
    void decreaseHp() {
        System.out.println("Test decreaseHp");
        heroAircraft.setHp(100);
        heroAircraft.decreaseHp(30);
        assertEquals(70, heroAircraft.getHp(), "HP should decrease by 30");

        heroAircraft.decreaseHp(70);
        assertEquals(0, heroAircraft.getHp(), "HP should become 0 after decreasing to zero");
        assertTrue(heroAircraft.notValid(), "Aircraft should be invalid when HP reaches 0");
    }
}