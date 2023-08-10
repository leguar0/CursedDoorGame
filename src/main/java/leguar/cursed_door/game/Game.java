package leguar.cursed_door.game;

import leguar.cursed_door.game.profession.Hero;
import leguar.cursed_door.game.profession.HeroType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game {

    private int levelCounter = 1;
    private final Player player;
    private final List<Mob> mobs;
    private DoorType leftDoor;
    private DoorType rightDoor;
    private boolean isAlive;
    private boolean mobIsAlive;
    private int roundCounter;

    public Game(Player player) {
        this.player = player;
        this.isAlive = true;
        mobs = new ArrayList<>();
        initializeMobs();
    }

    public Player getPlayer() {
        return player;
    }

    public DoorType getLeftDoor() {
        return leftDoor;
    }

    public DoorType getRightDoor() {
        return rightDoor;
    }

    public int getLevelCounter() {
        return levelCounter;
    }

    public void addLevel() {
        levelCounter++;
    }

    public int getMaxLevelDoor() {
        return 100;
    }

    public List<Mob> getMobs() {
        return mobs;
    }

    public void setRoundCounter() {
        roundCounter = 1;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setMobIsAlive() {
        mobIsAlive = true;
    }

    public boolean mobIsAlive() {
        return mobIsAlive;
    }

    public boolean isFinish() {
        int MAX_LEVEL = 100;
        return levelCounter > MAX_LEVEL;
    }

    private void initializeMobs() {
        String filePath = "mobs.txt";

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                int rand = (int) (Math.random() * 3 + 1);

                mobs.add(new Mob(values[0], Objects.requireNonNull(HeroType.getHeroType(rand)), Integer.parseInt(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3])));
            }

            scanner.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public void randomDoor() {
        if (levelCounter == 25 || levelCounter == 50 || levelCounter == 75 || levelCounter == 100) {
            leftDoor = DoorType.getImage(DoorType.MONSTROUS.getNumber());
            rightDoor = leftDoor;
        } else {
            int random = (int) (Math.random() * 4);

            leftDoor = DoorType.getImage(random);

            do {
                random = (int) (Math.random() * 4);
                rightDoor = DoorType.getImage(random);
            } while (rightDoor == leftDoor);
        }
    }

    public void trapDoor() {
        int rand = (int) (Math.random() * 3);

        switch (rand) {
            case 0 -> player.updateMoney(levelCounter * 0.5);
            case 1 -> {
                player.updateHealth(levelCounter * 0.25);

                if (player.getHero().getHealth() < 0) {
                    isAlive = false;
                }
            }
        }
    }

    public boolean mysteriousDoor() {
        int rand = (int) (Math.random() * 4);

        if (player.getKeys() > 0) {
            switch (rand) {
                case 1 -> {
                    player.getHero().setHealth(player.getHero().getHealth() + levelCounter * 0.3);

                    if (player.getHero().getHealth() < 0) {
                        isAlive = false;
                    }
                }
                case 2 -> player.updateMoney(-levelCounter * 0.5);
            }
            player.setKeys();
            return true;
        } else {
            return false;
        }
    }

    public void fight() {
        Hero playerHero = player.getHero();
        Hero mobHero = mobs.get(levelCounter).getHero();

        if (playerHero.getHealth() < 0) {
            isAlive = false;
            return;
        } else {
            if (playerHero.getEnergy() >= playerHero.getAttackEnergyCost()) {
                mobHero.setHealth(playerHero.getAttack());
            }
        }
        if (mobHero.getHealth() < 0) {
            mobIsAlive = false;
        } else {
            if (mobHero.getEnergy() >= mobHero.getAttackEnergyCost()) {
                playerHero.setHealth(mobHero.getAttack());
            }
        }

        if (!mobIsAlive) {
            player.winFight();
        }
        roundCounter++;
        playerHero.setEnergy(-(playerHero.getEnergyMax() * 0.1));
        mobHero.setEnergy(-(mobHero.getEnergyMax() * 0.1));
    }
}
