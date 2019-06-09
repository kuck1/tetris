package spypunk.tetris.ai;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AIImpl implements AI {

    @Override
    public void update() {
        move();
    }

    @Override
    public void move(){
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_DOWN);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
