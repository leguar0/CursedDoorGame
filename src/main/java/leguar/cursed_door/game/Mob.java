package leguar.cursed_door.game;

import leguar.cursed_door.game.profession.*;

public class Mob {

    private final String name;
    private Hero hero;

    public Mob(String name, HeroType heroType, int power, double health, double energy) {
        this.name = name;
        switch (heroType) {
            case ARCHER -> hero = new Archer(power, health, energy);
            case BERSERKER -> hero = new Berserker(power, health, energy);
            case DRUID -> hero = new Druid(power, health, energy);
        }
    }

    public Hero getHero() {
        return hero;
    }

    public String getName() {
        return name;
    }
}
