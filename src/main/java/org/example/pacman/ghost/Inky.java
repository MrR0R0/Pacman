package org.example.pacman.ghost;


import javafx.scene.Group;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.List;
import java.util.Random;

//Protects a certain area of the map
public class Inky extends Ghost{
    private int moveCounter;
    private Cell prevCell = null;

    public Inky(int x, int y, Game.Direction dir, Group root, int blockSize){
        this.x = x;
        this.y = y;
        this.blockSize = blockSize;
        setSpeed(dir);
        imageView = imageViewSetUp("file:src\\main\\resources\\inky\\down.gif");
        root.getChildren().add(imageView);
    }

    private Game.Direction bestDir(Cell currentCell) {
        List<Game.Direction> valid = validDirections(currentCell);
        Game.Direction currentDir = currentDirection();
        Game.Direction oppositeDir = oppositeDirection(currentDir);
        if (valid.contains(oppositeDir) && valid.size() > 1) {
            valid.remove(oppositeDir);
        }
        if(valid.contains(Game.Direction.L)) {
            return Game.Direction.L;
        }
        if(valid.contains(Game.Direction.D)){
            return Game.Direction.D;
        }
        return valid.get(new Random().nextInt(valid.size()));
    }

    public void move(List<List<Cell>> map) {
        int col = (x + blockSize / 2) / blockSize;
        int row = (y + blockSize / 2) / blockSize;
        Cell currentCell = map.get(row).get(col);
        Cell prevCell = currentCell;
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
                Game.Direction tmpDir = bestDir(currentCell);
                setSpeed(tmpDir);
                y = row * blockSize;
                x = col * blockSize;
            }
        }

        if (currentCell.isTopBordered()) {
            if (currentDirection().equals(Game.Direction.U)) {
                setSpeed(bestDir(currentCell));
                y = row * blockSize;
            }
        }
        if (currentCell.isBottomBordered()) {
            if (currentDirection().equals(Game.Direction.D)) {
                setSpeed(bestDir(currentCell));
                y = row * blockSize;
            }
        }
        if (currentCell.isLeftBordered()) {
            if (currentDirection().equals(Game.Direction.L)) {
                setSpeed(bestDir(currentCell));
                x = col * blockSize;
            }
        }
        if (currentCell.isRightBordered()) {
            if (currentDirection().equals(Game.Direction.R)) {
                setSpeed(bestDir(currentCell));
                x = col * blockSize;
            }
        }

        imageView.setX(x);
        imageView.setY(y);
    }
}