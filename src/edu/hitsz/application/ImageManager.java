package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.props.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage SECLECTED_BACKGROUND_IMAGE;

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE_2;
    public static BufferedImage BACKGROUND_IMAGE_3;

    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;

    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITEPLUS_ENEMY_IMAGE;
    public static BufferedImage ELITEPRO_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;

    public static BufferedImage BLOOD_PROP_IMAGE;
    public static BufferedImage BOMB_PROP_IMAGE;
    public static BufferedImage BULLET_PROP_IMAGE;
    public static BufferedImage BULLETPLUS_PROP_IMAGE;
    public static BufferedImage FREEZE_PROP_IMAGE;

    static {
        try {

            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE_2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE_3 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));

            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITEPLUS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/eliteplus.png"));
            ELITEPRO_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitepro.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            BLOOD_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            BOMB_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            BULLET_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            BULLETPLUS_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletplus.png"));
            FREEZE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_freeze.png"));


            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);

            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(), ELITEPLUS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteProEnemy.class.getName(), ELITEPRO_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);

            CLASSNAME_IMAGE_MAP.put(BloodProps.class.getName(), BLOOD_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombProps.class.getName(), BOMB_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletProps.class.getName(), BULLET_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletPlusProps.class.getName(), BULLETPLUS_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FreezeProps.class.getName(), FREEZE_PROP_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
