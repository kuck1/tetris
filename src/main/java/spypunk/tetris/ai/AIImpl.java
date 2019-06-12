package spypunk.tetris.ai;

import spypunk.tetris.model.Movement;
import spypunk.tetris.model.Shape;
import spypunk.tetris.model.Tetris;
import spypunk.tetris.model.TetrisInstance;
import spypunk.tetris.service.TetrisService;

import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class AIImpl implements AI {

    private boolean[][] board = new boolean[100][100];

    private int haveMoved = 100;

    @Override
    public void update(TetrisService tetrisService) {
        initializeBoard(tetrisService);

        if (tetrisService.getTetris().getCurrentShape() != null && haveMoved > 0) {
            System.out.print("shape available: " + tetrisService.getTetris().getCurrentShape().getShapeType());

            Point placement = findPlacement(tetrisService);
            System.out.print(placement.x + " ");
            System.out.print(placement.y + " placement returned ");
            move(tetrisService, placement);
            haveMoved --;
        }
    }


    private void initializeBoard(TetrisService tetrisService){
        TetrisInstance tetrisInstance = tetrisService.getTetris().getTetrisInstance(); // fix

        Map<Point, Shape.Block> blocks = tetrisInstance.getBlocks();

        for (Map.Entry<Point, Shape.Block> entry : blocks.entrySet()){
            Point point = entry.getKey();
            // Shape.Block block = entry.getValue();

            System.out.print(point.x + " ");
            System.out.print(point.y + "/n ");
            this.board[point.x][ point.y] = true;
        }
    }

    private Point findPlacement(TetrisService tetrisService){
        boolean[][] options = new boolean[10][10];

        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++){
                if (x == 0) { System.out.println(); }
                System.out.print(canPlace(tetrisService, x, y) ? 1 + " " : 0 + " ");
                options[x][y] = canPlace(tetrisService, x, y);
            }
        }
        System.out.println();

        return lowerRightPlacement(options);
    }

    private Point lowerRightPlacement(boolean[][] options){
        for (int y = 9; y >= 0; y--){
            for (int x = 9; x >= 0; x--){
                if (options[x][y] == true){
                    System.out.print(x + " ");
                    System.out.print(y + " placement found ");
                    return new Point(x, y);
                }
            }
        }

        return new Point(0, 0);
    }

    private boolean canPlace(TetrisService tetrisService, int x, int y) {
        for (Point point : tetrisService.getTetris().getCurrentShape().getShapeType().getRotations().get(0)) {
            int xPos = (int) point.getX() + x;
            int yPos = (int) point.getY() + y;
            if (xPos >= 10 || yPos >= 10 || this.board[xPos][yPos] == true) {
                return false;
            }
        }
        return true;
    }

    private void move(TetrisService tetrisService, Point placement1){

        System.out.print("shape moved ");
        // final Point placement = new Point(1,1);
        final Point placement = placement1;

        final Rectangle boundingBox = tetrisService.getTetris().getCurrentShape().getBoundingBox();
        final Rectangle newBoundingBox = new Rectangle(boundingBox);

        newBoundingBox.setLocation(placement);

        final Shape newShape = new Shape(tetrisService.getTetris().getCurrentShape().getShapeType(), newBoundingBox, tetrisService.getTetris().getCurrentShape().getCurrentRotation());

        tetrisService.getTetris().getCurrentShape().getBlocks().forEach(block -> newShape.getBlocks().add(block));

        List<Point> points = new ArrayList<Point>();
        points.addAll(tetrisService.getTetris().getCurrentShape().getShapeType().getRotations().get(0));

        List<Shape.Block> blocks =  tetrisService.getTetris().getCurrentShape().getBlocks();
        Map<Point, Shape.Block> locations = new HashMap<Point, Shape.Block>();

        for (int i = 0; i < points.size(); i ++){
            locations.put(points.get(i), blocks.get(i));
        }

        locations.entrySet().forEach(location -> location.getValue().setLocation(new Point((int) (location.getKey().getLocation().getX() + placement.getX()), (int) (location.getKey().getLocation().getY() + placement.getY()))));

        tetrisService.getTetris().setCurrentShape(newShape);      // fix

        tetrisService.getTetris().getCurrentShape().getBlocks()
                .forEach(block -> tetrisService.getTetris().getBlocks().put(block.getLocation(), block));  // fix

//        tetrisService.getTetris().getCurrentShape().getBlocks()
//                .forEach(block -> tetrisService.getTetris().getBlocks().put(block.getLocation(), block));

        tetrisService.getTetris().setCurrentShapeLocked(true);    // fix

        System.out.println("shape locked to position x,y: " + placement1.getX() + ", " + placement1.getY());
    }
}
