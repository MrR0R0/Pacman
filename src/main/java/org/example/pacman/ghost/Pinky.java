package org.example.pacman.ghost;


import javafx.scene.Group;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//Targets two blocks ahead of pacman
public class Pinky extends Ghost {
    private int moveCounter;
    private Cell prevCell = null;
    private final int height, width;

    public Pinky(int x, int y, Game.Direction dir, Group root, int blockSize, int height, int width) {
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        this.height = height;
        this.width = width;
        setSpeed(dir);
        imageView = imageViewSetUp("file:src\\main\\resources\\pinky\\down.gif");
        root.getChildren().add(imageView);
    }

    private Game.Direction bestDir(int xPac, int yPac, Cell currentCell, Game.Direction dir) {
        List<Game.Direction> valid = validDirections(currentCell);
        List<Game.Direction> best = new ArrayList<>();
        List<Integer> aheadCoordinates = twoBlocksAhead(xPac, yPac, dir);
        int xAheadPac = aheadCoordinates.get(0);
        int yAheadPac = aheadCoordinates.get(1);
        //either U or D
        if (xAheadPac == x) {
            if (yAheadPac < y) {
                if (valid.contains(Game.Direction.U))
                    return Game.Direction.U;
            } else {
                if (valid.contains(Game.Direction.D))
                    return Game.Direction.D;
            }
        }

        //either L or R
        if (yAheadPac == y) {
            if (xAheadPac < x) {
                if (valid.contains(Game.Direction.L))
                    return Game.Direction.L;
            } else {
                if (valid.contains(Game.Direction.R))
                    return Game.Direction.R;
            }
        }

        //either R or U
        if (xAheadPac > x && yAheadPac < y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either R or D
        if (xAheadPac > x && yAheadPac > y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or D
        if (xAheadPac < x && yAheadPac > y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or U
        if (xAheadPac < x && yAheadPac < y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        return valid.get(new Random().nextInt(valid.size()));
    }

    public void move(List<List<Cell>> map, int xPac, int yPac, Game.Direction dir) {
        int col = (x + blockSize / 2) / blockSize;
        int row = (y + blockSize / 2) / blockSize;
        Cell currentCell = map.get(row).get(col);
        prevCell = currentCell;
        x += dx;
        y += dy;
        moveCounter++;
        col = (x + blockSize / 2) / blockSize;
        row = (y + blockSize / 2) / blockSize;
        currentCell = map.get(row).get(col);

        if (!prevCell.borders().equals(currentCell.borders()) && moveCounter > (blockSize / moveUnit) + 5) {
            if (Math.abs(y - currentCell.getY()) < borderPixel && Math.abs(x - currentCell.getX()) < borderPixel) {
                moveCounter = 0;
                prevCell = currentCell;
                Game.Direction tmpDir = bestDir(xPac, yPac, currentCell, dir);
                setSpeed(tmpDir);
                y = row * blockSize;
                x = col * blockSize;
            }
        }

        if (currentCell.isTopBordered()) {
            if (currentDirection().equals(Game.Direction.U)) {
                setSpeed(bestDir(xPac, yPac, currentCell, dir));
                y = row * blockSize;
            }
        }
        if (currentCell.isBottomBordered()) {
            if (currentDirection().equals(Game.Direction.D)) {
                setSpeed(bestDir(xPac, yPac, currentCell, dir));
                y = row * blockSize;
            }
        }
        if (currentCell.isLeftBordered()) {
            if (currentDirection().equals(Game.Direction.L)) {
                setSpeed(bestDir(xPac, yPac, currentCell, dir));
                x = col * blockSize;
            }
        }
        if (currentCell.isRightBordered()) {
            if (currentDirection().equals(Game.Direction.R)) {
                setSpeed(bestDir(xPac, yPac, currentCell, dir));
                x = col * blockSize;
            }
        }

        imageView.setX(x);
        imageView.setY(y);
    }

    private ArrayList<Integer> twoBlocksAhead(int xPac, int yPac, Game.Direction direction) {
        ArrayList<Integer> result = new ArrayList<>();
        switch (direction) {
            case D -> {
                result.add(xPac);
                result.add(Math.min(yPac + blockSize * 2, height));
            }
            case U -> {
                result.add(xPac);
                result.add(Math.max(yPac - blockSize * 2, 0));
            }
            case R -> {
                result.add(Math.min(xPac + blockSize * 2, width));
                result.add(yPac);
            }
            case L -> {
                result.add(Math.max(xPac - blockSize * 2, 0));
                result.add(yPac);
            }
        }
        return result;
    }
}