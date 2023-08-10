package leguar.cursed_door.game;

import leguar.cursed_door.game.profession.*;

public class Player {
    private Hero hero;
    private double money;
    private int keys;
    private double costPower;
    private double costHealth;
    private double costEnergy;

    public Player(HeroType heroType) {
        switch (heroType) {
            case ARCHER -> hero = new Archer(15, 250, 100);
            case BERSERKER -> hero = new Berserker(16, 225, 110);
            case DRUID -> hero = new Druid(13, 200, 80);
        }
        money = 0;
        keys = 0;

        costPower = 0.5;
        costHealth = 0.5;
        costEnergy = 0.5;
    }

    public double getMoney() {
        return money;
    }

    public int getKeys() {
        return keys;
    }

    public Hero getHero() {
        return hero;
    }

    public double getCost(String statName) {
        switch (statName) {
            case "POWER" -> {
                return costPower;
            }
            case "HEALTH" -> {
                return costHealth;
            }
            case "ENERGY" -> {
                return costEnergy;
            }
        }
        return 0;
    }

    public void updateCost(String statName, double cost) {
        money -= cost;

        switch (statName) {
            case "POWER" -> costPower += 0.5;
            case "HEALTH" -> costHealth += 0.5;
            case "ENERGY" -> costEnergy += 0.5;
        }
    }

    public void updateMoney(double cost) {
        double newMoney = money - cost;

        if (newMoney < 0) {
            money = 0;
        } else {
            money = newMoney;
        }
    }

    public void updateHealth(double damage) {
        hero.setHealth(damage);
    }

    public void winFight() {
        keys++;
    }

    public void setKeys() {
        keys--;
    }
}
