/*
 * Copyright © 2016-2017 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.tetris.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import spypunk.tetris.model.Shape.Block;
import spypunk.tetris.model.Tetris.State;

public class TetrisInstance {

    private final Map<Point, Block> blocks = Maps.newHashMap();

    private final Map<ShapeType, Integer> statistics;

    private final List<TetrisEvent> tetrisEvents = Lists.newArrayList();

    private State state = State.STOPPED;

    private Shape currentShape;

    private List<Shape> nextShape = Lists.newArrayList();

    private Optional<Movement> movement = Optional.empty();

    private int level;

    private int score;

    private int completedRows;

    private int speed;

    private int currentGravityFrame;

    private int currentMovementScore;

    private boolean currentShapeLocked;

    private boolean hardDropEnabled;

    public TetrisInstance() {
        statistics = Arrays.asList(ShapeType.values()).stream()
                .collect(Collectors.toMap(Function.identity(), shapeType -> 0));
    }

    public Map<Point, Block> getBlocks() {
        return blocks;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(final Shape currentShape) {
        this.currentShape = currentShape;
    }

    public List<Shape> getNextShape() {
        return this.nextShape;
    }

    public void setNextShape(final List<Shape> nextShape) {
        this.nextShape = nextShape;
    }

    public Optional<Movement> getMovement() {
        return movement;
    }

    public void setMovement(final Optional<Movement> movement) {
        this.movement = movement;
    }

    public Map<ShapeType, Integer> getStatistics() {
        return statistics;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public int getCompletedRows() {
        return completedRows;
    }

    public void setCompletedRows(final int completedRows) {
        this.completedRows = completedRows;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    public int getCurrentGravityFrame() {
        return currentGravityFrame;
    }

    public void setCurrentGravityFrame(final int currentGravityFrame) {
        this.currentGravityFrame = currentGravityFrame;
    }

    public int getCurrentMovementScore() {
        return currentMovementScore;
    }

    public void setCurrentMovementScore(final int currentMovementScore) {
        this.currentMovementScore = currentMovementScore;
    }

    public boolean isCurrentShapeLocked() {
        return currentShapeLocked;
    }

    public void setCurrentShapeLocked(final boolean currentShapeLocked) {
        this.currentShapeLocked = currentShapeLocked;
    }

    public boolean isHardDropEnabled() {
        return hardDropEnabled;
    }

    public void setHardDropEnabled(final boolean hardDropEnabled) {
        this.hardDropEnabled = hardDropEnabled;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public List<TetrisEvent> getTetrisEvents() {
        return tetrisEvents;
    }
}
