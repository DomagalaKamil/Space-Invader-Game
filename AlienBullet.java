public class AlienBullet {

    // Fields to store the position and activity status of the alien bullet
    private int x;
    private int y;
    private boolean active;

    // Constructor to initialize the alien bullet's position and set it as active
    public AlienBullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    // Getter method to retrieve the x-coordinate of the alien bullet
    public int getX() {
        return x;
    }

    // Getter method to retrieve the y-coordinate of the alien bullet
    public int getY() {
        return y;
    }

    // Getter method to check if the alien bullet is active
    public boolean isActive() {
        return active;
    }

    // Method to move the alien bullet downwards; also deactivates the bullet if it goes below the screen
    public void move() {
        if (active) {
            y += 3; // Move the alien bullet downwards by increasing the y-coordinate
            if (y > 600) {
                active = false; // Deactivate the bullet if it goes below the screen
            }
        }
    }

    // Method to deactivate the alien bullet
    public void deactivate() {
        active = false;
    }
}
