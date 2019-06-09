package spypunk.tetris.ai;

import spypunk.tetris.service.TetrisService;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AIImpl implements AI {

    @Override
    public void update(TetrisService tetrisService) {
        move();
    }

    private void move() {
        try {
            System.out.print("A");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_DOWN);
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
    }
}

