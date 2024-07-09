public class PlayerBullet {
    
    // Private fields to store the bullet's coordinates and its activity status
    private int x;
    private int y;
    private boolean active;

    // Constructor to initialize the bullet's position and set it as active
    public PlayerBullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    // Getter method to retrieve the x-coordinate of the bullet
    public int getX() {
        return x;
    }

    // Getter method to retrieve the y-coordinate of the bullet
    public int getY() {
        return y;
    }

    // Getter method to check if the bullet is active or not
    public boolean isActive() {
        return active;
    }

    // Method to move the bullet upwards; also deactivates the bullet if it goes above the screen
    public void move() {
        if (active) {
            y -= 5; // Move the bullet upwards by decreasing the y-coordinate
            if (y < 0) {
                active = false; // Deactivate the bullet if it goes above the screen
            }
        }
    }

    // Method to deactivate the bullet
    public void deactivate() {
        active = false;
    }
}
