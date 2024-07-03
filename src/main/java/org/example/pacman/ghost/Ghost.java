package org.example.pacman.ghost;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.pacman.app.Game;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;


abstract public class Ghost {
    int x, y, dx, dy, moveUnit = 3, blockSize, borderPixel = 2;
    protected ImageView imageView;

    protected void setSpeed(Game.Direction dir) {
        if (dir == Game.Direction.D) {
            dx = 0;
            dy = moveUnit;
        } else if (dir == Game.Direction.U) {
            dx = 0;
            dy = -moveUnit;
        } else if (dir == Game.Direction.L) {
            dx = -moveUnit;
            dy = 0;
        } else if (dir == Game.Direction.R) {
            dx = moveUnit;
            dy = 0;
        }
    }

    protected ImageView imageViewSetUp(String path) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setX(this.x);
        imageView.setY(this.y);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(blockSize);
        return imageView;
    }

    protected List<Game.Direction> validDirections(Cell currentCell) {
        List<Game.Direction> valid = new ArrayList<>();
        if (!currentCell.isRightBordered()) {
            valid.add(Game.Direction.R);
        }
        if (!currentCell.isLeftBordered()) {
            valid.add(Game.Direction.L);
        }
        if (!currentCell.isTopBordered()) {
            valid.add(Game.Direction.U);
        }
        if (!currentCell.isBottomBordered()) {
            valid.add(Game.Direction.D);
        }
        return valid;
    }

    protected Game.Direction currentDirection() {
        if (dx > 0) {
            return Game.Direction.R;
        }
        if (dx < 0) {
            return Game.Direction.L;
        }
        if (dy > 0) {
            return Game.Direction.D;
        }
        return Game.Direction.U;
    }

    public ImageView getImageView() {
        return imageView;
    }

    protected Game.Direction oppositeDirection(Game.Direction dir) {
        if (dir == Game.Direction.U)
            return Game.Direction.D;
        if (dir == Game.Direction.D)
            return Game.Direction.U;
        if (dir == Game.Direction.L)
            return Game.Direction.R;
        return Game.Direction.L;
    }

}