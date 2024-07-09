import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    // Private fields to store player's coordinates, score, lives, movement status, and bullets
    private int x;
    private int y;
    private int score;
    private int lives;
    private boolean moveLeft;
    private boolean moveRight;
    private List<PlayerBullet> bullets;

    // Constructor to initialize player's default state
    public Player() {
        this.x = 250;
        this.y = 500;
        this.score = 0;
        this.lives = 3;
        this.moveLeft = false;
        this.moveRight = false;
        this.bullets = new ArrayList<>();
    }

    // Getter method to retrieve the player's x-coordinate
    public int getX() {
        return x;
    }

    // Getter method to retrieve the player's y-coordinate
    public int getY() {
        return y;
    }

    // Getter method to retrieve the player's score
    public int getScore() {
        return score;
    }

    // Getter method to retrieve the player's remaining lives
    public int getLives() {
        return lives;
    }

    // Getter method to retrieve the player's bullets
    public List<PlayerBullet> getBullets() {
        return bullets;
    }

    // Methods to handle player movement
    public void startMoveLeft() {
        moveLeft = true;
    }

    public void stopMoveLeft() {
        moveLeft = false;
    }

    public void startMoveRight() {
        moveRight = true;
    }

    public void stopMoveRight() {
        moveRight = false;
    }

    // Method to make the player shoot bullets
    public void shoot() {
        if (bullets.size() < 3) {
            bullets.add(new PlayerBullet(x + 25, y));
        }
    }

    // Methods to move the player left or right within the screen boundaries
    public void moveLeft() {
        if (moveLeft && x > 0) {
            x -= 5;
        }
    }

    public void moveRight() {
        if (moveRight && x < 450) {
            x += 5;
        }
    }

    // Methods to update the player's score and decrement lives
    public void increaseScore(int points) {
        score += points;
    }

    public void decrementLives() {
        lives--;
        if (lives == 0) {
            // Display game over message with the player's score and exit the program
            JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + score);
            System.exit(0);
        }
    }
}
