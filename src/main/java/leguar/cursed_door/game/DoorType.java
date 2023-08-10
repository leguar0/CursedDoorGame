package leguar.cursed_door.game;

import javafx.scene.image.Image;

import java.util.Objects;

public enum DoorType {
    MYSTERIOUS(0, "/icon/mysterious.png", "mysteriousDoor"),
    MONSTROUS(1, "/icon/skull.png", "monstrousDoor"),
    TRAP(3, "/icon/trap.png", "trapDoor"),
    EMPTY(2, "emptyDoor");

    private Image image;
    private final int number;
    private final String styleColorDoor;

    DoorType(int number, String imagePath, String styleColorDoor) {
        this.number = number;
        this.image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString());
        this.styleColorDoor = styleColorDoor;
    }

    DoorType(int number, String styleColorDoor) {
        this.number = number;
        this.styleColorDoor = styleColorDoor;
    }

    public Image getImage() {
        return image;
    }

    public String getStyleColorDoor() {
        return styleColorDoor;
    }

    public static DoorType getImage(int number) {
        for (DoorType doorType : DoorType.values()) {
            if (doorType.getNumber() == number) {
                return doorType;
            }
        }
        return null;
    }

    public int getNumber() {
        return number;
    }
}
