package leguar.cursed_door.game.profession;

import javafx.scene.paint.Color;

public enum HeroType {
    ARCHER(1, "ŁUCZNIK", Color.LIGHTGOLDENRODYELLOW),
    BERSERKER(2, "BERSERKER", Color.ORANGERED),
    DRUID(3, "DRUID", Color.GREENYELLOW);

    private final int number;
    private final String name;
    private final Color color;

    HeroType(int number, String name, Color color) {
        this.number = number;
        this.name = name;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public static HeroType getHeroType(int number) {
        for (HeroType hero : HeroType.values()) {
            if (number == hero.getNumber()) {
                return hero;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
