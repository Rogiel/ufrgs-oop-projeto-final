package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * Created by Rogiel on 12/1/16.
 */
public class GameUIController {

    private static final String ATTACK_COMMAND = "attack";
    private static final String NEXT_LEVEL_COMMAND = "next-level";
    private static final String STATUS_COMMAND = "status";
    private static final String EXIT_COMMAND = "exit";
    private static final String SHOW_EXPERIENCE = "experience";
    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A buffered reader that reads from stdin, used to read complete lines from the
     * terminal to get user input
     */
    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in)
    );

    /**
     * The game manager this controller is currently managing
     */
    private GameManager gameManager;

    public GameUIController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public boolean handleUserInput() throws IOException {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (map != null && map.getState() != GameMap.State.RUNNING) {
            System.out.println("Game over.");
            return true;
        }

        final String commandLine = reader.readLine();
        final StringTokenizer tokenizer = new StringTokenizer(commandLine);

        final String command = tokenizer.nextToken();
        switch (command) {
            case ATTACK_COMMAND:
                handleAttack(tokenizer);
                gameManager.performTurn();

                break;

            case NEXT_LEVEL_COMMAND:
                nextLevel(tokenizer);
//                gameManager.performTurn();

                break;
            case STATUS_COMMAND:
                handleStatus(tokenizer);
                break;

            case SHOW_EXPERIENCE:
                return false;

            case EXIT_COMMAND:
                return false;

            default:
                System.err.println("Unknown command: " + command);
                break;
        }

        return false;
    }

    private void handleAttack(final StringTokenizer tokenizer) {
        final GameCharacter character = (GameCharacter) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        character.attack();
    }

    private void nextLevel(final StringTokenizer tokenizer) {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if (!map.getCurrentLevel().isComplete()) {
            System.err.println("Current level is not complete.");
            return;
        }
        map.nextLevel();
    }

    private void handleStatus(final StringTokenizer tokenizer) {
        final GamePlayer character = (GamePlayer) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character: " + character.getName() + ", Race: " + character.getRaceTemplate().getName() + ", Classe: " + character.getClassTemplate().getName());
        System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(character.getHealth()));
        System.out.println("\tDamage: " + NumberFormat.getNumberInstance().format(character.getDamage()));

        final GameLevel level = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        if (level != null) {
            final GameMonster monster = level.getMonster();
            System.out.println("Monster: " + monster.getName());
            if (monster == null) {
                System.out.println("\tNo monster on current level");
            } else {
                System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(monster.getHealth()));
                System.out.println("\tDamage: " + monster.getDamage());
            }
        }
    }

}
