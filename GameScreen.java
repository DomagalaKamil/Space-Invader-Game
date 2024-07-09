import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends JPanel {

    // Game elements
    private Player player;
    private List<AlienInvader> invaders;
    private List<PlayerBullet> playerBullets;
    private List<AlienBullet> alienBullets;
    private long lastAlienSpawnTime;
    private JLabel scoreLabel;
    private List<Integer> shootersList;
    private int currentShooterIndex;

    // Constructor to initialize the game screen
    public GameScreen() {
        // Initializing game elements
        player = new Player();
        invaders = new ArrayList<>();
        playerBullets = new ArrayList<>();
        alienBullets = new ArrayList<>();
        shootersList = new ArrayList<>();

        // Creating initial set of alien invaders
        for (int i = 0; i < 10; i++) {
            invaders.add(new AlienInvader(i * 50, 50, shootersList, invaders));
            shootersList.add(i);
        }

        lastAlienSpawnTime = System.currentTimeMillis();

        // Setting up key listener for player controls
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.startMoveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.startMoveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    player.shoot();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.stopMoveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.stopMoveRight();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        // Setting up score label and game update timer
        scoreLabel = new JLabel("Score: 0   Lives: " + player.getLives());
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(scoreLabel);

        new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                repaint();
            }
        }).start();
    }

    // Method to update the game state
    private void updateGame() {
        // Updating player bullets and alien bullets
        for (PlayerBullet bullet : player.getBullets()) {
            bullet.move();
        }

        for (AlienBullet alienBullet : alienBullets) {
            alienBullet.move();
        }

        // Handling collisions between player bullets and alien invaders
        Iterator<AlienInvader> iterator = invaders.iterator();
        while (iterator.hasNext()) {
            AlienInvader invader = iterator.next();

            for (PlayerBullet bullet : player.getBullets()) {
                if (bullet.isActive() && invader.isAlive() && isCollision(bullet, invader)) {
                    bullet.deactivate();
                    player.increaseScore(100);
                    invader.setAlive(false);
                }
            }

            // Shooting bullets from alive invaders
            if (invader.isAlive() && invader.canShoot()) {
                List<AlienBullet> alienBullets = invader.shoot();
                if (alienBullets != null) {
                    this.alienBullets.addAll(alienBullets);
                }
            }
        }

        // Handling collisions between alien bullets and the player
        for (AlienBullet alienBullet : alienBullets) {
            if (alienBullet.isActive() && isCollision(alienBullet, player)) {
                alienBullet.deactivate();
                player.decrementLives();
            }
        }

        // Removing inactive bullets
        player.getBullets().removeIf(bullet -> !bullet.isActive());
        alienBullets.removeIf(bullet -> !bullet.isActive());

        // Respawning aliens if all are defeated
        if (invaders.stream().noneMatch(AlienInvader::isAlive)) {
            respawnAliens();
        }

        // Moving player and aliens, updating cooldowns
        player.moveLeft();
        player.moveRight();

        for (AlienInvader invader : invaders) {
            invader.move();
            invader.updateCooldown();
        }

        // Updating score label
        scoreLabel.setText("Score: " + player.getScore() + "   Lives: " + player.getLives());
    }

    // Method to respawn aliens
    private void respawnAliens() {
        invaders.clear();
        for (int i = 0; i < 10; i++) {
            invaders.add(new AlienInvader(i * 50, 50, shootersList, invaders));
        }

        shootersList.clear();
        for (int i = 0; i < invaders.size(); i++) {
            shootersList.add(i);
        }

        currentShooterIndex = 0;
    }

    // Method to check collision between a player bullet and an alien invader
    private boolean isCollision(PlayerBullet bullet, AlienInvader invader) {
        return bullet.getX() < invader.getX() + 30 &&
                bullet.getX() + 5 > invader.getX() &&
                bullet.getY() < invader.getY() + 30 &&
                bullet.getY() + 10 > invader.getY();
    }

    // Method to check collision between an alien bullet and the player
    private boolean isCollision(AlienBullet alienBullet, Player player) {
        return alienBullet.getX() < player.getX() + 50 &&
                alienBullet.getX() + 5 > player.getX() &&
                alienBullet.getY() < player.getY() + 10 &&
                alienBullet.getY() + 10 > player.getY();
    }

    // Method to paint the game components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Drawing player and aliens
        g.fillRect(player.getX(), player.getY(), 50, 10);

        for (AlienInvader invader : invaders) {
            if (invader.isAlive()) {
                g.fillRect(invader.getX(), invader.getY(), 30, 30);
            }
        }

        // Drawing player bullets and alien bullets
        for (PlayerBullet bullet : player.getBullets()) {
            if (bullet.isActive()) {
                g.fillRect(bullet.getX(), bullet.getY(), 5, 10);
            }
        }

        for (AlienBullet alienBullet : alienBullets) {
            if (alienBullet.isActive()) {
                g.fillRect(alienBullet.getX(), alienBullet.getY(), 5, 10);
            }
        }
    }
}
