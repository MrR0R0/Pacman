package org.example.pacman.map;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int x, y;
    String binaryCode;
    private final Rectangle rect = new Rectangle();
    private final Circle circle = new Circle();

    public Cell(int x, int y, int code, int blockSize) {
        this.x = x;
        this.y = y;
        if(code != -1){
            this.binaryCode = Integer.toBinaryString(code);
        }
        this.binaryCode = String.format("%" + 9 + "s", this.binaryCode).replace(" ", "0");
        if (binaryCode.charAt(0) == '1') {
            double ratio = 0.9;
            rect.setX(x + (0.5 - ratio / 2) * blockSize);
            rect.setY(y + (0.5 - ratio / 2) * blockSize);
            rect.setStroke(Color.BLUE);
            rect.setFill(Color.BLUE);
            rect.setWidth(blockSize * 0.9);
            rect.setHeight(blockSize * 0.9);
        }
        if (binaryCode.charAt(4) == '1') {
            double ratio = 0.15;
            circle.setCenterX(x + (double) blockSize / 2);
            circle.setCenterY(y + (double) blockSize / 2);
            circle.setRadius((double) blockSize * ratio);
            circle.setStroke(Color.YELLOW);
            circle.setFill(Color.YELLOW);
        }
        if (binaryCode.charAt(3) == '1') {
            double ratio = 0.25;
            circle.setCenterX(x + (double) blockSize / 2);
            circle.setCenterY(y + (double) blockSize / 2);
            circle.setRadius((double) blockSize * ratio);
            circle.setStroke(Color.YELLOW);
            circle.setFill(Color.YELLOW);
        }
    }

    public void draw(Group root) {
        root.getChildren().add(rect);
        root.getChildren().add(circle);
    }

    public String getBinaryCode() {
        return binaryCode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftBordered() {
        return binaryCode.charAt(8) == '1';
    }

    public boolean isRightBordered() {
        return binaryCode.charAt(7) == '1';
    }

    public boolean isTopBordered() {
        return binaryCode.charAt(6) == '1';
    }

    public boolean isBottomBordered() {
        return binaryCode.charAt(5) == '1';
    }

    public List<Boolean> borders() {
        List<Boolean> tmp = new ArrayList<>();
        tmp.add(isTopBordered());
        tmp.add(isRightBordered());
        tmp.add(isBottomBordered());
        tmp.add(isLeftBordered());
        return tmp;
    }

    public boolean hasSmallDot() {
        return binaryCode.charAt(4) == '1';
    }

    public boolean hasBigDot() {
        return binaryCode.charAt(3) == '1';
    }

    public void removeDot() {
        if (binaryCode.charAt(3) == '1' || binaryCode.charAt(4) == '1') {
            binaryCode = binaryCode.substring(0, 3) + "00" + binaryCode.substring(5);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.BLACK);
        }
    }
}
