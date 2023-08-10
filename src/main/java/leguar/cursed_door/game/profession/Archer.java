package leguar.cursed_door.game.profession;

public class Archer extends Hero {

    public Archer(int power, double health, double energy) {
        super(HeroType.ARCHER, power, health, energy);
    }

    @Override
    public void upgradeStat(String statName) {
        switch (statName) {
            case "POWER" -> super.setPower(getPower() + 3);
            case "HEALTH" -> super.setHealthMax(getHealthMax() + 25);
            case "ENERGY" -> super.setEnergyMax(getEnergyMax() + 15);
        }
    }

    @Override
    public double getAttack() {
        setEnergy(getAttackEnergyCost());
        return (Math.random() * 50 + 1) * getPower() / 10;
    }
}
