/*
 * Copyright © 2016-2017 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.tetris.ui.view;

import static spypunk.tetris.ui.constants.TetrisUIConstants.BLOCK_SIZE;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import spypunk.tetris.model.ShapeType;
import spypunk.tetris.model.Tetris;
import spypunk.tetris.model.Tetris.State;
import spypunk.tetris.model.Shape;
import spypunk.tetris.ui.cache.ImageCache;
import spypunk.tetris.ui.font.cache.FontCache;
import spypunk.tetris.ui.util.SwingUtils;
import spypunk.tetris.ui.util.SwingUtils.Text;
import sun.security.provider.SHA;

public class TetrisInfoView extends AbstractTetrisView {

    private static final int VIEW_HEIGHT = 1 + BLOCK_SIZE * 14;

    private static final int VIEW_WIDTH = 1 + BLOCK_SIZE * 6;

    private static final String SCORE = "SCORE";

    // private static final String LEVEL = "LEVEL";

    private static final String NEXT_SHAPE = "NEXT";

//    private static final String ROWS = "ROWS";

//    private final ValueTetrisInfo rowsTetrisInfo;

    private final ValueTetrisInfo scoreTetrisInfo;

    // private final ValueTetrisInfo levelTetrisInfo;

    private NextShapeTetrisInfo nextShapeTetrisInfo;

    private abstract class TetrisInfo {

        protected final Rectangle rectangle;

        private final Text titleText;

        TetrisInfo(final Rectangle rectangle, final String title) {
            this.rectangle = rectangle;

            titleText = new Text(title, fontCache.getDefaultFont());
        }

        public void render(final Graphics2D graphics) {
            SwingUtils.drawRectangleWithTitle(graphics, rectangle, titleText);
        }
    }

    private class ValueTetrisInfo extends TetrisInfo {

        ValueTetrisInfo(final Rectangle rectangle, final String title) {
            super(rectangle, title);
        }

        public void render(final Graphics2D graphics, final int value) {
            super.render(graphics);

            final Text valueText = new Text(String.valueOf(value), fontCache.getDefaultFont());

            SwingUtils.renderCenteredText(graphics, rectangle, valueText);
        }
    }

    private class NextShapeTetrisInfo extends TetrisInfo {
        private final NextShapeLayout nextShape1;
        private final NextShapeLayout nextShape2;
        private final NextShapeLayout nextShape3;

        NextShapeTetrisInfo() {
            super(new Rectangle(0, BLOCK_SIZE * 4, BLOCK_SIZE * 6, BLOCK_SIZE * 15), NEXT_SHAPE);

            List<Shape> shapes = tetris.getNextShape();
            nextShape1 = new NextShapeLayout(4, shapes.get(0).getShapeType());
            nextShape2 = new NextShapeLayout(8, shapes.get(1).getShapeType());
            nextShape3 = new NextShapeLayout(12, shapes.get(2).getShapeType());

        }

        @Override
        public void render(final Graphics2D graphics) {
            super.render(graphics);

            final State tetrisState = tetris.getState();

            if (tetrisState.equals(State.STOPPED)) {
                return;
            }

            nextShape1.render(graphics);
            nextShape2.render(graphics);
            nextShape3.render(graphics);
        }

        private class NextShapeLayout {
            private final Map<ShapeType, Pair<Image, Rectangle>> shapeTypeImageRectangles;
            private final ShapeType shapeType;

            NextShapeLayout(int offset, ShapeType shape) {
                shapeType = shape;
                Rectangle position = new Rectangle(0, BLOCK_SIZE * offset, BLOCK_SIZE * 6, BLOCK_SIZE * 5);
                shapeTypeImageRectangles = Arrays.asList(ShapeType.values())
                        .stream()
                        .collect(Collectors.toMap(Function.identity(), p -> this.createShapeTypeImageRectangle(p, position)));
            }

            private Pair<Image, Rectangle> createShapeTypeImageRectangle(final ShapeType shapeType, Rectangle rect) {
                final Image shapeTypeImage = imageCache.getShapeImage(shapeType);

                return Pair.of(shapeTypeImage, SwingUtils.getCenteredImageRectangle(shapeTypeImage, rect));
            }

            public void render(final Graphics2D graphics) {
                final Pair<Image, Rectangle> shapeTypeImageRectangle = this.shapeTypeImageRectangles.get(shapeType);
                SwingUtils.drawImage(graphics, shapeTypeImageRectangle.getLeft(), shapeTypeImageRectangle.getRight());

            }
        }
    }

    public TetrisInfoView(final FontCache fontCache,
            final ImageCache imageCache, final Tetris tetris) {
        super(fontCache, imageCache, tetris);

        // final Rectangle levelRectangle = new Rectangle(0, BLOCK_SIZE * 3, BLOCK_SIZE * 6, BLOCK_SIZE);
        final Rectangle scoreRectangle = new Rectangle(0, BLOCK_SIZE, BLOCK_SIZE * 6, BLOCK_SIZE);
//        final Rectangle rowsRectangle = new Rectangle(0, BLOCK_SIZE * 9, BLOCK_SIZE * 6, BLOCK_SIZE);

//        rowsTetrisInfo = new ValueTetrisInfo(rowsRectangle, ROWS);
        scoreTetrisInfo = new ValueTetrisInfo(scoreRectangle, SCORE);
        // levelTetrisInfo = new ValueTetrisInfo(levelRectangle, LEVEL);

        nextShapeTetrisInfo = new NextShapeTetrisInfo();

        initializeComponent(VIEW_WIDTH, VIEW_HEIGHT);
    }

    @Override
    protected void doPaint(final Graphics2D graphics) {
        // levelTetrisInfo.render(graphics, tetris.getLevel());
        scoreTetrisInfo.render(graphics, tetris.getScore());
//        rowsTetrisInfo.render(graphics, tetris.getCompletedRows());
        nextShapeTetrisInfo = new NextShapeTetrisInfo();
        nextShapeTetrisInfo.render(graphics);
    }
}
