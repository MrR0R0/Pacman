package org.example.pacman.Ghost;


import javafx.scene.Group;
import org.example.pacman.Game;
import org.example.pacman.Map.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Follows pacman
public class Shadow extends Ghost{
    private int moveCounter;
    private Cell prevCell = null;

    public Shadow(int x, int y, Game.Direction dir, Group root, int blockSize){
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        setSpeed(dir);
        imageView = imageViewSetUp("file:src\\main\\resources\\shadow\\down.gif");
        root.getChildren().add(imageView);
    }

    private Game.Direction bestDir(int xPac, int yPac, Cell currentCell) {
        List<Game.Direction> valid = validDirections(currentCell);
        List<Game.Direction> best = new ArrayList<>();

        //either U or D
        if (xPac == x) {
            if (yPac < y) {
                if (valid.contains(Game.Direction.U))
                    return Game.Direction.U;
            } else {
                if (valid.contains(Game.Direction.D))
                    return Game.Direction.D;
            }
        }

        //either L or R
        if (yPac == y) {
            if (xPac < x) {
                if (valid.contains(Game.Direction.L))
                    return Game.Direction.L;
            } else {
                if (valid.contains(Game.Direction.R))
                    return Game.Direction.R;
            }
        }

        //either R or U
        if (xPac > x && yPac < y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either R or D
        if (xPac > x && yPac > y) {
            if (valid.contains(Game.Direction.R))
                best.add(Game.Direction.R);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or D
        if (xPac < x && yPac > y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.D))
                best.add(Game.Direction.D);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        //either L or U
        if (xPac < x && yPac < y) {
            if (valid.contains(Game.Direction.L))
                best.add(Game.Direction.L);
            if (valid.contains(Game.Direction.U))
                best.add(Game.Direction.U);
            if (!best.isEmpty())
                return best.get(new Random().nextInt(best.size()));
        }

        return valid.get(new Random().nextInt(valid.size()));
    }

    public void move(List<List<Cell>> map, int xPac, int yPac) {
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
                Game.Direction tmpDir = bestDir(xPac, yPac, currentCell);
                setSpeed(tmpDir);
                y = row * blockSize;
                x = col * blockSize;
            }
        }

        if (currentCell.isTopBordered()) {
            if (currentDirection().equals(Game.Direction.U)) {
                setSpeed(bestDir(xPac, yPac, currentCell));
                y = row * blockSize;
            }
        }
        if (currentCell.isBottomBordered()) {
            if (currentDirection().equals(Game.Direction.D)) {
                setSpeed(bestDir(xPac, yPac, currentCell));
                y = row * blockSize;
            }
        }
        if (currentCell.isLeftBordered()) {
            if (currentDirection().equals(Game.Direction.L)) {
                setSpeed(bestDir(xPac, yPac, currentCell));
                x = col * blockSize;
            }
        }
        if (currentCell.isRightBordered()) {
            if (currentDirection().equals(Game.Direction.R)) {
                setSpeed(bestDir(xPac, yPac, currentCell));
                x = col * blockSize;
            }
        }

        imageView.setX(x);
        imageView.setY(y);
    }
}