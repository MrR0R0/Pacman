package org.example.pacman.app;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.pacman.Application;
import org.example.pacman.SoundEffect;
import org.example.pacman.map.Cell;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Pacman {
    //U:0 R:1 D:2 L:3
    private final int moveUnit = 3, blockSize, borderPixel = 3, xBlocks, yBlocks;
    private int x, y, dx = moveUnit, dy = 0, angle = 0, score = 0;
    private int lives;
    private final ImageView imageView;
    private List<ImageView> hearts = new ArrayList<>();
    private Game.Direction nextDirection;
    private Text text;

    public Pacman(int x, int y, Game.Direction dir, Group root, int blockSize, int xBlocks, int yBlocks, int lives) {
        this.x = x;
        this.y = y;
        this.xBlocks = xBlocks;
        this.yBlocks = yBlocks;
        this.blockSize = blockSize;
        this.nextDirection = dir;
        this.lives = lives;
        //setting up images
        imageView = imageViewSetUp("file:src\\main\\resources\\pacman.gif");
        text = new Text();
        text.setText("Score: " + score);
        text.setX(blockSize * xBlocks - 150);
        text.setY(blockSize * yBlocks + 30);
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.YELLOWGREEN);

        //setting up hearts
        heartSetUp();
        for(int i=0; i<lives; i++){
            root.getChildren().add(hearts.get(i));
        }
        root.getChildren().add(text);
        root.getChildren().add(imageView);
    }

    public void changeDirection(Game.Direction dir) {
        this.nextDirection = dir;
    }

    public void move(List<List<Cell>> map) {
        x += dx;
        y += dy;

        int col = (x + blockSize / 2) / blockSize;
        int row = (y + blockSize / 2) / blockSize;
        Cell currentCell = map.get(row).get(col);

        //Food control
        if (currentCell.hasSmallDot()) {
            score += 10;
            text.setText("Score: " + score);
            Game.remainingDot--;
            currentCell.removeDot();
            SoundEffect.playMunchSound();
        }
        if (currentCell.hasBigDot()) {
            score += 50;
            text.setText("Score: " + score);
            Game.remainingDot--;
            currentCell.removeDot();
            SoundEffect.playYummySound();
        }

        //Game.Direction control
        if ((currentDirection() == Game.Direction.U || nextDirection == Game.Direction.U) && currentCell.isTopBordered()) {
            if (y - currentCell.getY() < borderPixel)
                y = row * blockSize;
        }
        if ((currentDirection() == Game.Direction.D || nextDirection == Game.Direction.D) && currentCell.isBottomBordered()) {
            if (currentCell.getY() - y < borderPixel)
                y = row * blockSize;
        }
        if ((currentDirection() == Game.Direction.L || nextDirection == Game.Direction.L) && currentCell.isLeftBordered()) {
            if (x - currentCell.getX() < borderPixel)
                x = col * blockSize;
        }
        if ((currentDirection() == Game.Direction.R || nextDirection == Game.Direction.R) && currentCell.isRightBordered()) {
            if (currentCell.getX() - x < borderPixel)
                x = col * blockSize;
        }
        if (nextDirection == Game.Direction.U && !currentCell.isTopBordered()) {
            checkForRotation();
            dx = 0;
            dy = -moveUnit;
            x = col * blockSize;
        }
        if (nextDirection == Game.Direction.D && !currentCell.isBottomBordered()) {
            checkForRotation();
            dx = 0;
            dy = moveUnit;
            x = col * blockSize;

        }
        if (nextDirection == Game.Direction.L && !currentCell.isLeftBordered()) {
            checkForRotation();
            y = row * blockSize;
            dx = -moveUnit;
            dy = 0;
        }
        if (nextDirection == Game.Direction.R && !currentCell.isRightBordered()) {
            checkForRotation();
            y = row * blockSize;
            dx = moveUnit;
            dy = 0;
        }

        imageView.setX(x);
        imageView.setY(y);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    private ImageView imageViewSetUp(String path) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setX(this.x);
        imageView.setY(this.y);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(blockSize);
        return imageView;
    }

    public Game.Direction currentDirection() {
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

    private void checkForRotation() {
        if (nextDirection != Game.Direction.NA) {
            angle += (nextDirection.ordinal() - currentDirection().ordinal()) * 90;
            imageView.setRotate(angle);
            nextDirection = Game.Direction.NA;
        }
    }

    public boolean checkCollision(ImageView imageView) {
        double distanceX = abs(imageView.getX() - x);
        double distanceY = abs(imageView.getY() - y);
        return sqrt(pow(distanceY, 2) + pow(distanceX, 2)) < blockSize * 0.75;
    }

    private void heartSetUp(){
        for(int i=0; i<lives; i++){
            hearts.add(new ImageView(new Image("file:src\\main\\resources\\heart.jpg")));
        }
        for(int i=0; i<lives; i++){
            hearts.get(i).setY(blockSize * yBlocks);
            hearts.get(i).setX(blockSize * (1+i));
            hearts.get(i).setPreserveRatio(true);
            hearts.get(i).setFitHeight(blockSize);
        }
    }

    public void removeHearts(Group root){
        for(int i=0; i<hearts.size(); i++){
            root.getChildren().remove(hearts.get(i));
        }
    }

    public void removeALife(){
        lives--;
    }

    public int getLives(){
        return lives;
    }

    public void removeFromScene(Group root){
        root.getChildren().remove(imageView);
    }
}
