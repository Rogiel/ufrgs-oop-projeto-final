package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.gamelogic.GameManager;
import br.ufrgs.dunjeonsdragons.model.GameCharacter;
import br.ufrgs.dunjeonsdragons.model.GameLevel;
import br.ufrgs.dunjeonsdragons.model.GameMap;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;

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
    private static final String SPELL_COMMAND = "spell";
    private static final String NEXT_LEVEL_COMMAND = "next-level";
    private static final String STATUS_COMMAND = "status";
    private static final String EXIT_COMMAND = "exit";

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

    public void start() throws IOException {
        while (true) {
            final String commandLine = reader.readLine();
            final StringTokenizer tokenizer = new StringTokenizer(commandLine);

            final String command = tokenizer.nextToken();
            switch (command) {
                case ATTACK_COMMAND:
                    handleAttack(tokenizer);
                    break;
                case SPELL_COMMAND:
                    handleSpell(tokenizer);
                    break;
                case NEXT_LEVEL_COMMAND:
                    nextLevel(tokenizer);
                    break;
                case STATUS_COMMAND:
                    handleStatus(tokenizer);
                    break;

                case EXIT_COMMAND:
                    return;

                default:
                    System.err.println("Unknown command: " + command);
                    break;
            }
        }
    }

    private void handleAttack(final StringTokenizer tokenizer) {
        final GameCharacter character = (GameCharacter) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        character.attack();
    }

    private void handleSpell(final StringTokenizer commandTokenizer) throws IOException {
        final String commandLine = reader.readLine();
        final int spellIndex = Integer.parseInt(commandLine);

//        switch (spellIndex) {
//
//        }
    }

    private void nextLevel(final StringTokenizer tokenizer) {
        final GameMap map = (GameMap) gameManager.getEntity(GameMap.DEFAULT_MAP_ENTITY_NAME);
        if(!map.getCurrentLevel().isComplete()) {
            System.err.println("Current level is not complete.");
            return;
        }
        map.nextLevel();
    }

    private void handleStatus(final StringTokenizer tokenizer) {
        final GameCharacter character = (GameCharacter) gameManager.getEntity(GamePlayer.DEFAULT_PLAYER_ENTITY_NAME);
        System.out.println("Character:");
        System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(character.getHealth()));
        System.out.println("\tEnergy: " + NumberFormat.getNumberInstance().format(character.getEnergy()));

        final GameLevel level = (GameLevel) gameManager.getEntity(GameLevel.DEFAULT_LEVEL_ENTITY_NAME);
        if(level != null) {
            final GameCharacter monster = level.getMonster();
            System.out.println("Monster:");
            if (monster == null) {
                System.out.println("\tNo monster on current level");
            } else {
                System.out.println("\tHealth: " + NumberFormat.getNumberInstance().format(monster.getHealth()));
                System.out.println("\tEnergy: " + NumberFormat.getNumberInstance().format(monster.getEnergy()));
            }
        }
    }

}
