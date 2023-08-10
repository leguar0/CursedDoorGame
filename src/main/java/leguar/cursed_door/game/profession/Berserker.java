package leguar.cursed_door.game.profession;

public class Berserker extends Hero {

    public Berserker(int power, double health, double energy) {
        super(HeroType.BERSERKER, power, health, energy);
    }

    @Override
    public void upgradeStat(String statName) {
        switch (statName) {
            case "POWER" -> super.setPower(getPower() + 1);
            case "HEALTH" -> super.setHealthMax(getHealthMax() + 20);
            case "ENERGY" -> super.setEnergyMax(getEnergyMax() + 30);
        }
    }

    @Override
    public double getAttack() {
        setEnergy(getAttackEnergyCost());
        return (Math.random() * 50 + 1) * getPower() / 10;
    }
}
