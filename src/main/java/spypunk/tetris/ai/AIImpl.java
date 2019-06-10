package spypunk.tetris.ai;

import spypunk.tetris.model.Movement;
import spypunk.tetris.model.Shape;
import spypunk.tetris.model.Tetris;
import spypunk.tetris.model.TetrisInstance;
import spypunk.tetris.service.TetrisService;

import java.awt.*;
import java.util.Map;

public class AIImpl implements AI {

    private TetrisService tetrisService;

    private boolean[][] board = new boolean[10][10];

    private Shape currentShape;

    private boolean haveMoved = false;

    @Override
    public void update(TetrisService tetrisService) {
        this.tetrisService = tetrisService;

        initializeBoard();
        initializeCurrentShape();
        if (currentShape != null && !haveMoved) {
            Point placement = findPlacement();
            move(placement);
            haveMoved = true;
        }
    }

    private void initializeCurrentShape(){
        currentShape = tetrisService.getTetris().getCurrentShape();   // fix
    }

    private void initializeBoard(){
        TetrisInstance tetrisInstance = tetrisService.getTetris().getTetrisInstance(); // fix

        Map<Point, Shape.Block> blocks = tetrisInstance.getBlocks();

        for (Map.Entry<Point, Shape.Block> entry : blocks.entrySet()){
            Point point = entry.getKey();
            // Shape.Block block = entry.getValue();

            this.board[point.x][ point.y] = true;
        }
    }

    private Point findPlacement(){
        boolean[][] options = new boolean[10][10];

        for (int x = 0; x < 10; x++){
            for (int y = 0; y < 10; y++){
                options[x][y] = canPlace(x, y);
            }
        }

        return lowerRightPlacement(options);
    }

    private Point lowerRightPlacement(boolean[][] options){
        for (int y = 9; y >= 0; y--){
            for (int x = 9; x >= 0; x--){
                if (options[x][y] == true){
                    return new Point(x, y);
                }
            }
        }

        return new Point(0, 0);
    }

    private boolean canPlace(int x, int y) {
        for (Shape.Block block : currentShape.getBlocks()) {
            int xPos = (int) block.getLocation().getX() + x;
            int yPos = (int) block.getLocation().getY() + y;
            if (xPos >= 10 || yPos >= 10 || this.board[xPos][xPos] == true) {
                return false;
            }
        }
        return true;
    }

    private void move(Point placement){

        final Rectangle boundingBox = currentShape.getBoundingBox();
        final Rectangle newBoundingBox = new Rectangle(boundingBox);

        newBoundingBox.setLocation(placement);

        final Shape newShape = new Shape(currentShape.getShapeType(), newBoundingBox, currentShape.getCurrentRotation());

        currentShape.getBlocks().forEach(block ->  block.setLocation(new Point((int) (block.getLocation().getX() + placement.getX()), (int) (block.getLocation().getY() + placement.getY()))));

        tetrisService.getTetris().setCurrentShape(newShape);
    }

}
