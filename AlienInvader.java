import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienInvader {

    // Fields to store the position, original position, movement direction, status, last shot time, shooting ability, and related lists
    private int x;
    private int y;
    private int originalX;
    private int direction;
    private boolean alive;
    private long lastShotTime;
    private boolean canShoot;
    private List<Integer> shootersList;
    private List<AlienInvader> invaders;
    private int shootingIndex;

    // Constructor to initialize an alien invader
    public AlienInvader(int x, int y, List<Integer> shootersList, List<AlienInvader> invaders) {
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.direction = 1;
        this.alive = true;
        this.lastShotTime = System.currentTimeMillis();
        this.canShoot = true;
        this.shootersList = shootersList;
        this.invaders = invaders;
    }

    // Getter method to retrieve the x-coordinate of the alien invader
    public int getX() {
        return x;
    }

    // Getter method to retrieve the y-coordinate of the alien invader
    public int getY() {
        return y;
    }

    // Getter method to check if the alien invader is alive
    public boolean isAlive() {
        return alive;
    }

    // Setter method to set the alive status of the alien invader
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // Getter method to check if the alien invader can shoot
    public boolean canShoot() {
        return canShoot;
    }

    // Method to make the alien invader shoot bullets
    public List<AlienBullet> shoot() {
        if (canShoot) {
            lastShotTime = System.currentTimeMillis();
            canShoot = false;

            List<AlienBullet> bullets = new ArrayList<>();

            if (shootingIndex == -1) {
                Random random = new Random();
                List<Integer> availableShooters = new ArrayList<>(shootersList);

                if (shootingIndex != -1) {
                    availableShooters.remove(Integer.valueOf(shootingIndex));
                }

                if (!availableShooters.isEmpty()) {
                    shootingIndex = availableShooters.get(random.nextInt(availableShooters.size()));
                    bullets.add(new AlienBullet(invaders.get(shootingIndex).getX() + 15, invaders.get(shootingIndex).getY() + 30));
                }
            } else {
                bullets.add(new AlienBullet(invaders.get(shootingIndex).getX() + 15, invaders.get(shootingIndex).getY() + 30));
                shootingIndex = -1;
            }

            return bullets;
        }
        return null;
    }

    // Method to update the shooting cooldown
    public void updateCooldown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 3000) {
            canShoot = true;
        }
    }

    // Method to move the alien invader horizontally within a range
    public void move() {
        if (alive) {
            x += direction;
            if (x <= originalX - 100 || x >= originalX + 100) {
                direction *= -1;
            }
        }
    }
}
