package edu.hitsz.props;

import edu.hitsz.application.Main;

public class BulletPlusProps extends AbstractProps {
    public BulletPlusProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        if (this.getLocationY() >= Main.WINDOW_HEIGHT) {
            this.vanish();
        }
    }
}
