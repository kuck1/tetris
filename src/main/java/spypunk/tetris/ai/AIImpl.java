package spypunk.tetris.ai;

import spypunk.tetris.model.Movement;
import spypunk.tetris.service.TetrisService;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AIImpl implements AI {

    private TetrisService tetrisService;

    @Override
    public void update(TetrisService tetrisService) {
        this.tetrisService = tetrisService;
        move();
    }

    private void move(){
        tetrisService.move(Movement.DOWN);
    }

}
