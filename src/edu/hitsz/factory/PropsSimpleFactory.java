package edu.hitsz.factory;

import edu.hitsz.props.*;

public class PropsSimpleFactory {

    public static AbstractProps createProps(String type, int x, int y) {
        switch (type) {
            case "blood":
                return new BloodProps(x, y, 0, 10);
            case "bomb":
                return new BombProps(x, y, 0, 10);
            case "bullet":
                return new BulletProps(x, y, 0, 10);
            case "bulletPlus":
                return new BulletPlusProps(x, y, 0, 10);
            case "freeze":
                return new FreezeProps(x, y, 0, 10);
            default:
                return null;
        }
    }
}
