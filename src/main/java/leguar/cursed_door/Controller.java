package leguar.cursed_door;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import leguar.cursed_door.game.DoorType;
import leguar.cursed_door.game.Game;
import leguar.cursed_door.game.Player;
import leguar.cursed_door.game.profession.Hero;
import leguar.cursed_door.game.profession.HeroType;

public class Controller {

    // TITLEBAR
    @FXML
    private Button minimalizeButton;


    // MENUBAR
    @FXML
    private GridPane menuBar;
    @FXML
    private Button exitButton;

    // CHOICEPROFESSIONBAR
    @FXML
    private GridPane choiceProfessionBar;

    // GAMEBAR
    @FXML
    private GridPane gameBar;
    @FXML
    private ImageView rightDoor;
    @FXML
    private ImageView leftDoor;
    @FXML
    private GridPane leftDoorStyle;
    @FXML
    private GridPane rightDoorStyle;
    @FXML
    private Label labelCoins;
    @FXML
    private Label labelKeys;
    @FXML
    private Label labelProfession;
    @FXML
    private Label labelDoorCounter;
    @FXML
    private Label labelPower;
    @FXML
    private Label labelHealth;
    @FXML
    private Label labelEnergy;
    @FXML
    private Label labelText;
    @FXML
    private Label labelMenu;


    // FIGHTBAR
    @FXML
    private GridPane fightBar;
    @FXML
    private Label statsPlayer;
    @FXML
    private Label statsMob;
    @FXML
    private Label roundCounter;

    private Game game;

    public void setStartGameButton() {
        menuBar.setVisible(false);

        choiceProfessionBar.setVisible(true);
    }

    public void setMinimalizeButton() {
        Stage stage = (Stage) minimalizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void setExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setExitGameButton() {
        Platform.exit();
    }

    public void setChoiceArcherButton() {
        game = new Game(new Player(HeroType.ARCHER));
        startGame();
    }

    public void setChoiceBerserkButton() {
        game = new Game(new Player(HeroType.BERSERKER));
        startGame();
    }

    public void setChoiceDruidButton() {
        game = new Game(new Player(HeroType.DRUID));
        startGame();
    }

    public void startGame() {
        choiceProfessionBar.setVisible(false);

        gameBar.setVisible(true);
        setGameBar();
        setDoor();
    }

    public void setGameBar() {
        labelCoins.setText("Monety: " + game.getPlayer().getMoney());
        labelKeys.setText("Klucze: " + game.getPlayer().getKeys());
        labelProfession.setTextFill(game.getPlayer().getHero().getHeroType().getColor());
        labelProfession.setText(game.getPlayer().getHero().getHeroType().getName());
        labelDoorCounter.setText("Numer pokoju: " + game.getLevelCounter() + "/" + game.getMaxLevelDoor());
        labelPower.setText("Moc:\n" + game.getPlayer().getHero().getPower());
        labelHealth.setText("Punkty zdrowia:\n" + String.format("%.2f", game.getPlayer().getHero().getHealth()) + "/" + game.getPlayer().getHero().getHealthMax());
        labelEnergy.setText("Energia:\n" + String.format("%.2f", game.getPlayer().getHero().getEnergy()) + "/" + game.getPlayer().getHero().getEnergyMax());
    }

    public void setUpgradePower() {
        upgradeStat("POWER");
    }

    public void setUpgradeHealth() {
        upgradeStat("HEALTH");
    }

    public void setUpgradeEnergy() {
        upgradeStat("ENERGY");
    }

    private void upgradeStat(String statName) {
        Player player = game.getPlayer();

        if (player.getMoney() >= player.getCost(statName)) {
            player.getHero().upgradeStat(statName);
            player.updateCost(statName, player.getCost(statName));
            labelText.setText("Wybierz drzwi\nZakupiono: " + statName);
        } else {
            labelText.setText("Wybierz drzwi\nNie masz wystarczającej\n ilosci monet");
        }
        setGameBar();
    }

    public void setLeftDoor() {
        DoorType door = game.getLeftDoor();
        nextLevel(door);
    }

    public void setRightDoor() {
        DoorType door = game.getRightDoor();
        nextLevel(door);
    }

    private void nextLevel(DoorType door) {
        switch (door) {
            case MYSTERIOUS -> {
                if (!game.mysteriousDoor()) {
                    labelText.setText("Nie posiadacz kluczy");
                    return;
                }
            }
            case TRAP -> game.trapDoor();
            case MONSTROUS -> updateFightBar();
        }
        if (game.isAlive()) {
            game.addLevel();
            setDoor();
            setGameBar();

            if (game.isFinish()) {
                labelMenu.setText("Gratulacje! \nUkończyłes grę!");
            } else {
                return;
            }
        } else {
            labelMenu.setText("Przegrałeś!");
        }

        gameBar.setVisible(false);
        menuBar.setVisible(true);
    }

    private void setDoor() {
        game.randomDoor();

        leftDoor.setImage(game.getLeftDoor().getImage());
        leftDoorStyle.getStyleClass().setAll("door", game.getLeftDoor().getStyleColorDoor());
        leftDoor.setVisible(true);

        rightDoor.setImage(game.getRightDoor().getImage());
        rightDoorStyle.getStyleClass().setAll("door", game.getRightDoor().getStyleColorDoor());
        rightDoor.setVisible(true);
    }

    private void updateFightBar() {
        game.setRoundCounter();
        gameBar.setVisible(false);
        fightBar.setVisible(true);

        game.setMobIsAlive();

        Thread fightThread = new Thread(() -> {
            do {
                Platform.runLater(() -> {
                    setStatsPlayer();
                    setStatsMob();
                    roundCounter.setText("Runda: " + game.getRoundCounter());
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                game.fight();
            } while (game.isAlive() && game.mobIsAlive());

            Platform.runLater(() -> {
                if (!game.isAlive()) {
                    fightBar.setVisible(false);
                    menuBar.setVisible(true);
                    labelMenu.setText("Przegrałeś!");
                }

                if (!game.mobIsAlive()) {
                    fightBar.setVisible(false);
                    setGameBar();
                    gameBar.setVisible(true);
                }
            });
        });

        fightThread.start();
    }

    private void setStatsPlayer() {
        Hero player = game.getPlayer().getHero();
        statsPlayer.setText(player.getHeroType().getName() + "\n" + "MOC: " + player.getPower() + "\n" + "ZDROWIE: " + String.format("%.2f", player.getHealth()) + "\n" + "ENERGIA: " + String.format("%.2f", player.getEnergy()));
    }

    private void setStatsMob() {
        Hero mob = game.getMobs().get(game.getLevelCounter()).getHero();
        statsMob.setText(game.getMobs().get(game.getLevelCounter()).getName() + "\n" + mob.getHeroType().getName() + "\n" + "MOC: " + mob.getPower() + "\n" + "ZDROWIE: " + String.format("%.2f", mob.getHealth()) + "\n" + "ENERGIA: " + String.format("%.2f", mob.getEnergy()));
    }
}
