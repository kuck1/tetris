package spypunk.tetris.ui.controller.ai;

import spypunk.tetris.service.TetrisService;

public interface AI {
    void move();

    void update(TetrisService tetrisService);
}

