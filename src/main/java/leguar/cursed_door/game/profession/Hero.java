package leguar.cursed_door.game.profession;

public abstract class Hero {
    private int power;
    private double health;
    private double healthMax;
    private double energy;
    private double energyMax;
    private final HeroType heroType;
    private double attackEnergyCost;

    public Hero(HeroType heroType, int power, double health, double energy) {
        this.heroType = heroType;
        this.power = power;
        this.health = health;
        this.energy = energy;

        healthMax = health;
        energyMax = energy;

        switch (heroType) {
            case DRUID -> attackEnergyCost = 5;
            case ARCHER -> attackEnergyCost = 7.5;
            case BERSERKER -> attackEnergyCost = 10;
        }
    }

    public int getPower() {
        return power;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double damage) {
        health -= damage;
    }


    public double getHealthMax() {
        return healthMax;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy -= energy;
    }

    public double getEnergyMax() {
        return energyMax;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public double getAttackEnergyCost() {
        return attackEnergyCost;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setHealthMax(double healthMax) {
        this.healthMax = healthMax;
    }

    public void setEnergyMax(double energyMax) {
        this.energyMax = energyMax;
    }

    public abstract void upgradeStat(String statName);

    public abstract double getAttack();
}
