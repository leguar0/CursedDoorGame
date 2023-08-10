package leguar.cursed_door.game.profession;

public class Druid extends Hero {

    public Druid(int power, double health, double energy) {
        super(HeroType.DRUID, power, health, energy);
    }

    @Override
    public void upgradeStat(String statName) {
        switch (statName) {
            case "POWER" -> super.setPower(getPower() + 5);
            case "HEALTH" -> super.setHealthMax(getHealthMax() + 15);
            case "ENERGY" -> super.setEnergyMax(getEnergyMax() + 10);
        }
    }

    @Override
    public double getAttack() {
        setEnergy(getAttackEnergyCost());
        return (Math.random() * 50 + 1) * getPower() / 10 * 0.5;
    }
}
